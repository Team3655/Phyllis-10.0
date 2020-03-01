package frc.robot.event.customevents;

import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkMax;

import frc.robot.Robot;
import frc.robot.event.Event;

public class DriveEvent extends Event{
    private static double rotationsPerMeter=1;
    CANSparkMax fr = Robot.getInstance().getDriveRight();
    CANSparkMax br = Robot.getInstance().getDriveRearRight();
    CANSparkMax fl = Robot.getInstance().getDriveLeft();
    CANSparkMax bl = Robot.getInstance().getDriveRearLeft();
    int state=0;

    private double rotations;

    public DriveEvent(double length){
       super();
        rotations=length/rotationsPerMeter;
    }

    public DriveEvent(double length, long delay){
        super(delay);
        rotations=length/rotationsPerMeter;
    }

    @Override
    public void task(){
        switch(state){
            case 0:
                fl.getPIDController().setOutputRange(Robot.getInstance().getTuningValue("drive")*-1, Robot.getInstance().getTuningValue("drive"));
                fr.getPIDController().setOutputRange(Robot.getInstance().getTuningValue("drive")*-1, Robot.getInstance().getTuningValue("drive"));
                bl.getPIDController().setOutputRange(Robot.getInstance().getTuningValue("drive")*-1, Robot.getInstance().getTuningValue("drive"));
                br.getPIDController().setOutputRange(Robot.getInstance().getTuningValue("drive")*-1, Robot.getInstance().getTuningValue("drive"));
                fl.getPIDController().setP(Robot.getInstance().getTuningValue("driveP"));
                fr.getPIDController().setP(Robot.getInstance().getTuningValue("driveP"));
                bl.getPIDController().setP(Robot.getInstance().getTuningValue("driveP"));
                br.getPIDController().setP(Robot.getInstance().getTuningValue("driveP"));
                
                //set positions
                //bl.getPIDController().setReference(value, ctrl)
                state++;
            break;
            case 1:
            break;
        }
    }

    @Override
    public boolean eventCompleteCondition(){
        return true; 
    }

    /*private double averagePos(){
        return (Robot.getInstance())
    }*/

    /**Sets the rotation per meter based on diameter and gear ratio
     * 
     * @param diameter diameter (meters)
     * @param gearRatio gear ratio, g motor rotations: 1 wheel rotation
     */
    public static void configure(double diameter, double gearRatio){
        rotationsPerMeter=diameter*Math.PI*gearRatio;
    }
}
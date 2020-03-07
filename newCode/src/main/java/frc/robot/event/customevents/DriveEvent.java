package frc.robot.event.customevents;

import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
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
        rotations=length*rotationsPerMeter;
        System.out.println("Target: "+rotations);
    }

    public DriveEvent(double length, long delay){
        super(delay);
        rotations=length*rotationsPerMeter;
        System.out.println("Target: "+rotations);
    }

    @Override
    public void task(){
        switch(state){
            case 0:
                fl.getPIDController().setOutputRange(Robot.getInstance().getTuningValue("drive")*-1, Robot.getInstance().getTuningValue("drive"),1);
                fr.getPIDController().setOutputRange(Robot.getInstance().getTuningValue("drive")*-1, Robot.getInstance().getTuningValue("drive"),1);
                bl.getPIDController().setOutputRange(Robot.getInstance().getTuningValue("drive")*-1, Robot.getInstance().getTuningValue("drive"),1);
                br.getPIDController().setOutputRange(Robot.getInstance().getTuningValue("drive")*-1, Robot.getInstance().getTuningValue("drive"),1);
                fl.getPIDController().setP(Robot.getInstance().getTuningValue("driveP"),1);
                fr.getPIDController().setP(Robot.getInstance().getTuningValue("driveP"),1);
                bl.getPIDController().setP(Robot.getInstance().getTuningValue("driveP"),1);
                br.getPIDController().setP(Robot.getInstance().getTuningValue("driveP"),1);
                fl.getPIDController().setI(Robot.getInstance().getTuningValue("driveI"),1);
                fr.getPIDController().setI(Robot.getInstance().getTuningValue("driveI"),1);
                bl.getPIDController().setI(Robot.getInstance().getTuningValue("driveI"),1);
                br.getPIDController().setI(Robot.getInstance().getTuningValue("driveI"),1);
                fl.getPIDController().setFF(Robot.getInstance().getTuningValue("driveFF"));
                fr.getPIDController().setFF(Robot.getInstance().getTuningValue("driveFF"));
                bl.getPIDController().setFF(Robot.getInstance().getTuningValue("driveFF"));
                br.getPIDController().setFF(Robot.getInstance().getTuningValue("driveFF"));
                
                fl.getEncoder().setPosition(0);
                fr.getEncoder().setPosition(0);
                bl.getEncoder().setPosition(0);
                br.getEncoder().setPosition(0);

                //set positions
                fl.getPIDController().setReference(-rotations, ControlType.kPosition,1);
                fr.getPIDController().setReference(rotations, ControlType.kPosition,1);
                bl.getPIDController().setReference(-rotations, ControlType.kPosition,1);
                br.getPIDController().setReference(rotations, ControlType.kPosition,1);
                
                
                state++; //this only needs to be done once per event;
            break;
            
            /*case 0:
                fl.set(-.3);
                fr.set(.3);
                bl.set(-.3);
                br.set(.3);
                state++;
            break;*/
            /*case 1:
                state++;
            break;*/
        }
    }

    @Override
    public boolean eventCompleteCondition(){
        //return false;
        //System.out.println("averagePos() "+averagePos());
        return averagePos()>rotations-.05&&averagePos()<rotations+.05;//if it has made it to the position 
    }
    @Override
    public void endTask(){
        fl.set(0);
        fr.set(0);
        bl.set(0);
        br.set(0);
        //turn off motors to prepare for next instructions;
    }

    private double averagePos(){

        double aPos= (-fl.getEncoder().getPosition()-bl.getEncoder().getPosition()+fr.getEncoder().getPosition()+br.getEncoder().getPosition())/4;
        System.out.println("Average Pos"+aPos);
        return aPos;
    }

    /**Sets the rotation per meter based on diameter and gear ratio
     * 
     * @param diameter diameter (meters)
     * @param gearRatio gear ratio, g motor rotations: 1 wheel rotation
     */
    public static void configure(double diameter, double gearRatio){
        rotationsPerMeter=diameter*Math.PI*gearRatio;
    }
}
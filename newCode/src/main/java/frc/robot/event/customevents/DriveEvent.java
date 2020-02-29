package frc.robot.event.customevents;

import frc.robot.Robot;
import frc.robot.event.Event;

public class DriveEvent extends Event{
    private static double rotationsPerMeter=1;


    private double rotations;
    public DriveEvent(double length){
       super();
        rotations=length/.603;
    }

    public DriveEvent(double length, long delay){
        super(delay);
        rotations=length/.603;
    }

    @Override
    public void task(){
       // Robot.getInstance().getDriveLeft().getPIDController().setOutputRange(min, max)
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
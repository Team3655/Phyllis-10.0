package frc.robot.event.customevents;

import frc.robot.Robot;
import frc.robot.event.Event;

public class Drive extends Event{

    private double rotations;
    public Drive(double length){
       super();
        rotations=length/.603;
    }

    public Drive(double length, long delay){
        super(delay);
        rotations=length/.603;
    }

    @Override
    public void task(){
        Robot.getInstance().getDriveLeft().getPIDController().setOutputRange(min, max)
    }
}
package frc.robot.event.customevents;

import frc.robot.Robot;
import frc.robot.event.Event;

public class TurretEvent extends Event{
    private double rotations;

    
    public TurretEvent(int degrees){
        super();
        rotations=degrees/360;
    }


    public void task(){
        Robot.getInstance().turret.setPos(rotations);
    }
}
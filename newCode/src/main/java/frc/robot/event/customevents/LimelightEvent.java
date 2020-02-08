package frc.robot.event.customevents;

import frc.robot.Robot;
import frc.robot.event.Event;

public class LimelightEvent extends Event {
    private final double P = .01;

    public LimelightEvent(){
        super();
    }

    @Override
    public void task(){
        //error will equal the angle of x the limelight returns
        //set motor speed as P times error
        Robot.getInstance().centerIntakeBack().set(P*Robot.getInstance().getLimelight().getX());
        //Robot.getInstance().turret().set(P*Robot.getInstance().getLimelight().getX());
    }

    @Override
    public boolean eventCompleteCondition(){
        return Robot.getInstance().isEnabled();
    }
}

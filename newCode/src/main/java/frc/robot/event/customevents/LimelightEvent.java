package frc.robot.event.customevents;

import frc.robot.Robot;
import frc.robot.event.Event;

public class LimelightEvent extends Event {
    private final double P = .01;
    private boolean enabled;
    private boolean terminated;
    private double max=.2;
    public LimelightEvent(){
        super();
    }

    @Override
    public void task(){
        if (enabled){
            double power=P*Robot.getInstance().getLimelight().getX();
            if (Math.abs(power)>.2){
                power=max*Math.abs(power)/power;
            }
            //error will equal the angle of x the limelight returns
            //set motor speed as P times error
            Robot.getInstance().turret().set(power);
            //Robot.getInstance().turret().set(P*Robot.getInstance().getLimelight().getX());
        }
    }

    @Override
    public boolean eventCompleteCondition(){
        return false;//Robot.getInstance().isEnabled();//should run until robot is turned off
    }

    public void enable(){
        enabled=true;
    }

    public void disable(){
        enabled=false;
    }
}

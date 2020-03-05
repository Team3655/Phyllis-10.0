package frc.robot.event.customevents;

import frc.robot.Robot;
import frc.robot.event.Event;

public class LimelightEvent extends Event {
    private final double P = .1;
    private boolean enabled;
    private boolean terminated;
    private double max=.2;
    public LimelightEvent(){
        super();
    }

    @Override
    public void task(){
        if (enabled){
            double power=P*Robot.getInstance().getLimelight().getY();
            if (Math.abs(power)>max){
                power=max*Math.abs(power)/power;
            }
            //error will equal the angle of y the limelight returns
            //set motor speed as P times error
            Robot.getInstance().turret().set(power);
            Robot.eHandler.triggerEvent(new PrintEvent("Doing limelight yams."+Robot.getInstance().turret().getEncoder().getPosition()));
            //Robot.getInstance().turret().set(P*Robot.getInstance().getLimelight().getX());

            double h = 2; //distance between turret and target (height)
            double d = h/Math.tan(Robot.getInstance().getLimelight().getX()); //distance between turret and target (length)
            double t = Math.sqrt(19.6*h)/9.8; //time for power cell to travel from turret to target
            double v = Math.sqrt(Math.pow(d/t, 2)+19.6*h); //velocity of power cell
            double angle = Math.sinh(Math.sqrt(19.6*h)/v); //elevation angle relative to floor
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

    public void setEnabled(boolean enabled){
        this.enabled=enabled;
    }

    public boolean isEnabled(){
        return enabled;
    }
}

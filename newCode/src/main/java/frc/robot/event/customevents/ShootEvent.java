package frc.robot.event.customevents;

import com.revrobotics.ControlType;

import frc.robot.Robot;
import frc.robot.event.Event;

public class ShootEvent extends Event{
    
    double velocity;
    int state;

    public ShootEvent(double velocity,long time){
        super(0,time);
        if (Math.abs(velocity)>6000){
            velocity=6000*Math.abs(velocity)/velocity;
        }

        this.velocity=velocity;
    }

    public ShootEvent(double velocity,long time,long delay){
        super(delay,time);
        if (Math.abs(velocity)>6000){
            velocity=6000*Math.abs(velocity)/velocity;
        }

        this.velocity=velocity;
    }

    @Override
    public void task(){
        switch (state){
            case 0:
                Robot.getInstance().leftShooterWheel().getPIDController().setReference(-velocity, ControlType.kVelocity);
                Robot.getInstance().leftShooterWheel().getPIDController().setReference(velocity, ControlType.kVelocity);
            break;
        }
        state++;

    }

    @Override
    public boolean eventCompleteCondition() {
        return state==2;
    }

    @Override
    public void endTask(){
        Robot.getInstance().leftShooterWheel().set(0);
        Robot.getInstance().rightShooterWheel().set(0);
    }
}
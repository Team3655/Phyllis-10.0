package frc.robot.event.customevents;

import com.revrobotics.ControlType;

import frc.robot.Robot;
import frc.robot.event.Event;

public class ShootEvent extends Event{
    private Robot robot;    
    double velocity;
    int state;
    /*
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
    }*/

    public ShootEvent(double velocity,long delay){
        super(delay);
        if (Math.abs(velocity)>6000){
            velocity=6000*Math.abs(velocity)/velocity;
        }

        this.velocity=velocity;
    }

    @Override
    public void task(){
        switch (state){
            case 0:
                Robot.getInstance().leftShooterWheel().getPIDController().setReference(Robot.getInstance().getTuningValue("shoot")*-1, ControlType.kVelocity);
                Robot.getInstance().rightShooterWheel().getPIDController().setReference(Robot.getInstance().getTuningValue("shoot"), ControlType.kVelocity);
                Robot.getInstance().meteringWheel().getPIDController().setReference(Robot.getInstance().getTuningValue("meteringWheel")*-1,ControlType.kVelocity);
                //robot.meteringWheel().getPIDController().setReference(robot.getTuningValue("meteringWheel")*-1,ControlType.kVelocity);
                //robot.leftShooterWheel().getPIDController().setReference(robot.getTuningValue("shoot")*-1, ControlType.kVelocity);
                //robot.rightShooterWheel().getPIDController().setReference(robot.getTuningValue("shoot"), ControlType.kVelocity);
                //robot.verticalLoader().set(robot.getTuningValue("verticalIntake")*-1);
            break;
        }
        state++;

    }

    @Override
    public boolean eventCompleteCondition() {
        return true;//state==2;
    }

    @Override
    public void endTask(){
        //Robot.getInstance().leftShooterWheel().set(0);
        //Robot.getInstance().rightShooterWheel().set(0);
    }
}
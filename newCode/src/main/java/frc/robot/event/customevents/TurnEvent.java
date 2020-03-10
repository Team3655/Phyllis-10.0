package frc.robot.event.customevents;

import com.revrobotics.CANSparkMax;

import frc.robot.Robot;
import frc.robot.event.Event;

public class TurnEvent extends Event {
    
    double maxOutput;
    double target;
    static double P=1/180;
    CANSparkMax fr = Robot.getInstance().getDriveRight();
    CANSparkMax br = Robot.getInstance().getDriveRearRight();
    CANSparkMax fl = Robot.getInstance().getDriveLeft();
    CANSparkMax bl = Robot.getInstance().getDriveRearLeft();
    double deadband=.05;

    public TurnEvent(double power, double angle){
        super(0,0);
        maxOutput=power;
        target=angle;
    }

    public TurnEvent(double power,double angle,long delay){
        super(delay,0);
        maxOutput=power;
        target=angle;
    }

    @Override
    public void task(){
        double power=P*(Robot.getInstance().gyro().getAngle()-target);
        if (Math.abs(power)>maxOutput){
            power=maxOutput*Math.abs(power)/power;
        }
        System.out.println("power:"+power);
        fl.set(power);
        fr.set(power);
        bl.set(power);
        br.set(power);
    }

    @Override
    public boolean eventCompleteCondition(){
        double angle=Robot.getInstance().gyro().getAngle();
        return angle>target-deadband && angle<target+deadband;
    }

    @Override
    public void endTask(){
        fl.set(0);
        fr.set(0);
        bl.set(0);
        br.set(0);
    }
}
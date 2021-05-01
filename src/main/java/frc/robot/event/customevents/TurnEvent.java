package frc.robot.event.customevents;

import com.revrobotics.CANSparkMax;

import frc.robot.Robot;
import frc.robot.event.Event;

public class TurnEvent extends Event {
    
    double maxOutput;
    double target;
    static double P=1;
    CANSparkMax fr = Robot.getInstance().getDriveRight();
    CANSparkMax br = Robot.getInstance().getDriveRearRight();
    CANSparkMax fl = Robot.getInstance().getDriveLeft();
    CANSparkMax bl = Robot.getInstance().getDriveRearLeft();
    double deadband=0.1;

    public TurnEvent(double power, double angle){
        super(0,0);
        maxOutput=power;
        target=Robot.getInstance().gyro().getAngle()+angle;
        //System.out.println(angle);
        //System.out.println(Robot.getInstance().gyro().getAngle());
        //System.out.println(target);
        //System.out.println(deadband);
    }

    public TurnEvent(double power,double angle,long delay){
        super(delay,0);
        maxOutput=power;
        target=Robot.getInstance().gyro().getAngle()+angle;
    }

    @Override
    public void task(){
        //System.out.println("angle:"+(-Robot.getInstance().gyro().getAngle()));
        double power=-P*(Robot.getInstance().gyro().getAngle()-target);
        if (Math.abs(power)>maxOutput){
            power=maxOutput*Math.abs(power)/power;
        }
        //System.out.println("power:"+power);
        fl.set(power);
        fr.set(power);
        bl.set(power);
        br.set(power);
        //System.out.println(Robot.getInstance().gyro().getAngle());
    }

    @Override
    public boolean eventCompleteCondition(){
        double checkAngle=Robot.getInstance().gyro().getAngle();
        return checkAngle>target-deadband && checkAngle<target+deadband;
    }

    @Override
    public void endTask(){
        fl.set(0);
        fr.set(0);
        bl.set(0);
        br.set(0);
    }
}
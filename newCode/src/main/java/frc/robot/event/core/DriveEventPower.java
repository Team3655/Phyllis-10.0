package frc.robot.event.core;

import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.Spark;
import frc.robot.Robot;
import frc.robot.event.Event;

public class DriveEventPower extends Event{
    //private static double rotationsPerMeter=1;
    CANSparkMax fr = Robot.getInstance().getDriveRight();
    CANSparkMax br = Robot.getInstance().getDriveRearRight();
    CANSparkMax fl = Robot.getInstance().getDriveLeft();
    CANSparkMax bl = Robot.getInstance().getDriveRearLeft();

    double power;

    public DriveEventPower(double power){
        super();
        this.power=power;
    }

    public DriveEventPower(double power,long delay){
        super(delay);
        this.power=power;
    }

    @Override
    public void task(){
        fl.set(power);
        fr.set(-power);
        bl.set(-power);
        br.set(power);
    }

}
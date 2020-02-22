package frc.robot.motors;

import com.revrobotics.CANSparkMax;

import frc.robot.Robot;

public class Turret extends CANSparkMax{
    double defaultMaxSpeed;

	public Turret(int deviceID, MotorType type) {
        super(deviceID, type);
        defaultMaxSpeed=Robot.getInstance().getTuningValue("turretDefaultMaxSpeed");
    }

    @Override
    public void set(double speed){

    }

    /*public void setPos(double pos){
        if (pos>1){
            pos=
        }
    }*/

    
}
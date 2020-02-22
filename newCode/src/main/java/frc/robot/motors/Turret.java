package frc.robot.motors;

import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;

import frc.robot.Robot;

public class Turret extends Neo550{
    double defaultMaxSpeed;
    double target=0;//the target position

	public Turret(int deviceID) {
        super(deviceID);
        defaultMaxSpeed=Math.abs(Robot.getInstance().getTuningValue("turretDefaultMaxSpeed"));
    }

    @Override
    public void set(double speed){
        if (speed>0){
            setPos(1,speed);
        } else if (speed<0){
            setPos(0,speed);
        } else {
            super.set(0);
        }
    }

    public void setPos(double pos){
        if (pos>1){
            pos-=Math.floor(pos);
        } else if (pos<0){
            pos=1-Math.ceil(pos);
        }
        getPIDController().setOutputRange(defaultMaxSpeed*-1, defaultMaxSpeed);
        getPIDController().setReference(pos, ControlType.kPosition);
        target=pos;
    }

    public void setPos(double pos,double maxSpeed){
        maxSpeed=Math.abs(maxSpeed);
        if (pos>1){
            pos-=Math.floor(pos);
        } else if (pos<0){
            pos=1-Math.ceil(pos);
        }
        getPIDController().setOutputRange(maxSpeed*-1, maxSpeed);
        getPIDController().setReference(pos, ControlType.kPosition);
        target=pos;
    }

    public void setPos(double pos,double minSpeed,double maxSpeed){
        maxSpeed=Math.abs(maxSpeed);
        minSpeed=Math.abs(minSpeed)*-1;
        if (pos>1){
            pos-=Math.floor(pos);
        } else if (pos<0){
            pos=1-Math.ceil(pos);
        }
        getPIDController().setOutputRange(minSpeed, maxSpeed);
        getPIDController().setReference(pos, ControlType.kPosition);
        target=pos;
    }

    public double getTargetPos(){
        return target;
    }

    
}
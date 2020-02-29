package frc.robot.motors;

import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;

import frc.robot.Robot;
import frc.robot.event.Event;

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
            setPos(-1,speed);
        } else {
            super.set(0);
        }
        //super.set(speed);//for testing
    }

    public void setPos(double pos){
        if (pos>1){
            pos-=Math.floor(pos);
            pos*=Robot.getInstance().getTuningValue("turretMaxPos");
        } else if (pos<1){
            pos=-1+Math.ceil(pos);
            pos*=Robot.getInstance().getTuningValue("turretMinPos");
        } else if (pos>0){
            pos*=Robot.getInstance().getTuningValue("turretMaxPos");
        } else if (pos<0){
            pos*=Robot.getInstance().getTuningValue("turretMinPos");
        }
        getPIDController().setOutputRange(defaultMaxSpeed*-1, defaultMaxSpeed);
        getPIDController().setReference(pos, ControlType.kPosition);
        target=pos;
    }

    public void setPos(double pos,double maxSpeed){
        maxSpeed=Math.abs(maxSpeed);
        if (pos>1){
            pos-=Math.floor(pos);
            pos*=Robot.getInstance().getTuningValue("turretMaxPos");
        } else if (pos<1){
            pos=-1+Math.ceil(pos);
            pos*=Robot.getInstance().getTuningValue("turretMinPos");
        } else if (pos>0){
            pos*=Robot.getInstance().getTuningValue("turretMaxPos");
        } else if (pos<0){
            pos*=Robot.getInstance().getTuningValue("turretMinPos");
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
            pos*=Robot.getInstance().getTuningValue("turretMaxPos");
        } else if (pos<1){
            pos=-1+Math.ceil(pos);
            pos*=Robot.getInstance().getTuningValue("turretMinPos");
        } else if (pos>0){
            pos*=Robot.getInstance().getTuningValue("turretMaxPos");
        } else if (pos<0){
            pos*=Robot.getInstance().getTuningValue("turretMinPos");
        }
        getPIDController().setOutputRange(minSpeed, maxSpeed);
        getPIDController().setReference(pos, ControlType.kPosition);
        target=pos;
    }

    public double getTargetPos(){
        return target;
    }

    /*private class setEvent extends Event {
        double spd;
        public setEvent(double spd){
            this.spd=spd;
        }
    }*/

    
}
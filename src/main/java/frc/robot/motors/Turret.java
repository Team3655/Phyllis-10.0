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
        getPIDController().setP(1);
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
        pos=getRestrictedPos(pos);
        getPIDController().setOutputRange(defaultMaxSpeed*-1, defaultMaxSpeed);
        getPIDController().setReference(pos, ControlType.kPosition);
        target=pos;
        System.out.println(target);
    }

    public void setPos(double pos,double maxSpeed){
        maxSpeed=Math.abs(maxSpeed);
        target=pos;
        pos=getRestrictedPos(pos);
        getPIDController().setOutputRange(maxSpeed*-1, maxSpeed);
        getPIDController().setReference(pos, ControlType.kPosition);
        
        //System.out.println(target);
    }

    public void setPos(double pos,double minSpeed,double maxSpeed){
        maxSpeed=Math.abs(maxSpeed);
        minSpeed=Math.abs(minSpeed)*-1;
        target=pos;
        pos=getRestrictedPos(pos);
        getPIDController().setOutputRange(minSpeed, maxSpeed);
        getPIDController().setReference(pos, ControlType.kPosition);
    }

    public double getTargetPos(){
        return target;
    }

    public double getPosition(){
        double pos=getEncoder().getPosition();
        if (pos>0){
            pos/=Robot.getInstance().getTuningValue("turretMaxPos");
        } else if (pos<0){
            pos/=Robot.getInstance().getTuningValue("turretMinPos")*-1;
        }

        return pos;
    }

    public double getRestrictedPos(double unrestrictedPos){
        if (unrestrictedPos>1){
            unrestrictedPos-=Math.floor(unrestrictedPos);
            unrestrictedPos*=Robot.getInstance().getTuningValue("turretMaxPos");
        } else if (unrestrictedPos<-1){
            unrestrictedPos=-1+Math.ceil(unrestrictedPos);
            unrestrictedPos*=Robot.getInstance().getTuningValue("turretMinPos");
        } else if (unrestrictedPos>0){
            unrestrictedPos*=Robot.getInstance().getTuningValue("turretMaxPos");
        } else if (unrestrictedPos<0){
            unrestrictedPos*=Robot.getInstance().getTuningValue("turretMinPos")*-1;
        }
        return unrestrictedPos;
    }

    public void holdPos(){
        setPos(getEncoder().getPosition());
    }
    
    /*private class setEvent extends Event {
        double spd;
        public setEvent(double spd){
            this.spd=spd;
        }
    }*/

    
}
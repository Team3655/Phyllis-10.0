package frc.robot.event.customevents;

import frc.robot.Robot;
import frc.robot.event.Event;

public class ClimbArmEvent extends Event {
    double speed;
    int state=0;
    
    public ClimbArmEvent(double speed,long time){
        super(0,time);
        this.speed=speed;
    }

    public ClimbArmEvent(double speed,long time,long delay){
        super(delay,time);
        this.speed=speed;
    }

    @Override
    public void task(){
        System.out.println("climb arm event");
        switch(state){
            case 0:
                Robot.getInstance().climbArm().set(speed);
            break;
            case 1:
                Robot.getInstance().climbArm().set(0);
            break;
        }
        state++;
    }
    @Override
    public boolean eventCompleteCondition(){
        return state==2;
    }


}
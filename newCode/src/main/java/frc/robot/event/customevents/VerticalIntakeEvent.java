package frc.robot.event.customevents;

import com.revrobotics.ControlType;

import frc.robot.Robot;
import frc.robot.event.Event;

public class VerticalIntakeEvent extends Event{
    
    int state=0;
    
    public VerticalIntakeEvent(long time){
        super(0, time);

    }
    public VerticalIntakeEvent(long time, long delay){
        super(delay, time);

    }
    public void task()
    {
        switch(state){
            case 0:
            Robot.getInstance().verticalLoader().set(Robot.getInstance().getTuningValue("verticalintake"));
            Robot.getInstance().meteringWheel().getPIDController().setReference(Robot.getInstance().getTuningValue("meteringWheel"), ControlType.kVelocity);
            break;
        }
        state++;
    }
    @Override
    public void endTask(){
        Robot.getInstance().verticalLoader().set(0);
        Robot.getInstance().meteringWheel().set(0);
    }

    @Override
    public boolean eventCompleteCondition() {
        return state == 2;
    }

}
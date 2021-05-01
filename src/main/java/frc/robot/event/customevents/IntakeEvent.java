package frc.robot.event.customevents;

import frc.robot.Robot;
import frc.robot.event.Event;

public class IntakeEvent extends Event {
    boolean bool=false;
    int state=0;
        
    public IntakeEvent(final long time) {
        super(0, time);
    }

    public IntakeEvent(final boolean on) {
        bool = true;
        if (on) {
            state = 0;
        } else {
            state = 1;
        }
    }

    public IntakeEvent(final long time, final long delay) {
        super(delay,time);
    }

    @Override
    public void task(){
        switch (state){
            case 0:
                Robot.getInstance().outerIntakeBack().set(Robot.getInstance().getTuningValue("intake")*-1);
                //Robot.getInstance().outerIntakeFront().set(Robot.getInstance().getTuningValue("intake"));
            break;
            case 1:
                Robot.getInstance().outerIntakeBack().set(0);
                Robot.getInstance().outerIntakeFront().set(0);
                break;
        }
        state++;
    }

    @Override
    public boolean eventCompleteCondition(){
        return state==2||bool;
    }
}
package frc.robot.event.customevents;

import frc.robot.Robot;
import frc.robot.event.Event;

public class ConveyorEventSensor extends Event {
    int state=0;
    public ConveyorEventSensor(){

    }

    public ConveyorEventSensor(long delay){
        super(delay);
    }

    public void task(){
        
        switch(state){
            case 0:
                Robot.getInstance().bottomConveyor().set(Robot.getInstance().getTuningValue("conveyor")*-1);
                state++;
            break;
            case 1:
                if (Robot.getInstance().getBallState()==Robot.LOAD_STATE.ballLoaded){
                    state++;
                }
            break;
            case 2:
                Robot.getInstance().bottomConveyor().set(0);
                state++;
            break;
        }


    }

    @Override
    public boolean eventCompleteCondition(){
        return state==3;
    }
}
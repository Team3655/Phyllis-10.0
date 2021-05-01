package frc.robot.event.customevents;

import frc.robot.Robot;
import frc.robot.event.Event;

public class ConveyorEventBallPresent extends Event {
    int state=0;
    boolean detect=false;
    public ConveyorEventBallPresent(){

    }

    public ConveyorEventBallPresent(boolean detect){
        this.detect=detect;
    }

    public ConveyorEventBallPresent(long delay,boolean detect){
        super(delay);
        this.detect=detect;
    }

    public void task(){
        
        switch(state){
            case 0:
                Robot.getInstance().bottomConveyor().set(Robot.getInstance().getTuningValue("conveyor")*-1);
                state++;
                System.out.println("Starting Conveyor");
            break;
            case 1:
                if (Robot.getInstance().getBallState()==Robot.LOAD_STATE.noBall){
                    System.out.println("No ball detected");
                    state++;
                }
            break;
            case 2:
                Robot.getInstance().bottomConveyor().set(0);
                System.err.println("Stopping conveyor");
                state++;
            break;
            case 3:
                if (detect&&Robot.getInstance().getBallState()!=Robot.LOAD_STATE.noBall){
                    state=0;
                } else if (!detect) {
                    state++;
                }
            break;
        }


    }

    @Override
    public boolean eventCompleteCondition(){
        return state==4;
    }
}
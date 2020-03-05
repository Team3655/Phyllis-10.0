package frc.robot.event.customevents;

import frc.robot.Robot;
import frc.robot.event.Event;

public class ElevationEvent extends Event{
    
    double pos;

    public ElevationEvent(double pos){
        super();
        double max=Robot.getInstance().getTuningValue("elevatorMaxPos");
        double min=Robot.getInstance().getTuningValue("elevatorMinPos");
        if (pos>max){
            pos=max;
        } else if (pos<min){
            pos=min;     
        }
        this.pos=pos;

    }

    public ElevationEvent(double pos, long delay){
        super(delay);
        double max=Robot.getInstance().getTuningValue("elevatorMaxPos");
        double min=Robot.getInstance().getTuningValue("elevatorMinPos");
        if (pos>max){
            pos=max;
        } else if (pos<min){
            pos=min;
        }
        this.pos=pos;
    }

    @Override
    public void task(){
        Robot.getInstance().getElevatorLeft().setPosition(pos);
    }
}
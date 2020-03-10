package frc.robot.event.customevents;

import com.revrobotics.CANSparkMax;

import frc.robot.Robot;
import frc.robot.event.Event;

public class ConveyorEvent extends Event{
    
    int state=0;
    
    public ConveyorEvent(long time){
        super(0, time);

    }

    public ConveyorEvent(long time, long delay){
        super(delay, time);

    }
    public void task()
    {
        switch(state){
            case 0:
                Robot.getInstance().bottomConveyor().set(Robot.getInstance().getTuningValue("conveyor")*-1);
            break;
            case 1:
                Robot.getInstance().bottomConveyor().set(0);
            break;
        }
        state++;
    }

    @Override
    public boolean eventCompleteCondition(){
        return state==2;
    }
    

}
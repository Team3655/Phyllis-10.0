package frc.robot.event.customevents;

import com.revrobotics.ControlType;

import frc.robot.Robot;
import frc.robot.event.Event;

public class VerticalIntakeEvent extends Event{
    
    int state=0;
    double power;

    /*public VerticalIntakeEvent(long time){
        super(0, time);

    }
    public VerticalIntakeEvent(long time, long delay){
        super(delay, time);

    }*/

    public VerticalIntakeEvent(double power){
        super();
        this.power=power;
    }

    public VerticalIntakeEvent(double power,long delay){
        super(delay);
        this.power=power;
    }

    public void task()
    {
        //switch(state){
            //case 0:
                Robot.getInstance().verticalLoader().set(power);//Robot.getInstance().getTuningValue("verticalintake"));
                //Robot.getInstance().meteringWheel().getPIDController().setReference(Robot.getInstance().getTuningValue("meteringWheel"), ControlType.kVelocity);
            //break;
        //}
        //state++;
    }
    @Override
    public void endTask(){
        //Robot.getInstance().verticalLoader().set(0);
        //Robot.getInstance().meteringWheel().set(0);
    }

    @Override
    public boolean eventCompleteCondition() {
        return true;//state == 2;
    }

}
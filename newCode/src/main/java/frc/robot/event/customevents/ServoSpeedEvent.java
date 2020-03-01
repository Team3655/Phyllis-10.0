package frc.robot.event.customevents;

import edu.wpi.first.hal.sim.mockdata.RoboRioDataJNI;
import edu.wpi.first.wpilibj.Servo;
import frc.robot.Robot;
import frc.robot.event.Event;

public class ServoSpeedEvent extends Event {
    

    double min;
    double max;
    double increment;
    double target;
    Servo left=Robot.getInstance().getElevatorLeft();
    Servo right=Robot.getInstance().getElevatorRight();
    static boolean stop=false;
    static boolean running=false;

    public ServoSpeedEvent(double loopDelay,double increment,double min,double max){
        super(0l,(long) loopDelay);
        if (increment>1){
            increment=1;
        } else if (increment<-1){
            increment=-1;
        }
        if (min>max){
            double tempMin=min;
            min=max;
            max=tempMin;
        }
        if (min<-1){
            min=-1;
        } else if (min>1){
            min=1;
        }
        if (max<-1){
            max=-1;
        } else if (max>1){
            max=1;
        }
    
        this.increment=increment;
        this.max=max;
        this.min=min;
        target=left.getPosition();
        running=true;   
    }

    @Override
    public void task(){
        double newTarget=target+increment;
        if (newTarget>max){
            newTarget=max;
        } else if (newTarget<min){
            newTarget=min;
        }
    }

    @Override
    public boolean eventCompleteCondition(){
        boolean eventComplete=false;
        if (stop){
            stop=!stop;
            eventComplete=true;
        }
        return eventComplete;
    }

    @Override
    public void endTask(){
        running=false;
    }

    public static void stop(){
        stop=true;
    }




}
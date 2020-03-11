package frc.robot.event;

import frc.robot.Robot;

public class CompoundEvent extends Event {
    
    final Event[] events;

    boolean noWait=false;
    int state=0;

    public CompoundEvent(Event[] events){
        super();
        this.events=events;
    }
    
    public CompoundEvent(Event[] events,long delay){
        super(delay);
        this.events=events;
    }

    public CompoundEvent(Event[] events,long delay,boolean noWait){
        super(delay);
        this.events=events;
        this.noWait=noWait;
    }

    public CompoundEvent(Runnable[] events){
        super();
        this.events=new Event[events.length];
        for (int i=0;i<events.length;i++){
            this.events[i]=new Event(events[i]);
        }
    }
    
    public CompoundEvent(Runnable[] events,long delay){
        super(delay);
        this.events=new Event[events.length];
        for (int i=0;i<events.length;i++){
            this.events[i]=new Event(events[i]);
        }
    }

    public CompoundEvent(Runnable[] events,long delay,boolean noWait){
        super(delay);
        this.events=new Event[events.length];
        for (int i=0;i<events.length;i++){
            this.events[i]=new Event(events[i]);
        }
        this.noWait=noWait;
    }

    @Override
    public void task() {
        switch (state){
            case 0:
                for (Event e:events){
                    Robot.eHandler.triggerEvent(e);
                }
                state++;
            break;
        }
        
    }

    @Override
    public boolean eventCompleteCondition(){
        boolean allEventsComplete=true;
        for (Event e:events){
            if (!e.taskDone()){
                allEventsComplete=false;
                break;
            }
        }
        return allEventsComplete||noWait;
    }
    
}
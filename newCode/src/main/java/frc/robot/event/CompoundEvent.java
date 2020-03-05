package frc.robot.event;

import frc.robot.Robot;

public class CompoundEvent extends Event {
    
    final Event[] events;

    public CompoundEvent(Event[] events){
        super();
        this.events=events;
    }
    
    public CompoundEvent(Event[] events,long delay){
        super(delay);
        this.events=events;
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

    @Override
    public void task() {
        for (Event e:events){
            Robot.eHandler.triggerEvent(e);
        }
    }
}
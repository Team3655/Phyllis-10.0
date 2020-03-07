package frc.robot.event.sequence;

import frc.robot.event.CompoundEvent;
import frc.robot.event.Event;
import frc.robot.event.EventSequence;
import frc.robot.event.core.DriveEventPower;
import frc.robot.event.customevents.ConveyorEvent;
import frc.robot.event.customevents.DriveEvent;
import frc.robot.event.customevents.ShootEvent;
import frc.robot.event.customevents.VerticalIntakeEvent;

public class AutonEventSequence2 extends EventSequence{
    public AutonEventSequence2(){
        super(new Event[]{new DriveEventPower(.8,100),new DriveEventPower(0,3000)});
        //super(new Event[] {new CompoundEvent(new Event[] {new ShootEvent(6000, 6),new VerticalIntakeEvent(4000,1),new ConveyorEvent(4000, 1)}),new DriveEvent(3.048,6)});
        
        //add Elevation to start of sequence
        //add drive event to end of sequence
        //super(new Event[] {new CompoundEvent(new Event[] {new ShootEvent(3000,6000),new VerticalIntakeEvent(4500,1500), new ConveyorEvent(4500, 1500),new DriveEvent(0.5,6000)})});
        //super(new Event[] {new DriveEvent(0.5, 1000), new DriveEvent(1, 1500)});
    }

}
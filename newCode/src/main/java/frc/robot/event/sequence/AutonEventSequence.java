package frc.robot.event.sequence;

import frc.robot.event.CompoundEvent;
import frc.robot.event.Event;
import frc.robot.event.EventSequence;
import frc.robot.event.customevents.ConveyorEvent;
import frc.robot.event.customevents.DriveEvent;
import frc.robot.event.customevents.ShootEvent;
import frc.robot.event.customevents.VerticalIntakeEvent;

public class AutonEventSequence extends EventSequence{
    public AutonEventSequence(){
        //super(new Event[] {new DriveEvent(1),new CompoundEvent(new Event[] {new ShootEvent(2000, 1000),new VerticalIntakeEvent(1000,500)})});
        
        //add Elevation to start of sequence
        //add drive event to end of sequence
        super(new Event[] {new CompoundEvent(new Event[] {new ShootEvent(3000,5000),new VerticalIntakeEvent(3500,1500), new ConveyorEvent(3500, 1500)})});
    }

}
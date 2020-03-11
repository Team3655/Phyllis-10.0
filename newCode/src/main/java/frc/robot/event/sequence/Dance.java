package frc.robot.event.sequence;

import frc.robot.event.CompoundEvent;
import frc.robot.event.Event;
import frc.robot.event.EventSequence;
import frc.robot.event.core.DriveEventPower;
import frc.robot.event.customevents.ConveyorEvent;
import frc.robot.event.customevents.DriveEvent;
import frc.robot.event.customevents.IntakeEvent;
import frc.robot.event.customevents.ShootEvent;
import frc.robot.event.customevents.TurnEvent;

public class Dance extends EventSequence {

    public Dance(long mspb){
        super(new Event[]{
            //4/4 time:

            //1
            new DriveEventPower(.5),
            new CompoundEvent(new Event[]{new DriveEventPower(0,mspb),new IntakeEvent(mspb/4),
            new IntakeEvent(mspb/4,3*mspb/4)}),
            new DriveEventPower(-.5,mspb*2),
            new CompoundEvent(new Event[]{new DriveEventPower(0,mspb),new IntakeEvent(mspb/4),new IntakeEvent(mspb/4,3*mspb/4)}),

            //2
            new DriveEventPower(.3),
            new CompoundEvent(new Event[]{new DriveEventPower(0,mspb),new IntakeEvent(mspb/4),
            new IntakeEvent(mspb/4,3*mspb/4)}),
            new DriveEventPower(-.3,mspb*2),
            new CompoundEvent(new Event[]{new DriveEventPower(0,mspb),new IntakeEvent(mspb/4),new IntakeEvent(mspb/4,3*mspb/4)}),

            //3
            new DriveEventPower(.2),
            new CompoundEvent(new Event[]{new DriveEventPower(0,mspb),new IntakeEvent(mspb/4),
            new IntakeEvent(mspb/4,3*mspb/4)}),
            new DriveEventPower(-.2,mspb*2),
            new CompoundEvent(new Event[]{new DriveEventPower(0,mspb),new IntakeEvent(mspb/4),new IntakeEvent(mspb/4,3*mspb/4)}),

            //4
            new DriveEventPower(1),
            new CompoundEvent(new Event[]{new DriveEventPower(0,mspb),new IntakeEvent(mspb/4),
            new IntakeEvent(mspb/4,3*mspb/4)}),
            new DriveEventPower(1,mspb*2),
            new CompoundEvent(new Event[]{new DriveEventPower(0,mspb),new IntakeEvent(mspb/4),new IntakeEvent(mspb/4,3*mspb/4)}),

            //5
            new ShootEvent(4500, 0),
            new DriveEventPower(.2),
            new ConveyorEvent(mspb),
            new DriveEventPower(0),
            new CompoundEvent(new Event[] {
                new TurnEvent(.5,.25,mspb),
                new DriveEventPower(.2,mspb),
                new ConveyorEvent(mspb*2),
            },0,true),
            new DriveEventPower(0,mspb*3),
            new ShootEvent(0,mspb),

        });
    }
}
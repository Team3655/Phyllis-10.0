package frc.robot.event.sequence;

import frc.robot.Robot;
import frc.robot.event.CompoundEvent;
import frc.robot.event.Event;
import frc.robot.event.EventSequence;
import frc.robot.event.core.DriveEventPower;
import frc.robot.event.customevents.ConveyorEventTime;
import frc.robot.event.customevents.DriveEvent;
import frc.robot.event.customevents.IntakeEvent;
import frc.robot.event.customevents.ShootEvent;
import frc.robot.event.customevents.TurnEvent;

public class Dance extends EventSequence {

    public Dance(long mspb){
        super(new Event[]{
            //calibrate gyro
            new Event(Robot.getInstance().gyro()::calibrate),
            new Event(Robot.getInstance().gyro()::reset),
            //4/4 time:

            //1
            new DriveEventPower(1,500),
            new CompoundEvent(new Event[]{new DriveEventPower(0,mspb),new IntakeEvent(mspb/4),
            new IntakeEvent(mspb/4,3*mspb/4)}),
            new DriveEventPower(-1,mspb*2),
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
            new ConveyorEventTime(mspb),
            new DriveEventPower(0),
            new CompoundEvent(new Event[] {
                new TurnEvent(-.2,.25,mspb),
                new DriveEventPower(.2,mspb),
                new ConveyorEventTime(mspb*2),
            },0,true),
            new DriveEventPower(0,mspb*3),
            new ShootEvent(0,mspb),

        });
    }
}
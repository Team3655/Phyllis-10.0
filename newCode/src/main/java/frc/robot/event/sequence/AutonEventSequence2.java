package frc.robot.event.sequence;

import frc.robot.Robot;
import frc.robot.event.CompoundEvent;
import frc.robot.event.Event;
import frc.robot.event.EventSequence;
import frc.robot.event.core.DriveEventPower;
import frc.robot.event.customevents.ClimbArmEvent;
import frc.robot.event.customevents.ConveyorEvent;
import frc.robot.event.customevents.DriveEvent;
import frc.robot.event.customevents.IntakeEvent;
import frc.robot.event.customevents.ShootEvent;
import frc.robot.event.customevents.TurretEvent;
import frc.robot.event.customevents.VerticalIntakeEvent;

public class AutonEventSequence2 extends EventSequence{
    public AutonEventSequence2(){
    super(new Event[]{
            new DriveEvent(-1.5),
            new ClimbArmEvent(-.28, 500),
            new TurretEvent(-.41),
            new Event(Robot.getInstance().getLimelight()::enable),
            new ShootEvent(4250,900),
            new VerticalIntakeEvent(Robot.getInstance().getTuningValue("verticalIntake")*-1,500),
            new VerticalIntakeEvent(0,1000),
            new ConveyorEvent(1500,0),
            new VerticalIntakeEvent(Robot.getInstance().getTuningValue("verticalIntake")*-1,500),
            new VerticalIntakeEvent(0,3000),
            new ClimbArmEvent(.6, 600),
            new CompoundEvent(new Event[]{
                new ConveyorEvent(2000),
                new IntakeEvent(2000)
            }),
            new ConveyorEvent(1500,0),
            new VerticalIntakeEvent(Robot.getInstance().getTuningValue("verticalIntake")*-1,500),
            new VerticalIntakeEvent(0,3000),
            new ShootEvent(0,0),
            //return turret to start (for testing)
            new Event(Robot.getInstance().getLimelight()::disable),
            new TurretEvent(0),
            //prepare climb arm to be pulled up by ana
            
        });
        //super(new Event[] {new CompoundEvent(new Event[] {new ShootEvent(6000, 6),new VerticalIntakeEvent(4000,1),new ConveyorEvent(4000, 1)}),new DriveEvent(3.048,6)});
        
        //add Elevation to start of sequence
        //add drive event to end of sequence
        //super(new Event[] {new CompoundEvent(new Event[] {new ShootEvent(3000,6000),new VerticalIntakeEvent(4500,1500), new ConveyorEvent(4500, 1500),new DriveEvent(0.5,6000)})});
        //super(new Event[] {new DriveEvent(0.5, 1000), new DriveEvent(1, 1500)});
    }

}
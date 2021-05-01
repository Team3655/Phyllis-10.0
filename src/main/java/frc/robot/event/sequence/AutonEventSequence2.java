package frc.robot.event.sequence;

import com.revrobotics.CANSparkMax;

import frc.robot.Robot;
import frc.robot.Robot.LOAD_STATE;
import frc.robot.event.CompoundEvent;
import frc.robot.event.Event;
import frc.robot.event.EventSequence;
import frc.robot.event.core.DriveEventPower;
import frc.robot.event.customevents.ClimbArmEvent;
import frc.robot.event.customevents.ConveyorEventBallPresent;
import frc.robot.event.customevents.ConveyorEventSensor;
import frc.robot.event.customevents.ConveyorEventTime;
import frc.robot.event.customevents.DriveEvent;
import frc.robot.event.customevents.IntakeEvent;
import frc.robot.event.customevents.ShootEvent;
import frc.robot.event.customevents.TurretEvent;
import frc.robot.event.customevents.VerticalIntakeEvent;

public class AutonEventSequence2 extends EventSequence{
    public AutonEventSequence2(){
    super(new Event[]{
            new CompoundEvent(new Event[]{
                new DriveEvent(-1.5),
                new ClimbArmEvent(-.24, 500),
            }),
            new TurretEvent(-.41),
            new Event(Robot.getInstance().getLimelight()::enable),
            new ShootEvent(Robot.getInstance().getTuningValue("shoot"),900),
            new VerticalIntakeEvent(Robot.getInstance().getTuningValue("verticalIntake")*-1,500),
            /*new Event(){
                @Override
                public boolean eventCompleteCondition() {
                    // TODO Auto-generated method stub
                    
                    LOAD_STATE state=Robot.getInstance().getBallState();
                    //System.out.println(state);
                    return Robot.getInstance().getBallState()==Robot.LOAD_STATE.noBall||Robot.getInstance().getBallState()==LOAD_STATE.ballPresent;
                }
            },
            new VerticalIntakeEvent(0,500),*/
            //new ConveyorEventSensor(),
            
            new ConveyorEventBallPresent(),
            
            /*new Event(){
                @Override
                public boolean eventCompleteCondition() {
                    // TODO Auto-generated method stub
                    
                    LOAD_STATE state=Robot.getInstance().getBallState();
                    //System.out.println(state);
                    return Robot.getInstance().getBallState()==Robot.LOAD_STATE.noBall||Robot.getInstance().getBallState()==LOAD_STATE.ballPresent;
                }
            },
            new VerticalIntakeEvent(0,500),*/
            
            //new ConveyorEventSensor(),
            /*new VerticalIntakeEvent(Robot.getInstance().getTuningValue("verticalIntake")*-1,0),
            new Event(){
                @Override
                public boolean eventCompleteCondition() {
                    // TODO Auto-generated method stub
                    
                    LOAD_STATE state=Robot.getInstance().getBallState();
                    //System.out.println(state);
                    return Robot.getInstance().getBallState()==Robot.LOAD_STATE.noBall||Robot.getInstance().getBallState()==LOAD_STATE.ballPresent;
                }
            },
            new VerticalIntakeEvent(0,800),*/
            new CompoundEvent(new Event[]{
                new IntakeEvent(true),
                new DriveEvent(-8,.1),
                new ConveyorEventBallPresent(true){
                    CANSparkMax fr = Robot.getInstance().getDriveRight();
                    CANSparkMax br = Robot.getInstance().getDriveRearRight();
                    CANSparkMax fl = Robot.getInstance().getDriveLeft();
                    CANSparkMax bl = Robot.getInstance().getDriveRearLeft();

                    @Override
                    public boolean eventCompleteCondition(){
                        return super.eventCompleteCondition()||(averagePos()>-8-.1&&averagePos()<-8+.1);
                    }

                    private double averagePos(){

                        double aPos= (-fl.getEncoder().getPosition()-bl.getEncoder().getPosition()+fr.getEncoder().getPosition()+br.getEncoder().getPosition())/4;
                        //System.out.println("Average Pos"+aPos);
                        return aPos;
                    }
                },
            },0,false),
            
            new ShootEvent(0,0),
            new VerticalIntakeEvent(0,0),
            
            
            
            //return turret to start (for testing)
            //new ClimbArmEvent(.6, 500),
            
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
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
import frc.robot.event.customevents.TurnEvent;
import frc.robot.event.customevents.TurretEvent;
import frc.robot.event.customevents.VerticalIntakeEvent;

public class AutoRedA extends EventSequence{
    public AutoRedA(){
    super(new Event[]{
                new DriveEvent(-2,0.9,200),
                new TurnEvent(0.15,11),
                new DriveEvent(-7.8,0.3,200),
                new IntakeEvent(true),
                new DriveEvent(-2,.1,200),
                new IntakeEvent(false),
                new TurnEvent(0.15,-60),
                new DriveEvent(-4.5,0.3,200),
                new IntakeEvent(true),
                new DriveEvent(-2,.1,200),
                new IntakeEvent(false),
                new TurnEvent(0.15,8),
                new DriveEvent(-2.5,0.3,200),
                new IntakeEvent(true),
                new DriveEvent(-2,.1,20),
                new IntakeEvent(false),
                //new TurnEvent(0.15,-1),
                new DriveEvent(-5,0.5,200),
        });
        
    }

}
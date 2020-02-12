/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.CAN;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.buttons.ButtonHandler;
import frc.robot.buttons.JLSBAdapter;
import frc.robot.buttons.JRSBAdapter;
import frc.robot.buttons.TSBAdapter;
import frc.robot.event.EventHandler;
import frc.robot.event.customevents.LimelightEvent;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  
  private static Robot instance;
  private Limelight limelight=new Limelight();


  private CANSparkMax br = new CANSparkMax(12, MotorType.kBrushless);
  private CANSparkMax fr = new CANSparkMax(13, MotorType.kBrushless);
  private CANSparkMax fl = new CANSparkMax(11, MotorType.kBrushless);
  private CANSparkMax bl = new CANSparkMax(10, MotorType.kBrushless);
  private CANSparkMax centerIntakeFront=null;//new CANSparkMax(/*17*/50, MotorType.kBrushless);
  private CANSparkMax centerIntakeBack=new CANSparkMax(16, MotorType.kBrushless);
  private CANSparkMax verticalLoader=null;//new CANSparkMax(18, MotorType.kBrushless);
  private CANSparkMax outerIntakeBack = new CANSparkMax(14,MotorType.kBrushless);
  private CANSparkMax outerIntakeFront = new CANSparkMax(15,MotorType.kBrushless);
  private CANSparkMax meteringWheel = new CANSparkMax(19,MotorType.kBrushless);
  private CANSparkMax leftShooterWheel = null;//new CANSparkMax(20,MotorType.kBrushless);
  private CANSparkMax rightShooterWheel =null;// new CANSparkMax(21,MotorType.kBrushless);
  private CANSparkMax turret = new CANSparkMax(/*22*/18,MotorType.kBrushless);//17 for testing
  private CANSparkMax shooterElevator =null;// new CANSparkMax(23,MotorType.kBrushless);
  private CANSparkMax climb1 =null;// new CANSparkMax(24,MotorType.kBrushless);
  private CANSparkMax climb2 =null;// new CANSparkMax(25,MotorType.kBrushless);

  public CANSparkMax centerIntakeFront(){
    return  centerIntakeFront; 
  }
  
  public CANSparkMax centerIntakeBack(){
    return  centerIntakeBack; 
  }

  public CANSparkMax verticalLoader(){
    return verticalLoader; 
  }

  public CANSparkMax  outerIntakeBack(){
    return outerIntakeBack; 
  }

  public CANSparkMax outerIntakeFront(){
    return outerIntakeFront; 
  }

  public CANSparkMax meteringWheel(){
    return meteringWheel; 
  }

  public CANSparkMax leftShooterWheel(){
    return leftShooterWheel; 
  }

  public CANSparkMax rightShooterWheel(){
    return rightShooterWheel; 
  }

  public CANSparkMax turret(){
    return turret; 
  }

  public CANSparkMax shooterElevator(){
    return shooterElevator; 
  }

  public CANSparkMax climb1(){
    return climb1;
  }

  public CANSparkMax climb2(){
    return  climb2; 
  }
 

  DifferentialDrive driveControl;
  Joystick leftJoystick = new Joystick(0);
  Joystick rightJoystick = new Joystick(1);
  Joystick tractorJoystick = new Joystick(2);
  JLSBAdapter leftJoystickAdapter = new JLSBAdapter(leftJoystick, this);
  JRSBAdapter rightJoystickAdapter = new JRSBAdapter(rightJoystick, this);
  TSBAdapter tractorAdapter = new TSBAdapter(tractorJoystick, this);

  public DifferentialDrive driveControl(){
    return driveControl;
  }

  public Joystick leftJoystick(){
    return leftJoystick;
  }

  public Joystick rightJoystick(){
    return rightJoystick;
  }

  public static EventHandler eHandler=new EventHandler();
  
  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit() {
    instance=this;
    //centerIntakeFront.clearFaults();

    driveControl = new DifferentialDrive(fl, fr);
  }

  public Limelight getLimelight(){
    return limelight;
  }

  @Override
  public void autonomousInit() {
    limelight.enable();
  }

  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void teleopInit() {
    limelight.enable();
  }

  @Override
  public void teleopPeriodic() {
    tractorAdapter.update();
    leftJoystickAdapter.update();
    rightJoystickAdapter.update();
    double rightY=rightJoystick.getY();
    double leftY=leftJoystick.getY();
    double leftX=leftJoystick.getX();
    if (rightY<.1 && rightY>-.1){
      rightY=0;
    }
      if (leftX<.1 && leftX>-.1){
        leftX=0;
    }

    if (rightJoystickAdapter.isArcade()){
    driveControl.arcadeDrive(rightY, leftX);
    br.set(fr.get());
    bl.set(fl.get());
    }else if(rightJoystickAdapter.isTank()){
      driveControl.tankDrive(leftY, rightY);
    }
}

  @Override
  public void testInit() {
  }

  @Override
  public void testPeriodic() {
  }

  @Override
  public void disabledInit(){
    limelight.disable();
  }

  public static Robot getInstance(){
    return instance;
  }
}

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
import frc.robot.buttons.JLSBAdapter;
import frc.robot.buttons.TSBAdapter;
import frc.robot.event.EventHandler;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  
  private static Robot instance;
  Limelight limelight=new Limelight();


  private CANSparkMax br = new CANSparkMax(12, MotorType.kBrushless);
  private CANSparkMax fr = new CANSparkMax(13, MotorType.kBrushless);
  private CANSparkMax fl = new CANSparkMax(11, MotorType.kBrushless);
  private CANSparkMax bl = new CANSparkMax(10, MotorType.kBrushless);
  private CANSparkMax centerIntakeFront=new CANSparkMax(17, MotorType.kBrushless);
  private CANSparkMax centerIntakeBack=new CANSparkMax(16, MotorType.kBrushless);
  private CANSparkMax verticalLoader=new CANSparkMax(18, MotorType.kBrushless);
  private CANSparkMax outerIntakeBack = new CANSparkMax(14,MotorType.kBrushless);
  private CANSparkMax outerIntakeFront = new CANSparkMax(15,MotorType.kBrushless);
  private CANSparkMax meteringWheel = new CANSparkMax(19,MotorType.kBrushless);
  private CANSparkMax leftShooterWheel = new CANSparkMax(20,MotorType.kBrushless);
  private CANSparkMax rightShooterWheel = new CANSparkMax(21,MotorType.kBrushless);
  private CANSparkMax turret = new CANSparkMax(22,MotorType.kBrushless);
  private CANSparkMax shooterElevator = new CANSparkMax(23,MotorType.kBrushless);
  private CANSparkMax climb1 = new CANSparkMax(24,MotorType.kBrushless);
  private CANSparkMax climb2 = new CANSparkMax(25,MotorType.kBrushless);

  public void setCenterIntakeFront(double speed) {
    centerIntakeFront.set(speed);
  }

  public double getCenterIntakeFront() {
    return centerIntakeFront.get();
  }
  
  public void setCenterIntakeBack(double speed) {
    centerIntakeBack.set(speed);
  }

  public double getCenterIntakeBack() {
    return centerIntakeBack.get();
  }

  public void setVerticalLoader(double speed) {
    verticalLoader.set(speed);
  }

  public double getVerticalLoader() {
    return verticalLoader.get();
  }

  public void setOuterIntakeBack(double speed) {
    outerIntakeBack.set(speed);
  }

  public double getOuterIntakeBack() {
    return outerIntakeBack.get();
  }

  public void setOuterIntakeFront(double speed) {
    outerIntakeFront.set(speed);
  }

  public double getOuterIntakeFront() {
    return outerIntakeFront.get();
  }

  public void setMeteringWheel(double speed) {
    meteringWheel.set(speed);
  }

  public double getMeteringWheel() {
    return meteringWheel.get();
  }

  public void setLeftShooterWheel(double speed) {
    leftShooterWheel.set(speed);
  }

  public double getLeftShooterWheel() {
    return leftShooterWheel.get();
  }

  public void setRightShooterWheel(double speed) {
    rightShooterWheel.set(speed);
  }

  public double getRightShooterWheel() {
    return rightShooterWheel.get();
  }

  public void setTurret(double speed) {
    turret.set(speed);
  }

  public double getTurret() {
    return turret.get();
  }

  public void setShooterElevator(double speed) {
    shooterElevator.set(speed);
  }

  public double getShooterElevator() {
    return shooterElevator.get();
  }

  public void setClimb1(double speed) {
    climb1.set(speed);
  }

  public double getClimb1() {
    return climb1.get();
  }

  public void setClimb2(double speed) {
    climb2.set(speed);
  }

  public double getClimb2() {
    return climb2.get();
  }

  DifferentialDrive driveControl;
  Joystick leftJoystick = new Joystick(0);
  Joystick rightJoystick = new Joystick(1);
  Joystick tractorJoystick = new Joystick(2);
  JLSBAdapter leftJoystickAdapter = new JLSBAdapter(leftJoystick, this);
  JLSBAdapter rightJoystickAdapter = new JLSBAdapter(rightJoystick, this);
  TSBAdapter tractorAdapter = new TSBAdapter(tractorJoystick, this);

  public static EventHandler eHandler=new EventHandler();
  
  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit() {
    instance=this;
    centerIntakeFront.clearFaults();

    driveControl = new DifferentialDrive(fl, fr);
  }

  @Override
  public void autonomousInit() {
  }

  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void teleopInit() {
  }

  @Override
  public void teleopPeriodic() {
    tractorAdapter.update();
    leftJoystickAdapter.update();
    rightJoystickAdapter.update();
    double driveX=rightJoystick.getY();
    double driveZ=leftJoystick.getX();
    if (driveX<.1 && driveX>-.1){
      driveX=0;
    }
      if (driveZ<.1 && driveZ>-.1){
        driveZ=0;
    }

    driveControl.arcadeDrive(driveX, driveZ);
    br.set(fr.get());
    bl.set(fl.get());
  }

  @Override
  public void testInit() {
  }

  @Override
  public void testPeriodic() {
  }

  public static Robot getInstance(){
    return instance;
  }
}

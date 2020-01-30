/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

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

  CANSparkMax br = new CANSparkMax(12, MotorType.kBrushless);
  CANSparkMax fr = new CANSparkMax(13, MotorType.kBrushless);
  CANSparkMax fl = new CANSparkMax(11, MotorType.kBrushless);
  CANSparkMax bl = new CANSparkMax(10, MotorType.kBrushless);
  public CANSparkMax centerIntakeFront=new CANSparkMax(17, MotorType.kBrushless);
  public CANSparkMax centerIntakeBack=new CANSparkMax(16, MotorType.kBrushless);
  public CANSparkMax verticalLoader=new CANSparkMax(18, MotorType.kBrushless);
  public CANSparkMax outerIntakeBack = new CANSparkMax(14,MotorType.kBrushless);
  public CANSparkMax outerIntakeFront = new CANSparkMax(15,MotorType.kBrushless);
  public CANSparkMax meteringWheel = new CANSparkMax(19,MotorType.kBrushless);
  public CANSparkMax = new CANSparkMax(20,MotorType.kBrushless);

  DifferentialDrive driveControl;
  Joystick leftJoystick = new Joystick(0);
  Joystick rightJoystick = new Joystick(1);
  Joystick tractorJoystick = new Joystick(2);
  JLSBAdapter leftJoystickAdapter = new JLSBAdapter(leftJoystick, this);
  JLSBAdapter rightJoystickAdapter = new JLSBAdapter(rightJoystick, this)
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

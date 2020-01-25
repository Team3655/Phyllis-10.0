/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  
  CANSparkMax br = new CANSparkMax(12, MotorType.kBrushless);
  CANSparkMax fr = new CANSparkMax(13, MotorType.kBrushless);
  CANSparkMax fl = new CANSparkMax(11, MotorType.kBrushless);
  CANSparkMax bl = new CANSparkMax(10, MotorType.kBrushless);
 
  DifferentialDrive driveControl;

  Joystick leftJoystick = new Joystick(0);
  Joystick rightJoystick = new Joystick(1);
  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit() {
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
    driveControl.arcadeDrive(rightJoystick.getX(), leftJoystick.getZ());
    br.set(fr.get());
    bl.set(fl.get());
  }

  @Override
  public void testInit() {
  }

  @Override
  public void testPeriodic() {
  }

}

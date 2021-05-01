/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.util.Hashtable;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.buttons.JLSBAdapter;
import frc.robot.buttons.JRSBAdapter;
import frc.robot.buttons.TSBAdapter;
import frc.robot.event.EventHandler;
import frc.robot.event.EventSequence;
import frc.robot.event.customevents.DriveEvent;
import frc.robot.event.customevents.PrintEvent;
import frc.robot.event.sequence.AutoRedA;
import frc.robot.event.sequence.AutoRedB;
import frc.robot.event.sequence.AutonEventSequence2;
import frc.robot.motors.JEPLG;
import frc.robot.motors.Neo;
import frc.robot.motors.Neo550;
import frc.robot.motors.Pro775;
import frc.robot.motors.Turret;
import frc.robot.sensors.IRDistanceSensor;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  public static enum LOAD_STATE {noBall,ballPresent,ballLoaded};
  private static Robot instance;
  private final Limelight limelight = new Limelight();

  public static enum DRIVE_MODE {
    twoStickArcade, oneStickArcade, tank
  };

  DRIVE_MODE driveMode = DRIVE_MODE.twoStickArcade;
  private final CANSparkMax br = attemptGetMotor(13);// new CANSparkMax(12, MotorType.kBrushless);
  private final CANSparkMax fr = attemptGetMotor(12);// new CANSparkMax(13, MotorType.kBrushless);
  private final CANSparkMax fl = attemptGetMotor(11);// new CANSparkMax(11, MotorType.kBrushless);
  private final CANSparkMax bl = attemptGetMotor(10);// new CANSparkMax(10, MotorType.kBrushless);

  EventSequence autonSequence;

  private final CANSparkMax bottomConveyor = new Neo(17);// attemptGetMotor(17);//null;//8new CANSparkMax(/*17*/50,
                                                         // MotorType.kBrushless);
  // Snowblower motor (similar current limit to JEPLG)

  private final CANSparkMax climbArm = new JEPLG(23);// attemptGetMotor(16);//new CANSparkMax(16, MotorType.kBrushless);
  // 550

  private final CANSparkMax verticalLoader = new Neo550(18);// null; //new CANSparkMax(18, MotorType.kBrushless);
  // 550

  private final CANSparkMax outerIntakeBack = new Pro775(14);// null; //new CANSparkMax(14,MotorType.kBrushless);
  // 775 pro

  private final CANSparkMax outerIntakeFront = new Pro775(15); // null; // new CANSparkMax(15,MotorType.kBrushless);
  // 775 pro

  private final CANSparkMax meteringWheel = new Neo550(19);// null; //new CANSparkMax(19,MotorType.kBrushless);
  // 550 neo

  private final CANSparkMax leftShooterWheel = attemptGetMotor(21);// null; //new CANSparkMax(20,MotorType.kBrushless);
  // neo

  private final CANSparkMax rightShooterWheel = attemptGetMotor(20);// null;//new CANSparkMax(21,MotorType.kBrushless);
  // neo

  private Turret turret;// new CANSparkMax(/*22*/18,MotorType.kBrushless);//17 for testing
  // 550

  private final CANSparkMax colorWheel = new JEPLG(16);// null;//new CANSparkMax(23,MotorType.kBrushless);
  // JE PLG andymark

  // shooter elevator going to be two servos
  private final Servo elevatorLeft = new Servo(0);
  private final Servo elevatorRight = new Servo(1);

  private final ADXRS450_Gyro gyro = new ADXRS450_Gyro();

  private final CANSparkMax climb1 = attemptGetMotor(24);// null;//new CANSparkMax(24,MotorType.kBrushless);
  // neo

  private final CANSparkMax climb2 = attemptGetMotor(25);// null;//new CANSparkMax(25,MotorType.kBrushless);
  // neo

  DifferentialDrive driveControl;
  Joystick leftJoystick = new Joystick(0);
  Joystick rightJoystick = new Joystick(1);
  Joystick tractorJoystick = new Joystick(2);
  JLSBAdapter leftJoystickAdapter = new JLSBAdapter(leftJoystick, this);
  JRSBAdapter rightJoystickAdapter = new JRSBAdapter(rightJoystick, this);
  TSBAdapter tractorAdapter;

  private final IRDistanceSensor ballDistance = new IRDistanceSensor(3);

  private Hashtable<String, Double> tuningValues;

  public static EventHandler eHandler = new EventHandler();

  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit() {
    instance = this;

    // centerIntakeFront.clearFaults();
    eHandler.start();
    // new DifferentialDrive(fl, fr);
    // driveControl.
    br.follow(fr);
    bl.follow(fl);
    br.setIdleMode(IdleMode.kCoast);
    fr.setIdleMode(IdleMode.kCoast);
    bl.setIdleMode(IdleMode.kCoast);
    fl.setIdleMode(IdleMode.kCoast);
    bl.setInverted(false);
    fl.setInverted(false);
    br.setInverted(false);
    fr.setInverted(false);
    gyro.calibrate();
    leftShooterWheel.setIdleMode(IdleMode.kCoast);
    rightShooterWheel.setIdleMode(IdleMode.kCoast);
    leftShooterWheel().getPIDController().setD(0);
    leftShooterWheel().getPIDController().setD(0);
    leftShooterWheel().getPIDController().setP(.00014);// .00014);
    leftShooterWheel().getPIDController().setI(0);
    // leftShooterWheel().getPIDController().setIZone(.00034);
    // leftShooterWheel().getPIDController().setSmartMotionMaxVelocity(6000,0);
    // leftShooterWheel().getPIDController().setSmartMotionMinOutputVelocity(-6000,
    // 0);
    leftShooterWheel().getPIDController().setFF(.00017);
    rightShooterWheel().getPIDController().setP(.00014);
    rightShooterWheel.getPIDController().setFF(.00017);
    meteringWheel.getPIDController().setP(.00001);
    meteringWheel.getPIDController().setFF(.0001);
    meteringWheel.getPIDController().setSmartMotionMaxVelocity(11000, 0);
    meteringWheel.getPIDController().setSmartMotionMinOutputVelocity(-11000, 0);
    climb1.setIdleMode(IdleMode.kBrake);
    // getElevatorRight().set(getElevatorLeft().getSpeed());

    tuningValues = new Hashtable<>();

    // add tuning values
    tuningValues.put("climb", .6);
    tuningValues.put("climbArmUp", .5);
    tuningValues.put("climbArmDown", .28);
    tuningValues.put("drive", 1d);
    tuningValues.put("driveP", .075d);
    tuningValues.put("driveI", 0d);
    tuningValues.put("driveFF", 0d);
    tuningValues.put("conveyor", .9);
    tuningValues.put("verticalIntake", 1d);
    tuningValues.put("meteringWheel", -7000d);
    tuningValues.put("shoot", 4500d);
    tuningValues.put("turret", .1);
    tuningValues.put("shooterElevation", .1);
    tuningValues.put("turretDefaultMaxSpeed", .2);
    tuningValues.put("turretMaxPos", 43.76);
    tuningValues.put("turretMinPos", -84.5);// 47.07);
    tuningValues.put("intake", 0.9d);
    tuningValues.put("elevator", .1d);// servo increment per wheel move (between -1 and 1)
    tuningValues.put("elevatorMaxPos", 1d); // don't set this higher than 1 or less than min
    tuningValues.put("elevatorMinPos", 0d);// don't set this less than 1 or more than max
    tuningValues.put("passThrough", 1d);
    tuningValues.put("velocityCoefficient", 900d);
    tuningValues.put("sequence", 1d);
    turret = new Turret(22);
    turret.setIdleMode(IdleMode.kBrake);

    final UsbCamera front = CameraServer.getInstance().startAutomaticCapture();
    final UsbCamera other = CameraServer.getInstance().startAutomaticCapture();
    tractorAdapter = new TSBAdapter(tractorJoystick, this);
    DriveEvent.configure(.2032, 10.71);
    driveControl = new DifferentialDrive(fl, fr);
    // gyro.calibrate();
  }

  public Limelight getLimelight() {
    return limelight;
  }

  @Override
  public void autonomousInit() {
    // limelight.enable();
    try {
      driveControl.close();
    } catch (final NullPointerException e) {
      // do nothing
    }
    driveControl = null;

    try {
      tractorAdapter.setMode(TSBAdapter.Mode.Tune);
    } catch (final Exception e) {

    }
    limelight.disable();
    // gyro.calibrate();//Need to uncomment for turning capabilitys
    // need to test
    stopAuton();// distance from back wall to line is 3.048
    startAuton();
  }

  @Override
  public void autonomousPeriodic() {
    // fl.set(.4);

  }

  @Override
  public void teleopInit() {
    // limelight.enable();
    try {
      driveControl.close();
    } catch (final NullPointerException e) {
      // do nothing
    }
    driveControl = new DifferentialDrive(fl, fr);
    try {
      tractorAdapter.setMode(TSBAdapter.Mode.RobotResponse);
    } catch (final Exception e) {

    }
    limelight.disable();
    stopAuton();
  }

  @Override
  public void teleopPeriodic() {
    //System.out.println(Robot.getInstance().gyro().getAngle());
    // printMotorTemps();//224 not normal temp, 45 and below is normal
    // checkMotorTemps();//disables motors above 260 deg F internal
    tractorAdapter.update();
    leftJoystickAdapter.update();
    rightJoystickAdapter.update();
    double rightY = rightJoystick.getY() * -1;
    double rightX = rightJoystick.getX();
    double leftY = leftJoystick.getY() * -1;
    double leftX = leftJoystick.getX();
    final boolean slowSpeed = rightJoystick.getRawButton(3);
    if (rightY < .1 && rightY > -.1) {
      rightY = 0;
    }
    if (rightX < .1 && rightX > -.1) {
      rightX = 0;
    }
    if (leftY < .1 && leftY > -.1) {
      leftY = 0;
    }
    if (leftX < .1 && leftX > -.1) {
      leftX = 0;
    }
    if (slowSpeed) {
      rightY = rightY * .5;
    }
    if (slowSpeed) {
      rightX = rightX * .5;
    }
    if (slowSpeed) {
      leftY = leftY * .75;
    }
    if (slowSpeed) {
      leftX = leftX * .75;
    }

    switch (driveMode) {
    case twoStickArcade:
      driveControl.arcadeDrive(rightY, leftX);
      // eHandler.triggerEvent(new PrintEvent("arcade driving"));
      break;
    case oneStickArcade:
      driveControl.arcadeDrive(rightY, rightX);
      break;
    case tank:
      driveControl.tankDrive(leftY, rightY);
      break;
    }
    // System.out.println("E Angle"+elevatorLeft.getAngle());
    // System.out.println(ballDistance.distance());
  }

  @Override
  public void testInit() {
    try {
      tractorAdapter.setMode(TSBAdapter.Mode.RobotResponse);
    } catch (final Exception e) {

    }
  }

  @Override
  public void testPeriodic() {
  }

  @Override
  public void disabledInit() {
    limelight.disable();
    try {
      tractorAdapter.setMode(TSBAdapter.Mode.Tune);
    } catch (final Exception e) {

    }
  }

  @Override
  public void disabledPeriodic() {
    tractorAdapter.update();
  }

  public void setDriveMode(final DRIVE_MODE d) {
    driveMode = d;
  }

  public DRIVE_MODE getDriveMode() {
    return driveMode;
  }

  public static Robot getInstance() {
    return instance;
  }

  public void printMotorTemps() {
    eHandler.triggerEvent(new PrintEvent(fl.getMotorTemperature()));
    eHandler.triggerEvent(new PrintEvent(bl.getMotorTemperature()));
    eHandler.triggerEvent(new PrintEvent(fr.getMotorTemperature()));
    eHandler.triggerEvent(new PrintEvent(br.getMotorTemperature()));
    eHandler.triggerEvent(new PrintEvent(bottomConveyor.getMotorTemperature()));
    eHandler.triggerEvent(new PrintEvent(climbArm.getMotorTemperature()));
    eHandler.triggerEvent(new PrintEvent(verticalLoader.getMotorTemperature()));
    eHandler.triggerEvent(new PrintEvent(outerIntakeBack.getMotorTemperature()));
    eHandler.triggerEvent(new PrintEvent(outerIntakeFront.getMotorTemperature()));
    eHandler.triggerEvent(new PrintEvent(meteringWheel.getMotorTemperature()));
    eHandler.triggerEvent(new PrintEvent(leftShooterWheel.getMotorTemperature()));
    eHandler.triggerEvent(new PrintEvent(rightShooterWheel.getMotorTemperature()));
    eHandler.triggerEvent(new PrintEvent(turret.getMotorTemperature()));
    eHandler.triggerEvent(new PrintEvent(colorWheel.getMotorTemperature()));
    eHandler.triggerEvent(new PrintEvent(climb1.getMotorTemperature()));
    eHandler.triggerEvent(new PrintEvent(climb2.getMotorTemperature()));
  }

  public void printMotorPositions() {
    eHandler.triggerEvent(new PrintEvent(turret.getPosition()));
  }

  public void checkMotorTemps() {
    if (fr.getMotorTemperature() > 260) {
      fr.disable();
    }
    if (fl.getMotorTemperature() > 260) {
      fl.disable();
    }
    if (br.getMotorTemperature() > 260) {
      br.disable();
    }
    if (bl.getMotorTemperature() > 260) {
      bl.disable();
    }
    if (bottomConveyor.getMotorTemperature() > 260) {
      bottomConveyor.disable();
    }
    if (climbArm.getMotorTemperature() > 260) {
      climbArm.disable();
    }
    if (verticalLoader.getMotorTemperature() > 260) {
      verticalLoader.disable();
    }
    if (outerIntakeBack.getMotorTemperature() > 260) {
      outerIntakeBack.disable();
    }
    if (outerIntakeFront.getMotorTemperature() > 260) {
      outerIntakeFront.disable();
    }
    if (meteringWheel.getMotorTemperature() > 260) {
      meteringWheel.disable();
    }
    if (leftShooterWheel.getMotorTemperature() > 260) {
      leftShooterWheel.disable();
    }
    if (rightShooterWheel.getMotorTemperature() > 260) {
      rightShooterWheel.disable();
    }
    if (turret.getMotorTemperature() > 260) {
      turret.disable();
    }
    if (colorWheel.getMotorTemperature() > 260) {
      colorWheel.disable();
    }
    if (climb1.getMotorTemperature() > 260) {
      climb1.disable();
    }
    if (climb2.getMotorTemperature() > 260) {
      climb2.disable();
    }
  }

  public TSBAdapter getTractorAdapter() {
    return this.tractorAdapter;
  }

  public CANSparkMax getDriveLeft() {
    return fl;
  }

  public CANSparkMax getDriveRight() {
    return fr;
  }

  public CANSparkMax getDriveRearLeft() {
    return bl;
  }

  public CANSparkMax getDriveRearRight() {
    return br;
  }

  public CANSparkMax climbArm() {
    return climbArm;
  }

  public CANSparkMax verticalLoader() {
    return verticalLoader;
  }

  public CANSparkMax outerIntakeBack() {
    return outerIntakeBack;
  }

  public CANSparkMax outerIntakeFront() {
    return outerIntakeFront;
  }

  public CANSparkMax meteringWheel() {
    return meteringWheel;
  }

  public CANSparkMax leftShooterWheel() {
    return leftShooterWheel;
  }

  public CANSparkMax rightShooterWheel() {
    return rightShooterWheel;
  }

  public Turret turret() {
    return turret;
  }

  public CANSparkMax colorWheel() {
    return colorWheel;
  }

  public CANSparkMax climb1() {
    return climb1;
  }

  public CANSparkMax climb2() {
    return climb2;
  }

  public CANSparkMax bottomConveyor() {
    return bottomConveyor;
  }

  public Servo getElevatorRight() {
    return elevatorRight;
  }

  public Servo getElevatorLeft() {
    return elevatorLeft;
  }

  public LOAD_STATE getBallState() {
    final double voltage = ballDistance.distance();
    if (voltage >= 3) {
      return LOAD_STATE.ballLoaded;
    } else if (voltage >= .5) {
      return LOAD_STATE.ballPresent;
    } else {
      return LOAD_STATE.noBall;
    }
  }

  // shouldn't need to access this from anywhere else
  /*
   * public DifferentialDrive driveControl(){ return driveControl; }
   */

  public Joystick leftJoystick() {
    return leftJoystick;
  }

  public Joystick rightJoystick() {
    return rightJoystick;
  }

  public ADXRS450_Gyro gyro() {
    return gyro;
  }

  private CANSparkMax attemptGetMotor(final int i) {
    try {
      return new CANSparkMax(i, MotorType.kBrushless);
    } catch (final Exception e) {
      return null;
    }
  }

  public double getTuningValue(final String key) {
    return tuningValues.get(key);
  }

  public void setTuningValue(final String key, final int value) {
    tuningValues.replace(key, (double) value);
  }

  public void setTuningValue(final String key, final double value) {
    tuningValues.replace(key, value);
  }

  public String[] getKeys() {
    final String[] keys = new String[tuningValues.keySet().size()];
    tuningValues.keySet().toArray(keys);
    return keys;
  }

  private void startAuton() {
    switch (tuningValues.get("sequence").intValue()) {
    case 1:
      autonSequence = /* new Dance(Music.bpmTomspb(65)); */new AutonEventSequence2();
      break;
    case 2:
      autonSequence = new AutoRedA();
      break;
    case 3:
      autonSequence = new AutoRedB();
      break;
    }
    eHandler.triggerEvent(autonSequence);
  }

  private void stopAuton() {
    try {
      autonSequence.terminate();
    } catch (final NullPointerException e) {

    }
    stopEverything();
  }
  
  public void stopEverything(){
    colorWheel().set(0);
    bottomConveyor().set(0);
    verticalLoader().set(0);
    meteringWheel().set(0);
    outerIntakeBack().set(0);
    outerIntakeFront().set(0);
    getLimelight().disable();
    turret().set(0);
    leftShooterWheel.set(0);
    rightShooterWheel.set(0);
    fl.set(0);
    fr.set(0);
    bl.set(0);
    br.set(0);
  }
}


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
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.CAN;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.buttons.ButtonHandler;
import frc.robot.buttons.JLSBAdapter;
import frc.robot.buttons.JRSBAdapter;
import frc.robot.buttons.TSBAdapter;
import frc.robot.event.EventHandler;
import frc.robot.event.customevents.DriveEvent;
import frc.robot.event.customevents.LimelightEvent;
import frc.robot.event.customevents.PrintEvent;
import frc.robot.motors.JEPLG;
import frc.robot.motors.Neo550;
import frc.robot.motors.Pro775;
import frc.robot.motors.Turret;

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
  public static enum DRIVE_MODE {twoStickArcade,oneStickArcade,tank};
  DRIVE_MODE driveMode=DRIVE_MODE.twoStickArcade;
  private CANSparkMax br = attemptGetMotor(13);//new CANSparkMax(12, MotorType.kBrushless);
  private CANSparkMax fr = attemptGetMotor(12);//new CANSparkMax(13, MotorType.kBrushless);
  private CANSparkMax fl = attemptGetMotor(11);//new CANSparkMax(11, MotorType.kBrushless);
  private CANSparkMax bl = attemptGetMotor(10);//new CANSparkMax(10, MotorType.kBrushless);
  
  private CANSparkMax bottomConveyor=new Neo550(17);//attemptGetMotor(17);//null;//8new CANSparkMax(/*17*/50, MotorType.kBrushless);
  //550

  private CANSparkMax climbArm=new Neo550(16);//attemptGetMotor(16);//new CANSparkMax(16, MotorType.kBrushless);
  //550

  private CANSparkMax verticalLoader=new Neo550(18);//null; //new CANSparkMax(18, MotorType.kBrushless);
  //550

  private CANSparkMax outerIntakeBack = new Pro775(14);//null; //new CANSparkMax(14,MotorType.kBrushless);
  //775 pro
  
  private CANSparkMax outerIntakeFront = new Pro775(15); //null; // new CANSparkMax(15,MotorType.kBrushless);
  //775 pro

  private CANSparkMax meteringWheel = new Neo550(19);//null; //new CANSparkMax(19,MotorType.kBrushless);
  //550 neo

  private CANSparkMax leftShooterWheel = attemptGetMotor(21);//null; //new CANSparkMax(20,MotorType.kBrushless);
  //neo

  private CANSparkMax rightShooterWheel =attemptGetMotor(20);//null;//new CANSparkMax(21,MotorType.kBrushless);
  //neo

  private CANSparkMax turret;//new CANSparkMax(/*22*/18,MotorType.kBrushless);//17 for testing
  //550

  private CANSparkMax colorWheel = new JEPLG(23);//null;//new CANSparkMax(23,MotorType.kBrushless);
  //JE PLG andymark

  //shooter elevator going to be two servos
  private Servo elevatorLeft = new Servo(0);
  private Servo elevatorRight = new Servo(1);
  
  public Servo getElevatorRight(){
    return elevatorRight;
  }

  public Servo getElevatorLeft(){
    return elevatorLeft;
  }

  private CANSparkMax climb1 =attemptGetMotor(24);// null;//new CANSparkMax(24,MotorType.kBrushless);
  //neo

  private CANSparkMax climb2 = attemptGetMotor(25);//null;//new CANSparkMax(25,MotorType.kBrushless);
  //neo

  DifferentialDrive driveControl;
  Joystick leftJoystick = new Joystick(0);
  Joystick rightJoystick = new Joystick(1);
  Joystick tractorJoystick = new Joystick(2);
  JLSBAdapter leftJoystickAdapter = new JLSBAdapter(leftJoystick, this);
  JRSBAdapter rightJoystickAdapter = new JRSBAdapter(rightJoystick, this);
  TSBAdapter tractorAdapter;

  private Hashtable
  <String, Double> tuningValues;

  public static EventHandler eHandler=new EventHandler();
  
  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit() {
    instance=this;
    
    //centerIntakeFront.clearFaults();
    eHandler.start();
    driveControl = new DifferentialDrive(fl, fr);
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

    leftShooterWheel.setIdleMode(IdleMode.kCoast);
    rightShooterWheel.setIdleMode(IdleMode.kCoast);

    //getElevatorRight().set(getElevatorLeft().getSpeed());

    tuningValues=new Hashtable<>();

    //add tuning values
    tuningValues.put("climb", .5);
    tuningValues.put("drive", 1d);
    tuningValues.put("driveP", 1d);
    tuningValues.put("conveyor", .8);
    tuningValues.put("verticalIntake",0d);//.9);
    tuningValues.put("meteringWheel", 1d);
    tuningValues.put("turret", .2);
    tuningValues.put("shoot", 1d);
    tuningValues.put("shooterElevation", .1);
    tuningValues.put("turretDefaultMaxSpeed",.2);
    tuningValues.put("turretMaxPos",43.76);
    tuningValues.put("turretMinPos", -47.07);
    tuningValues.put("intake", .8);
    turret=new Turret(22);

    UsbCamera front=CameraServer.getInstance().startAutomaticCapture();
    
    tractorAdapter= new TSBAdapter(tractorJoystick, this);
    DriveEvent.configure(.2032, 11);
  }

  public Limelight getLimelight(){
    return limelight;
  }

  @Override
  public void autonomousInit() {
    //limelight.enable();
    
    try{
      tractorAdapter.setMode(TSBAdapter.Mode.Tune);
    } catch (Exception e){
      
    }
    
  }

  @Override
  public void autonomousPeriodic() {
    fl.set(.4);
  }

  @Override
  public void teleopInit() {
    //limelight.enable();
    
    try{
      tractorAdapter.setMode(TSBAdapter.Mode.RobotResponse);
    } catch (Exception e){
      
    }
  }

  @Override
  public void teleopPeriodic() {
    //printMotorTemps();//224 not normal temp, 45 and below is normal
    //checkMotorTemps();//disables motors above 260 deg F internal
    tractorAdapter.update();
    leftJoystickAdapter.update();
    rightJoystickAdapter.update();
    double rightY=rightJoystick.getY()*-1;
    double rightX=rightJoystick.getX();
    double leftY=leftJoystick.getY()*-1;
    double leftX=leftJoystick.getX();
    if (rightY<.1 && rightY>-.1){
      rightY=0;
    }
    if (rightX<.1 && rightX>-.1){
      rightX=0;
    }
      if (leftY<.1 && leftY>-.1){
      leftY=0;
    }
      if (leftX<.1 && leftX>-.1){
      leftX=0;
    }
    switch (driveMode){
      case twoStickArcade:
        driveControl.arcadeDrive(rightY, leftX);
        //eHandler.triggerEvent(new PrintEvent("arcade driving"));
      break;
      case oneStickArcade:
        driveControl.arcadeDrive(rightY, rightX);
      break;
      case tank:
        driveControl.tankDrive(leftY, rightY);
      break;
    }
    
}

  @Override
  public void testInit() {
    try{
      tractorAdapter.setMode(TSBAdapter.Mode.RobotResponse);
    } catch (Exception e){
      
    }
  }

  @Override
  public void testPeriodic() {
  }

  @Override
  public void disabledInit(){
    limelight.disable();
    try{
      tractorAdapter.setMode(TSBAdapter.Mode.Tune);
    } catch (Exception e){

    }
  }

  @Override
  public void disabledPeriodic(){
    tractorAdapter.update();
  }

  public void setDriveMode(DRIVE_MODE d){
    driveMode=d;
  }

  public DRIVE_MODE getDriveMode(){
    return driveMode;
  }

  public static Robot getInstance(){
    return instance;
  }

  public void printMotorTemps(){
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

  public void printMotorPositions(){
    eHandler.triggerEvent(new PrintEvent(turret.getEncoder().getPosition()));
  }

  public void checkMotorTemps(){
    if (fr.getMotorTemperature()>260){
      fr.disable();
    }
    if (fl.getMotorTemperature()>260){
      fl.disable();
    }
    if (br.getMotorTemperature()>260){
      br.disable();
    }
    if (bl.getMotorTemperature()>260){
      bl.disable();
    }
    if (bottomConveyor.getMotorTemperature()>260){
      bottomConveyor.disable();
    }
    if (climbArm.getMotorTemperature()>260){
      climbArm.disable();
    }
    if (verticalLoader.getMotorTemperature()>260){
      verticalLoader.disable();
    }
    if (outerIntakeBack.getMotorTemperature()>260){
      outerIntakeBack.disable();
    }
    if (outerIntakeFront.getMotorTemperature()>260){
      outerIntakeFront.disable();
    }
    if (meteringWheel.getMotorTemperature()>260){
      meteringWheel.disable();
    }
    if (leftShooterWheel.getMotorTemperature()>260){
      leftShooterWheel.disable();
    }
    if (rightShooterWheel.getMotorTemperature()>260){
      rightShooterWheel.disable();
    }
    if (turret.getMotorTemperature()>260){
      turret.disable();
    }
    if (colorWheel.getMotorTemperature()>260){
      colorWheel.disable();
    }
    if (climb1.getMotorTemperature()>260){
      climb1.disable();
    }
    if (climb2.getMotorTemperature()>260){
      climb2.disable();
    }
  }
  public CANSparkMax getDriveLeft(){
    return fl;
  }
  public CANSparkMax getDriveRight(){
    return fr;
  }

  public CANSparkMax getDriveRearLeft(){
    return bl;
  }

  public CANSparkMax getDriveRearRight(){
    return br;
  }

  public CANSparkMax climbArm(){
    return  climbArm; 
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

  public CANSparkMax colorWheel(){
    return colorWheel; 
  }

  public CANSparkMax climb1(){
    return climb1;
  }

  public CANSparkMax climb2(){
    return  climb2; 
  }

  public CANSparkMax botomConveyor(){
    return bottomConveyor;
  }
 

  
  //shouldn't need to access this from anywhere else
  /*public DifferentialDrive driveControl(){
    return driveControl;
  }*/

  public Joystick leftJoystick(){
    return leftJoystick;
  }

  public Joystick rightJoystick(){
    return rightJoystick;
  }

  private CANSparkMax attemptGetMotor(int i){
    try{
      return new CANSparkMax(i, MotorType.kBrushless);
    }catch(Exception e){
      return null;
    }
  }

  public double getTuningValue(String key){
    return tuningValues.get(key);
  }

  public void setTuningValue(String key,int value){
    tuningValues.replace(key,(double)value);
  }
  public void setTuningValue(String key,double value){
    tuningValues.replace(key,value);
  }

  public String[] getKeys(){
    String[] keys=new String[tuningValues.keySet().size()];
    tuningValues.keySet().toArray(keys);
    return keys;
  }

}


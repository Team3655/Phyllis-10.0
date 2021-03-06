package frc.robot.buttons;



import java.util.Hashtable;

import com.revrobotics.ControlType;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import frc.robot.Limelight;
import frc.robot.Robot;
import frc.robot.event.Event;
import frc.robot.event.EventSequence;
import frc.robot.event.customevents.PrintEvent;
/**Tractor Simulator Button Adapter for long
 * 
 */
public class TSBAdapter extends ButtonHandler{
    private Robot robot;
    public enum Mode{RobotResponse,Tune,RobotRecord,Test};
    private enum ControlMode{Joystick,PID};
    private ControlMode elevatorControlMode;
    private ControlMode armControlMode;
    private Mode mode;
    private String[] tuningValues=Robot.getInstance().getKeys();
    private int currentPropertyNo;
    private String currentTuningValue;
    private String inputCache;
    private Joystick armJoystick;


    public TSBAdapter(Joystick tractorPanel, Robot robot){
        super(tractorPanel,28); //button 28 is the red button on the joystick and button 27 is press on wheel (those buttons aren't labled on the panel)
        this.robot=robot;
        mode=Mode.RobotResponse;
        currentPropertyNo=0;
        currentTuningValue=tuningValues[currentPropertyNo];
        inputCache="";
        elevatorControlMode=ControlMode.Joystick;
        armControlMode=ControlMode.PID;
        //setArmJoystick(getJoystick());
    }
    public void buttonPressed(int no){
        if (mode==Mode.RobotResponse&&robot.isEnabled()){
            switch (no){
                //Outer intake in
                case 1://intake
                    robot.outerIntakeFront().set(robot.getTuningValue("intake"));
                    robot.outerIntakeBack().set(robot.getTuningValue("intake")*-1);
                break;
                //Conveyor in
                case 2:
                    robot.bottomConveyor().set(robot.getTuningValue("conveyor")*-1);
                break;
                //Load shooter in
                case 3:
                    robot.verticalLoader().set(robot.getTuningValue("verticalIntake")*-1);
                    //robot.meteringWheel().getPIDController().setReference(robot.getTuningValue("meteringWheel")*-1,ControlType.kVelocity);
                    //robot.meteringWheel().set(robot.getTuningValue("meteringWheel")*-1);
                break;
                //climb 1&2 up (winch up)
                case 4:
                    robot.climb1().set(robot.getTuningValue("climb"));
                    //robot.climb2().set(robot.getTuningValue("climb"));
                break;
                //null
                //case 5:
                    
                //break;
                //Outer intake out
                case 6:
                    robot.outerIntakeFront().set(robot.getTuningValue("intake")*-1);
                    robot.outerIntakeBack().set(robot.getTuningValue("intake"));
                break;
                //Conveyor out
                case 7:
                    robot.bottomConveyor().set(robot.getTuningValue("conveyor"));
                break;
                //Load shooter out
                case 8:
                    robot.verticalLoader().set(robot.getTuningValue("verticalIntake"));
                    robot.meteringWheel().getPIDController().setReference(robot.getTuningValue("meteringWheel"),ControlType.kVelocity);
                    //robot.meteringWheel().set(robot.getTuningValue("meteringWheel"));
                break;
                //Climb 1&2 down
                case 9:
                    //climb 1&2 down
                    robot.climb1().set(robot.getTuningValue("climb")*-1);
                    //robot.climb2().set(robot.getTuningValue("climb")*-1);
                break;
                //null
                //case 10:
                //break;
                //passthrough backward
                case 11:
                    robot.outerIntakeFront().set(robot.getTuningValue("passThrough")*-1);
                    robot.outerIntakeBack().set(robot.getTuningValue("passThrough")*-1);
                                 
                break;//passthrough forward
                case 12:
                    robot.outerIntakeFront().set(robot.getTuningValue("passThrough"));
                    robot.outerIntakeBack().set(robot.getTuningValue("passThrough"));
                break;
                case 13://everything in
                    robot.outerIntakeFront().set(robot.getTuningValue("intake"));
                    robot.outerIntakeBack().set(robot.getTuningValue("intake")*-1);
                    robot.bottomConveyor().set(-.6);
                    //vertical loader set in button down function
                    
                break;
                //everything out
                case 14:
                    robot.outerIntakeFront().set(robot.getTuningValue("intake"));
                    robot.outerIntakeBack().set(robot.getTuningValue("intake")*-1);
                    robot.bottomConveyor().set(robot.getTuningValue("conveyor"));
                    robot.verticalLoader().set(robot.getTuningValue("verticalIntake"));
                break;
                //shoot
                case 15:
                    robot.meteringWheel().getPIDController().setReference(robot.getTuningValue("meteringWheel")*-1,ControlType.kVelocity);
                    robot.leftShooterWheel().getPIDController().setReference(robot.getTuningValue("shoot")*-1, ControlType.kVelocity);
                    robot.rightShooterWheel().getPIDController().setReference(robot.getTuningValue("shoot"), ControlType.kVelocity);
                break;
                //null
                //case 16:

                //break;
                //Color wheel
                case 17:
                    robot.setTuningValue("meteringWheel", -7000d);
                    robot.setTuningValue("shoot", 4250d);
                break;
                //Color wheel
                case 18://temp climb arm up
                    robot.climbArm().set(robot.getTuningValue("climbArmUp"));
                break; 
                //Color wheel
                case 19:
                    robot.setTuningValue("meteringWheel", -8000d);
                    robot.setTuningValue("shoot", 5500d);
                break;
                //color wheel
                case 20://temp climb arm down
                    robot.climbArm().set(robot.getTuningValue("climbArmDown")*-1);
                break;
                //null
                //case 21:

                //break;
                //manual override shooting controls 
                case 22:
                    robot.getLimelight().setEnabled(!robot.getLimelight().isEnabled());
                    robot.eHandler.triggerEvent(new PrintEvent("Manual controls boolean set to "+String.valueOf(!robot.getLimelight().isEnabled())+"."));
                break;
                //everything off
                case 23:
                    robot.stopEverything();
                break;
                //null
                case 24:
                    //robot.getInstance().printMotorPositions();
                    System.out.println(robot.getBallState().toString());
                break;
                //null
                case 25:

                break;
                //lower shooter elevation
                case 26: {
                    /*if (!robot.getLimelight().isEnabled()){
                        robot.getElevatorLeft().set(.1);
                        robot.getElevatorLeft().setPosition(robot.getElevatorLeft().getPosition()-1);
                    } else {
                        robot.eHandler.triggerEvent(new PrintEvent("Manual controls not enabled."));
                    }*/
                    double newTarget=robot.getElevatorRight().get()+robot.getTuningValue("elevator");
                    double max=robot.getTuningValue("elevatorMaxPos");
                    double min=robot.getTuningValue("elevatorMinPos");
                    if (newTarget>max){
                        newTarget=max;
                    } else if (newTarget<min){
                        newTarget=min;
                    }
                    //System.out.println(newTarget);
                    robot.getElevatorLeft().set(newTarget);
                    robot.getElevatorRight().set(newTarget);
                } break;
                //raise shooter elevation
                case 27: {
                    /*if (!robot.getLimelight().isEnabled()){
                        robot.getElevatorLeft().setPosition(robot.getElevatorLeft().getPosition()+1);
                        robot.getElevatorLeft().setPosition(robot.getElevatorLeft().getPosition()+1);
                    } else {
                        robot.eHandler.triggerEvent(new PrintEvent("Manual controls not enabled."));
                    }*/

                    double newTarget=robot.getElevatorLeft().get()-robot.getTuningValue("elevator");
                    double max=robot.getTuningValue("elevatorMaxPos");
                    double min=robot.getTuningValue("elevatorMinPos");
                    if (newTarget>max){
                        newTarget=max;
                    } else if (newTarget<min){
                        newTarget=min;
                    }
                    robot.getElevatorLeft().set(newTarget);
                    robot.getElevatorRight().set(newTarget);
                } break;
                //change mode
                case 28:
                    mode=Mode.Tune;
                break;

            }
        } else if (mode==Mode.Tune) {
            if (no<10){
                //if (!this.getButtonDown(27)){
                    inputCache=inputCache+no;
                    Robot.eHandler.triggerEvent(new PrintEvent("Input Cache: "+inputCache));
                //} else {
                    /*if (no==1){
                        //robot.setTuningValue("eTop", robot.elevatorPos());
                        if (robot.isEnabled()){
                            mode=Mode.RobotResponse;
                            Robot.eHandler.triggerEvent(new PrintEvent("Mode set to 'RobotResponse'"));
                        }
                    } else if (no==2){
                        //robot.setTuningValue("eMid", robot.elevatorPos());
                        if (robot.isEnabled()){
                            mode=Mode.RobotResponse;
                            Robot.eHandler.triggerEvent(new PrintEvent("Mode set to 'RobotResponse'"));
                        }
                    } else if (no==3){
                        //robot.setTuningValue("eHat", robot.elevatorPos());
                        if (robot.isEnabled()){
                            mode=Mode.RobotResponse;
                            Robot.eHandler.triggerEvent(new PrintEvent("Mode set to 'RobotResponse'"));
                        }
                    }*/
                //}
                
            } else if (no<28) {
                switch (no){
                    case 10:
                        inputCache=inputCache+0;
                        Robot.eHandler.triggerEvent(new PrintEvent("Input Cache: "+inputCache));
                    break;
                    case 11:
                        try {
                            inputCache=inputCache.substring(0, inputCache.length()-1);
                        } catch (Exception e){
                        }
                        Robot.eHandler.triggerEvent(new PrintEvent("Input Cache: "+inputCache));
                    break;
                    case 12:
                        if (!inputCache.contains(".")){
                            inputCache=inputCache+".";
                            Robot.eHandler.triggerEvent(new PrintEvent("Input Cache: "+inputCache));
                        }
                    break;
                    /*case 15:
                        if (getButtonDown(28)){
                            //robot.setTuningValue("aHat", robot.armPos());
                            if (robot.isEnabled()){
                                mode=Mode.RobotResponse;
                                Robot.eHandler.triggerEvent(new PrintEvent("Mode set to 'RobotResponse'"));
                            }
                        }
                    break;*/
                    /*case 13:
                        if (getButtonDown(28)){
                            //robot.setTuningValue("eHat", robot.elevatorPos());
                            if (robot.isEnabled()){
                                mode=Mode.RobotResponse;
                                Robot.eHandler.triggerEvent(new PrintEvent("Mode set to 'RobotResponse'"));
                            }
                        }
                    break;
                    case 16:
                    if (getButtonDown(28)){
                        //robot.setTuningValue("aBal", robot.elevatorPos());
                        if (robot.isEnabled()){
                            mode=Mode.RobotResponse;
                            Robot.eHandler.triggerEvent(new PrintEvent("Mode set to 'RobotResponse'"));
                        }
                    }
                    break;*/
                    case 17:
                        if (!inputCache.contains("-")){
                            inputCache="-"+inputCache;
                            Robot.eHandler.triggerEvent(new PrintEvent("Input Cache: "+inputCache));
                        } else {
                            inputCache=inputCache.substring(1);
                        }
                    break;


                    /*case 19:
                        Event[] printNos={new PrintEvent(4),new PrintEvent(3),new PrintEvent(2),new PrintEvent(1),new PrintEvent(0),new PrintEvent("Good Job!"),new PrintEvent(-1)};
                        Robot.eHandler.triggerEvent(new EventSequence(printNos));
                    break;*/
                    case 20:
                        Robot.eHandler.clear();
                    break;
                                    

                    //Button 21 set value to input
                    case 21:
                        try {
                            robot.setTuningValue(currentTuningValue, Double.parseDouble(inputCache));
                            Robot.eHandler.triggerEvent(new PrintEvent(currentTuningValue+" set to "+inputCache));
                            inputCache="";
                            //robot.elevatorPID(robot.getTuningValue("eP"), robot.getTuningValue("eI"), robot.getTuningValue("eD"),robot.getTuningValue("eFF"));
                        } catch (NumberFormatException e){
                            //robot.setProp(currentTuningValue, 0);
                            Robot.eHandler.triggerEvent(new PrintEvent("User did not enter a number"));
                            //System.err.println(currentTuningValue+" defaulted to 0");
                            inputCache="";
                        }
                    break;
                    case 22:
                        //robot.printSensorPositions();
                        //robot.printMotorTemps();
                    break;
                    case 23:
                        //turn everything off
                        robot.colorWheel().set(0);
                        robot.bottomConveyor().set(0);
                        robot.verticalLoader().set(0);
                        robot.meteringWheel().set(0);
                        robot.outerIntakeBack().set(0);
                        robot.outerIntakeFront().set(0);
                        robot.getLimelight().disable();
                        robot.turret().set(0);
                    break;
                    case 24:
                        //print current tuning value
                        Robot.eHandler.triggerEvent(new PrintEvent("Current value of "+currentTuningValue+": "+robot.getTuningValue(currentTuningValue)));
                        //setTuningValues
                        //Robot.eHandler.triggerEvent(new PrintEvent("TUNING VALUES SET TO TEST ROBOT"));
                    break;
                    case 25:
                        //Robot.eHandler.triggerEvent(new PrintEvent("Current value of "+currentTuningValue+": "+robot.getTuningValue(currentTuningValue)));
                    break;
                    //button 26 changes what property you are editing (++)
                    case 27:
                        currentPropertyNo++;
                        if (currentPropertyNo>=tuningValues.length){
                            currentPropertyNo=0;
                        }
                        currentTuningValue=tuningValues[currentPropertyNo];
                        //System.out.println("Now edititing "+currentTuningValue);
                        if (!Robot.eHandler.triggerEvent(new PrintEvent("Now edititing "+currentTuningValue))){
                            System.err.println("Print failed to queue");
                        }
                    break;
                    //button 26 changes what property you are editing (--)
                    case 26:
                        currentPropertyNo--;
                        if (currentPropertyNo<0){
                            currentPropertyNo=tuningValues.length-1;
                        }
                        currentTuningValue=tuningValues[currentPropertyNo];
                        //System.out.println("Now edititing "+currentTuningValue);
                        if (!Robot.eHandler.triggerEvent(new PrintEvent("Now editing"+currentTuningValue))){
                            System.err.println("Print failed to queue");
                        }
                    break;
                }
             } else {
                switch (no){
                case 28:
                    /*if (robot.isEnabled()){
                        mode=Mode.RobotResponse;
                        Robot.eHandler.triggerEvent(new PrintEvent("Mode set to 'RobotResponse'"));
                    } else {
                        Robot.eHandler.triggerEvent(new PrintEvent("RobotResponse mode not available while robot is disabled",true));
                    }*/
                    if (robot.isEnabled()){
                        mode=Mode.Test;
                        Robot.eHandler.triggerEvent(new PrintEvent("Mode set to 'Test'"));
                    } else {
                        Robot.eHandler.triggerEvent(new PrintEvent("Test mode not available while robot is disabled",true));
                    }
                break;
                }
            }
        }  else if (mode==Mode.Test){
            switch (no){
                case 1:
                    robot.outerIntakeFront().set(robot.getTuningValue("intake"));
                break;
                case 2:
                    robot.outerIntakeFront().set(robot.getTuningValue("intake")*-1);
                break;
                case 3:
                    robot.bottomConveyor().set(robot.getTuningValue("conveyor"));
                break;
                case 4:
                    robot.verticalLoader().set(robot.getTuningValue("verticalIntake"));
                break;
                case 5:
                    robot.meteringWheel().set(robot.getTuningValue("meteringWheel")*-1);
                break;
                case 6:
                    robot.outerIntakeBack().set(robot.getTuningValue("intake")*-1);
                break;
                case 7:
                    robot.outerIntakeBack().set(robot.getTuningValue("intake"));
                break;
                case 8:
                    robot.bottomConveyor().set(robot.getTuningValue("conveyor")*-1);
                break;
                case 9:
                    robot.verticalLoader().set(robot.getTuningValue("verticalIntake")*-1);
                break;
                case 10:
                    robot.leftShooterWheel().set(robot.getTuningValue("shoot")*-1);
                    robot.rightShooterWheel().set(robot.getTuningValue("shoot"));
                break;
                case 28:
                    if (robot.isEnabled()){
                        mode=Mode.RobotResponse;
                        Robot.eHandler.triggerEvent(new PrintEvent("Mode set to 'RobotResponse'"));
                    } else {
                        mode=Mode.Tune;
                        Robot.eHandler.triggerEvent(new PrintEvent("RobotResponse mode not available while robot is disabled -- neither is test. Mode set to 'Tune'",true));
                    }
                break;
            }
        }
    }
    public void buttonReleased(int no){
        if (mode==Mode.RobotResponse){
            switch (no){
                //Outer intake in
                case 1://intake
                    robot.outerIntakeFront().set(0);
                    robot.outerIntakeBack().set(0);
                break;
                //Conveyor in
                case 2:
                    robot.bottomConveyor().set(0);
                break;
                //Load shooter in
                case 3:
                    robot.verticalLoader().set(0);
                    //robot.meteringWheel().set(0);
                break;
                //climb 1&2 up (winch up)
                case 4:
                    robot.climb1().set(0);
                    //robot.climb2().set(0);
                break;
                //null
                //case 5:
                    
                //break;
                //Outer intake out
                case 6:
                    robot.outerIntakeFront().set(0);
                    robot.outerIntakeBack().set(0);
                break;
                //Conveyor out
                case 7:
                    robot.bottomConveyor().set(0);
                break;
                //Load shooter out
                case 8:
                    robot.verticalLoader().set(0);
                    robot.meteringWheel().set(0);
                break;
                //Climb 1&2 down
                case 9:
                    //climb 1&2 down
                    robot.climb1().set(0);
                    //robot.climb2().set(0);
                break;
                //null
                //case 10:
                //break;
                //passthrough backward
                case 11:
                    robot.outerIntakeFront().set(0);
                    robot.outerIntakeBack().set(0);
                                 
                break;//passthrough forward
                case 12:
                    robot.outerIntakeFront().set(0);
                    robot.outerIntakeBack().set(0);
                break;
                case 13://everything in
                    robot.outerIntakeFront().set(0);
                    robot.outerIntakeBack().set(0);
                    robot.bottomConveyor().set(0);
                    robot.verticalLoader().set(0);
                    
                break;
                //everything out
                case 14:
                    robot.outerIntakeFront().set(0);
                    robot.outerIntakeBack().set(0);
                    robot.bottomConveyor().set(0);
                    robot.verticalLoader().set(0);
                    robot.leftShooterWheel().set(0);
                    robot.rightShooterWheel().set(0);
                    robot.meteringWheel().set(0);
                    robot.getDriveLeft().set(0);
                    robot.getDriveRight().set(0);
                break;
                //shoot
                case 15:
                    robot.leftShooterWheel().set(0);
                    robot.rightShooterWheel().set(0);
                    robot.meteringWheel().set(0);
                break;
                //null
                //case 16:

                //break;
                //Color wheel
                case 17:

                break;
                //Color wheel
                case 18://temp climb arm up
                    robot.climbArm().set(0);
                break; 
                //Color wheel
                case 19:

                break;
                //color wheel
                case 20://temp climb arm down
                    robot.climbArm().set(0);
                break;
                //null
                //case 21:

                //break;
                //manual override shooting controls 
                case 22:
                    ///////nothing when released
                break;
                //null
                //case 23:

                //break;
                //null
                //case 24:

                //break;
                //null
                //case 25:

                //break;
                //lower shooter elevation
                case 26:
                    ///////////////////////////////////////need to do stuff here
                break;
                //raise shooter elevation
                case 27:
                    //////////nothing on release
                break;

                
            }
        } else if (mode==Mode.Test){
            switch (no){
                case 1:
                    robot.outerIntakeFront().set(0);
                break;
                case 2:
                    robot.outerIntakeFront().set(0);
                break;
                case 3:
                    robot.bottomConveyor().set(0);
                break;
                case 4:
                    robot.verticalLoader().set(0);
                break;
                case 5:
                    robot.meteringWheel().set(0);
                break;
                case 6:
                    robot.outerIntakeBack().set(0);
                break;
                case 7:
                    robot.outerIntakeBack().set(0);
                break;
                case 8:
                    robot.bottomConveyor().set(0);
                break;
                case 9:
                    robot.verticalLoader().set(0);
                break;
                case 10:
                    robot.leftShooterWheel().set(0);
                    robot.rightShooterWheel().set(0);
                break;
            }
        }
    }
    public void buttonDown(int no){
        if (mode==Mode.RobotResponse){
            switch(no){
                //everything in (vertical loader only if ball loaded)
                case 13:
                    //double check states before testing
                    if (robot.getBallState()==Robot.LOAD_STATE.ballPresent){
                        robot.verticalLoader().set(-.6);
                    } else {
                        robot.verticalLoader().set(0);
                    }
                break;
                /*case 2:
                    robot.verticalLoader().set(0);
                    robot.meteringWheel().set(0);
                break;
                //TODO mass eject button, set every loading motor to its opposite
                /*case 12:
                robot.verticalLoader().set(.2);
                robot.meteringWheel().set(0);
                break;
                */
            }
        }
    }

    @Override
    public void update() {
        super.update();
        //TODO shooter elevation with wheel
        //turret with joystick twist check //want to be get z but z is wonky on this controller
        if ((!robot.getLimelight().isEnabled()||!robot.getLimelight().hasTarget())&&mode==Mode.RobotResponse){
            /*f (Math.abs(getZ())<.9){
                robot.turret().holdPos();
            } else {*/
                robot.turret().set(getZ()*Robot.getInstance().getTuningValue("turretDefaultMaxSpeed"));
            //}
            //robot.eHandler.triggerEvent(new PrintEvent("Z"+getZ()));
        }
    }

    public void setMode(Mode mode){
        this.mode=mode;
    }

}
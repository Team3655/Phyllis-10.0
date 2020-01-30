package frc.robot.buttons;



import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import frc.robot.Robot;
import frc.robot.event.Event;
import frc.robot.event.EventSequence;
import frc.robot.event.customevents.PrintEvent;
/**Tractor Simulator Button Adapter for long
 * 
 */
public class TSBAdapter extends ButtonHandler{
    private Robot robot;
    public enum Mode{RobotResponse,Tune,RobotRecord};
    private enum ControlMode{Joystick,PID};
    private ControlMode elevatorControlMode;
    private ControlMode armControlMode;
    private Mode mode;
    private String[] tuningValues={"eTop","eBot","eMid","eCar","eHat","aCar","aHat","aBal","aSit","aDec","lTop","lBot","aPIDOR","lPIDOR","ePIDORUp","ePIDORDow","eSpdRPM","eAclRPMS","eSpdUp","eSpdDow","lSpdRPM","lAclRPMS","lSpdUp","lSpdDow","eSpdJ","aSpd","aSpdJ","iSpd","eCurUp","eCurDow","eCurPID","eCurJoy","lCur","aCur","eErr","eP","eI","eD","eFF","port"};
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
                case 4:

                break;
                case 9:
                    robot.centerIntakeFront().set(.5);
                break;

            }
        } else if (mode==Mode.Tune) {
            if (no<10){
                if (!this.getButtonDown(27)){
                    inputCache=inputCache+no;
                Robot.eHandler.triggerEvent(new PrintEvent("Input Cache: "+inputCache));
                } else {
                    if (no==1){
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
                    }
                }
                
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
                    case 15:
                        if (getButtonDown(28)){
                            //robot.setTuningValue("aHat", robot.armPos());
                            if (robot.isEnabled()){
                                mode=Mode.RobotResponse;
                                Robot.eHandler.triggerEvent(new PrintEvent("Mode set to 'RobotResponse'"));
                            }
                        }
                    break;
                    case 13:
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
                    break;
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
                    break;
                    case 20:
                        Event[] printNos1={new PrintEvent(1),new PrintEvent(2,500,false),new PrintEvent(3,1000,false),new PrintEvent(4,1000,false)};
                        Robot.eHandler.triggerEvent(new EventSequence(printNos1));
                    break;*/
                                    

                    //Button 21 set value to input
                    case 21:
                        try {
                            //robot.setTuningValue(currentTuningValue, Double.parseDouble(inputCache));
                            Robot.eHandler.triggerEvent(new PrintEvent(currentTuningValue+" set to "+inputCache));
                            inputCache="";
                            //robot.elevatorPID(robot.getTuningValue("eP"), robot.getTuningValue("eI"), robot.getTuningValue("eD"),robot.getTuningValue("eFF"));
                        } catch (NumberFormatException e){
                            //robot.setProp(currentTuningValue, 0);
                            Robot.eHandler.triggerEvent(new PrintEvent("User did not enter a number"));
                            //System.err.println(currentTuningValue+" defaulted to 0");
                            //inputCache="";
                        }
                    break;
                    case 22:
                        //turn everything off
                    break;
                    case 23:
                        //robot.printSensorPositions();
                    break;
                    case 24:
                        //setTuningValues
                        Robot.eHandler.triggerEvent(new PrintEvent("TUNING VALUES SET TO TEST ROBOT"));
                    break;
                    case 25:
                        //Robot.eHandler.triggerEvent(new PrintEvent("Current value of "+currentTuningValue+": "+robot.getTuningValue(currentTuningValue)));
                    break;
                    //button 26 changes what property you are editing (++)
                    case 27:
                        currentPropertyNo++;
                        if (currentPropertyNo>39){
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
                            currentPropertyNo=39;
                        }
                        currentTuningValue=tuningValues[currentPropertyNo];
                        //System.out.println("Now edititing "+currentTuningValue);
                        if (!robot.eHandler.triggerEvent(new PrintEvent("Now editing"+currentTuningValue))){
                            System.err.println("Print failed to queue");
                        }
                    break;
                }
             } else {
                switch (no){
                case 28:
                    if (robot.isEnabled()){
                        mode=Mode.RobotResponse;
                        Robot.eHandler.triggerEvent(new PrintEvent("Mode set to 'RobotResponse'"));
                    } else {
                        Robot.eHandler.triggerEvent(new PrintEvent("RobotResponse mode not available while robot is disabled",true));
                    }
                break;
                }
            }
        }
    }
    public void buttonReleased(int no){
        if (mode==Mode.RobotResponse){
            switch (no){
                
            }
        }
    }
    public void buttonDown(int no){
    }

    @Override
    public void update() {
        super.update();
        
    }

    public void setMode(Mode mode){
        this.mode=mode;
    }

}
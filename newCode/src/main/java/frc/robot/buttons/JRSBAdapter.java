package frc.robot.buttons;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.Robot;

/**
 * Button handler for right joystick
 */
public class JRSBAdapter extends ButtonHandler{
    private Robot robot;

    boolean isArcade = true;
    boolean isTank= false;

    public boolean isArcade(){
        return isArcade;
    }

    public boolean isTank(){
        return isTank;
    }

    public JRSBAdapter(Joystick joystick, Robot robot){
        super(joystick,12);
        this.robot=robot;
    }
    
    public void buttonPressed(int no){
        switch (no){
            /*case 1:
                robot.leftShooterWheel().set(-.5);
                robot.rightShooterWheel().set(.5);
                robot.meteringWheel().set(Robot.getInstance().getTuningValue("meteringWheel")*-1);
                robot.verticalLoader().set(Robot.getInstance().getTuningValue("verticalIntake")*-1);
            break;*/
            /*case 2:
                robot.outerIntakeFront().set(-.5);
                robot.outerIntakeBack().set(.5);
            break;*/
            //Color wheel
            case 3:

            break;
            //Color wheel
            case 4:

            break; 
            //Color wheel
            case 5:

            break;
            //color wheel
            case 6:

            break;
            case 11:
                switch (Robot.getInstance().getDriveMode()){
                    case twoStickArcade:
                        Robot.getInstance().setDriveMode(Robot.DRIVE_MODE.oneStickArcade);
                    break;
                    case oneStickArcade:
                        Robot.getInstance().setDriveMode(Robot.DRIVE_MODE.tank);
                    break;
                    case tank:
                        Robot.getInstance().setDriveMode(Robot.DRIVE_MODE.twoStickArcade);
                    break;
                }
            break;
        }  
    }
    public void buttonReleased(int no){
        switch (no){
            case 1:
                robot.leftShooterWheel().set(0);
                robot.rightShooterWheel().set(0);
                robot.meteringWheel().set(0);
                robot.verticalLoader().set(0);
            break;
            case 2:
                robot.outerIntakeFront().set(0);
                robot.outerIntakeBack().set(0);
            break;
        }
    }
    public void buttonDown(int no){}
}
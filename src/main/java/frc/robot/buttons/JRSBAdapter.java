package frc.robot.buttons;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.Robot;
import com.revrobotics.ControlType;

/**
 * Button handler for right joystick
 */
public class JRSBAdapter extends ButtonHandler{
    private Robot robot;

    public JRSBAdapter(Joystick joystick, Robot robot){
        super(joystick,12);
        this.robot=robot;
    }
    
    public void buttonPressed(int no){
        if (Robot.getInstance().isOperatorControl()){
            switch (no){
                case 1:
                    //robot.leftShooterWheel().set(-.7);
                    //robot.rightShooterWheel().set(.7);
                    //robot.meteringWheel().set(Robot.getInstance().getTuningValue("meteringWheel")*-1);
                    //robot.verticalLoader().set(Robot.getInstance().getTuningValue("verticalIntake")*-1);
                    robot.meteringWheel().getPIDController().setReference(robot.getTuningValue("meteringWheel")*-1,ControlType.kVelocity);
                    robot.leftShooterWheel().getPIDController().setReference(robot.getTuningValue("shoot")*-1, ControlType.kVelocity);
                    robot.rightShooterWheel().getPIDController().setReference(robot.getTuningValue("shoot"), ControlType.kVelocity);
                    robot.verticalLoader().set(robot.getTuningValue("verticalIntake")*-1);
                break;
                case 2:
                    robot.outerIntakeFront().set(robot.getTuningValue("intake"));
                    robot.outerIntakeBack().set(robot.getTuningValue("intake")*-1);
                break;
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
        } else {
            switch (no){
                case 3:
                    
                break;
                case 4:

                break;
            }
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
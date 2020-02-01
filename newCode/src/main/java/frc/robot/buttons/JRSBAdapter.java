package frc.robot.buttons;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import frc.robot.Robot;

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
        switch (no){
            case 1:
            robot.leftShooterWheel.set(-.5);
            robot.rightShooterWheel.set(-.5);
            break;
        }  
    }
    public void buttonReleased(int no){
        switch (no){
            case 1:
            robot.leftShooterWheel.set(0);
            robot.rightShooterWheel.set(0);
            break;
        }
    }
    public void buttonDown(int no){}
}
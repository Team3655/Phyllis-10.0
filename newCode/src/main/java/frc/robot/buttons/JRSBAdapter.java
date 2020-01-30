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
            
            
        }  
    }
    public void buttonReleased(int no){
        switch (no){
            
        }
    }
    public void buttonDown(int no){}
}
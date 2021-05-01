package frc.robot.event.customevents;

import frc.robot.Robot;
import frc.robot.event.Event;

/**@author Max
 * @author Keegan
 */
public class TurretEvent extends Event{
    private double target;
    private int state=0;
    
    public TurretEvent(double rotations){
        super();
        target=rotations;
    }


    public void task(){
        Robot.getInstance().turret().setPos(target);
    }

    @Override
    public boolean eventCompleteCondition(){
        double pos=Robot.getInstance().turret().getPosition();
        return (pos>(target-.1) && pos<(target+.1));
    }
}
package frc.robot;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import frc.robot.event.customevents.LimelightEvent;
import frc.robot.event.customevents.PrintEvent;

public class Limelight {
    private NetworkTable limelight=NetworkTableInstance.getDefault().getTable("limelight");
    private NetworkTableEntry x = limelight.getEntry("tx");
    private NetworkTableEntry y = limelight.getEntry("ty");
    private NetworkTableEntry a = limelight.getEntry("ta");
    private NetworkTableEntry v = limelight.getEntry("tv");
    private LimelightEvent loop;

    public Limelight(){
        loop=new LimelightEvent();
        Robot.eHandler.triggerEvent(loop);
    }

    public double getX(){
        Robot.getInstance().eHandler.triggerEvent(new PrintEvent(x.getDouble(0)));
        return x.getDouble(0);
    }
    public double getY(){
        return y.getDouble(0);
    }
    public double getArea(){
        return a.getDouble(0);
    }
    public boolean hasTarget(){
        return v.getDouble(0)==1;
    }

    public void enable(){
        loop.enable();
    }

    public void disable(){
        loop.disable();
    }

    public void setEnabled(boolean enabled){
        loop.setEnabled(enabled);
    }

    public boolean isEnabled(){
        return loop.isEnabled();
    }

    /**Terminates limelight event loop
     * 
     */
    public void close(){
        loop.terminate();
    }

}
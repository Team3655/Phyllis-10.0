package frc.robot;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Limelight {
    private NetworkTable limelight=NetworkTableInstance.getDefault().getTable("limelight");
    private NetworkTableEntry x = limelight.getEntry("tx");
    private NetworkTableEntry y = limelight.getEntry("ty");
    private NetworkTableEntry a = limelight.getEntry("ta");


    public Limelight(){
        
    }

    public double getX(){
        return x.getDouble(0);
    }
    public double getY(){
        return y.getDouble(0);
    }
    public double getArea(){
        return a.getDouble(0);
    }
}
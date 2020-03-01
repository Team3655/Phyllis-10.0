package frc.robot.motors;

public class Neo extends Motor{
    public Neo(int deviceID){
        super(deviceID,MotorType.kBrushless,String.valueOf(deviceID),type());
        setSmartCurrentLimit(80);
    }

    public Neo(int deviceID,String name){
        super(deviceID,MotorType.kBrushless,name,type());
        setSmartCurrentLimit(80);
    }

    public static String type(){
        return "Neo";
    }
}
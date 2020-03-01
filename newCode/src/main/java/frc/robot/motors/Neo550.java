package frc.robot.motors;

import com.revrobotics.CANSparkMax;

public class Neo550 extends Motor {

    public Neo550(int deviceID) {
        super(deviceID, MotorType.kBrushless,String.valueOf(deviceID),type());
        setSmartCurrentLimit(40);//fourty amp limit
    }

    public Neo550(int deviceID, String name) {
        super(deviceID, MotorType.kBrushless,name,type());
        setSmartCurrentLimit(40);//fourty amp limit
    }
    
    public static String type(){
        return "Neo550";
    }
}
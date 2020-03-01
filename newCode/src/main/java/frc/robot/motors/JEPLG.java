package frc.robot.motors;

import com.revrobotics.CANSparkMax;

public class JEPLG extends Motor{
    public JEPLG(int deviceID) {
        super(deviceID, MotorType.kBrushed,String.valueOf(deviceID),type());
        setSmartCurrentLimit(10);//fourty amp limit
    }

    public JEPLG(int deviceID,String name) {
        super(deviceID, MotorType.kBrushed,name,type());
        setSmartCurrentLimit(10);//fourty amp limit
    }

    public static String type(){
        return "JEPLG";
    }
}
package frc.robot.motors;

import com.revrobotics.CANSparkMax;

public class JEPLG extends CANSparkMax{
    public JEPLG(int deviceID) {
        super(deviceID, MotorType.kBrushed);
        setSmartCurrentLimit(10);//fourty amp limit
    }
}
package frc.robot.motors;

import com.revrobotics.CANSparkMax;

public class Neo550 extends CANSparkMax implements Runnable{

    public Neo550(int deviceID) {
        super(deviceID, MotorType.kBrushless);
        setSmartCurrentLimit(40);//fourty amp limit
    }

    @Override
    public void run(){
        //temp limit goes here
    }

}
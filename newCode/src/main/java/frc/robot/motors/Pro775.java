package frc.robot.motors;

import com.revrobotics.CANSparkMax;

import com.revrobotics.CANSparkMax;

public class Pro775 extends CANSparkMax{
    public Pro775(int id){
        super(id,MotorType.kBrushed);
        setSmartCurrentLimit(40);
    }
}
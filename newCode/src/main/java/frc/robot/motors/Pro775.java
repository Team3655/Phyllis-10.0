package frc.robot.motors;

import com.revrobotics.CANSparkMax;

import com.revrobotics.CANSparkMax;

public class Pro775 extends Motor{
    public Pro775(int id){
        super(id,MotorType.kBrushed,String.valueOf(id),type());
        setSmartCurrentLimit(40);
    }

    public Pro775(int id,String name){
        super(id,MotorType.kBrushed,name,type());
        setSmartCurrentLimit(40);
    }

    public static String type(){
        return "Pro775";
    }
}
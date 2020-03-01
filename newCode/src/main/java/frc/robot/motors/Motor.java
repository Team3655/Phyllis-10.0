package frc.robot.motors;

import com.revrobotics.CANSparkMax;

public abstract class Motor extends CANSparkMax {
    private String name;
    private String type;

    public Motor(int id, MotorType type,String name){
        super(id,type);
        this.name=name;
        if (type==MotorType.kBrushed){
            this.type="BRUSHED";
        } else {
            this.type="BRUSHLESS";
        }
        
    }

    public Motor(int id, MotorType type,String name,String typeName){
        super(id,type);
        this.name=name;
        this.type=typeName;
    }
    
    public final String getType(){
        return type;
    }

    public final String getName(){
        return name;
    }

    public final String props(){
        return String.valueOf(getDeviceId())+" "+name+" "+type;
    }

}
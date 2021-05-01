package frc.robot.motors.system;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Hashtable;

import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.motors.Motor;

public class MotorSystem {
    protected Hashtable<String,Motor> motors;
    private Hashtable<String,MotorSystem> subSystems;
    public MotorSystem(){
        motors=new Hashtable();
        subSystems=new Hashtable();
    }

    public final Motor[] getMotors(){
        ArrayList<Motor> allMotors=new ArrayList<>();
        allMotors.addAll(motors.values());
        for (MotorSystem s:subSystems.values()){
            allMotors.addAll(s.getMotorsAsList());
        }
        Motor[] motors=new Motor[allMotors.size()];
        allMotors.toArray(motors);
        return motors;
    }

    private ArrayList<Motor> getMotorsAsList(){
        ArrayList<Motor> allMotors=new ArrayList<>();
        allMotors.addAll(motors.values());
        for (MotorSystem s:subSystems.values()){
            allMotors.addAll(s.getMotorsAsList());
        }
        return allMotors;
    }

    public final MotorSystem[] getSubSystems(){
        MotorSystem[] subSystems=new MotorSystem[this.subSystems.size()];
        this.subSystems.values().toArray(subSystems);
        return subSystems;
    }

    public Motor[] getMotors(String name){
        ArrayList<Motor> motors=new ArrayList<>();
        for (Motor m:getMotors()){
            if (m.getName().equals(name)){
                motors.add(m);
            }
        }
        Motor[] motorsArray=new Motor[motors.size()];
        motors.toArray(motorsArray);
        return motorsArray;
    }

    /*public Motor[] getMotorsByType(String type){

    }*/

    public void addSubSystem(MotorSystem m,String name){
        subSystems.put(name, m);
    }

    public MotorSystem getSubSystem(String name){
        return subSystems.get(name);
    }

}
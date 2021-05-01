package frc.robot.sensors;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.SensorUtil;

public class IRDistanceSensor {
    private final int id;
    private final AnalogInput input;

    public IRDistanceSensor(int id){
        this.id=id;
        input=new AnalogInput(id);

    }

    public double distance(){
        return input.getVoltage();
    }
}
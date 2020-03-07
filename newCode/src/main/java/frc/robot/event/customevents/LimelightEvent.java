package frc.robot.event.customevents;

import frc.robot.Robot;
import frc.robot.event.Event;

public class LimelightEvent extends Event {
    private final double P = .02;
    private final double Ppos=.1;
    private boolean enabled;
    private boolean terminated;
    private double max=.2;
    public LimelightEvent(){
        super();
    }

    @Override
    public void task(){
        if (enabled&&Robot.getInstance().getLimelight().hasTarget()){
            //do limelight yams
            double power=P*Robot.getInstance().getLimelight().getY();
            if (Math.abs(power)>max){
                power=max*Math.abs(power)/power;
            }
            //error will equal the angle of y the limelight returns
            //set motor speed as P times error
            Robot.getInstance().turret().set(power);
            //Robot.eHandler.triggerEvent(new PrintEvent("Doing limelight yams."+Robot.getInstance().turret().getEncoder().getPosition()));
            //Robot.getInstance().turret().set(P*Robot.getInstance().getLimelight().getX());

            double h = 2; //distance between turret and target (height)
            double d = h/Math.tan(-Robot.getInstance().getLimelight().getX()); //distance between turret and target (length)
            double t = Math.sqrt(19.6*h)/9.8; //time for power cell to travel from turret to target
            double v = Math.sqrt(Math.pow(d/t, 2)+19.6*h)*Robot.getInstance().getTuningValue("velocityCoefficient"); //velocity of power cell
            //double angle = Math.sinh(Math.sqrt(19.6*h)/v); //elevator angle relative to floor
            //double targetAngle = angle+Robot.getInstance().getLimelight().getX(); //the target angle from Limelight*/
            if (Math.abs(v)>6000){
                v=6000*Math.abs(v)/v;
            }
            //Robot.getInstance().setTuningValue("shoot", v);
            //easier way is recording power and angle requirements for many distances and 
            //other way:
            double pos=P*Robot.getInstance().getLimelight().getX();
            //error will equal the angle of x the limelight returns
            //set motor pos+= P times error
            System.out.println("PID output: "+pos);
            System.out.println("new elevator pos: "+(Robot.getInstance().getElevatorLeft().getPosition()+pos));
            System.out.println("current elevator pos: "+Robot.getInstance().getElevatorLeft().getPosition());
            //Robot.eHandler.triggerEvent(new ElevationEvent(Robot.getInstance().getElevatorLeft().getPosition()-pos));
            double posmax=Robot.getInstance().getTuningValue("elevatorMaxPos");
            double posmin=Robot.getInstance().getTuningValue("elevatorMinPos");
            if (pos>posmax){
                pos=posmax;
            } else if (pos<posmin){
                pos=posmin;
            }
            Robot.getInstance().getElevatorLeft().setPosition(Robot.getInstance().getElevatorLeft().getPosition()+pos);
        }
    }

    @Override
    public boolean eventCompleteCondition(){
        return false;//Robot.getInstance().isEnabled();//should run until robot is turned off
    }

    public void enable(){
        enabled=true;
    }

    public void disable(){
        enabled=false;
    }

    public void setEnabled(boolean enabled){
        this.enabled=enabled;
    }

    public boolean isEnabled(){
        return enabled;
    }
}

package frc.robot.event.customevents;

import frc.robot.event.Event;

public class DriveEvent extends Event{

    private double distance;

    public DriveEvent(double distance, double feedForward){
        super();
        this.distance=distance;

    }

}

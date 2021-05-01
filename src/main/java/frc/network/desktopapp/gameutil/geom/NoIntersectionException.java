package frc.network.desktopapp.gameutil.geom;

public class NoIntersectionException extends Exception {
	protected NoIntersectionException() {
		super("Failed to find intersection point due to the fact that the lines do not intersect.");
		
	}
}

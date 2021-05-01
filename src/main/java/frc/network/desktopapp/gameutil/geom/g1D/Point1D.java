package frc.network.desktopapp.gameutil.geom.g1D;

public class Point1D {
	/*TO DO:
	 *  -Make functions for adding and subtracting other vectors
	 *  
	 */
	private double x;

	public Point1D(double x) {
		this.x = x;
	}

	public double getX() {
		return x;
	}

	public double distance(Point1D p) {
		return Math.abs(x - p.x);
	}

}

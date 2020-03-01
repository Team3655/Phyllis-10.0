package frc.network.desktopapp.gameutil.geom.g2D;

public class LineOverlapException extends Exception{
	public LineOverlapException() {
		super("During the calculation of a line intersection point it was found that the lines overlap.");
	}
}

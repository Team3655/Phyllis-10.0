package frc.network.desktopapp.gameutil.geom.g3D;

import frc.network.desktopapp.gameutil.geom.g1D.Point1D;
import frc.network.desktopapp.gameutil.geom.g1D.Vector1D;

public class LineR3 {
	private PointR3 r0;
	private PointR3 P;
	private PointR3 r;
	private Vector1D v;

	/**
	 * Constructs a line which exists in 3-space
	 * 
	 * @param r0
	 *            the point of origen (t - a measure of distance from this point
	 *            - is based off of a one dimensional vector with this point as
	 *            it's origen and r as it's end point)
	 * @param P
	 *            a point on the line used to find r
	 */
	public LineR3(PointR3 r0, PointR3 P) {
		this.r0 = r0;
		this.P = P;
		double t0 = P.getDistance(r0);
		double a = (P.getX() - r0.getX()) / t0;
		double b = (P.getY() - r0.getY()) / t0;
		double c = (P.getZ() - r0.getZ()) / t0;
		r = new PointR3(a, b, c);
		v = new Vector1D(new Point1D(0), new Point1D(r.getDistance(r0)));
	}

	/**
	 * Returns the point that is at the end of a position vector which is the
	 * equivalent to the sum a position vector with an end of r0 and a vector
	 * parallel to the line
	 * 
	 * @return
	 */
	public PointR3 getR() {
		return new PointR3(r.getX(), r.getY(), r.getZ());
	}

	/**
	 * Returns the point at the origen of the line (where t=0), the first point
	 * used to initialize the object
	 * 
	 * @return
	 */
	public PointR3 getR0() {
		return new PointR3(r0.getX(), r0.getY(), r0.getZ());
	}

	/**
	 * Returns the one dimensional vector which begins at point r0 and ends at
	 * point r (points in the direction of r)
	 * 
	 * @return
	 */
	public Vector1D getV() {
		return v;
	}

	/**
	 * Returns the second point on the line input during initialization
	 * 
	 * @return
	 */
	public PointR3 getP() {
		return new PointR3(P.getX(), P.getY(), P.getZ());
	}

	/**
	 * Returns the point on the line at a distance of <code>t</code>
	 * 
	 * @param t
	 *            the distance from the r0 (polarity dependent on the one
	 *            dimensional vector v)
	 * @return
	 */
	public PointR3 equation(double t) {
		double x = r0.getX() + t * r.getX();
		double y = r0.getY() + t * r.getY();
		double z = r0.getZ() + t * r.getZ();
		return new PointR3(x, y, z);
	}

	public boolean containsPoint(PointR3 p) {
		double t = p.getDistance(r0);
		if (equation(t).equals(p) || equation(-1 * t).equals(p)) {
			return true;
		}
		return false;
	}

	public double getTOfPoint(PointR3 p) {
		double t = 0;
		if (containsPoint(p)) {
			if (equation(t).equals(p)) {
				return p.getDistance(r0);
			}else {
				return -1*p.getDistance(r0);
			}
		}
		return 0;
	}
	
	
}

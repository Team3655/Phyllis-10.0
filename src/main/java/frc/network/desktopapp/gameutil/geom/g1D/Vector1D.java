package frc.network.desktopapp.gameutil.geom.g1D;

public class Vector1D {
	private Point1D base;
	private Point1D end;
	
	public static enum direction {
		positive, negative
	};

	private direction d;

	public Vector1D(Point1D base, Point1D end) {
		this.base = base;
		this.end = end;
		if (base.getX() == end.getX()) {
			System.err
					.println("WARNING: Vector1D initialized with point coordinates... defaulting to direction right.");
			d = direction.positive;
		} else if (base.getX() < end.getX()) {
			d = direction.positive;
		} else {
			d = direction.negative;
		}

	}

	public Vector1D(Point1D p, direction d) {
		base = p;
		end = p;
		this.d = d;
	}

	public double distance(Point1D p) {
		int coef;
		double distance = p.distance(end);
		if (p.getX() < end.getX()) {
			coef = -1;
		} else {
			coef = 1;
		}
		if (d == direction.negative) {
			coef *= -1;
		}
		return distance * coef;
	}

	public boolean contains(Point1D p) {
		return (p.getX() >= base.getX() && p.getX() <= end.getX());
	}
	
	public double getMagnetude() {
		return end.getX()-base.getX();
	}
}

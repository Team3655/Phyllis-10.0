package frc.network.desktopapp.gameutil.geom.g2D;

import java.awt.Rectangle;
import java.awt.geom.Line2D;

public class LineR2 {
	// ana ++ **cardinal directions of 4th dimension points
	// kata--
	protected double m;
	protected double b;// x coord for vertical lines
	protected double x1;
	protected double x2;
	protected double y1;
	protected double y2;
	protected PointR2 p1;
	protected PointR2 p2;
	protected boolean vertical;

	public LineR2(double x1, double y1, double x2, double y2) throws Exception {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		p1 = new PointR2(x1, y1);
		p2 = new PointR2(x2, y2);
		if (x1 == x2 && y1 == y2) {
			throw new Exception("Can't make a line out of one point.");
		}
		if (x1 - x2 == 0) {
			vertical = true;
			m = 0;
			b = x1;
		} else {
			vertical = false;
			m = (y1 - y2) / (x1 - x2);
			b = y1 - (m * x1);
		}
	}

	public LineR2(PointR2 p1, PointR2 p2) throws Exception {
		x1 = p1.getX();
		y1 = p1.getY();
		x2 = p2.getX();
		y2 = p2.getY();
		this.p1 = p1;
		this.p2 = p2;
		if (x1 == x2 && y1 == y2) {
			throw new Exception("Can't make a line out of one point.");
		}
		if (x1 - x2 == 0) {
			vertical = true;
			m = 0;
			b = x1;
		} else {
			vertical = false;
			m = (y1 - y2) / (x1 - x2);
			b = y1 - (m * x1);
		}
	}
	
	/**Give the option of making a point-line for special cases...
	 * 
	 * @param p1
	 * @param p2
	 * @param overridePointLineException if this is true, then try catch and ignore exception. Otherwise handle it normally.
	 * @throws Exception
	 */
	
	protected LineR2(PointR2 p1, PointR2 p2,boolean overridePointLineException) throws Exception{
		x1 = p1.getX();
		y1 = p1.getY();
		x2 = p2.getX();
		y2 = p2.getY();
		this.p1 = p1;
		this.p2 = p2;
		if (x1 == x2 && y1 == y2) {
			m=0;
			if (x1==0) {
				b=y1;
			} else {
				b=0;
			}
			vertical = false;
			if (!overridePointLineException) {
				throw new Exception("Can't make a line out of one point.");
			} else {
				return;
			}
		}
		if (x1 - x2 == 0) {
			vertical = true;
			m = 0;
			b = x1;
		} else {
			vertical = false;
			m = (y1 - y2) / (x1 - x2);
			b = y1 - (m * x1);
		}
	}
	
	public LineR2(double m,double b) {
		this.x1 = 1;
		this.y1 = m+b;
		this.x2 = 0;
		this.y2 = b;
		System.out.println("m:"+m+" b:"+b);
		p1 = new PointR2(x1, y1);
		p2 = new PointR2(x2, y2);
		this.m=m;
		this.b=b;
	}

	//functional
	public PointR2 intersection(LineR2 l) throws NoIntersectionException, LineOverlapException {
		if (intersects(l)) {
			if ((vertical && l.vertical) || (m == 0 && l.m == 0)) {
				throw new LineOverlapException();
			} else if (vertical) {
				try {
					return new PointR2(b, l.equation(b));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (l.vertical) {
				try {
					return new PointR2(l.b, equation(l.b));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (m==l.m){
				throw new LineOverlapException();
			} else {
				double x = (l.b - b) / (m - l.m);
				try {
					return new PointR2(x, equation(x));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		throw new NoIntersectionException("Can't find intersection point of lines that don't intersect.");

	}

	public PointR2 intersection(LineSegR2 l) throws NoIntersectionException, LineOverlapException {
		return l.intersection(this);
		
	}

	public boolean intersects(LineR2 l) {
		if ((vertical && l.vertical) || (m == 0 && l.m == 0)) {
			return b==l.b;
		} else if (vertical||l.vertical) {
			return true;
		} else {
			//lines are not vertical at all, if they have the same slope but different y-int they don't intersect, otherwise they do.
			return !(m==l.m && !(b==l.b));
		}
	}

	public boolean intersects(Rectangle r) {
		LineSegR2[] segments = LineSegR2.rectToLineSegs(r);
		return (intersects(segments[0]) || intersects(segments[1]) || intersects(segments[2])
				|| intersects(segments[3]));
	}

	public boolean intersects(LineSegR2 l) {
		
		return l.intersects(this);
	}

	public double equation(double x) throws Exception {
		if (vertical) {
			throw new Exception("Cannot give one value for y on a vertical line.");
		}
		return m * x + b;
	}

	public boolean containsPoint(PointR2 p) {
		return (Line2D.ptLineDist(x1, y1, x2, y2, p.getX(), p.getY()) == 0);
	}
	
	public double pointDistance(PointR2 p) {
		return Line2D.ptLineDist(x1, y1, x2, y2, p.getX(), p.getY());
	}

	public PointR2 endPoint1() {
		return new PointR2(x1, y1);
	}

	public PointR2 endPoint2() {
		return new PointR2(x2, y2);
	}
}

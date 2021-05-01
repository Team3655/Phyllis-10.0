package frc.network.desktopapp.gameutil.geom;

public class Point {
	public Tuple tuple;
	public Point(Tuple t){
		tuple=t;
	}
	
	/**Returns the distance between this point and a point <code>p</code>
	 * 
	 * @param p
	 * @return
	 */
	public double distance(Point p){
		return Math.sqrt((p.tuple .$S$ (tuple) .sq()).sum());
	}
	
	/**Returns the distance from this point to the origin.
	 * 
	 * @return
	 */
	public double distanceO(){
		return distance(new Point(Tuple.origin(tuple.n())));
	}
	
	public boolean equals(Point p){
		return tuple.equals(p.tuple);
	}
	
	public void printVals() {
		tuple.printVals();
	}
	
	public void printVals(String lable) {
		tuple.printVals(lable);
	}
}

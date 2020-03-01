package frc.network.desktopapp.gameutil.geom;

public class Tuple {
	
	
	
	private double[] tuple;
	private int n;
	
	/**Instantiates a tuple with the values input.
	 * 
	 * @param values
	 */
	public Tuple(double[] values){
		tuple=values;
		n=tuple.length;
	}
	
	/**Instantiates a tuple with a size of <code>n</code> and 0 for all values.
	 * 
	 * @param n
	 */
	public Tuple(int n){
		tuple=new double[n];
		this.n=n;
	}
	
	/**Instantiates a tuple with a size of <code>n</code> and <code>val</code> for all values.
	 * 
	 * @param n
	 */
	public Tuple(int n, double val){
		tuple=new double[n];
		this.n=n;
		set(val);
	}
	
	/**Adds together two tuples. If the tuples are different sizes a tuple the size of the larger will be returned.
	 * 
	 * @param t
	 * @return
	 */
	public Tuple $A$(Tuple t){
		int n;
		double[] sum;
		if (this.n>t.n){
			n=this.n;
			sum=new double[n];
			for (int i=0; i<n; i++){
				try {
					sum[i]=tuple[i]+t.tuple[i];
				} catch (IndexOutOfBoundsException e) {
					sum[i]=tuple[i];
				}
			}
		} else {
			n=t.n;
			sum=new double[n];
			for (int i=0; i<n; i++){
				try {
					sum[i]=tuple[i]+t.tuple[i];
				} catch (IndexOutOfBoundsException e) {
					sum[i]=t.tuple[i];
				}
			}
		}
		return new Tuple(sum);
	}
	
	/**subtracts a tuple <code>(t)</code> from this tuple. If the tuples are different sizes a tuple the size of the larger will be returned.
	 * 
	 * @param t
	 * @return
	 */
	public Tuple $S$(Tuple t){
		int n;
		double[] dif;
		if (this.n>t.n){
			n=this.n;
			dif=new double[n];
			for (int i=0; i<n; i++){
				try {
					dif[i]=tuple[i]-t.tuple[i];
				} catch (IndexOutOfBoundsException e) {
					dif[i]=tuple[i];
				}
			}
		} else {
			n=t.n;
			dif=new double[n];
			for (int i=0; i<n; i++){
				try {
					dif[i]=tuple[i]-t.tuple[i];
				} catch (IndexOutOfBoundsException e) {
					dif[i]=0-t.tuple[i];
				}
			}
		}
		return new Tuple(dif);
	}
	
	/**Multiplies two tuples. If the tuples are different sizes a tuple the size of the larger will be returned.
	 * 
	 * @param t
	 * @return
	 */
	public Tuple $X$(Tuple t){
		int n;
		double[] pro;
		if (this.n>t.n){
			n=this.n;
			pro=new double[n];
			for (int i=0; i<n; i++){
				try {
					pro[i]=tuple[i]*t.tuple[i];
				} catch (IndexOutOfBoundsException e) {
					pro[i]=0;
				}
			}
		} else {
			n=t.n;
			pro=new double[n];
			for (int i=0; i<n; i++){
				try {
					pro[i]=tuple[i]*t.tuple[i];
				} catch (IndexOutOfBoundsException e) {
					pro[i]=0;
				}
			}
		}
		return new Tuple(pro);
	}
	
	/**Divides two tuples. If the tuples are different sizes a tuple the size of the larger will be returned.
	 * 
	 * @param t
	 * @return
	 */
	public Tuple $D$(Tuple t){
		int n;
		double[] quo;
		if (this.n>t.n){
			n=this.n;
			quo=new double[n];
			for (int i=0; i<n; i++){
				try {
					quo[i]=tuple[i]/t.tuple[i];
				} catch (IndexOutOfBoundsException e) {
					System.err.println("WARNING: Dividing by zero!");
					System.err.println("  Time: during $D$");
					System.err.println("  Tuple index: "+i);
					quo[i]=tuple[i]/0;
				}
			}
		} else {
			n=t.n;
			quo=new double[n];
			for (int i=0; i<n; i++){
				try {
					quo[i]=tuple[i]/t.tuple[i];
				} catch (IndexOutOfBoundsException e) {
					quo[i]=0;
				}
			}
		}
		return new Tuple(quo);
	}
	
	/**Adds together two tuples. If the tuples are different sizes a tuple the size of the larger will be returned.
	 * 
	 * @param t
	 * @return
	 */
	public Tuple $A$(double d){
		Tuple t=new Tuple(n,d);
		double[] dif;
		dif=new double[n];
		for (int i=0; i<n; i++){
			dif[i]=tuple[i]+t.tuple[i];
		}
		return new Tuple(dif);
	}
	
	/**subtracts a tuple <code>(t)</code> from this tuple. If the tuples are different sizes a tuple the size of the larger will be returned.
	 * 
	 * @param t
	 * @return
	 */
	public Tuple $S$(double d){
		Tuple t=new Tuple(n,d);
		double[] sum;
		sum=new double[n];
		for (int i=0; i<n; i++){
			sum[i]=tuple[i]-t.tuple[i];
		}
		return new Tuple(sum);
	}
	
	/**Multiplies two tuples. If the tuples are different sizes a tuple the size of the larger will be returned.
	 * 
	 * @param t
	 * @return
	 */
	public Tuple $X$(double d){
		Tuple t=new Tuple(n,d);
		double[] pro=new double[n];
		for (int i=0; i<n; i++){
			pro[i]=tuple[i]*t.tuple[i];
		}
		return new Tuple(pro);
	}
	
	/**Divides two tuples. If the tuples are different sizes a tuple the size of the larger will be returned.
	 * 
	 * @param t
	 * @return
	 */
	public Tuple $D$(double d){
		Tuple t=new Tuple(n,d);
		double[] quo=new double[n];
		for (int i=0; i<n; i++){
				quo[i]=tuple[i]/t.tuple[i];
		}
		return new Tuple(quo);
	}
	
	/**Returns the square of this tuple.
	 * 
	 * @return
	 */
	public Tuple sq(){
		return $X$(this);
	}
	
	/**Returns this tuple to the power of <code>(exp)</code>.
	 * 
	 * @param exp
	 * @return
	 */
	public Tuple $E$(double exp){
		double[] pow=new double[n];
		for (int i=0; i<n;i++){
			pow[i]=tuple[i];
			pow[i]=Math.pow(pow[i], exp);
		}
		return new Tuple(pow);
	}
	/**Returns the square root of this tuple.
	 * 
	 * @return
	 */
	public Tuple sqrt(){
		double[] pow=new double[n];
		for (int i=0; i<n;i++){
			pow[i]=tuple[i];
			pow[i]=Math.pow(pow[i],.5);
		}
		return new Tuple(pow);
	}
	
	/**Returns the value at the specified index of this tuple
	 * 
	 * @param index
	 * @return
	 */
	public double i(int index){
		try {
			return tuple[index];
		} catch (ArrayIndexOutOfBoundsException e) {
			return 0;
		}
	}
	
	/**Returns and array form of this tuple
	 * 
	 * @return
	 */
	public double[] a(){
		return tuple;
	}
	
	public double sum(){
		double sum=0;
		for (int i=0; i<n; i++){
			sum+=tuple[i];
		}
		return sum;
	}
	
	/**Returns the size of this tuple
	 * 
	 * @return
	 */
	public int n(){
		return n;
	}
	
	/**Sets the value of a specific index of this tuple
	 * 
	 * @param index
	 * @param val
	 * @return weather this tuple grew to compensate for an index that was out of bounds
	 */
	public boolean set(int index,double val){
		if (index>tuple.length-1){
			double[] d=new double[index];
			d[index]=val;
			tuple=this .$A$ (new Tuple(d)).a();
			n=tuple.length;
			return true;
		} else {
			tuple[index]=val;
			return false;
		}
	}
	
	/**Sets the values of this tuple to the specified array of values
	 * 
	 * @param values
	 */
	public void set(double[] values){
		tuple=values;
		n=tuple.length;
	}
	
	/**Sets this tuple to imitate the properties of another tuple
	 * 
	 * @param t
	 */
	public void set(Tuple t){
		tuple=t.a();
		n=tuple.length;
	}
	
	/**Sets all values of this touple to the double <code>d</code>
	 * 
	 * @param d
	 */
	public void set(double d){
		for (int i=0; i<n; i++){
			tuple[i]=d;
		}
	}
	
	/**Returns a tuple with all zero values
	 * 
	 * @param n size
	 * @return tuple that represents origin
	 */
	public static Tuple origin(int n){
		return new Tuple(new double[n]);
	}
	
	public boolean equals(Tuple t){
		int n;
		boolean tuplesEqual=true;
		if (t.n>this.n){
			n=t.n;
			for (int i=0; i<n; i++){
				try{
					if (!(tuple[i]==t.tuple[i])){
						tuplesEqual=false;
						break;
					}
				} catch (IndexOutOfBoundsException e){
					//undefined index of this tuple defaults to zero
					if (!(t.tuple[i]==0)){
						tuplesEqual=false;
						break;
					}
				}
			}
		} else {
			n=this.n;
			for (int i=0; i<n; i++){
				try{
					if (!(tuple[i]==t.tuple[i])){
						tuplesEqual=false;
						break;
					}
				} catch (IndexOutOfBoundsException e){
					//undefined index of tuple t defaults to zero
					if (!(tuple[i]==0)){
						tuplesEqual=false;
						break;
					}
				}
			}
		}
		
		return tuplesEqual;
	}
	
	public boolean contains(double d){
		for (double tupDoub:tuple) {
			if (tupDoub==d) {
				return true;
			}
		}
		return false;
	}
	
	public Tuple containsAtIndexes(double d) {
		Tuple indexes=new Tuple(0);
		boolean tupleIsNew=true;
		int i=0;
		for (double tupDoub:tuple) {
			if (tupDoub==d) {
				if (!tupleIsNew) {
					indexes.set(indexes.n+1,i);
				} else {
					indexes.set(0,i);
					tupleIsNew=false;
				}
			}
			i++;
			
		}
		return indexes;
	}
	
	public void printVals() {
		for (int i=0;i<n;i++) {
			System.out.println(tuple[i]);
		}
	}
	
	public void printVals(String lable) {
		System.out.println(lable+": ");
		for (int i=0;i<n;i++) {
			System.out.println(tuple[i]);
		}
	}
}

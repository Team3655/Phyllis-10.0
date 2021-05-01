package frc.network.desktopapp.gameutil.text;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class Argument {
	
	private String[] args;
	
	public Argument(String[] args) {
		this.args=args;
	}
	
	
	/**Arg 0 is command
	 * 
	 * The rest are the other args
	 * 
	 * @param cmd
	 * @return args
	 */
	public static Argument getArgs(String cmd) {
		//initialize variables
		String[] args;
		ArrayList<String> argList=new ArrayList<>();
		//this stub represents the unscanned part of the string.
		String cmdStub=cmd;
		
		//Scan the cmd string for args
		while (cmdStub.contains(" ")) {
			//get index of next space
			int spaceIndex=cmdStub.indexOf(" ");
			//parse arg
			argList.add(cmdStub.substring(0, spaceIndex));
			//skip spaceIndex
			cmdStub=cmdStub.substring(spaceIndex+1);
		}
		
		//add last arg
		argList.add(cmdStub);
		
		//initialize args array to be size of list
		args=new String[argList.size()];
		
		//put args from list into array
		int i=0;
		for (String s:argList) {
			args[i]=s;
			i++;
		}
		
		return new Argument(args);
		
	}
	
	public String get(int index) {
		try {
			return args[index];
		} catch (NullPointerException e) {
			System.out.println("Nothing isn't where it's supposed to be...");
		} catch (IndexOutOfBoundsException e) {
			
		}
		
		return "";
	}
	
	public boolean hasArg(int index) {
		return !get(index).equals("");
	}
	
	public String cmd() {
		return get(0);
	}
	
	public boolean equals(Argument args) {
		int i=0;
		for (String s:this.args) {
			if (!s.equals(args.get(i))) {
				return false;
			}
			i++;
		}
		return true;
	}
	
	/**-|/p\\| arg signifies that subsequent args are primitive
	 * -|/o\\| signals that subsequent args are objects that require instantiation
	 * -|/e(depth)\\| signals the end of arguments for an object at a specific depth
	 * -|/l\\| signals the beginning of a long string if placed after String (long strings can have spaces)
	 * -|/eS\\| signals the end of a long string if placed at the end of a long string
	 * -note that \\ is the escape code for \ 
	 * @param args class name and args
	 * @param depth the amount of "functions deep" the function is. For example, an object that is instantiated in order to instantiate an object which is being used to instantiate the main object has a depth of 2
	 * @return
	 */
	private static Object constructFromArgs(Argument args,int depth) {
		
        try {
            
            ArrayList<Class> classList=new ArrayList<>(); //list of classes of arguments
            ArrayList<Object> arguments=new ArrayList<>(); //the initialization arguments for the object being instantiated
            //start after class name (i=1 instead of 0)
            int i = 1;
            //assume primitives
            boolean primitive=true;
            //decode args and add argument objects
            while (args.hasArg(i)) {
            	
                if (args.get(i).equals("|/p\\|")) {
                	//signal that subsequent args are primitive
                	primitive=true;
                } else if (args.get(i).equals("|/o\\|")) {
                	primitive=false;
                	//signal that subsequent args are objects that require instantiation
                } else {
                	
                	
                    
                    if (primitive) {
                    	//if type is int then parse int etc. and then put object into argument list and add respective class to the classList
                    	if (args.get(i).equals("int")) {
                    		
                    		classList.add(int.class);
                    		arguments.add(Integer.parseInt(args.get(i+1)));
                    		//increase i to catch up
                        	i++;
                        	
                    	} else if (args.get(i).equals("double")) {
                    		
                    		classList.add(double.class);
                    		arguments.add(Double.parseDouble(args.get(i+1)));
                    		//increase i to catch up
                        	i++;
                        	
                    	} else if (args.get(i).equals("float")) {
                    		
                    		classList.add(float.class);
                    		arguments.add(Float.parseFloat(args.get(i+1)));
                    		//increase i to catch up
                        	i++;
                        	
                    	} else if (args.get(i).equals("long")) {
                    		
                    		classList.add(long.class);
                    		arguments.add(Long.parseLong(args.get(i+1)));
                    		//increase i to catch up
                        	i++;
                        	
                    	} else if (args.get(i).equals("char")) {
                    		
                    		classList.add(char.class);
                    		arguments.add(args.get(i+1).charAt(0));
                    		//increase i to catch up
                        	i++;
                        	
                    	} else if (args.get(i).equals("String")) {
                    		
                    		classList.add(String.class);
                    		//increase i to check next arg for long string code or regular string value
                        	i++;
                        	
                    		//check if it's a long string
                    		if (args.get(i).equals("|/l\\|")) {
                    			//increase i to check next value
                            	i++;
                    			String s="";
                        		while (args.hasArg(i)) {
                            		if (args.get(i).equals("|/eS\\|")) {
                            			//break out of loop if end of string code is reached
                            			break;
                            		}
                            		//add next arg to string with space
                            		s+=" ";
                            		s+=args.get(i);
                            		i++;
                            	}
                        		if (!s.equals("")) {
                        			//cutoff extra space at beginning
                        			s=s.substring(1);
                        		}
                        		//add long string to arguments
                        		arguments.add(s);
                    		} else {
                    			//add regular string to arguments
                    			arguments.add(args.get(i));
                    			
                    		}
                    	}
                    	
                    	
                    } else {
                    	//get arg class and add it to class list
                        classList.add(Class.forName(args.get(i)));
                        //first arg is classname
                        String objectArgs=args.get(i);
                    	//increase i to catch up
                    	i++;
                    	int objectDepth=depth+1;//the depth of the new object
                    	while (args.hasArg(i)) {
                    		if (args.get(i).equals("|/e"+objectDepth+"\\|")) {
                    			//break out of loop if end of object if the end code for the object of it's own depth is reached
                    			break;
                    		}
                    		//add next arg to object args *note that this includes p and o signals so that the object can be fully initialized
                    		objectArgs+=" ";
                    		objectArgs+=args.get(i);
                    		i++;
                    	}
                    	//instantiate object and with specified args and at next depth and then add it to 
                    	arguments.add(constructFromArgs(Argument.getArgs(objectArgs),objectDepth));
                    }
                }
                
                i++;
            }
            //convert classList to an array of classes
            Class[] classes=new Class[classList.size()];
            for (i=0; i<classList.size();i++) {
            	classes[i]=classList.get(i);
            }
            
            //instantiate class
            return Class.forName(args.cmd()).getConstructor(classes).newInstance(arguments.toArray());
            
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
		
		return null;
	}
	
	/**-|/p\\| arg signifies that subsequent args are primitive
	 * -|/o\\| signals that subsequent args are objects that require instantiation
	 * -|/e(depth)\\| signals the end of arguments for an object at a specific depth (eg. object used to instantiate object used to instantiate object has depth 2 while the object that's used to instantiate an object has depth 1)
	 * -note that \\ is the escape code for \ 
	 * 
	 * No support for primitive arrays yet...
	 * 
	 * @param args class name and args as argument class
	 * @return
	 */
	public static Object constructFromArgs(Argument args) {
		return constructFromArgs(args,0);
	}
	
	/**-|/p\\| arg signifies that subsequent args are primitive
	 * -|/o\\| signals that subsequent args are objects that require instantiation
	 * -|/e(depth)\\| signals the end of arguments for an object at a specific depth (eg. object used to instantiate object used to instantiate object has depth 2 while the object that's used to instantiate an object has depth 1)
	 * -note that \\ is the escape code for \ 
	 * 
	 * No support for primitive arrays yet...
	 * 
	 * @param args class name and args as string
	 * @return
	 */
	public static Object constructFromArgs(String args) {
		return constructFromArgs(Argument.getArgs(args),0);
	}
	
	public String[] raw() {
		return args;
	}
}

package frc.network.desktopapp.gameutil.text;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Robot;
import java.awt.TextArea;
import java.awt.Toolkit;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Hashtable;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultEditorKit;

public class Console {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFrame frame;
	private JTextArea field;
	private JTextArea display;
	private TAdapter keylistener;
	private boolean userNextLineEnabled;
	private JScrollBar vertical;
	private boolean autoScroll;
	private boolean initialized=false;
	
	public enum theme {
		shell1, shell2, white, sea, forest, pink
	};

	public static Console s = new Console();

	public Console() {
		userNextLineEnabled = false;
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width / 2,
				(Toolkit.getDefaultToolkit().getScreenSize().height / 3)));
		frame.pack();
		field = new JTextArea();
		field.setEditable(false);
		field.setFocusable(true);
		keylistener = new TAdapter(field);
		field.addKeyListener(keylistener);
		field.setBackground(Color.BLACK);
		field.setForeground(Color.WHITE);
		field.getActionMap().get(DefaultEditorKit.deletePrevCharAction).setEnabled(false);
		JScrollPane scrollPane = new JScrollPane(field);
		scrollPane.setAutoscrolls(true);
		vertical = scrollPane.getVerticalScrollBar();
		frame.add(scrollPane);
		
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		field.addFocusListener(new FocusListener() {
			private boolean init=!initialized;
			
			@Override
			public void focusGained(FocusEvent arg0) {
				if (init) {
					Robot r=null;
					int tries=0;
					while (r==null) {
						try {
							r=new Robot();
							r.keyPress(KeyEvent.VK_ENTER);
							for (int i=0;i<50000;i++) {
								println(42);
							}
							//System.out.println("A console has been initialized.");
						} catch (AWTException e) {
							
							tries++;
							if (tries>=100) {
								System.err.println("Failed to instantiate Robot class");
								System.err.println("Console not properly initialized. Press enter to continue.");
								e.printStackTrace();
								break;
							}
						}
						
					}
					init=false;
					
				}
					
			}

			@Override
			public void focusLost(FocusEvent arg0) {
				// TODO Apéndice de método generado automáticamente
				
			}
			
		});
		
		readInt();
		clr();
		
	}
	
	public Console(int initializationInteger) {
		userNextLineEnabled = false;
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width / 2,
				(Toolkit.getDefaultToolkit().getScreenSize().height / 3)));
		frame.pack();
		field = new JTextArea();
		
		field.setEditable(false);
		field.setFocusable(true);
		keylistener = new TAdapter(field);
		field.addKeyListener(keylistener);
		field.setBackground(Color.BLACK);
		field.setForeground(Color.WHITE);
		field.getActionMap().get(DefaultEditorKit.deletePrevCharAction).setEnabled(false);
		JScrollPane scrollPane = new JScrollPane(field);
		frame.add(scrollPane);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		field.addFocusListener(new FocusListener() {
			private boolean init=true;
			
			@Override
			public void focusGained(FocusEvent arg0) {
				if (init) {
					Robot r=null;
					int tries=0;
					while (r==null) {
						try {
							r=new Robot();
							r.keyPress(KeyEvent.VK_ENTER);
							for (int i=0;i<50000;i++) {
								//println(42);
							}
							//System.out.println("A console has been initialized.");
						} catch (AWTException e) {
							
							tries++;
							if (tries>=100) {
								System.err.println("Failed to instantiate Robot class");
								System.err.println("Console not properly initialized. Press enter to continue.");
								e.printStackTrace();
								break;
							}
						}
						
					}
					
				}
			}

			@Override
			public void focusLost(FocusEvent arg0) {
				// TODO Apéndice de método generado automáticamente
				
			}
			
		});
		int no=readInt();
		/*if (no==initializationInteger) {
			System.out.println("The correct number was entered, initialization complete.");
		} else {
			for (int i=0;i<50000;i++) {
				System.out.println("FATAL CONSOLE ERROR!");
				System.out.println("NUMBER ENTERED DID NOT MATCH INITIALIZATION REQUIREMENT!");
				println("ERROR: Case 666 - number entered is incorrect... attempting reinitialization...");
			}
			
			setTheme(theme.shell2);
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Bloque catch generado automáticamente
				e.printStackTrace();
			}
			print("Reinitialization Successful");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Bloque catch generado automáticamente
				e.printStackTrace();
			}
			clr();
			
		}*/
		clr();
		
	}
	
	@Deprecated
	public Console(boolean userNextLineEnabled) {
		this.userNextLineEnabled = userNextLineEnabled;
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width / 2,
				(Toolkit.getDefaultToolkit().getScreenSize().height / 3)));
		frame.pack();
		field = new JTextArea();
		field.setEditable(false);
		keylistener = new TAdapter(field);
		field.addKeyListener(keylistener);
		field.setBackground(Color.BLACK);
		field.setForeground(Color.WHITE);
		field.getActionMap().get(DefaultEditorKit.deletePrevCharAction).setEnabled(false);
		JScrollPane scrollPane = new JScrollPane(field);
		frame.add(field);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	}

	@Deprecated
	public Console(theme t) {
		userNextLineEnabled = false;
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width / 2,
				(Toolkit.getDefaultToolkit().getScreenSize().height / 3)));
		frame.pack();
		field = new JTextArea();
		field.setEditable(false);
		keylistener = new TAdapter(field);
		field.addKeyListener(keylistener);
		setTheme(t);
		field.getActionMap().get(DefaultEditorKit.deletePrevCharAction).setEnabled(false);
		JScrollPane scrollPane = new JScrollPane(field);
		frame.add(scrollPane);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	}
	
	
	@Deprecated
	public Console(theme t, boolean userNextLineEnabled) {
		this.userNextLineEnabled = userNextLineEnabled;
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width / 2,
				(Toolkit.getDefaultToolkit().getScreenSize().height / 3)));
		frame.pack();
		field = new JTextArea();
		field.setEditable(false);
		keylistener = new TAdapter(field);
		field.addKeyListener(keylistener);
		setTheme(t);
		field.getActionMap().get(DefaultEditorKit.deletePrevCharAction).setEnabled(false);
		JScrollPane scrollPane = new JScrollPane(field);
		frame.add(scrollPane);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	}

	public void setTheme(theme t) {
		if (t == theme.shell1) {
			field.setBackground(Color.BLACK);
			field.setForeground(Color.WHITE);
		} else if (t == theme.shell2) {
			field.setBackground(Color.BLACK);
			field.setForeground(Color.GREEN);
		} else if (t == theme.white) {
			field.setBackground(Color.WHITE);
			field.setForeground(Color.BLACK);
		} else if (t == theme.sea) {
			field.setBackground(Color.CYAN);
			field.setForeground(Color.BLUE);
		} else if (t == theme.forest) {
			field.setBackground(Color.getHSBColor(50, 334, 60));
			field.setForeground(Color.GREEN);
		} else if (t == theme.pink) {
			field.setBackground(Color.PINK);
			field.setForeground(Color.MAGENTA);
		}
	}

	/**
	 * Sets weather the console adds a line and continues accepting input when
	 * the user presses enter while holding shift
	 * 
	 * @param userNextLineEnabled
	 */
	public void setUserNextLineEnabled(boolean userNextLineEnabled) {
		this.userNextLineEnabled = userNextLineEnabled;
	}

	/**
	 * Prints a string to the console
	 * 
	 * @param s
	 */
	public void print(String s) {
		field.append(s);
		if (autoScroll) {
			scrollBottom();
		}
	}

	/**
	 * Prints a double to the console
	 * 
	 * @param d
	 */
	public void print(double d) {
		field.append(String.valueOf(d));
		if (autoScroll) {
			scrollBottom();
		}
	}

	/**
	 * Prints a integer to the console
	 * 
	 * @param i
	 */
	public void print(int i) {
		field.append(String.valueOf(i));
		if (autoScroll) {
			scrollBottom();
		}
	}

	/**
	 * Prints a long to the console
	 * 
	 * @param l
	 */
	public void print(long l) {
		field.append(String.valueOf(l));
		if (autoScroll) {
			scrollBottom();
		}
	}

	/**
	 * Prints a character to the console
	 * 
	 * @param c
	 */
	public void print(char c) {
		field.append(String.valueOf(c));
		if (autoScroll) {
			scrollBottom();
		}
	}

	/**
	 * Prints a float to the console
	 * 
	 * @param f
	 */
	public void print(float f) {
		field.append(String.valueOf(f));
		if (autoScroll) {
			scrollBottom();
		}
	}

	/**
	 * Prints an object to the console
	 * 
	 * @param o
	 */
	public void print(Object o) {
		field.append(String.valueOf(o));
		if (autoScroll) {
			scrollBottom();
		}
	}

	/**
	 * Prints a boolean to the console
	 * 
	 * @param b
	 */
	public void print(boolean b) {
		field.append(String.valueOf(b));
		if (autoScroll) {
			scrollBottom();
		}
	}

	/**
	 * Prints a character array to the console
	 * 
	 * @param data
	 */
	public void print(char[] data) {
		field.append(String.valueOf(data));
		if (autoScroll) {
			scrollBottom();
		}
	}

	/**
	 * Prints a string to the console and goes to the next line
	 * 
	 * @param s
	 */
	public void println(String s) {
		field.append(s + "\n");
		if (autoScroll) {
			scrollBottom();
		}
	}

	/**
	 * Prints a boolean to the console and goes to the next line
	 * 
	 * @param b
	 */
	public void println(Boolean b) {
		field.append(b + "\n");
		if (autoScroll) {
			scrollBottom();
		}
	}

	/**
	 * Prints a integer to the console and goes to the next line
	 * 
	 * @param i
	 */
	public void println(int i) {
		field.append(i + "\n");
		if (autoScroll) {
			scrollBottom();
		}
	}

	/**
	 * Prints a double to the console and goes to the next line
	 * 
	 * @param d
	 */
	public void println(double d) {
		field.append(d + "\n");
		if (autoScroll) {
			scrollBottom();
		}
	}

	/**
	 * Prints a long to the console and goes to the next line
	 * 
	 * @param l
	 */
	public void println(long l) {
		field.append(l + "\n");
		if (autoScroll) {
			scrollBottom();
		}
	}

	/**
	 * Prints a character to the console and goes to the next line
	 * 
	 * @param c
	 */
	public void println(char c) {
		field.append(c + "\n");
		if (autoScroll) {
			scrollBottom();
		}
	}

	/**
	 * Prints a character array to the console and goes to the next line
	 * 
	 * @param data
	 */
	public void println(char[] data) {
		field.append(String.valueOf(data) + "\n");
		if (autoScroll) {
			scrollBottom();
		}
	}

	/**
	 * Prints an object to the console and goes to the next line
	 * 
	 * @param o
	 */
	public void println(Object o) {
		field.append(o + "\n");
		if (autoScroll) {
			scrollBottom();
		}
	}

	/**
	 * Prints an object to the console and goes to the next line
	 * 
	 * @param f
	 */
	public void println(float f) {
		field.append(f + "\n");
		if (autoScroll) {
			scrollBottom();
		}
	}

	/**
	 * Gets input from the user and goes to the next line
	 * 
	 * @return input
	 */
	public String readLine() {
		return keylistener.readLine();
	}

	/**
	 * Gets input from the user and goes to the next line
	 * 
	 * @return input
	 */
	public String read() {
		return keylistener.read();
	}

	/**
	 * Gets integer input from the user
	 * 
	 * @return input
	 */
	public int readInt() {
		return keylistener.readInt();
	}

	/**
	 * Gets integer input from the user and goes to the next line
	 * 
	 * @return input
	 */
	public int readLineInt() {
		return keylistener.readLineInt();
	}

	/**
	 * Gets double input from the user
	 * 
	 * @return input
	 */
	public double readDouble() {
		return keylistener.readDouble();
	}

	/**
	 * Gets double input from the user and goes to the next line
	 * 
	 * @return input
	 */
	public double readLineDouble() {
		return keylistener.readLineDouble();
	}

	/**
	 * Clears all text from the console window
	 */
	public void clr() {
		field.setText("");
	}

	/**
	 * Replaces any current text in the console with the <code>String s</code>.
	 * 
	 * @param s
	 */
	public void setText(String s) {
		field.setText(s);
	}

	public void backSpace() {
		keylistener.backSpace();
	}

	public void backSpace(int i) {
		if (i < 1) {
			if (i < 0) {
				i = -1 * i;
			} else {
				return;
			}
		}
		for (int x = 0; x < i; x++) {
			backSpace();
		}
	}

	public void backSpace(long i) {
		if (i < 1) {
			if (i < 0) {
				i = -1 * i;
			} else {
				return;
			}
		}
		for (long x = 0; x < i; x++) {
			backSpace();
		}
	}

	/**
	 * Returns all of the text in the console in the form of a string
	 * 
	 * @return
	 */
	public String getText() {
		return field.getText();
	}

	/**
	 * Sets whether the viewer can see the console
	 * 
	 * @param b
	 */
	public void setVisible(boolean b) {
		frame.setVisible(b);
	}

	public String getLine(int line) {
		return keylistener.getLine(line);
	}
	
	public void setAutoScroll(boolean as) {
		autoScroll=true;
	}
	
	public void scrollBottom() {
		vertical.setValue(vertical.getModel().getMaximum()+vertical.getModel().getExtent());
	}
	
	public void pause() {
		println("Press any key to continue...");// wasn't going to include this but got some feedback about people not knowing what to do
		keylistener.Pause();
	}

	public class TAdapter extends KeyAdapter {
		private JTextArea field;
		private boolean reading;
		private boolean readingNumber;
		private boolean paused;
		private boolean isInt;
		private boolean decimaled;
		private String readCache;
		private boolean skipLineAfterRead;

		public TAdapter(JTextArea field) {
			this.field = field;
			reading = false;
			isInt = false;
			readingNumber = false;
			readCache = "";
			skipLineAfterRead = false;
			decimaled = false;
			/*ScheduledExecutorService s=Executors.newScheduledThreadPool(1);
			s.schedule(new Runnable() {public void run() {keySim(KeyEvent.VK_ENTER);}}, 500, TimeUnit.MILLISECONDS);
			readInt();*/
			
		}
		
		public void keySim(int keyCode) {
			if (readingNumber && !reading) {
				String print = "";
				if (keyCode == KeyEvent.VK_ENTER) {
					readingNumber = false;
					if (skipLineAfterRead) {
						println();
					}
					return;
				} else if (keyCode >= 48 && keyCode < 58) {
					int no = keyCode - 48;
					print = Integer.toString(no);
				} else if (keyCode == KeyEvent.VK_BACK_SPACE || keyCode == KeyEvent.VK_DELETE) {
					if (readCache.length() > 0) {
						if (readCache.endsWith(".")) {
							decimaled = false;
						}
						readCache = readCache.substring(0, readCache.length() - 1);
						backSpace();
					}
				} else if (!isInt && !decimaled && keyCode == KeyEvent.VK_PERIOD) {
					print = ".";
					decimaled = true;
				} else if (keyCode == KeyEvent.VK_SUBTRACT || keyCode == KeyEvent.VK_MINUS) {
					if (readCache.equals("")) {
						readCache = "-" + readCache;
						print("-");
					}
				}
				print(print);
				readCache = readCache + print;
			}
			if (reading && keyCode == KeyEvent.VK_ENTER) {
				if (skipLineAfterRead) {
					println();
				}
				reading = false;
			}
		}
		
		public void keySim(int keyCode,boolean shiftDown) {
			if (readingNumber && !reading) {
				String print = "";
				if (keyCode == KeyEvent.VK_ENTER) {
					readingNumber = false;
					if (skipLineAfterRead) {
						println();
					}
					return;
				} else if (keyCode >= 48 && keyCode < 58) {
					int no = keyCode - 48;
					print = Integer.toString(no);
				} else if (keyCode == KeyEvent.VK_BACK_SPACE || keyCode == KeyEvent.VK_DELETE) {
					if (readCache.length() > 0) {
						if (readCache.endsWith(".")) {
							decimaled = false;
						}
						readCache = readCache.substring(0, readCache.length() - 1);
						backSpace();
					}
				} else if (!isInt && !decimaled && keyCode == KeyEvent.VK_PERIOD) {
					print = ".";
					decimaled = true;
				} else if (keyCode == KeyEvent.VK_SUBTRACT || keyCode == KeyEvent.VK_MINUS) {
					if (readCache.equals("")) {
						readCache = "-" + readCache;
						print("-");
					}
				}
				print(print);
				readCache = readCache + print;
			}
			if (reading && keyCode == KeyEvent.VK_ENTER) {
				if (!userNextLineEnabled || !shiftDown) {
					if (skipLineAfterRead) {
						println();
					}
					reading = false;
				} else {
					println();
					readCache = readCache + "\n";
				}
			}
		}
		
		public void keyPressed(KeyEvent e) {
			if (paused) {
				//unpause but don't interfere with reading actions
				paused=false;
				return;
			} else if (readingNumber && !reading) {
				String print = "";
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					readingNumber = false;
					if (skipLineAfterRead) {
						println();
					}
					return;
				} else if (e.getKeyCode() >= 48 && e.getKeyCode() < 58) {
					int no = e.getKeyCode() - 48;
					print = Integer.toString(no);
				} else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE || e.getKeyCode() == KeyEvent.VK_DELETE) {
					if (readCache.length() > 0) {
						if (readCache.endsWith(".")) {
							decimaled = false;
						}
						readCache = readCache.substring(0, readCache.length() - 1);
						backSpace();
					}
				} else if (!isInt && !decimaled && e.getKeyCode() == KeyEvent.VK_PERIOD) {
					print = ".";
					decimaled = true;
				} else if (e.getKeyCode() == KeyEvent.VK_SUBTRACT || e.getKeyCode() == KeyEvent.VK_MINUS) {
					if (readCache.equals("")) {
						readCache = "-" + readCache;
						print("-");
					}
				}
				print(print);
				readCache = readCache + print;
			}
			if (reading && e.getKeyCode() == KeyEvent.VK_ENTER) {
				if (!userNextLineEnabled || !e.isShiftDown()) {
					if (skipLineAfterRead) {
						println();
					}
					reading = false;
				} else {
					println();
					readCache = readCache + "\n";
				}
			}
		}

		public void keyTyped(KeyEvent e) {
			if (reading && !readingNumber && !paused) {
				String print = "";
				if (Character.getName(e.getKeyChar()).equals("LINE FEED (LF)")) {
					// need this so that it doesn't print enter
					return;
				} else if (Character.getName(e.getKeyChar()).equals("BACKSPACE")
						|| Character.getName(e.getKeyChar()).equals("DELETE")) {
					if (readCache.length() > 0) {
						readCache = readCache.substring(0, readCache.length() - 1);
						field.setText(field.getText().substring(0, field.getText().length() - 1));
					}
					return;
				} else if (!(e.getKeyCode() == KeyEvent.VK_CONTROL || e.getKeyCode() == KeyEvent.VK_SHIFT
						|| e.getKeyCode() == KeyEvent.VK_BACK_SPACE || e.getKeyCode() == KeyEvent.VK_ENTER)) {
					print = String.valueOf(e.getKeyChar());
					if (!e.isShiftDown()) {
						print = print.toLowerCase();
					}
				}
				print(print);
				readCache = readCache + print;
			}
		}

		public void backSpace() {
			field.setText(field.getText().substring(0, field.getText().length() - 1));
		}

		public String getLine(int line) {
			Scanner s = new Scanner(field.getText());
			for (int x = 0; x < line - 1; x++) {
				try {
					s.nextLine();
				} catch (NoSuchElementException e) {
					println("\nâ—„â•�â•�â•�â•�ERRORâ•�â•�â•�â•�â–º");
					println("Line " + line + " not found!");
					println("â—„â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â–º");
					return null;
				}
			}
			try {
				return s.nextLine();
			} catch (NoSuchElementException e) {
				println("\nâ—„â•�â•�â•�â•�ERRORâ•�â•�â•�â•�â–º");
				println("Line " + line + " not found!");
				println("â—„â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â–º");
				return null;
			}

		}

		public String getLine(long line) {
			Scanner s = new Scanner(field.getText());
			for (long x = 0; x < line - 1; x++) {
				try {
					s.nextLine();
				} catch (NoSuchElementException e) {
					println("\nâ—„â•�â•�â•�â•�ERRORâ•�â•�â•�â•�â–º");
					println("Line " + line + " not found!");
					println("â—„â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â–º");
					return null;
				}
			}
			try {
				return s.nextLine();
			} catch (NoSuchElementException e) {
				println("\nâ—„â•�â•�â•�â•�ERRORâ•�â•�â•�â•�â–º");
				println("Line " + line + " not found!");
				println("â—„â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â–º");
				return null;
			}

		}

		public long getNoOfLines() {
			Scanner s = new Scanner(field.getText());
			long line = 0;
			while (true) {
				try {
					s.nextLine();
				} catch (NoSuchElementException e) {
					return line;
				}
				line++;
			}
		}

		public void print(String s) {
			field.append(s);
		}

		public void println(String s) {
			field.append(s + "\n");
		}

		public void println() {
			field.append("\n");
		}

		public String readLine() {
			System.out.println("Awaiting user input");
			skipLineAfterRead = true;
			readCache = "";
			reading = true;
			while (reading) {
				doNothing();
			}
			if (autoScroll) {
				scrollBottom();
			}
			//System.out.println("User input recieved");
			return readCache;
		}
		
		public void Pause() {
			paused=true;
			while (paused) {
				doNothing();
			}
		}
		
		public String read() {
			reading = true;
			readCache = "";
			skipLineAfterRead = false;
			//System.out.println("Awaiting user input");
			while (reading) {
				doNothing();
			}
			if (autoScroll) {
				scrollBottom();
			}
			//System.out.println("User input recieved");
			return readCache;
		}

		public int readInt() {
			readingNumber = true;
			readCache = "";
			skipLineAfterRead = false;
			isInt = true;
			//System.out.println("Awaiting user input of integer type");
			while (readingNumber) {
				doNothing();
			}
			if (autoScroll) {
				scrollBottom();
			}
			//System.out.println("User input recieved");
			if (readCache.equals("")) {
				return 0;
			}
			int val;
			try {
				val = Integer.parseInt(readCache);
			} catch (NumberFormatException e) {
				if (Long.parseLong(readCache) > 2147483647) {
					val = 2147483647;
					println("\nâ—„â•�â•�â•�â•�ERRORâ•�â•�â•�â•�â–º");
					println("Number entered is too large!");
					println("â—„â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â–º");
				} else {
					val = -2147483648;
					println("\nâ—„â•�â•�â•�â•�ERRORâ•�â•�â•�â•�â–º");
					println("Number entered is too small!");
					println("â—„â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â–º");
				}
			}
			return val;
		}

		public int readLineInt() {
			readingNumber = true;
			readCache = "";
			skipLineAfterRead = true;
			isInt = true;
			//System.out.println("Awaiting user input of integer type");
			while (readingNumber) {
				doNothing();
			}
			if (autoScroll) {
				scrollBottom();
			}
			//System.out.println("User input recieved");
			if (readCache.equals("")) {
				return 0;
			}
			int val;
			try {
				val = Integer.parseInt(readCache);
			} catch (NumberFormatException e) {
				if (Long.parseLong(readCache) > 2147483647) {
					val = 2147483647;
					println("â—„â•�â•�â•�ERRORâ•�â•�â•�â–º");
					println("Number entered is too large!");
					println("â—„â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â–º");
				} else {
					val = -2147483648;
					println("â—„â•�â•�â•�ERRORâ•�â•�â•�â–º");
					println("Number entered is too small!");
					println("â—„â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â•�â–º");
				}
			}
			return val;
		}

		public double readDouble() {
			readingNumber = true;
			readCache = "";
			skipLineAfterRead = false;
			isInt = false;
			decimaled = false;
			//System.out.println("Awaiting user input of double type");
			while (readingNumber) {
				doNothing();
			}
			if (autoScroll) {
				scrollBottom();
			}
			//System.out.println("User input recieved");
			if (readCache.equals("")) {
				return 0;
			}
			return Double.parseDouble(readCache);
		}

		public double readLineDouble() {
			readingNumber = true;
			readCache = "";
			skipLineAfterRead = true;
			isInt = false;
			decimaled = false;
			//System.out.println("Awaiting user input of double type");
			while (readingNumber) {
				doNothing();
			}
			if (autoScroll) {
				scrollBottom();
			}
			//System.out.println("User input recieved");
			if (readCache.equals("")) {
				return 0;
			}
			
			return Double.parseDouble(readCache);
		}

		/**
		 * filler for infinite while loop
		 */
		private void doNothing() {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		public void interrupt() {
			readingNumber=false;
			reading=false;
		}
	}

	
	
}

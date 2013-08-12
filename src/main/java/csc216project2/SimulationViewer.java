package csc216project2;

import java.util.ArrayList;
import java.util.Hashtable;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Animates simulations of vehicles at a toll booth plaza.
 * @author Jo P
 * @version 2.0
 */
public class SimulationViewer extends JFrame implements ActionListener, ChangeListener { 
	
	// Size constants for the window and panels
	private final static int FRAME_WIDTH = 950;  // Application window width
	private final static int FRAME_HEIGHT = 600; // Application window height
	private final static int GRAPH_WIDTH = 650;  // Simulation graph width
	private final static int GRAPH_HEIGHT = 550;  // Simulation graph height
	private final static int SCREEN_OFFSET = 100; // Placement of application window on screen
	private final static int PAD = 20; // Offset padding	
	
	//	Strings for window, labels, slider, and combo
	private final static String TITLE = "Highway Tollbooth Simulation"; 
	private final static String INPUT_VEHICLES = "Number of Vehicles: ";
	private final static String START = "Start";
	private final static String QUIT = "Quit";
	private final static String BLANK = "";
	private final static String WAIT_TIME = "Average Time to Wait: ";
	private final static String PAY_TIME = "Average Time to Pay: ";
	private final static String NUMBER_OF_TOLLBOOTHS = "Number of TollBooths: ";
	private final static String ANIMATION_SPEED  = "Animation Speed: ";
	private final static String PROGRESS = "Progress: ";
	private final static String GO_SLOW = "Slow";
	private final static String GO_FAST = "Fast";
	private final static String[] labelBoothNumbers = {"3","4","5","6","7","8","9"};
	
	// Constants for animation
	private final static int VEHICLE_WIDTH = 12; // Radius of circle representing vehicle
	private final static int SLOW = 500; // Thread sleep for slow animation
	private final static int FAST = 0;  // Thread sleep for fast animation
	
	// Buttons 
	private JButton btnStart = new JButton(START); // Start a new simulation
	private JButton btnQuit = new JButton(QUIT); // Quit execution	

	// Labels
	private final JLabel lblNumVehicles = new JLabel(INPUT_VEHICLES);
	private final JLabel lblSpeed = new JLabel(ANIMATION_SPEED);
	private final JLabel lblSlow = new JLabel(GO_SLOW);
	private final JLabel lblFast = new JLabel(GO_FAST);	
	private final JLabel lblWaitTime = new JLabel(WAIT_TIME);
	private final JLabel lblPayTime = new JLabel(PAY_TIME);
	private final JLabel lblProgress = new JLabel(PROGRESS);
	private final JLabel lblNumTollBooths = new JLabel(NUMBER_OF_TOLLBOOTHS);
	private JLabel lblWaitCalc = new JLabel(BLANK);
	private JLabel lblPayCalc = new JLabel(BLANK);		

	// Fields for user input and results
	private JTextField txtNumVehicles = new JTextField("100");
	private JComboBox cmbTollBooths = new JComboBox(labelBoothNumbers);
	private JSlider slideSleep = new JSlider(JSlider.HORIZONTAL, FAST, SLOW, (FAST + SLOW)/2);
	private int sleepTime = 100;
	private JProgressBar progress = new JProgressBar();
	
	// Bookkeeping members
	private static Hashtable<Integer,JLabel> sleepTimeLabel = new Hashtable<Integer,JLabel>();	
	private ArrayList<Color>[] laneColors;
	
	// Panels
	private JPanel pnlButtons = new JPanel(new FlowLayout());   // Start, quit buttons
	private JPanel pnlSimulation = new JPanel(); // Simulation animation
	private JPanel pnlBottom = new JPanel(); // Groups bottom panels
	private JPanel pnlInput = new JPanel();  // Input widgets
	private JPanel pnlOutput = new JPanel(); // Simulation results

	// Backend and backend parameters
	private int numberOfVehicles; // Number of vehicles in a simulation run
	private int numberOfTollbooths; // Number of toll booths in a simulation run 
	private Simulator simulator; // Traffic at toll booth plaza simulator
	
	/** Constructor. Creates the user interface for the application.
	 */
	public SimulationViewer() {
		// Initial work. Set up the buttons and the panels
		addListeners();
		setUpInputPanels();
		
		// Add the panels to the window. Set its size, location, title
		Container c = getContentPane();
		c.add(pnlButtons, BorderLayout.NORTH);
		c.add(pnlSimulation, BorderLayout.CENTER);
		c.add(pnlBottom,BorderLayout.SOUTH);
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		setLocation(SCREEN_OFFSET,SCREEN_OFFSET);	
		setTitle(TITLE);

		// Finish setting up the frame. Exit the application when the user closes the window.
		pack();
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	/**
	 * Specifies actions to be performed when the user clicks a button or selects from a combo box.
	 * @param e Source is the button clicked.
	 */
	public void actionPerformed(ActionEvent e) {
		// Run the simulation when the user clicks the start button	
		if (e.getSource() == btnStart){
			btnStart.setEnabled(false);
			
			// Clear off current information and get parameters for the new simulation run
			clearSimulationPanel();
			numberOfVehicles = Integer.parseInt(txtNumVehicles.getText());
			numberOfTollbooths = Integer.parseInt((String)cmbTollBooths.getSelectedItem());
			clearLabel(lblWaitCalc);
			clearLabel(lblPayCalc);	
			
			// Set up the simulator, run it, and show the results
			simulator = new Simulator (numberOfTollbooths, numberOfVehicles);           	
			run();
			lblWaitCalc.setText(String.format("%1$.2f seconds", simulator.averageWaitTime()));
			lblPayCalc.setText(String.format("%1$.2f seconds", simulator.averageProcessTime()));
			btnStart.setEnabled(true);
		}
		// Pick up the number of tollbooths from the combo box
		if (e.getSource() == cmbTollBooths) {
			int x = cmbTollBooths.getSelectedIndex();
			numberOfTollbooths = Integer.parseInt(labelBoothNumbers[x]);
		}
		// Quit program execution
		if (e.getSource() == btnQuit) {
			System.exit(0);
		}
	}
	
	/**
	 * Determines whether the user changed the animation speed.
	 */
	public void stateChanged(ChangeEvent e) {
		if (e.getSource().equals(slideSleep))
			sleepTime = SLOW - slideSleep.getValue();
	}

//-----------------------Private Methods, Classes--------------------------------------------------
	
	/** Private method. Adds listeners to the button, combo box, and slider bar
	 */
	private void addListeners(){
		btnStart.addActionListener(this);
		btnQuit.addActionListener(this);
		cmbTollBooths.addActionListener(this);
		slideSleep.addChangeListener(this);
	}  
	
	/**
	 * Private method. Sets up the slider that controls speed of animation
	 */
	private void setUpSlider() {
		sleepTimeLabel.put(new Integer(SLOW), lblFast);
		sleepTimeLabel.put(new Integer(FAST), lblSlow);
		slideSleep.setLabelTable(sleepTimeLabel);
		slideSleep.setPaintLabels(true);
	}
	
	/**
	 * Private method. Set up progress bar
	 */
	private void setUpProgressBar() {
		progress = new JProgressBar( );
		progress.setPreferredSize( new Dimension( 100, 20 ) );
		progress.setBounds( 0, 0, 100, 20 );		
	}
	
	/**
	 * Private method. Right aligns a label and adds it to the panel
	 * @param panel panel doing the add
	 * @param label label being right aligned
	 */
	private void addAndAlign(JPanel panel, JLabel label) {
		label.setHorizontalAlignment(JLabel.RIGHT);
		panel.add(label);
	}

	/**
	 * Private method. Puts widgets on the panels
	 */
	private void setUpInputPanels() {
		pnlSimulation.setBackground(Color.WHITE);
		pnlSimulation.setPreferredSize(new Dimension(GRAPH_WIDTH,GRAPH_HEIGHT));
		pnlButtons.add(btnStart);
		pnlButtons.add(btnQuit);
		
		// Add labels, progress bar for results
		pnlOutput.setBorder((EmptyBorder) BorderFactory.createEmptyBorder( PAD, PAD / 2, PAD, PAD));
		pnlOutput.setLayout(new GridLayout(3,2));
		addAndAlign(pnlOutput, lblWaitTime);
		pnlOutput.add(lblWaitCalc);
		addAndAlign(pnlOutput, lblPayTime);
		pnlOutput.add(lblPayCalc);
		addAndAlign(pnlOutput, lblProgress);
		setUpProgressBar();
		pnlOutput.add(progress);			

		// Add labels, text field, combo, slider for user input
		pnlInput.setBorder((EmptyBorder) BorderFactory.createEmptyBorder( PAD, PAD / 2, PAD, PAD));
		pnlInput.setLayout(new GridLayout(3,2));
		addAndAlign(pnlInput, lblNumVehicles);
		pnlInput.add(txtNumVehicles);
		addAndAlign(pnlInput,lblNumTollBooths);
		pnlInput.add(cmbTollBooths);
		addAndAlign(pnlInput,lblSpeed);
		setUpSlider();
		pnlInput.add(slideSleep);
		
		// Complete the bottom panel
		pnlBottom.setLayout(new BoxLayout(pnlBottom, BoxLayout.LINE_AXIS));
		pnlBottom.add(pnlInput);
		pnlBottom.add(pnlOutput);	
	}
	
	/**
	 * Private method. Erases everything on the simulation panel
	 */
	private void clearSimulationPanel() {
		Graphics brush = pnlSimulation.getGraphics();
		brush.setColor(Color.white);
		brush.fillRect(0, 0, pnlSimulation.getWidth(), pnlSimulation.getHeight());		
	}
	
	/**
	 * Private method. Immediately sets the text for a label to blank.
	 * @param lbl the label being cleared
	 */
	private void clearLabel(JLabel lbl){
		lbl.setText(BLANK);
		Rectangle rectLbl = lbl.getBounds();
		rectLbl.x = 0;
		rectLbl.y = 0;
		lbl.paintImmediately(rectLbl);
	}
		
	/**
	 * Private method. Animates the simulation and shows the results.
	 */
	private void run()  {
		// Draw the toll booths and set up for animating lanes
		drawSystem(pnlSimulation, numberOfTollbooths);				
		laneColors = new ColorQueue[numberOfTollbooths];
		for (int k = 0; k < numberOfTollbooths; k++)
			laneColors[k] = new ColorQueue();
		// "Decorate" every other vehicle. decorated keeps track of whether the 
		//    rear vehicle is decorated
		boolean[] decorated = new boolean[numberOfTollbooths]; 
		for (int k = 0; k < numberOfTollbooths; k++)
			decorated[k] = false;
		
		// Prepare the progress bar
		progress.setMinimum(0);
		progress.setValue(0);
		progress.setMaximum(simulator.totalNumberOfSteps());

		// Step through the simulation
		while (simulator.moreSteps()) {
			simulator.step();
			int soFar = simulator.getStepsTaken();
			int index = simulator.getCurrentLaneIndex();			
			int length = laneColors[index].size();
			if (simulator.vehicleLeftSimulation()) {
				laneColors[index].remove(0);
				decorated[index] = !decorated[index];
			}
			else
				laneColors[index].add(simulator.getCurrentVehicleColor());
			
			// Erase the last vehicle on the tollbooth line that changed on this step
			//   (needed only if the vehicle leaves the line)
			plotPoint(pnlSimulation, (index + 1) * GRAPH_WIDTH / (numberOfTollbooths + 1), 
					20 + 12 * (length - 1), Color.WHITE, false);
			// Redraw the line that changed, marking every other vehicle with a dot so you
			//   can detect the motion
			int j = 0;
			boolean colorMarker = false;
			for (Color col : laneColors[index]) {
				plotPoint(pnlSimulation, (index + 1) * GRAPH_WIDTH/ (numberOfTollbooths + 1), 
						20 + 12 * j++, col, colorMarker ^ decorated[index]);
				colorMarker = !colorMarker;
			}
			// Update the progress bar
			progress.setValue(soFar);
			Rectangle progressRect = progress.getBounds();
			progressRect.x = 0;
			progressRect.y = 0;
			progress.paintImmediately( progressRect );

			// Slow down the animation
			try {
				Thread.sleep(sleepTime);
			}
			catch ( InterruptedException e) {
			}
		}          
	}			

	/** Private method. Sets up the panels by specifying sizes and borders and adding components.
	 */
	private void drawSystem(JPanel panel, int lanes) {
		Graphics g = panel.getGraphics();
		g.setColor(Color.BLACK);
		// Draw the tollbooths
		for (int k = 0; k < lanes; k++) {
			g.drawRect((k + 1) * GRAPH_WIDTH/(lanes + 1) - 1, 19, 
					VEHICLE_WIDTH + 2, VEHICLE_WIDTH + 2); 
		}
	}

	/**
	 * Private method. Plots a solid circle on a graph.
	 * @param panel Panel where the graph is drawn.
	 * @param x x-coordinate of the point
	 * @param y y-coordinate of the point
	 * @param c color of the point
	 * @param withHole determines whether there should be a whole in the circle
	 */
	private void plotPoint(JPanel panel, int x, int y, Color c, boolean withHole) {
		Graphics g = panel.getGraphics();
		g.setColor(c);
		g.fillOval(x, y, VEHICLE_WIDTH, VEHICLE_WIDTH);
		if (withHole) {
			if (c == Color.green)
				g.setColor(Color.LIGHT_GRAY);
			else
				g.setColor(Color.green);
			g.fillOval(x + VEHICLE_WIDTH/4 + 1, y + VEHICLE_WIDTH/4 + 1, VEHICLE_WIDTH/3, VEHICLE_WIDTH/3);	
		}
	}

	/**
	 * Private inner class to set up equivalent of a C typedef for an ArrayList of Color objects.
	 */
	private class ColorQueue extends ArrayList<Color> {	}
	
//-------------------End Private Methods, Classes--------------------------------------------------
	
	/**
	 * Main method. Instantiates the Simulation
	 * @param args Not used.
	 */
	public static void main(String[] args) {
		new SimulationViewer();
	}
}
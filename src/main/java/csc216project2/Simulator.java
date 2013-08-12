package csc216project2;

import java.awt.Color;

/**
 * 
 * @author Gabe
 * @version Jun 22, 2010 1:50:25 AM
 */
public class Simulator {
	private Log myLog;
	private EventCalendar eventCalendar;
	private int numTollBooths;
	private int numVehicles;
	private TollBooth[] tollBooth;
	private Highway highway;
	private Vehicle currentVehicle;
	private boolean vehicleLeftTollbooth;
	private int stepsTaken;
	
	/**
	 * Constructor - create a Highway and an array of TollBooths. 
	 * 	The constructor also creates the Log to keep track of the traffic statistics 
	 * 	and an EventCalendar to control the order of events
	 * @param numVehicles
	 * @param numTollBooths
	 */
	public Simulator(int numTollBooths, int numVehicles) {
		this.numTollBooths = numTollBooths;
		this.numVehicles = numVehicles;
		myLog = new Log();
		tollBooth = new TollBooth[numTollBooths];
		setUp();
		highway = new Highway(this.numVehicles, tollBooth);
		this.eventCalendar = new EventCalendar(tollBooth, highway);
		
	}
	
	/**
	 * Helper method initializes the tollBooth array and other variables
	 * for the constructor
	 */
	private void setUp() {
		// initialize each tollbooth with the myLog
		for (int i=0; i < tollBooth.length; i++) {
			tollBooth[i] = new TollBooth( myLog );
		}
		currentVehicle = null;
		vehicleLeftTollbooth = false;
		stepsTaken = 0;
	}
	
	/**
	 * Process the next event from the EventCalendar.
	 * 	1.	Find out which queue (highway or tollbooth line) has the vehicle for the next event.
	 *  2.	Get the next vehicle from that queue.
	 *  3.	What is the length of the vehicle's selected line?
	 *  4.	Send a message to the line to process the vehicle.
	 *  5.	Keep track of what's happening to that vehicle's tollbooth line -- did this event make it shorter (did the vehicle leave the line)?.
	 */
	public void step() {
		/* Get the queue with next vehicle
		   Highway queue or tollbooth[number] queue */
		VehiclesInLine nextQueue = eventCalendar.nextToBeProcessed();
		int index = eventCalendar.getIndexOfNextEvent();
		
		// Validate that highway and tollbooth queues are not null
		if ( eventCalendar.getTimeOfNextEvent() == Integer.MAX_VALUE )
			return;
			
			// process the tollbooth vehicle
			if ((index > -1) && (index < numTollBooths)) {

				if ( nextQueue.departTimeNextVehicle() == Integer.MAX_VALUE)
					return;
				
				// Get the next vehicle from that queue
				currentVehicle = tollBooth[index].nextVehicle();

				// What is the length of the vehicle's selected line?
				int size = tollBooth[index].size();

				if (size > 0) {
					// Send a message to the line to process the vehicle
					tollBooth[index].processNextVehicle();

					// Keep track of what's happening to that vehicle's tollbooth line -- did this event make it shorter (did the vehicle leave the line)?
					if (size > tollBooth[index].size())
						vehicleLeftTollbooth = true;
					stepsTaken++;
					System.out.println("" + currentVehicle.getClass() + stepsTaken + "\t Lane " + currentVehicle.getLaneChosen()
							+ "\n\tA: " + currentVehicle.getTimeOfArrival() + "\tP: " + currentVehicle.getTimeToPay() + "\tW: " + currentVehicle.getTimeToWait()
							+ "\t\t\t" + tollBooth[currentVehicle.getLaneChosen()]);
				}
			}

			// Highway queue step
			else if ((nextQueue.size() > 0) && (index == -1)) {
				// Get the next vehicle from highway queue
				currentVehicle = nextQueue.nextVehicle();

				// What is the length of the vehicle's selected line?
				int size = nextQueue.size();

				if (size > 0) {
					// Send a message to the line to process the vehicle
					nextQueue.processNextVehicle();
					vehicleLeftTollbooth = false;
					stepsTaken++;
					System.out.println("" + currentVehicle.getClass() + stepsTaken + "\t Lane " + currentVehicle.getLaneChosen()
							+ "\n\tA: " + currentVehicle.getTimeOfArrival() + "\tP: " + currentVehicle.getTimeToPay() + "\tW: " + currentVehicle.getTimeToWait()
							+ "\t\t\t" + tollBooth[currentVehicle.getLaneChosen()]);
				}
			}
		
		System.out.println( "Step " + this.getStepsTaken() + "/"  + this.totalNumberOfSteps() + " just finished.");
		//else System.out.println("There is nothing to be processed!");
	}
	
	/**
	 * Getter
	 * @return number of steps taken so far
	 */
	public int getStepsTaken() {
		return stepsTaken;
	}
	
	/**
	 * Total number of steps in the simulation.
	 * @return a value corresponding to the total number of events that will occur
	 */
	public int totalNumberOfSteps() {
		return numVehicles * 2;
	}
	
	/**
	 * Are there more steps in the simulation?
	 * @return true if the simulation hasn't finished, false if it has
	 */
	public boolean moreSteps() {
		if ( stepsTaken == totalNumberOfSteps() )
			return false;
		return true;
	}
	
	/**
	 * Index of tollbooth selected by the most recently processed vehicle
	 * @return
	 */
	public int getCurrentLaneIndex() {
		return currentVehicle.getLaneChosen();
	}
	
	/**
	 * Color of most recently processed vehicle
	 * @return
	 */
	public Color getCurrentVehicleColor() {
		return currentVehicle.getColor();
	}
	
	/**
	 * Determines if the vehicle processed last left the simulation
	 * 		true if the most recently processed vehicle paid its toll and left the toll booth line; 
	 * 		false if the most recently processed vehicle left the highway to join a toll booth line
	 * @return vehicleLeftTollbooth
	 */
	public boolean vehicleLeftSimulation() {
		return vehicleLeftTollbooth;
	}
	
	/**
	 * Average number of seconds vehicles had to wait in line prior to paying their tolls
	 * @return
	 */
	public double averageWaitTime() {
		return myLog.averageWaitTime();
	}
	
	/**
	 * Average number of seconds vehicles spent paying their tolls
	 * @return
	 */
	public double averageProcessTime() {
		return myLog.averageProcessTime();
	}
	
	/**
	 * Returns a string representation of a Simulator
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String string = "Steps taken: " + getStepsTaken() + 
						"\nTotal number of steps: " + totalNumberOfSteps() + 
						"\nMore steps? " + moreSteps() + 
						"\nAverage wait time " + averageWaitTime() + 
						"\nAverage process time: " + averageProcessTime();
		return string;
	}
	
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Simulator simulate = new Simulator(5,3);
		
		for (int i = 0; i < simulate.totalNumberOfSteps(); i++) {
			
			System.out.println(simulate + "\n");
			simulate.step();
		}
	}

}

package csc216project2;
import java.awt.Color;

/**
 * Major objects in the traffic simulation problem domain. Subclasses include Car, Truck, and EZPass
 * @author Gabe
 * @see Car
 * @see Truck
 * @see EZPass
 */
abstract public class Vehicle {	
	private int timeOfArrival;	// Time when vehicle enters a toll booth line (Specified by the constructor)
	private int timeToPay;		// Number of seconds at the actual toll booth (Specified by the constructor)
	private int timeToWait;		// Number of seconds the vehicle waits in line before paying. Computed when vehicle first enters toll booth line
	
	/**
	 * Creates new vehicles
	 * @param arrivalTime the time the vehicle arrives at the toll booth plaza
	 * @param payingTime the time it spends paying the fine
	 */
	public Vehicle( int timeOfArrival, int timeToPay) {
		this.timeOfArrival = timeOfArrival;
		this.timeToPay = timeToPay;
		timeToWait = 0;
	}
	
	/**
	 * Getter - gets the time of arrival
	 * @return time the vehicle arrives at the toll booth plaza
	 */
	public int getTimeOfArrival() {
		return timeOfArrival;
	}
	
	/**
	 * Getter - gets the time it takes to pay at a toll booth
	 * @return time it takes to pay at a toll booth
	 */
	public int getTimeToPay() {
		return timeToPay;
	}

	/**
	 * Getter - gets time to wait in a toll booth line
	 * @return time spent waiting in line at a toll booth
	 */
	public int getTimeToWait() {
		return timeToWait;
	}

	/**
	 * Setter - sets the time to wait in line
	 * @param timeToWait time to wait in a toll booth line
	 */
	public void setTimeToWait( int timeToWait ) {
		this.timeToWait = timeToWait;
	}

	/**
	 * Abstract method - Places the Car in the shortest toll-booth line on the highway according to its algorithm
	 * @param tollBooth an array of TollBooths
	 */
	public abstract void getinLine(TollBooth[] tollBooth);
	
	/**
	 * Abstract method - Gets the lane chosen for a particular vehicle type
	 * @return the lane that the Vehicle chose
	 */
	public abstract int getLaneChosen();
	
	/**
	 * Abstract method - Gets the Vehicle's color
	 * @return the vehicle's color
	 */
	public abstract Color getColor();
	
	/**
	 * Abstract method - Sets the Vehicle's color
	 * @param color desired color for the Vehicle
	 */
	public abstract void setColor(Color color);
	
	/**
	 * Prints a string representation of a Vehicle
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String s = "-----VEHICLE-----\n" 
			+ "timeOfArrival: " + timeOfArrival 
			+ "\ntimeToPay: " + timeToPay 
			+ "\ntimeToWait: " + timeToWait 
			+ "\n-----";
		return s;
	}
	
}

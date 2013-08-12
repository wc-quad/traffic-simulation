package csc216project2;

/**
 * Book keeping class for the simulation
 * @author Gabe
 * @version Jun 21, 2010 9:40:36 PM
 */
public class Log {
	private int numCompleted = 0;		// The number of vehicles that have logged their information.
	private int totalWaitTime = 0;		// The sum of all wait times logged by vehicles so far.
	private int totalProcessTime = 0;	// The sum of all times that vehicles took to pay their tolls (not including wait time)
	
	/**
	 * Getter - Gets the number of vehicles that have left the simulation
	 * @return number of vehicles that have logged their information
	 */
	public int getNumCompleted() {
		return numCompleted;
	}
	
	/**
	 * Adds a vehicle's stats to the log
	 * @param vehicle vehicle currently being processed
	 */
	public void logVehicle(Vehicle vehicle) {
		totalWaitTime += vehicle.getTimeToWait();
		totalProcessTime += vehicle.getTimeToPay();
		numCompleted++;
	}
	
	/**
	 * Computes the average wait time in the simulation
	 * @return average wait time
	 */
	public double averageWaitTime() {
		if (numCompleted == 0)
			return 0;
		return (double) totalWaitTime / numCompleted;
	}
	
	/**
	 * Computes the average processing time in the simulation
	 * @return average processing time
	 */
	public double averageProcessTime() {
		if (numCompleted == 0)
			return 0;
		return (double) totalProcessTime / numCompleted;
	}

}

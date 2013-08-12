package csc216project2;

/**
 * 
 * @author Gabe
 * @version Jun 20, 2010 12:47:14 AM
 */
public class TollBooth implements VehiclesInLine {
	private VehicleQueue waitingLine;	// The queue of vehicles in line.
	private Log myLog;					// The departing vehicle logs its information here
	private int timeWhenAvailable;		// The time when this toll booth will finally clear out all the vehicles currently in line
	
	/**
	 * Constructor - Initializes all TollBooth variables
	 * @param myLog keeps track of tollBooth  stats
	 */
	public TollBooth(Log myLog) {
		waitingLine = new VehicleQueue();
		this.myLog = myLog;
		timeWhenAvailable = 0;
	}
	
	/**
	 * Get the number of vehicles in line.
	 * @return the number in line
	 * @see csc216project2.VehiclesInLine#size()
	 */
	public int size() {
		if (waitingLine.isEmpty() || waitingLine == null)
			return 0;
		else
			return waitingLine.size();
	}

	/**
	 * Get vehicle at the front of the line without moving it.
	 * @return the front vehicle
	 * @see csc216project2.VehiclesInLine#nextVehicle()
	 */
	public Vehicle nextVehicle() {
		return waitingLine.front();
	}

	/**
	 * Removes the front vehicle from the queue, 
	 * logging its information in the process.
	 * @see csc216project2.VehiclesInLine#processNextVehicle()
	 */
	public void processNextVehicle() {
		try {
			// Remove the front vehicle from the toll-booth queue
			System.out.println(this + "\n");
			Vehicle v = waitingLine.remove();
			
			// Log the vehicle's information
			myLog.logVehicle(v);
			
			// Subtract vehicles data from timeWhenAvailable
			timeWhenAvailable -= v.getTimeToPay() + v.getTimeOfArrival();
			
			System.out.println("A vehicle has just been processed");
			
		} catch (Exception e) {
			// If there is no front vehicle in the queue
			System.out.println("The toll-booth queue is empty!!!");
			e.printStackTrace();
		}
	}

	/**
	 * Tells when the vehicle currently at the front of the line 
	 * (paying its toll) will finish paying its toll and leave the simulation.
     * @return the time the next vehicle is processed
	 * @see csc216project2.VehiclesInLine#departTimeNextVehicle()
	 */
	public int departTimeNextVehicle() {
		int departTime;
		
		// If the queue of vehicles is empty return Integer.MAX_VALUE
		if (waitingLine.isEmpty() ) 
			departTime = Integer.MAX_VALUE;
		
		// Get the front vehicle's depart time
		else {
			departTime = nextVehicle().getTimeOfArrival() 
					   + nextVehicle().getTimeToWait()
					   + nextVehicle().getTimeToPay();
		}

		return departTime;
	}

	/**
	 * Adds a vehicle to the tollbooth queue
	 * @param vehicle vehicle to be added at the back of the queue
	 */
	public void addVehicle(Vehicle vehicle) {
		
		// Compute timeWhenAvailable, (earliest time when the next vehicle that joins it will be able to pay its toll)
		if ( this.departTimeNextVehicle() == Integer.MAX_VALUE ) {
			vehicle.setTimeToWait( 0 );
			timeWhenAvailable += vehicle.getTimeToPay() + vehicle.getTimeOfArrival(); 
		}
		else {
			vehicle.setTimeToWait( timeWhenAvailable );
			timeWhenAvailable += vehicle.getTimeToWait() + vehicle.getTimeToPay() + vehicle.getTimeOfArrival();
		}
		
		// Add the current vehicle to this toll-booth's waiting line
		waitingLine.add(vehicle);
		
	}

	/**
	 * Returns a string representation of a tollbooth
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String string = "\tTollbooth size: " + size() + 
						"\tTime when available: " + timeWhenAvailable + 
						"\tDepart time next vehicle: " + departTimeNextVehicle();
		return string;
	}
	
	/**
	 * Main method - performs some preliminary tests for class methods
	 * @param args none
	 */
	public static void main(String[] args) {
		Log log = new Log();
		TollBooth[] tb = new TollBooth[3];
		
		// Initialize each tollbooth
		for(int i = 0; i < tb.length; i++) {
			tb[i] = new TollBooth(log);
		}
		
		// Print each tollbooth
		for( TollBooth x: tb) {
			System.out.println( x );
		}
		
		tb[0].addVehicle( VehicleFactory.generateVehicle() );
		
		// Print each tollbooth
		for( TollBooth x: tb) {
			System.out.println( x );
		}
	}
}

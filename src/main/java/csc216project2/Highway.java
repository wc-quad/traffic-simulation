package csc216project2;

/**
 * A queue of cars on the highway in the simulation
 * @author Gabe
 * @see VehicleQueue
 * @see VehiclesInLine
 */
public class Highway implements VehiclesInLine {
	private VehicleQueue oncomingVehicles;
	private TollBooth[] tollbooth;
	
	/**
	 * Constructor - Initializes the variables for a Highway
	 * @param numberOfVehicles total number of vehicles to be used in the simulation
	 * @param tollbooth an array of TollBooths to be used in the simulation
	 */
	public Highway(int numberOfVehicles, TollBooth[] tollbooth) {
		// Initialize variables
		oncomingVehicles = new VehicleQueue();
		this.tollbooth = tollbooth;
		
		// Use VehicleFactory to construct the amount of vehicles given and add them to the highway queue
		for (int i=0; i < numberOfVehicles; i++) {
			Vehicle x = VehicleFactory.generateVehicle();
			oncomingVehicles.add(x);
		}
		
	}

	/**
	 * Get the number of vehicles in line.
	 * @return the number in line
	 * @see csc216project2.VehiclesInLine#size()
	 */
	public int size() {
		if (oncomingVehicles.isEmpty() || oncomingVehicles == null )
			return 0;
		else
			return oncomingVehicles.size();
	}

	/**
	 * Get vehicle at the front of the line without removing it.
	 * @return the front vehicle
	 * @see csc216project2.VehiclesInLine#nextVehicle()
	 */
	public Vehicle nextVehicle() {
		return oncomingVehicles.front();
	}

	/**
	 * Removes the front vehicle from the highway queue 
	 * and sends it a getInLine message.
	 * @see csc216project2.VehiclesInLine#processNextVehicle()
	 */
	public void processNextVehicle() {
		try {
			// Remove the front vehicle from the highway queue
			Vehicle vehicle = oncomingVehicles.remove();
			
			// Send it to the shortest tollBooth for its type
			vehicle.getinLine(tollbooth);
			System.out.println( "Highway.processNextVehicle()\nProcessing vehicle: " + vehicle);
			
		} catch (Exception e) {
			// If there is no front vehicle in the queue
			
			e.printStackTrace();
		}
		
	}

	/**
	 * When will the front vehicle leave the line?
     * @return the time the next vehicle is processed
	 * @see csc216project2.VehiclesInLine#departTimeNextVehicle()
	 */
	public int departTimeNextVehicle() {
		int departTime;
		
		// If the queue of vehicles is empty return Integer.MAX_VALUE
		if  (oncomingVehicles.isEmpty() ) 
			departTime = Integer.MAX_VALUE;
		
		// Get the front vehicle's timeOfArrival
		else {
			departTime = nextVehicle().getTimeOfArrival();
		}

		return departTime;
	}

	/**
	 * Returns a string representation of a Highway
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String string = "Highway size: " + size() + 
						"\nNext vehicle: " + nextVehicle() + 
						"\nDepart time next vehicle: " + departTimeNextVehicle();
		
		return string;
	}
	
	/**
	 *  contains some preliminary tests for the class methods
	 * @param args
	 */
	public static void main(String[] args) {
		Log log = new Log();
		TollBooth[] tb = new TollBooth[5];
		
		// Initialize each tollbooth
		for( int i=0; i < tb.length; i++) {
			tb[i] = new TollBooth(log);
		}
		
		// Create a highway
		Highway highway = new Highway(5, tb);
		System.out.println( highway );
		

	}

}

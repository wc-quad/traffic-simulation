package csc216project2;

/**
 * Operations that a line of Vehicles (such as on a highway or
 *   waiting to enter a toll booth or parking lot) must support. 
 * @author Jo P
 */
public interface VehiclesInLine {
	/**
	 * Get vehicle at the front of the line. Do not
	 * remove it from the line.
	 * @return the front vehicle
	 */
	Vehicle nextVehicle();
	
	/**
	 * Processes the vehicle at the front of the line, 
	 * removing it from the line.
	 */
    void processNextVehicle();
    
    /**
     * When will the front vehicle leave the line?
     * @return the time the next vehicle is processed
     */
    int departTimeNextVehicle();
    
    /**
     * Get the number of vehicles in line.
     * @return the number in line
     */
    int size();
}
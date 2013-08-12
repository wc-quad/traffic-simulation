package csc216project2;

/**
 * Provides a calendar of queues that determines the order in which vehicles leave their queues. 
 *  Vehicles leaving constitute the events.
 * @author Jo P
 * @version 1.0
 * @see Highway
 * @see TollBooth
 */
public class EventCalendar {
    private VehiclesInLine[] tollbooth; // Cars at the tollbooths
    private VehiclesInLine highway;       // Cars on the highway (entry to TollBooths)
    private int timeOfNextEvent = Integer.MAX_VALUE; // Time when next event will occur
    private int indexOfNextEvent = -1; // Index of next event if a toll booth, -1 if the highway

    /**
     * Initializes the queues.
     * @param tb toll booths
     * @param hw highway leading to toll booths
     */
    EventCalendar(VehiclesInLine[] tb, VehiclesInLine hw) {
        this.tollbooth = tb;
        this.highway = hw;
    }

    /**
     * Determines which line (toll booth or highway) contains the next item to be processed.
     * @return The line whose front item has the smallest time.
     */
    public VehiclesInLine nextToBeProcessed() {
        int departureAtHighway = highway.departTimeNextVehicle(); // Time next item leaves highway queue
        int soonest = 0;
        for (int k = 1; k < tollbooth.length; k++)
        	if (tollbooth[k].departTimeNextVehicle() < tollbooth[soonest].departTimeNextVehicle())
        		soonest = k;
        int departureAtTollbooth = tollbooth[soonest].departTimeNextVehicle();
        // Are all queues empty?
        if (departureAtHighway == Integer.MAX_VALUE && departureAtTollbooth == Integer.MAX_VALUE) {
        	timeOfNextEvent = Integer.MAX_VALUE;
            return null;
        }

        // Is the next event a vehicle leaving the highway?
        if (departureAtHighway <= departureAtTollbooth) {
        	indexOfNextEvent = -1;
        	timeOfNextEvent = departureAtHighway;
        	return highway;
        }

    	timeOfNextEvent = departureAtTollbooth;
    	indexOfNextEvent = soonest;
        return tollbooth[soonest];
    }
    
    /**
     * When will the next event occur?
     * @return time of the next event
     */
    public int getTimeOfNextEvent() {
    	return timeOfNextEvent;
    }
    
    /**
     * What is the index of the next event (which toll booth)?
     * @return the index of the toll booth of the next event or -1 if 
     * 		the next event occurs at the highway
     */
    public int getIndexOfNextEvent() {
    	return indexOfNextEvent;
    }   
}
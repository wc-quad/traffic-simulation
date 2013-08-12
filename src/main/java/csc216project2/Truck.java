package csc216project2;
import java.awt.Color;

/**
 * A child of the Vehicle class
 * @author Gabe
 * @see Vehicle
 */
public class Truck extends Vehicle{
	private static Color color = Color.BLACK;		// A static member used for display
	private int laneChosen;							// Index of the toll booth that the vehicle chooses
	
	/**
	 * Constructor - Initializes a  new Truck object
	 * @param timeOfArrival time a Truck arrives at the toll booth plaza
	 * @param timeToPay time t takes a Truck to pay at a toll booth
	 */
	public Truck(int timeOfArrival, int timeToPay) {
		super(timeOfArrival, timeToPay);
	}

	/**
	 * Getter - gets the color 
	 * @return the color of a Truck
	 */
	public Color getColor() {
		return Truck.color;
	}
	
	/**
	 * Setter - sets the color of all Trucks
	 * @param color the color to set all Trucks
	 */
	public void setColor(Color color) {
		Truck.color = color;
	}
	
	/**
	 * Places the Truck in the line of the shortest truck toll-booth
	 * 	default lane is the first Truck toll-booth (index [tollbooth.length - truckLanes])
	 * @param tollBooth an array of toll-booths on the highway
	 */
	public void getinLine(TollBooth[] tollBooth) {
		// Compute number of Truck lanes
		int truckLanes = (int) Math.ceil(tollBooth.length * 0.25 );
		System.out.println("Number of Truck lanes: " + truckLanes);
		laneChosen = tollBooth.length - truckLanes;	// shortestLane defaults to the leftmost toll-booth
		
		System.out.println("tollbooth[" + laneChosen + "] has " + tollBooth[laneChosen].size() + " vehicles.");
		
		if( laneChosen + 1 < tollBooth.length) {

			for( int i = laneChosen + 1; i < tollBooth.length; i++) {

				if ( tollBooth[ i ].size() < tollBooth[ laneChosen ].size() )
					laneChosen = i;
			}
		}


		// Add the Car to the shortest toll-booth lane
		tollBooth[laneChosen].addVehicle(this); 
		System.out.println("The Truck was added to toll-booth: " + laneChosen + "\n");

		
	}
	
	/**
	 * Getter - get the lane chosen
	 * @return number of the lane chosen
	 */
	public int getLaneChosen() {
		return laneChosen;
	}

	/**
	 * Prints a Truck Object
	 * @Override
	 * @return the string version of a Truck
	 */
	public String toString() {
		String s = super.toString() + "Truck-----" + "\ncolor: " + color + "\nlaneChosen: " + laneChosen + "\n<<<<<o>>>>>\n";
		return s;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TollBooth[] tollbooth = new TollBooth[5];
		Log log = new Log();
		for ( int i = 0; i < tollbooth.length; i++) {
			tollbooth[i] = new TollBooth( log );
		}
		Vehicle a = new Truck(1, (int) (5.4 * .629));
		System.out.println( a );
		
		Vehicle b = new Truck(8, (int) (5.4 * .336));
		System.out.println( b );

		// setColor()
		a.setColor(Color.ORANGE);
		
		a.getinLine(tollbooth);
		b.getinLine(tollbooth);
		
		System.out.println( "Time to wait for truck a: " + a.getTimeToWait() + "\nTime to wait for truck b: " + b.getTimeToWait());
		
		

	}

}

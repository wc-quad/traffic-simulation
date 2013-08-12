package csc216project2;
import java.awt.Color;

/**
 * A child of the Vehicle class
 * @author Gabe
 * @see Vehicle
 */
public class Car extends Vehicle {
	private static Color color = Color.RED;		// A static member used for display
	protected int laneChosen;					// Index of the toll booth that the vehicle chooses
	
	/**
	 * Constructor - Initializes new Car objects
	 * @param timeOfArrival time a Car arrives at the toll booth plaza
	 * @param timeToPay time it takes a Car to pay at a toll booth
	 */
	public Car(int timeOfArrival, int timeToPay) {
		super(timeOfArrival, timeToPay);
		laneChosen = 1;			// default lane for a car
	}
	
	/**
	 * Getter - returns a Car's color
	 * @Override
	 * @return the color of the calling Car
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Setter - sets the color of a Car
	 * @Override
	 */
	public void setColor(Color color) {
		Car.color = color;
	}

	/**
	 * Places the Car in the shortest toll-booth line on the highway according to its algorithm
	 * @Override
	 */
	public void getinLine(TollBooth[] tollBooth) {
		
		if( laneChosen + 1 <  tollBooth.length) {

			for( int i = laneChosen + 1; i < tollBooth.length; i++) {

				if ( tollBooth[ i ].size() < tollBooth[ laneChosen ].size() )
					laneChosen = i;
			}
		}

		
		// Add the Car to the shortest toll-booth lane
		tollBooth[laneChosen].addVehicle(this); 
		System.out.println("The Car chose toll-booth: " + laneChosen + "\n");

	}
	
	/**
	 * Computes the lane chosen by the calling Car
	 * @Override
	 * @return the the lane chosen by the calling Car based on 
	 * the algorithm for a Car
	 */
	public int getLaneChosen() {
		return laneChosen;
	}
	
	/**
	 * Prints a Car Object
	 * @Override
	 * @return the string version of a Car
	 */
	public String toString() {
		String s = super.toString() + "Car-----" + "\ncolor: " + color + "\nlaneChosen: " + laneChosen + "\n<<<<<o>>>>>\n";
		return s;
	}

	/**
	 * Preliminary tests for class methods
	 * @param args none
	 */
	public static void main(String[] args) {
		Log log = new Log();
		TollBooth[] tb = new TollBooth[6];
		for (int i=0; i < tb.length; i++) {
			tb[i] = new TollBooth(log);
		}
		
		Vehicle a = new Car(0, (int) (5.4 * .629));
		a.getinLine(tb);
		a.setColor(Color.CYAN);
		System.out.println( "Car a chose lane " + a.getLaneChosen() );
		
		Vehicle b = new Car(0, (int) (5.4 * .336));
		b.getinLine(tb);
		System.out.println( "Car b chose lane " + b.getLaneChosen()  );

		
		
		
		
	}
}

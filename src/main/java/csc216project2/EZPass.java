package csc216project2;
import java.awt.Color;

/**
 * A child of the Car class, descendant of a Vehicle
 * @author Gabe
 * @see Car
 * @see Vehicle
 */
public class EZPass extends Car {
	private static Color color = Color.MAGENTA;	// A static member used for display
	
	/**
	 * Constructor - Initializes a  new EZPay object
	 * @param timeOfArrival time a Car arrives at the toll booth plaza
	 * @param timeToPay time t takes a Car to pay at a toll booth
	 */
	public EZPass(int timeOfArrival, int timeToPay) {
		super(timeOfArrival, timeToPay);
		laneChosen = 0;
	}

	/**
	 * Getter - gets the color 
	 * @return the color of an EZPass car
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Setter - sets the color of all EZPass Cars
	 * @param color the color to set all EZPass cars
	 */
	public void setColor(Color color) {
		EZPass.color = color;
	}
	
	/**
	 * Places the EZPass car in the line of the shortest toll-booth
	 * default lane is the first toll-booth (index 0)
	 * @param tollBooth an array of toll-booths on the highway
	 */
	public void getInLine(TollBooth[] tollBooth) {
		// shortestLane defaults to toll-booth[0]
		System.out.println("tollbooth[" + laneChosen + "] has " + tollBooth[laneChosen].size() + " vehicles.");
		
		if( laneChosen + 1 <  tollBooth.length) {

			for( int i = laneChosen + 1; i < tollBooth.length; i++) {

				if ( tollBooth[ i ].size() < tollBooth[ laneChosen ].size() )
					laneChosen = i;
			}
		}

			
		// Add the Car to the shortest toll-booth lane
		tollBooth[laneChosen].addVehicle(this); 
		System.out.println("The EZ-Pass Car chose toll-booth: " + laneChosen + "/n");

		
	}

	/**
	 * Prints an EZPass Car Object
	 * @Override
	 * @return the string version of an EZPass Car
	 */
	public String toString() {
		String s = super.toString() + "EZPass Car-----" + "\ncolor: " + color + "\nlaneChosen: " + laneChosen + "\n<<<<<o>>>>>\n";
		return s;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Vehicle a = new EZPass(0, (int) (5.4 * .629));
		System.out.println( a );
		
		Vehicle b = new EZPass(0, (int) (5.4 * .336));
		System.out.println( b );

		// setColor()
		a.setColor(Color.CYAN);
		System.out.println( b );
		
		// getColor()
		System.out.println( b.getColor() );

	}

}

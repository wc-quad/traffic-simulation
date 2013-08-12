package csc216project2;
import java.util.*;

/**
 * A simple factory class whose only task is to generate Vehicles.<br>
 *   Trucks are generated 20% of the time, with pay time 25 - 28 seconds. <br>
 *   EZPass cars are generated 15% of the time, with pay time 10 - 12 seconds.<br>
 *   Cars without EZPass are generated 65% of the time, with pay time 15 - 19 seconds.
 * @author Jo P
 * @see Truck
 * @see Car
 * @see EZPass
 */
public class VehicleFactory {
    private static int time = 0;  // absolute time
    private static Random randomNumber = new Random(10); // randomizes values

    /**
     * Generate a new Vehicle as described above.
     * @return the Vehicle created
     */
    public static Vehicle generateVehicle() {
        double x = randomNumber.nextDouble();
        time += (int)(randomNumber.nextDouble() * 5.4);
        if (x < .20) {
        	return new Truck(time, 25 + (int) (randomNumber.nextDouble() * 3)); 
        }
        else if (x < .35){
        	return new EZPass(time, 10 + (int) (randomNumber.nextDouble() * 2));
        }
        else {
        	return new Car(time, 15 + (int) (randomNumber.nextDouble() * 4));
        }
    }
}
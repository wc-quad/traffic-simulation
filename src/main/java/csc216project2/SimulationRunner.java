package csc216project2;

import java.util.Scanner;

/**
 * Simple class to run traffic/toll booth simulations. 
 *   Input is from standard input. Output is to standard output.
 *   No error checking is performed. 
 * @author Jo P
 */
public class SimulationRunner {
	
	public static void main(String[] args) {		
		Scanner sc = new Scanner(System.in);
		System.out.print("Number of vehicles: ");
		int numberOfVehicles = sc.nextInt();
		System.out.print("Number of toll booths: ");
		int numberOfTollBooths = sc.nextInt();
		Simulator s = new Simulator(numberOfTollBooths, numberOfVehicles);
		while (s.moreSteps())
			s.step();
		System.out.printf("Average Wait Time: %1$.2f seconds\n", s.averageWaitTime());
		System.out.printf("Average Time to Pay Toll: %1$.2f seconds\n", s.averageProcessTime());		
	}

}
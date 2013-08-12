package csc216project2;

import java.util.LinkedList;

/**
 * Implements a simple queue (first-in, first-out list) of Vehicles.
 * @author Jo P
 */
public class VehicleQueue {
	
	private LinkedList<Vehicle> queue;  // The underlying queue data structure
	
	/**
	 * Constructor. Creates an empty queue.
	 */
	public VehicleQueue() { 
		queue = new LinkedList<Vehicle>(); 
	}
	
	/**
	 * Gets the number of elements in the queue.
	 * @return the number of elements
	 */
	public int size() {
		return queue.size();
	}
	
	/**
	 * Adds a new vehicle to the rear of the queue.
	 * @param v the vehicle to add
	 */
	public void add(Vehicle v){
		queue.addLast(v);
	}
	
	/**
	 * Removes the front vehicle from the queue. Throws an exception
	 *  if the queue is empty.
	 * @return
	 */
	public Vehicle remove() {
		return queue.removeFirst();
	}
	
	/**
	 * Gets the front element of the queue without removing it, or null
	 *  if the queue is empty.
	 * @return the front element
	 */
	public Vehicle front() {
		return queue.peek();
	}
	
	/**
	 * Is the queue empty?
	 * @return true if the queue has no elements, false otherwise
	 */
	public boolean isEmpty() {
		return queue.isEmpty();
	}
}
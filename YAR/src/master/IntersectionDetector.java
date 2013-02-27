package master;

import lejos.nxt.LightSensor;

/**
 * This class will detect when a black line intersection is crossed. At each
 * intersection it will send a message to the other NXT brick to poll the US to
 * detect obstacles.
 * 
 * @author alex
 * 
 */
public class IntersectionDetector extends Thread {
	
	public IntersectionDetector (LightSensor ls) {
		// TODO
	}
	
	/**
	 * This is called whenever an intersection has been detected.
	 */
	private void IntersectionDetected () {
		// TODO
		// Need to update Odometer
		// Need to US Poll to see if there is an obstacle ahead.
	}

}

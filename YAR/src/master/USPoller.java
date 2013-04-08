/**
 * 
 */
package master;

import lejos.nxt.LCD;
import lejos.nxt.UltrasonicSensor;

/**
 * This class is used to retrieve data from the ultrasonic sensor.
 * @author alex
 *
 */
public class USPoller {
	
	private UltrasonicSensor us;
	private final int FILTER_OUT = 10;
	
	public USPoller (UltrasonicSensor us) {
		this.us = us;
	}

	public int getFilteredData() {
		boolean filter = true;
		int filterControl = 0;
		int distance = 255;
		
		while (filter) {
			// do a ping
			us.ping();
			// wait for the ping to complete
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
			}
			distance = us.getDistance();
			if (distance == 255 && (filterControl < FILTER_OUT)) {
				filterControl++;
			} else {
				filter = false;
			}
		}

		return distance;
	}
	
	public boolean isObjectInRange(double range) {
		int dist = getFilteredData();
		return dist < range;
	}
	
}

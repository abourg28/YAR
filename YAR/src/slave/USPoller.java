package slave;

/**
 * This object is used to determine whether there is an obstacle in front of the
 * robot while it is navigating.
 * 
 * @author alex
 * 
 */
public class USPoller {

	/**
	 * Polls the ultrasonic sensor to find the distance of the object in front
	 * of it.
	 * 
	 * @return the distance to the object
	 */
	public int poll() {
		// TODO
		return 255;
	}
	
	/**
	 * This method will poll the ultrasonic sensor and only return once it sees an object in range.
	 * @param range the threshold distance at which an object must be
	 */
	public void waitUntilObjectInRange(int range) {
		// TODO
	}

}

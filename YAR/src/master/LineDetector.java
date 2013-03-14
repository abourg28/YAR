package master;

import java.io.IOException;

import lejos.nxt.LightSensor;
import lejos.nxt.Sound;

/**
 * This class will detect when a black line intersection is crossed. At each
 * intersection it will send a message to the other NXT brick to poll the US to
 * detect obstacles.
 * 
 * @author alex
 * 
 */
public class LineDetector extends Thread {
	
	public LineDetector (LightSensor ls) {
		// TODO
	}
	
	/**
	 * This is called whenever an intersection has been detected.
	 */
	private void lineDetected () {
		// TODO
		// Need to update Odometer
		// Need to US Poll to see if there is an obstacle ahead.
		try {
			MasterBluetoothCommunicator.sendUSPollRequest();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Sound.buzz();
			e.printStackTrace();
		}
	}

}

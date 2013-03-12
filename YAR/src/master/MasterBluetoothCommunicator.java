/**
 * 
 */
package master;

import javax.bluetooth.RemoteDevice;

import lejos.nxt.comm.BTConnection;
import lejos.nxt.comm.Bluetooth;
import common.Pos;
import common.Protocol;

/**
 * This class takes care of communicating with the other NXT brick.
 * 
 * @author alex
 * 
 */
public class MasterBluetoothCommunicator {

	private static BTConnection conn;

	public static void InitializeConnection() {
		Bluetooth.setFriendlyName(Protocol.MASTER_NAME);

		// Setup bluetooth connection
		// NOTE: This will only work if these devices have previously been
		// paired manually
		RemoteDevice slave = Bluetooth.getKnownDevice(Protocol.SLAVE_NAME);
		conn = Bluetooth.connect(slave);
	}

	/**
	 * Polls the ultrasonic sensor of the slave brick.
	 * 
	 * @return the distance to the obstacle in from of the ultrasonic sensor
	 */
	public static int sendUSPollRequest() {
		// TODO
		// Send Protocol.US_POLL_REQUEST to slave
		// Read distance
		return 255;
	}

	public static void sendOpenFanRequest() {
		// TODO
	}

	public static void sendCloseFanRequest() {
		// TODO
	}

	public static void sendInstructions(Instructions instructions) {
		// TODO
	}

	public static Pos sendLaunchPositionRequest() {
		// TODO
		return null;
	}

	public static void sendLaunchRequest() {
		// TODO
	}

	/**
	 * Returns when an edge has been detected by the ultrasonic sensor. Beware
	 * that there is a latency involved since this information needs to be
	 * communicated through bluetooth.
	 * 
	 * @param range
	 *            the distance at which the edge should be detected
	 * @param isFalling
	 *            if true this will return once an object is outside the range;
	 *            if false this will return once an object is inside the range
	 */
	public static void sendUSDetectEdgeRequest(int range, boolean isFalling) {
		// TODO
		// Send Protocol.US_DETECT_EDGE_REQUEST to slave
		// Send isFalling to slave
		// Send range to slave
		// Wait for stop signal

	}

}

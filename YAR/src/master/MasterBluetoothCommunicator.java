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
 * @author alex
 *
 */
public class MasterBluetoothCommunicator {
	
	private static BTConnection conn;
	
	public static void InitializeConnection() {
		Bluetooth.setFriendlyName(Protocol.MASTER_NAME);
		
		// Setup bluetooth connection
		// NOTE: This will only work if these devices have previously been paired manually
		RemoteDevice slave = Bluetooth.getKnownDevice(Protocol.SLAVE_NAME);
		conn = Bluetooth.connect(slave);
	}
	
	public static boolean sendUSPollRequest() {
		// TODO
		return false;
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
	
	public static void sendLocalizationRequest() {
		// TODO
		int response = 0;
		// Write LOCALIZATION_REQUEST
		while (true) {
			// Wait for slave response
			if (response == Protocol.ROTATE_REQUEST) {
				// Slave will send the rotate speed as an integer
				// (read from input stream)
				
			} else if (response == Protocol.STOP_REQUEST) {
				// Navigate robot to center of current tile
				break;
			} else {
				// Shouldn't get here. Throw error.
			}
		}
	}

}

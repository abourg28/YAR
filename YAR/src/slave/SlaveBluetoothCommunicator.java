/**
 * 
 */
package slave;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import lejos.nxt.comm.BTConnection;
import lejos.nxt.comm.Bluetooth;
import common.IDefender;
import common.Protocol;

/**
 * This class takes care of receiving requests from the other NXT brick.
 * 
 * @author alex
 * 
 */
public class SlaveBluetoothCommunicator {

	private USPoller poller;
	private ILauncher launcher;
	private IDefender defender;

	private BTConnection conn;

	public SlaveBluetoothCommunicator(USPoller poller, ILauncher launcher,
			IDefender defender) {
		this.poller = poller;
		this.launcher = launcher;
		this.defender = defender;
		this.conn = null;
	}

	/**
	 * This method will wait for connection with the master brick and will
	 * handle incoming requests in a continuous loop.
	 */
	public void handleRequests() {
		// TODO
		Bluetooth.setFriendlyName(Protocol.SLAVE_NAME);
		// Connect with master
		conn = Bluetooth.waitForConnection();

		// Handle requests
		while (true) {
			try {
				DataInputStream input = conn.openDataInputStream();
				int request = input.readInt();
				switch (request) {
				case Protocol.CLOSE_FAN_REQUEST:
					handleCloseFanRequest();
					break;
				case Protocol.LAUNCH_POSITION_REQUEST:
					handleLaunchPositionRequest();
					break;
				case Protocol.LAUNCH_REQUEST:
					handleLaunchRequest();
					break;
				case Protocol.OPEN_FAN_REQUEST:
					handleOpenFanRequest();
					break;
				case Protocol.UPDATE_INSTRUCTIONS_REQUEST:
					handleUpdateInstructionsRequest();
					break;
				case Protocol.US_POLL_REQUEST:
					handleUsPollRequest();
					break;
				case Protocol.US_DETECT_EDGE_REQUEST:
					handleUsDetectEdgeRequest();
					break;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void handleUsDetectEdgeRequest() {
		// TODO
		// Read in the isFalling boolean
		// Read in the range integer
		// Wait until the appropriate edge is detected
		// Send Protocol.STOP_REQUEST to Master
	}

	private void handleUsPollRequest() {
		// TODO Auto-generated method stub

	}

	private void handleUpdateInstructionsRequest() {
		// TODO Auto-generated method stub

	}

	private void handleOpenFanRequest() {
		// TODO Auto-generated method stub

	}

	private void handleLaunchRequest() {
		// TODO Auto-generated method stub

	}

	private void handleLaunchPositionRequest() {
		// TODO Auto-generated method stub

	}

	private void handleCloseFanRequest() {
		// TODO Auto-generated method stub

	}

}

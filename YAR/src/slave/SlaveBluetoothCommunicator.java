/**
 * 
 */
package slave;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import lejos.nxt.comm.BTConnection;
import lejos.nxt.comm.Bluetooth;
import common.IDefender;
import common.Instructions;
import common.ParseInstructions;
import common.Pos;
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
	private PrintStream out;
	private DataInputStream in;

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
		out = new PrintStream(conn.openDataOutputStream());
		in = conn.openDataInputStream();

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
		// TODO Unused
		// Read in the isFalling boolean
		// Read in the range integer
		// Wait until the appropriate edge is detected
		// Send Protocol.STOP_REQUEST to Master
	}

	private void handleUsPollRequest() {
		// Poll US sensor
		int distance = poller.poll();
		// Send distance to Master
		out.println(distance);
		out.flush();
	}

	private void handleUpdateInstructionsRequest() {
		Instructions inst = ParseInstructions.parse(in);
		// TODO update instructions
	}

	private void handleOpenFanRequest() {
		// TODO Auto-generated method stub

	}

	private void handleLaunchRequest() {
		this.launcher.launch();
		this.launcher.retract();
	}

	private void handleLaunchPositionRequest() {
		this.launcher.calculateLaunchPosition();
		out.println(this.launcher.getLaunchX());
		out.println(this.launcher.getLaunchY());
		out.println(this.launcher.getLaunchTheta());
		out.flush();
	}

	private void handleCloseFanRequest() {
		// TODO Auto-generated method stub

	}

}

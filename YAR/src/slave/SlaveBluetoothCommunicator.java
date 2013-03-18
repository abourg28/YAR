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

	private ILauncher launcher;
	private IDefender defender;

	private BTConnection conn;
	private PrintStream out;
	private DataInputStream in;

	public SlaveBluetoothCommunicator(ILauncher launcher,
			IDefender defender) {
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
				case Protocol.LAUNCH_POSITION_REQUEST:
					handleLaunchPositionRequest();
					break;
				case Protocol.LAUNCH_REQUEST:
					handleLaunchRequest();
					break;
				case Protocol.UPDATE_INSTRUCTIONS_REQUEST:
					handleUpdateInstructionsRequest();
					break;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void handleUpdateInstructionsRequest() {
		Instructions inst = ParseInstructions.parse(in);
		// TODO update instructions
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

}

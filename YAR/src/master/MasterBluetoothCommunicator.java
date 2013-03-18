/**
 * 
 */
package master;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import javax.bluetooth.RemoteDevice;

import lejos.nxt.comm.BTConnection;
import lejos.nxt.comm.Bluetooth;
import common.Instructions;
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
	private static PrintStream out;
	private static DataInputStream in;

	public static void InitializeConnection() {
		Bluetooth.setFriendlyName(Protocol.MASTER_NAME);

		// Setup bluetooth connection
		// NOTE: This will only work if these devices have previously been
		// paired manually
		RemoteDevice slave = Bluetooth.getKnownDevice(Protocol.SLAVE_NAME);
		conn = Bluetooth.connect(slave);
		out = new PrintStream(conn.openDataOutputStream());
		in = conn.openDataInputStream();
	}

	public static void sendInstructions(Instructions instructions) {
		out.println(Protocol.UPDATE_INSTRUCTIONS_REQUEST);
		out.println(instructions);
		out.flush();
	}

	public static Pos sendLaunchPositionRequest() throws NumberFormatException, IOException {
		out.println(Protocol.LAUNCH_POSITION_REQUEST);
		out.flush();
		Pos p = new Pos();
		p.x = Double.parseDouble(in.readLine());
		p.y = Double.parseDouble(in.readLine());
		p.theta = Double.parseDouble(in.readLine());
		
		return p;
	}

	public static void sendLaunchRequest() {
		out.println(Protocol.LAUNCH_REQUEST);
		out.flush();
	}

}

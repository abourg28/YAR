/**
 * 
 */
package master;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import javax.bluetooth.RemoteDevice;

import lejos.nxt.Sound;
import lejos.nxt.comm.BTConnection;
import lejos.nxt.comm.Bluetooth;
import lejos.nxt.comm.NXTConnection;
import lejos.nxt.comm.RS485;
import lejos.nxt.comm.RS485Connection;
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

	private static NXTConnection conn;
	private static DataOutputStream out;
	private static DataInputStream in;

	public static void InitializeConnection() {
		Bluetooth.setFriendlyName(Protocol.MASTER_NAME);
		
		conn = RS485.connect(Protocol.SLAVE_NAME, NXTConnection.PACKET);
		if (conn == null) {
			Sound.buzz();
			conn = RS485.connect(Protocol.SLAVE_NAME, NXTConnection.PACKET);
		}
		out = conn.openDataOutputStream();
		in = conn.openDataInputStream();
	}

	public static void sendInstructions(Instructions instructions)
			throws IOException {
		out.writeInt(Protocol.UPDATE_INSTRUCTIONS_REQUEST);
		out.flush();
		out.writeInt(instructions.role.getId());
		out.writeChar(',');
		out.writeInt(instructions.startingCorner.getId());
		out.writeChar(',');
		out.writeInt(instructions.bx);
		out.writeChar(',');
		out.writeInt(instructions.by);
		out.writeChar(',');
		out.writeInt(instructions.w1);
		out.writeChar(',');
		out.writeInt(instructions.w2);
		out.writeChar(',');
		out.writeInt(instructions.d1);
		out.flush();
	}

	public static Pos sendLaunchPositionRequest() throws NumberFormatException,
			IOException {
		out.writeInt(Protocol.LAUNCH_POSITION_REQUEST);
		out.flush();
		Pos p = new Pos();
		p.x = in.readDouble();
		p.y = in.readDouble();
		p.theta = in.readDouble();

		return p;
	}

	public static void sendLaunchRequest() throws IOException {
		out.writeInt(Protocol.LAUNCH_REQUEST);
		out.flush();
	}

}

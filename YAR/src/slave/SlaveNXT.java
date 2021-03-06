/**
 * 
 */
package slave;

import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.comm.Bluetooth;

import common.FanDefender;
import common.IDefender;
import common.Protocol;

/**
 * @author alex
 *
 */
public class SlaveNXT {
	

	private static NXTRegulatedMotor rightMotor = Motor.C;
	private static NXTRegulatedMotor leftMotor = Motor.A;
	private static SlaveBluetoothCommunicator comm;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Bluetooth.setFriendlyName(Protocol.SLAVE_NAME);
		ILauncher launcher = new AngleLauncher(leftMotor, rightMotor);
		IDefender defender = new FanDefender();
		comm = new SlaveBluetoothCommunicator(launcher, defender);
		comm.handleRequests();
	}

}

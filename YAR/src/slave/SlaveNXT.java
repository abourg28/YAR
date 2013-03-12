/**
 * 
 */
package slave;

import common.FanDefender;
import common.IDefender;

import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;

/**
 * @author alex
 *
 */
public class SlaveNXT {
	

	private static NXTRegulatedMotor rightMotor = Motor.A;
	private static NXTRegulatedMotor leftMotor = Motor.B;
	private static SlaveBluetoothCommunicator comm;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		USPoller poller = new USPoller();
		ILauncher launcher = new AngleLauncher(leftMotor, rightMotor);
		IDefender defender = new FanDefender();
		comm = new SlaveBluetoothCommunicator(poller, launcher, defender);
		comm.handleRequests();
	}

}

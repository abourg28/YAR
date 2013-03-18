/**
 * 
 */
package slave;

import common.FanDefender;
import common.IDefender;

import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;

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
		ILauncher launcher = new AngleLauncher(leftMotor, rightMotor);
		IDefender defender = new FanDefender();
		comm = new SlaveBluetoothCommunicator(launcher, defender);
		comm.handleRequests();
	}

}

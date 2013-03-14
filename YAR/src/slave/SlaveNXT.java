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
	private static SensorPort usPort = SensorPort.S1;
	private static SlaveBluetoothCommunicator comm;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		USPoller poller = new USPoller(new UltrasonicSensor(usPort));
		ILauncher launcher = new AngleLauncher(leftMotor, rightMotor);
		IDefender defender = new FanDefender();
		comm = new SlaveBluetoothCommunicator(poller, launcher, defender);
		comm.handleRequests();
	}

}

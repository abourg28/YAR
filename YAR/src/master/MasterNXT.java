/**
 * 
 */
package master;

import common.IDefender;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import master.USLocalizer.LocalizationType;

/**
 * @author alex
 * 
 */
public class MasterNXT {

	private static IRobot robot;
	private static Instructions instructions;
	private static Odometer odo;
	private static INavigator nav;
	private static IDefender defender;
	private static USLocalizer localizer;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		instructions = new Instructions();
		robot = new YARRobot();
		odo = new Odometer(robot, true);
		nav = odo.getNavigator();
		localizer = new USLocalizer(odo, LocalizationType.FALLING_EDGE);

		// clear the display
		LCD.clear();

		// ask the user whether the motors should drive in a square or
		// float
		LCD.drawString("Press any button ", 0, 0);
		LCD.drawString("    to start     ", 0, 1);

		Button.waitForPress();

		// Receive instructions from server
		ParseInstructions.parse(null); // Replace null with the data input
										// stream
		// Send instructions to slave brick
		MasterBluetoothCommunicator.sendInstructions(instructions);

		// Localize robot and go to the center of the corner tile
		localizer.doLocalization();

		if (instructions.isOffense) {
			// On offense
			// Obtain launch position (send request to other brick)
			// Offensive loop
			while (true) {
				// Navigate to the ball dispenser
				// Load ball
				// Navigate to the launch position
				// Launch (send request to other brick)
			}
		} else {
			// Calculate defense position
			// Navigate to defender position
			// Orient robot
			// Defensive loop
			while (true) {
				// Defend
			}

		}

	}

}

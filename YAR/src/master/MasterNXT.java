/**
 * 
 */
package master;

import java.io.IOException;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.SensorPort;
import lejos.nxt.Sound;
import lejos.nxt.UltrasonicSensor;
import master.USLocalizer.LocalizationType;

import common.Instructions;
import common.PlayerRole;
import common.Position;
import common.StartCorner;

/**
 * @author alex
 * 
 */
public class MasterNXT {

	private static SensorPort usPort = SensorPort.S3;
	private static SensorPort leftLsPort = SensorPort.S1;
	private static SensorPort rightLsPort = SensorPort.S2;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		IRobot robot = new YARRobot();
		UltrasonicSensor us = new UltrasonicSensor(usPort);
		USPoller poller = new USPoller(us);
		Odometer odo = new Odometer(robot, true, poller);
		OdometryDisplay disp = new OdometryDisplay(odo);
		INavigator nav = BlockNavigator.getInstance(robot, odo, poller);
		USLocalizer localizer = new USLocalizer(odo, us,
				LocalizationType.FALLING_EDGE);

		// clear the display
		LCD.clear();

		// ask the user whether the motors should drive in a square or
		// float
		LCD.drawString("Press any button ", 0, 0);
		LCD.drawString("    to start     ", 0, 1);

		Button.waitForAnyPress();
		disp.start();

		// Receive instructions from server
		BluetoothConnection bt = new BluetoothConnection();
		Instructions instructions = bt.getInstructions();
		bt.printTransmission();
		// Instructions instructions = getSampleInst();

		localizer.doLocalization(instructions.startingCorner);

		if (instructions.role == PlayerRole.ATTACKER) {
			// On offense
			// Obtain launch position (send request to other brick)
			Position p = null;
			MasterBluetoothCommunicator.InitializeConnection();
			try {
				// Send instructions to slave brick
				MasterBluetoothCommunicator.sendInstructions(instructions);
			} catch (IOException e) {
				Sound.buzz();
			}

			//  Try getting the launch position a few times
			for (int i = 0; p == null || Double.isInfinite(p.x) || Double.isInfinite(p.y); i++) {
				if (i >= 3) {
					// If slave isn't cooperating then default to 8 tiles away
					p = new Position();
					p.x = 180;
					p.y = 330 - (8 * 30);
					p.theta = 90;
					break;
				}
				try {
					p = MasterBluetoothCommunicator.sendLaunchPositionRequest();
				} catch (NumberFormatException e) {
					Sound.buzz();
				} catch (IOException e) {
					Sound.buzz();
				}
			}
			Sound.twoBeeps();
			// Offensive loop
			while (true) {

				// Localize robot and go to the center of the corner tile
				// Navigate to the ball dispenser and load balls
				nav.goToLoader(instructions.getLoaderX(),
						instructions.getLoaderY());

				try {
					// Navigate to the launch position
					nav.travelTo(p.x, p.y);
					LCD.clear(4);
					LCD.drawString("Launch x: " + p.x, 0, 4);
					LCD.clear(5);
					LCD.drawString("Launch y: " + p.y, 0, 5);
					nav.turnTo(p.theta);
					// Launch (send request to other brick)
					for (int i = 0; i < 5; i++) {
						MasterBluetoothCommunicator.sendLaunchRequest();
						Thread.sleep(3000);
					}
				} catch (NumberFormatException e1) {
					Sound.buzz();
				} catch (IOException e1) {
					Sound.buzz();
				} catch (InterruptedException e) {
					Sound.buzz();
				}

				// TODO remove
				break;
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

	public static Instructions getSampleInst() {
		Instructions i = new Instructions();
		i.bx = 0;
		i.by = 0;
		i.d1 = 1;
		i.role = PlayerRole.ATTACKER;
		i.startingCorner = StartCorner.BOTTOM_LEFT;
		i.w1 = 2;
		i.w2 = 3;
		return i;
	}

}

/**
 * 
 */
package master;

import lejos.nxt.LCD;
import lejos.nxt.Sound;
import lejos.nxt.UltrasonicSensor;

/**
 * One of the first stages of the robot. This is the initial localization to
 * find out which corner the robot is in. This routine makes use of the walls at
 * the corners to find its absolute theta. Once the theta is known then finding
 * which corner the robot is situated in is easy (i.e. If the walls are at 0 and
 * 90 degrees then you're in the top left corner)
 * 
 * @author alex
 * 
 */
public class USLocalizer {

	public enum LocalizationType {
		FALLING_EDGE, RISING_EDGE
	};

	public static double ROTATION_SPEED = 30;
	public static int WALL_DIST = 40;
	public static int NOISE = 1;
	public static int TRESH_HOLD = 7;

	private Odometer odo;
	private IRobot robot;
	private LocalizationType locType;
	private INavigator nav;

	/** The number of times a wall is detected. */
	private int visibleCount = 0;
	/** The number of times a wall is not detected. */
	private int notVisibleCount = 0;

	public USLocalizer(Odometer odo, LocalizationType locType) {
		this.odo = odo;
		this.robot = odo.getTwoWheeledRobot();
		this.locType = locType;
		this.nav = odo.getNavigator();
	}

	public void doLocalization() {
		double[] pos = new double[3];
		double[] noisePos = new double[3];
		double angle, alphaAngle, betaAngle;

		if (locType == LocalizationType.FALLING_EDGE) {
			LCD.drawString("Falling Edge!", 0, 5);
			// Rotate until the wall is not visible
			while (getFilteredData() <= WALL_DIST || notVisibleCount < 5) {
				robot.setRotationSpeed(ROTATION_SPEED);
			}

			// Rotate until the wall is visible
			while (getFilteredData() > WALL_DIST + NOISE || visibleCount < 5) {
				robot.setRotationSpeed(ROTATION_SPEED);
			}
			odo.getPosition(noisePos);
			while (getFilteredData() >= WALL_DIST - NOISE || visibleCount < 5) {
				robot.setRotationSpeed(ROTATION_SPEED);
			}
			// Record alpha angle
			Sound.beep();
			odo.getPosition(pos);
			alphaAngle = (pos[2] + noisePos[2]) / 2;

			// Rotate in other direction until the wall is not visible
			while (getFilteredData() < WALL_DIST || notVisibleCount < 5) {
				robot.setRotationSpeed(-ROTATION_SPEED);
			}

			// Rotate until the wall is visible
			while (getFilteredData() > WALL_DIST + NOISE || visibleCount < 5) {
				robot.setRotationSpeed(-ROTATION_SPEED);
			}
			odo.getPosition(noisePos);
			while (getFilteredData() >= WALL_DIST - NOISE || visibleCount < 5) {
				robot.setRotationSpeed(-ROTATION_SPEED);
			}
			// Record beta angle
			Sound.beep();
			odo.getPosition(pos);
			betaAngle = (pos[2] + noisePos[2]) / 2;

			if (alphaAngle > betaAngle) {
				angle = 45 - (alphaAngle + betaAngle) / 2;
			} else {
				angle = 225 - (alphaAngle + betaAngle) / 2;
			}

			// Update the odometer
			odo.setPosition(new double[] { 0.0, 0.0, betaAngle + angle },
					new boolean[] { true, true, true });
			nav.turnTo(0.0);
			odo.setPosition(new double[] { 0.0, 0.0, 0.0 }, new boolean[] {
					true, true, true });
			robot.setRotationSpeed(0);
			Sound.buzz();
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
			}

		} else {
			/*
			 * The robot should turn until it sees the wall, then look for the
			 * "rising edges:" the points where it no longer sees the wall. This
			 * is very similar to the FALLING_EDGE routine, but the robot will
			 * face toward the wall for most of it.
			 */

			while (getFilteredData() > WALL_DIST - TRESH_HOLD) {
				robot.setRotationSpeed(ROTATION_SPEED);
			}

			// Rotate until the wall isn't visible
			while (getFilteredData() <= WALL_DIST - NOISE) {
				robot.setRotationSpeed(ROTATION_SPEED);
			}
			Sound.beep();
			odo.getPosition(noisePos);
			betaAngle = (pos[2] + noisePos[2]) / 2;

			// Rotate in other direction until the wall is visible
			while (getFilteredData() > WALL_DIST - TRESH_HOLD) {
				robot.setRotationSpeed(-ROTATION_SPEED);
			}

			// Rotate until wall isn't visible anymore
			while (getFilteredData() < WALL_DIST - NOISE) {
				robot.setRotationSpeed(-ROTATION_SPEED);
			}
			odo.getPosition(noisePos);
			while (getFilteredData() <= WALL_DIST + NOISE) {
				robot.setRotationSpeed(-ROTATION_SPEED);
			}

			Sound.beep();
			odo.getPosition(pos);
			alphaAngle = (noisePos[2] + pos[2]) / 2;
			if (alphaAngle > betaAngle) {
				angle = 105 - (alphaAngle + betaAngle) / 2;
			} else {
				angle = 285 - (alphaAngle + betaAngle) / 2;
			}

			// Update the odometer
			odo.setPosition(new double[] { 0.0, 0.0, alphaAngle + angle },
					new boolean[] { false, false, true });
			nav.turnTo(0.0);
			odo.setPosition(new double[] { 0.0, 0.0, 0.0 }, new boolean[] {
					true, true, true });
			robot.setRotationSpeed(0);
			Sound.buzz();
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
			}
		}
	}

	private int getFilteredData() {
		int distance;

		// do a ping
		distance = MasterBluetoothCommunicator.sendUSPollRequest();

		// wait for the ping to complete
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
		}
		if (distance > WALL_DIST) {
			notVisibleCount++;
			visibleCount = 0;
		} else {
			visibleCount++;
			notVisibleCount = 0;
		}
		return distance;
	}

}

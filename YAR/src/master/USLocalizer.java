/**
 * 
 */
package master;

import lejos.nxt.LCD;
import lejos.nxt.Sound;
import lejos.nxt.UltrasonicSensor;

import common.StartCorner;

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

	private static final int MAX = 360;

	public static int ROTATION_SPEED = 110;
	public static int WALL_DIST = 40;
	public static int NOISE = 1;
	public static int TRESH_HOLD = 7;
	private final int FILTER_OUT = 10;

	private Odometer odo;
	private IRobot robot;
	private UltrasonicSensor us;
	private LocalizationType locType;
	private INavigator nav;
	private int correctDistance;
	private int distanceWant;
	private int filterControl;
	private int distance;

	/** The number of times a wall is detected. */
	private int visibleCount = 0;
	/** The number of times a wall is not detected. */
	private int notVisibleCount = 0;

	public USLocalizer(Odometer odo, UltrasonicSensor us,
			LocalizationType locType) {
		this.odo = odo;
		this.robot = odo.getRobot();
		this.locType = locType;
		this.nav = odo.getNavigator();
		this.us = us;
		filterControl = 0;
	}

	/**
	 * Localize to the intersection two tiles away from the corner.
	 */
	public void doLocalization(StartCorner corner) {
		double[] pos = new double[3];
		double[] pos2 = new double[3];
		double angleA, angleB;

		if (locType == LocalizationType.FALLING_EDGE) {
			correctDistance = getFilteredData();

			// 1 corresponds to seeing a wall and 0 corresponds to no wall
			int firstWall = 1;

			robot.getLeftMotor().setSpeed(ROTATION_SPEED);// starts rotating
			robot.getRightMotor().setSpeed(-ROTATION_SPEED);
			robot.getLeftMotor().forward();
			robot.getRightMotor().backward();
			while (firstWall == 1) {
				if (correctDistance - distanceWant > 2) {
					// falls out of noise margin no wall
					firstWall = firstWall - 1; // exit
				}
				correctDistance = getFilteredData();// re-update the distance
			}
			// Sound.playTone(1200,150);
			// robot is now not facing any wall.
			LCD.drawString("US: " + us.getDistance(), 0, 4);
			while (getFilteredData() < 54) {
			}
			while (getFilteredData() > 50) {
			}
			Sound.beep();
			robot.getLeftMotor().stop(true);
			robot.getRightMotor().stop();
			try {// rest for 300 ms
				Thread.sleep(300);
			} catch (InterruptedException e) {
			}
			angleA = odo.getTheta();
			robot.getRightMotor().forward();
			robot.getLeftMotor().backward();
			robot.getLeftMotor().setSpeed(-ROTATION_SPEED);// starts rotating
															// the other way
			robot.getRightMotor().setSpeed(ROTATION_SPEED);

			// switch direction and wait until it sees no wall
			// keep rotating until the robot sees a wall, then latch the angle
			while (getFilteredData() < 54) {
			}
			while (getFilteredData() > 50) {
			}
			Sound.playTone(1200, 150);

			angleB = odo.getTheta();

			robot.getLeftMotor().setSpeed(0);
			robot.getRightMotor().setSpeed(0);
			try {
				// rest for 300 ms
				Thread.sleep(300);
			} catch (InterruptedException e) {
			}
			// angleA is clockwise from angleB, so assume the average of the
			// angles to the right of angleB is 45 degrees past 'north'

			double offset = -90;
			if (angleA > angleB) {

				robot.getLeftMotor().setSpeed(0);
				robot.getRightMotor().setSpeed(0);

				nav.turnTo(360
						- Math.abs((360 - angleB)
								- Math.abs(45 - (angleA + (360 - angleB)) / 2))
						- 180 + offset);

				Sound.playTone(1200, 150);

				odo.setX(0);
				odo.setY(0);
				odo.setTheta(90);
			} else {
				robot.getLeftMotor().setSpeed(0);
				robot.getRightMotor().setSpeed(0);
				nav.turnTo(offset
						+ 360
						- Math.abs((360 - angleB)
								- Math.abs(45 - (angleA + (360 - angleB)) / 2)));
				Sound.playTone(1200, 150);

				odo.setX(0);
				odo.setY(0);
				odo.setTheta(90);
			}
		}

		// Go to intersection
		BlockNavigator blockNav = (BlockNavigator) nav;
		// blockNav.travelToNearestIntersection();
		nav.turnTo(0);
		blockNav.getDetector().advanceToIntersection();
		odo.setX(60);
		nav.turnTo(90);
		blockNav.getDetector().advanceToIntersection();
		odo.setY(60);

		// Correct position according to corner
		double x, y;
		switch (corner) {
		case BOTTOM_LEFT:
			// No need to do anything. Facing 90
			break;
		case BOTTOM_RIGHT:
			// Facing 180
			odo.setTheta(odo.getTheta() + 90);
			x = odo.getX();
			y = odo.getY();
			odo.setX(MAX - y);
			odo.setY(x);
			break;
		case TOP_LEFT:
			// Facing 0
			odo.setTheta(odo.getTheta() - 90);
			x = odo.getX();
			y = odo.getY();
			odo.setX(y);
			odo.setY(MAX - x);
			break;
		case TOP_RIGHT:
			// Facing 270
			odo.setTheta(odo.getTheta() + 180);
			x = odo.getX();
			y = odo.getY();
			odo.setX(MAX - x);
			odo.setY(MAX - y);
			break;
		}

	}

	private int getFilteredData() {
		boolean filter = true;
		filterControl = 0;

		while (filter) {
			// do a ping
			us.ping();
			// wait for the ping to complete
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
			}
			distance = us.getDistance();
			if (distance == 255 && (filterControl < FILTER_OUT)) {
				filterControl++;
			} else {
				filter = false;
			}
		}

		return distance;
	}

	private static int convertAngle(double radius, double width, double angle) {
		return convertDistance(radius, Math.PI * width * angle / 360.0);
	}

	private static int convertDistance(double radius, double distance) {
		return (int) ((180 * distance) / (Math.PI * radius));
	}

}

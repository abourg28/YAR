package master;

import lejos.nxt.Motor;

/*
 * Odometer.java
 */

public class Odometer extends Thread {
	// robot position
	private double x, y, theta;

	// odometer update period, in ms
	private static final long ODOMETER_PERIOD = 25;
	private double wheelRadius;
	private double widthRobot;

	// lock object for mutual exclusion
	private Object lock;

	private double deltaAngleLeft;
	private double deltaAngleRight;
	private double displacementAngleRobot;
	private double displacementMagnitudeRobot;
	private double dLeft;
	private double dRight;
	private int currentLeftTacho;
	private int currentRightTacho;
	private int oldLeftTacho = 0;
	private int oldRightTacho = 0;
	private IRobot robot;

	// default constructor
	public Odometer(IRobot robot) { // default constructor for odometer takes in
									// the Radius of wheel and Width of robot
		this.widthRobot = IRobot.WHEEL_WIDTH;
		this.wheelRadius = IRobot.LEFT_WHEEL_RADIUS;
		this.setRobot(robot);
	}

	// run method (required for Thread)
	public void run() {
		long updateStart, updateEnd;

		while (true) {
			updateStart = System.currentTimeMillis();
			// gets current degree
			currentLeftTacho = Motor.A.getTachoCount();
			currentRightTacho = Motor.B.getTachoCount();
			// change in angle
			deltaAngleLeft = currentLeftTacho - oldLeftTacho;
			deltaAngleRight = currentRightTacho - oldRightTacho;
			// calculates the change in distance of the left and right wheel
			// traveled
			dLeft = deltaAngleLeft * Math.PI * wheelRadius / 180;
			dRight = deltaAngleRight * Math.PI * wheelRadius / 180;
			// changes the old one to become the new one
			oldLeftTacho = currentLeftTacho;
			oldRightTacho = currentRightTacho;
			// finds the magnitude by checking how much the left wheel changed
			// with respect to the right wheel
			// find the angle by seeing how much the left angle changed compared
			// to the right angle
			// counter clockwise being positive and clockwise being negative
			displacementMagnitudeRobot = (dLeft + dRight) / 2;
			displacementAngleRobot = (deltaAngleRight - deltaAngleLeft)
					* wheelRadius / widthRobot;

			synchronized (lock) {
				x = x
						+ displacementMagnitudeRobot
						* Math.sin((theta + displacementAngleRobot / 2)
								* (Math.PI / 180));

				y = y
						+ displacementMagnitudeRobot
						* Math.cos((theta + displacementAngleRobot / 2)
								* (Math.PI / 180));

				theta = (theta + displacementAngleRobot);
				if (theta < 0) {
					theta = theta + 360;
				}
				if (theta > 360) { // reset the angle to zero when it reaches
									// more than 360 degrees
					theta = 0;
				}
				// makes sure everything is between zero and 360
			}

			// this ensures that the odometer only runs once every period
			updateEnd = System.currentTimeMillis();
			if (updateEnd - updateStart < ODOMETER_PERIOD) {
				try {
					Thread.sleep(ODOMETER_PERIOD - (updateEnd - updateStart));
				} catch (InterruptedException e) {
					// there is nothing to be done here because it is not
					// expected that the odometer will be interrupted by
					// another thread
				}
			}
		}
	}

	public double getX() {
		double result;

		synchronized (lock) {
			result = x;
		}

		return result;
	}

	public double getY() {
		double result;

		synchronized (lock) {
			result = y;
		}

		return result;
	}

	public double getTheta() {
		double result;

		synchronized (lock) {
			result = theta;
		}

		return result;
	}

	public void getPosition(double[] position, boolean[] update) {
		// ensure that the values don't change while the odometer is running
		synchronized (lock) {
			if (update[0])
				position[0] = x;
			if (update[1])
				position[1] = y;
			if (update[2])
				position[2] = theta;
		}
	}

	public void setX(double x) {
		synchronized (lock) {
			this.x = x;
		}
	}

	public void setY(double y) {
		synchronized (lock) {
			this.y = y;
		}
	}

	public void setTheta(double theta) {
		synchronized (lock) {
			this.theta = theta;
		}
	}

	public IRobot getRobot() {
		return robot;
	}

	public void setRobot(IRobot robot) {
		this.robot = robot;
	}
}
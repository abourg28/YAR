package master;

import lejos.nxt.LCD;
import lejos.nxt.Sound;

/**
 * This is an abstract navigator. This is where we want to have the default
 * navigator code.
 * 
 * @author alex
 * 
 */
public abstract class Navigator extends Thread implements INavigator {

	final static double DEG_ERR = 1, CM_ERR = 2.0;
	final static int FORWARD_SPEED = 70, ROTATION_SPEED = 110;

	protected Odometer odo;
	protected boolean isNavigating;
	protected IRobot robot;

	protected Navigator(IRobot robot, Odometer odo) {
		this.odo = odo;
		this.robot = robot;
		this.isNavigating = false;
	}

	@Override
	public void travelTo(double x, double y) {
		double thetaGoTo = calcPathAngle(x, y);
		turnTo(thetaGoTo);
		robot.getLeftMotor().setSpeed(FORWARD_SPEED);
		robot.getRightMotor().setSpeed(FORWARD_SPEED);

		double distance = calcPathDistance(x, y);
		robot.getLeftMotor().rotate(
				convertDistance(robot.LEFT_WHEEL_RADIUS, distance), true);
		robot.getRightMotor().rotate(
				convertDistance(robot.RIGHT_WHEEL_RADIUS, distance), false);

	}

	@Override
	public void turnTo(double angle) {
		isNavigating = true;
		Sound.beep();

		double[] pos = new double[3];
		this.odo.getPosition(pos);
		double error = minimizeAngle(angle - pos[2]);

		// LCD.drawString("Angle:" + angle + "  ", 0, 4);
		// LCD.drawString("Error:" + error + "   ", 0, 5);
		this.odo.getPosition(pos);
		error = minimizeAngle(angle - pos[2]);

		if (error > DEG_ERR && error <= 180) {
			this.robot.getLeftMotor().setSpeed(ROTATION_SPEED);
			this.robot.getLeftMotor().backward();
			this.robot.getRightMotor().setSpeed(ROTATION_SPEED);
			this.robot.getRightMotor().forward();
		}
		if (error >= -180 && error < DEG_ERR) {
			this.robot.getLeftMotor().setSpeed(ROTATION_SPEED);
			this.robot.getLeftMotor().forward();
			this.robot.getRightMotor().setSpeed(ROTATION_SPEED);
			this.robot.getRightMotor().backward();
		}

		while (Math.abs(error) > DEG_ERR) {
			this.odo.getPosition(pos);
			error = minimizeAngle(angle - pos[2]);
		}

		this.robot.getLeftMotor().setSpeed(0);
		this.robot.getRightMotor().setSpeed(0);

		this.robot.setSpeeds(0, 0);
	}

	private double minimizeAngle(double error) {
		error = error % (360);
		if (error > 180) {
			// Gives us small negative angle instead of large positive angle
			error = error - 360;
		} else if (error < -180) {
			// Gives us small positive angle instead of large negative angle
			error = error + 360;
		}
		return error;
	}

	double calcPathDistance(double DesiredX, double DesiredY) {
		double currentX = odo.getX();
		double currentY = odo.getY();
		return Math.sqrt((DesiredX - currentX) * (DesiredX - currentX)
				+ (DesiredY - currentY) * (DesiredY - currentY));

	}

	double calcPathAngle(double DesiredX, double DesiredY) {

		double currentX = odo.getX();
		double currentY = odo.getY();

		if (Math.abs(DesiredX - currentX) < 4) {
			if ((DesiredX - currentX) < 0) {
				return 270;
			} else {
				return 90.00;
			}

		}

		else if (Math.abs(DesiredY - currentY) < 4) {
			if ((DesiredY - currentY) > 0) {
				return 0;
			} else {
				return 180;
			}
		}

		double degreeWant = Math.atan(Math.abs(DesiredY - currentY)
				/ Math.abs(DesiredX - currentX))
				* 180 / Math.PI;
		if (DesiredX - currentX > 0 && DesiredY - currentY > 0) {// in the first
																	// octet
			return degreeWant;

		} else if (DesiredX - currentX < 0 && DesiredY - currentY > 0) {// in
																		// the
																		// second
																		// octet
			return 180 - degreeWant;
		} else if (DesiredX - currentX < 0 && DesiredY - currentY < 0) {
			return 180 + degreeWant;
		}

		else if (DesiredX - currentX > 0 && DesiredY - currentY < 0) {// in the
																		// 4th
																		// octet
			return -degreeWant;
		} else {
			return 0;
		}

	}

	@Override
	public boolean isNavigating() {
		return this.isNavigating;
	}

	protected static int convertAngle(double radius, double width, double angle) {
		return convertDistance(radius, width * angle / 2);
	}

	protected static int convertDistance(double radius, double distance) {
		return (int) ((180.0 * distance) / (Math.PI * radius));
	}
}

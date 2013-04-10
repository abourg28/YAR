/**
 * 
 */
package slave;

import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.Sound;
import lejos.util.Delay;

import common.Instructions;

/**
 * This class takes care of launching the projectile on an angle.
 * 
 * @author alex
 * 
 */
public class AngleLauncher implements ILauncher {

	private final int GOAL_X = 5;// 5;
	private final int GOAL_Y = 10;
	private double x;
	private double y;
	private double theta = 90;
	private NXTRegulatedMotor leftMotor;
	private NXTRegulatedMotor rightMotor;
	private Instructions inst;
	private int range;

	public AngleLauncher(NXTRegulatedMotor leftMotor,
			NXTRegulatedMotor rightMotor) {
		this.leftMotor = leftMotor;
		this.rightMotor = rightMotor;
		this.inst = new Instructions();
		this.range = 6;
		this.theta = 90;
		leftMotor.setSpeed(120);
		rightMotor.setSpeed(120);
		leftMotor.rotateTo(90, true);
		rightMotor.rotateTo(90);
	}

	/**
	 * Rotates the catapult to launch projectile.
	 */
	public void launch() {
		int launchspeed = 0;
		switch (range) {
		case 5:
			launchspeed = 320;
			break;
		case 6:
			launchspeed = 410;
			break;
		case 7:
			launchspeed = 520;
			break;
		case 8:
			launchspeed = 540;
			break;
		}

		Delay.msDelay(500);
		// fire the ball after
		leftMotor.setSpeed(launchspeed);
		rightMotor.setSpeed(launchspeed);
		leftMotor.rotateTo(110, true);
		rightMotor.rotateTo(110);
		Sound.beepSequence();
	}

	/**
	 * Rotates the catapult back to original position
	 */
	public void retract() {
		leftMotor.setSpeed(120);
		rightMotor.setSpeed(120);
		leftMotor.rotateTo(40, true);
		rightMotor.rotateTo(40);
		leftMotor.setSpeed(20);
		rightMotor.setSpeed(20);
		leftMotor.rotateTo(0, true);
		rightMotor.rotateTo(0);
	}

	/**
	 * Update the x, y, and theta according to parameters.
	 */
	public void calculateLaunchPosition() {
		range = inst.d1; // launches from WHERE ITS SUPPOSED TO!!
		int angleOffset = 4;
		// Opposite of the ball loader
		if (inst.getLoaderX() > 180) {
			x = ((GOAL_X + 1 - angleOffset) * 30) + 10; // (GOAL_X + 1) * 30;
		} else {
			x = ((GOAL_X + 1 + angleOffset) * 30) - 10; // (GOAL_X + 1) * 30;
		}
		y = (GOAL_Y + 1 - range) * 30;

		theta = launchAngle(x - 10, y);
	}

	private double launchAngle(double launchX, double launchY) {
		double goalX = (GOAL_X + 1) * 30;
		double goalY = (GOAL_Y + 1) * 30;

		double degreeWant = Math.atan(Math.abs(goalY - launchY)
				/ Math.abs(goalX - launchX))
				* 180 / Math.PI;
		if (goalX - launchX > 0 && goalY - launchY > 0) {// in the first
															// octet
			return degreeWant;

		} else if (goalX - launchX < 0 && goalY - launchY > 0) {// in
																// the
																// second
																// octet
			return 180 - degreeWant;
		} else if (goalX - launchX < 0 && goalY - launchY < 0) {
			return 180 + degreeWant;
		}

		else if (goalX - launchX > 0 && goalY - launchY < 0) {// in the
																// 4th
																// octet
			return -degreeWant;
		} else {
			return 0;
		}

	}

	public double getLaunchX() {
		return x;
	}

	public double getLaunchY() {
		return y;
	}

	public double getLaunchTheta() {
		return theta;
	}

	public void updateInstructions(Instructions inst) {
		this.inst = inst;
	}

}

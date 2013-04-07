/**
 * 
 */
package slave;

import common.Instructions;

import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.Sound;
import lejos.util.Delay;

/**
 * This class takes care of launching the projectile on an angle.
 * 
 * @author alex
 * 
 */
public class AngleLauncher implements ILauncher {

	private final int GOAL_X = 5;
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
	}

	/**
	 * Rotates the catapult to launch projectile.
	 */
	public void launch() {
		int launchspeed = 0;
		switch(range)
		{
			case 5: launchspeed = 315;
				break;
			case 6: launchspeed = 375;
				break;
			case 7: launchspeed = 415;
				break;
			case 8: launchspeed = 600;
				break;
		}
		leftMotor.setSpeed(120);
		rightMotor.setSpeed(120);
		leftMotor.rotateTo(-30, true);
		rightMotor.rotateTo(-30);

		Delay.msDelay(500);
		// fire the ball after
		leftMotor.setSpeed(launchspeed);
		rightMotor.setSpeed(launchspeed);
		leftMotor.rotateTo(110, true);
		rightMotor.rotateTo(110);
		Sound.beepSequence();

		leftMotor.setSpeed(120);
		rightMotor.setSpeed(120);
		leftMotor.rotateTo(0, true);
		rightMotor.rotateTo(0);
	}

	/**
	 * Rotates the catapult back to original position
	 */
	public void retract() {
		leftMotor.setSpeed(120);
		rightMotor.setSpeed(120);
		leftMotor.rotateTo(0, true);
		rightMotor.rotateTo(0);
	}

	/**
	 * Update the x, y, and theta according to parameters.
	 */
	public void calculateLaunchPosition() {
		range = inst.d1; // launches from WHERE ITS SUPPOSED TO!!
		x = (GOAL_X + 1) * 30;
		y = (GOAL_Y + 1 - range) * 30;
		theta = 90;
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

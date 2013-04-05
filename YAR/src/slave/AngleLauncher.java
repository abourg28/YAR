/**
 * 
 */
package slave;

import common.Instructions;

import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.Sound;

/**
 * This class takes care of launching the projectile on an angle.
 * 
 * @author alex
 * 
 */
public class AngleLauncher implements ILauncher {

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
		Motor.A.setSpeed(120);
		Motor.B.setSpeed(120);
		Motor.A.rotateTo(-30, true);
		Motor.C.rotateTo(-30);
		try {
			Thread.sleep(500);
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
		// fire the ball after
		Motor.A.setSpeed(launchspeed);
		Motor.C.setSpeed(launchspeed);
		Motor.A.rotateTo(110, true);
		Motor.C.rotateTo(110);
		Sound.beepSequence();

		Motor.A.setSpeed(120);
		Motor.B.setSpeed(120);
		Motor.A.rotateTo(0, true);
		Motor.C.rotateTo(0);
		Motor.A.stop();
		Motor.B.stop();
	}

	/**
	 * Rotates the catapult back to original position
	 */
	public void retract() {
		Motor.A.setSpeed(120);
		Motor.B.setSpeed(120);
		Motor.A.rotateTo(0, true);
		Motor.C.rotateTo(0);
		Motor.A.stop();
		Motor.B.stop();
	}

	/**
	 * Update the x, y, and theta according to parameters.
	 */
	public void calculateLaunchPosition() {
		// TODO Auto-generated method stub
		int xGoal = 5 + 1; // to be changed in final
		int yGoal = 10 + 1; // to be changed in final
		int distToHoop = 6; // launches from 5 squares
		int xCoord = xGoal * 30;
		int yCoord = (yGoal - distToHoop) * 30;
		x = xCoord;
		y = yCoord;
		theta = 90;
		range = distToHoop;

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

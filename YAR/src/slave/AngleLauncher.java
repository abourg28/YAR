/**
 * 
 */
package slave;

import common.Instructions;

import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;


/**
 * This class takes care of launching the projectile on an angle.
 * @author alex
 *
 */
public class AngleLauncher implements ILauncher {
	
	private double x;
	private double y;
	private double theta;
	private NXTRegulatedMotor leftMotor;
	private NXTRegulatedMotor rightMotor;
	private Instructions inst;
	
	public AngleLauncher (NXTRegulatedMotor leftMotor,
						   NXTRegulatedMotor rightMotor) {
		this.leftMotor = leftMotor;
		this.rightMotor = rightMotor;
		this.inst = new Instructions();
	}
	
	/**
	 * Rotates the catapult to launch projectile.
	 */
	public void launch() {
		Motor.A.setSpeed(750);
		Motor.C.setSpeed(750);
		Motor.A.rotateTo(110,true);
		Motor.C.rotateTo(110,false);
	}

	/**
	 * Rotates the catapult back to original position
	 */
	public void retract() {
		Motor.A.setSpeed(120);
		Motor.B.setSpeed(120);
		Motor.A.rotateTo(0,true);
		Motor.C.rotateTo(0,false);
		Motor.A.stop();
		Motor.B.stop();
	}

	/**
	 * Update the x, y, and theta according to parameters.
	 */
	public void calculateLaunchPosition() {
		// TODO Auto-generated method stub
		
	}

	public double getLaunchX() {
		// TODO Auto-generated method stub
		return 0;
	}

	public double getLaunchY() {
		// TODO Auto-generated method stub
		return 0;
	}

	public double getLaunchTheta() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void updateInstructions(Instructions inst) {
		this.inst = inst;	
	}

}

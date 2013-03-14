/**
 * 
 */
package slave;

import common.Instructions;

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
		// TODO Auto-generated method stub
		
	}

	/**
	 * Rotates the catapult back to original position
	 */
	public void retract() {
		// TODO Auto-generated method stub
		
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

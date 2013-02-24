/**
 * 
 */
package slave;

import NXTRegulatedMotor;

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
	
	public AngleLauncher (NXTRegulatedMotor leftMotor,
						   NXTRegulatedMotor rightMotor) {
		this.leftMotor = leftMotor;
		this.rightMotor = rightMotor;
	}
	
	@Override
	public void launch() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void retract() {
		// TODO Auto-generated method stub
		
	}

	@Override
	/**
	 * Update the x, y, and theta according to parameters.
	 * @param d1
	 * @param w1
	 */
	public void calculateLaunchPosition(double d1, double w1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double getLaunchX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getLaunchY() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getLaunchTheta() {
		// TODO Auto-generated method stub
		return 0;
	}

}

package master;

import lejos.nxt.NXTRegulatedMotor;
/**
 * 
 */

/**
 * 
 * @author alex
 *
 */
public interface IRobot {
	// Robot variables
	public static final double LEFT_WHEEL_RADIUS = 2.75;
	public static final double RIGHT_WHEEL_RADIUS = 2.75;
	public static final double WHEEL_WIDTH = 15;
	
	public void setForwardSpeed(double speed);
	public void setRotationSpeed(double speed);
	public void setSpeeds(double forwardSpeed, double rotationSpeed);
	public void getDisplacementAndHeading(double[] dDH);
	public double getDisplacement();
	public double getHeading();
	public NXTRegulatedMotor getLeftMotor();
	public NXTRegulatedMotor getRightMotor();

}

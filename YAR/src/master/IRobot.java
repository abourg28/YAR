package master;
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

}

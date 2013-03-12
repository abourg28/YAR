package master;

/**
 * This is an implementation of a Navigator where the robot will follow along
 * the grid lines.
 * 
 * @author alex
 * 
 */
public class LineFollowingNavigator extends Navigator {
	
	private LineDetector detector;

	public LineFollowingNavigator(Odometer odo) {
		// TODO

		// Need to create an Intersection Detector with appropriate LightSensor
		super(odo);
	}

	/**
	 * Same as travelTo except the robot will make use of following the lines as
	 * much as possible. It will travel horizontally then vertically along the
	 * lines to achieve its destination.
	 * 
	 * @param x The x value of the destination point.
	 * @param y The y value of the destination point.
	 */
	public void followLineTo(double x, double y) {

	}

	public void run() {
		// TODO
	}
}

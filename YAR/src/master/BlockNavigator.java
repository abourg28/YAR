/**
 * 
 */
package master;

/**
 * This is a singleton implementation of a Navigator where the robot will
 * attempt to travel as though in a grid. For example the navigator will break
 * down the path to the destination in terms of number of vertical and
 * horizontal blocks.
 * 
 * @author alex
 * 
 */
public class BlockNavigator extends Navigator {

	private LineDetector detector;
	private static INavigator nav;

	private BlockNavigator(IRobot robot, Odometer odo) {
		super(robot, odo);
		// TODO Auto-generated constructor stub
		// Need to create new LineDetector
	}

	/**
	 * Get the instance of the BlockNavigator.
	 * 
	 * @param robot Robot containing the corresponding motors for the wheels.
	 * @param odo Odometer that is used to guide the navigation and that will be corrected.
	 * @return
	 */
	public static INavigator getInstance(IRobot robot, Odometer odo) {
		if (nav == null) {
			nav = new BlockNavigator(robot, odo);
		}
		return nav;
	}

}

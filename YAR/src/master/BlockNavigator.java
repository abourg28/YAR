/**
 * 
 */
package master;

/**
 * This is an implementation of a Navigator where the robot will attempt to
 * travel as though in a grid. For example the navigator will break down the
 * path to the destination in terms of number of vertical and horizontal blocks.
 * 
 * @author alex
 * 
 */
public class BlockNavigator extends Navigator {

	private LineDetector detector;
	private static INavigator nav;

	private BlockNavigator(Odometer odo) {
		super(odo);
		// TODO Auto-generated constructor stub
		// Need to create new LineDetector
	}
	
	public static INavigator getInstance(Odometer odo) {
		if (nav == null) {
			nav = new BlockNavigator(odo);
		}
		return nav;
	}

}

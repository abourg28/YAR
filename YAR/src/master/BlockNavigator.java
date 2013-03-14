/**
 * 
 */
package master;

import common.Pos;

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

	@Override
	public void travelTo(double x, double y) {
		// TODO I put comments for a "simple" yet not perfect algorithm. If this isn't enough we can use the wavefront algorithm.
		this.isNavigating = true;
		// Travel to the center of the current tile
		
		// Calculate destination row
		// Travel vertically loop (while not at destination row)
			// dir = turn right
			// If there is an obstacle within the next tile
				// While there is an obstacle
					// Turn dir
					// If there is an obstacle
						// dir = opposite direction (-dir)
						// Turn dir
					// Else (no obstacle)
						// Advance one tile
						// Turn -dir
				// Advance one tile
				// this.travelTo(x,y)
				// return
			
			// Advance one tile
		
		// Calculate destination column		
		// Travel horizontally loop (while not at destination column)
			// dir = turn right
			// If there is an obstacle within the next tile
				// While there is an obstacle
					// Turn dir
					// If there is an obstacle
						// dir = opposite direction (-dir)
						// Turn dir
					// Else (no obstacle)
						// Advance one tile
						// Turn -dir
				// Advance one tile
				// this.travelTo(x,y)
				// return
			
			// Advance one tile
		
		// Travel to destination
		
		this.isNavigating = false;
	}
	
	/**
	 * This navigation method does not make use of blocks.
	 * @param x
	 * @param y
	 */
	public void simpleTravelTo(double x, double y) {
		super.travelTo(x, y);
	}
	
	/**
	 * Calculates the position of the center of the tile encapsulating x and y.
	 * @param x X position indicating the tile of interest.
	 * @param y Y position indicating the tile of interest.
	 * @return The position of the center of the tile of interests.
	 */
	private Pos getCenterOfTile(double x, double y) {
		// TODO
		return null;
	}

}

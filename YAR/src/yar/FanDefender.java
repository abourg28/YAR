/**
 * 
 */
package yar;

/**
 * This class will orient and defend the goal using a fan. This class only opens
 * one side of the fan (only one motor) therefore two instances are needed.
 * 
 * @author alex
 * 
 */
public class FanDefender implements IDefender {

	/**
	 * This method will rotate the robot to the heading needed for this defense
	 * strategy. This requires that the robot be in the correct position.
	 */
	public void orient() {
		// TODO Auto-generated method stub

	}

	/**
	 * This method will open one side of the fan.
	 */
	public void defend() {
		// TODO Auto-generated method stub

	}

	/**
	 * This method will close one side of the fan.
	 */
	public void stop() {
		// TODO Auto-generated method stub

	}

	/**
	 * This method will calculate the ideal defender position for this strategy.
	 * The calculated position should then be requested from the navigator
	 * before calling orient and defend.
	 */
	public void calculateDefenderPosition(double d1, double w1, double w2) {
		// TODO Auto-generated method stub

	}

}

/**
 * 
 */
package yar;

/**
 * @author alex
 *
 */
public interface IDefender {
	
	/**
	 * This method will rotate the robot to the heading needed for this defense
	 * strategy. This requires that the robot be in the correct position.
	 */
	public void orient();
	/**
	 * This method will open one side of the fan.
	 */
	public void defend();
	/**
	 * This method will close one side of the fan.
	 */
	public void stop();
	/**
	 * This method will calculate the ideal defender position for this strategy.
	 * The calculated position should then be requested from the navigator
	 * before calling orient and defend.
	 * 
	 * @param d1 Distance of offensive line. Taken from instructions.
	 * @param w1 Width of defensive line. Taken from instructions.
	 * @param w2 Distance of defensive line. Taken from instructions.
	 */
	public void calculateDefenderPosition(double d1, double w1, double w2);

}

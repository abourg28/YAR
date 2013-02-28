/**
 * 
 */
package master;

import yar.IDefender;

/**
 * @author alex
 *
 */
public class MasterNXT {
	
	private static IRobot robot;
	private static Instructions instructions;
	private static Odometer odo;
	private static INavigator nav;
	private static IDefender defender;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		instructions = new Instructions();
		ParseInstructions.parse(null);
		robot = new YARRobot();
		odo = new Odometer();
		nav = new LineFollowingNavigator(odo);
		MasterBluetoothCommunicator.sendInstructions(instructions);
		
		

	}

}

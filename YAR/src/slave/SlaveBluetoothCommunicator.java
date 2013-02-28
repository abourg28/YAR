/**
 * 
 */
package slave;

import yar.IDefender;

/**
 * This class takes care of receiving requests from the other NXT brick.
 * @author alex
 *
 */
public class SlaveBluetoothCommunicator {
	
	private USPoller poller;
	private USLocalizer localizer;
	private ILauncher launcher;
	private IDefender defender;
	
	public SlaveBluetoothCommunicator() {
		
	}
	
	public void waitForRequest() {
		// TODO
	}

}

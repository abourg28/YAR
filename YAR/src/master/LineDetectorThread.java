/**
 * 
 */
package master;

import lejos.nxt.LightSensor;

/**
 * @author Frank the Tank
 *
 */
public class LineDetectorThread extends Thread {
	
	private LightSensor ls;
	private Odometer odo;
	private IRobot robot;
	
	public LineDetectorThread(LightSensor ls, Odometer odo) {
		this.ls = ls;
		this.odo = odo;
		this.robot = odo.getRobot();
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
	}

}

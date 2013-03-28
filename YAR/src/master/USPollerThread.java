package master;

import lejos.nxt.UltrasonicSensor;

public class USPollerThread extends Thread {
	
	private UltrasonicSensor us;
	
	public USPollerThread(UltrasonicSensor us) {
		this.us = us;
	}
	
	public boolean inRange(int range) {
		// TODO
		return false;
	}
	
	public int getDistance() {
		// TODO
		return 255;
	}

	@Override
	public void run() {
		// TODO
	}
}

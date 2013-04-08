package master;

import lejos.nxt.UltrasonicSensor;
import lejos.util.Delay;

public class USPollerThread implements Runnable {
	private int distance;
	public boolean obstacle;
	private final int FILTER_OUT = 10;
	private int filterControl = 0;
	private int tolerance;

	private UltrasonicSensor us;

	public USPollerThread(UltrasonicSensor us, int distanceAcceptable) {
		this.us = us;
		tolerance = distanceAcceptable;
		obstacle = false;
	}

	private int getFilteredData() {

		boolean filter = true;
		filterControl = 0;

		while (filter) {

			// do a ping
			us.ping();
			// wait for the ping to complete
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
			}

			distance = us.getDistance();

			if (distance == 255 && (filterControl < FILTER_OUT)) {
				filterControl++;
			}

			else {
				filter = false;
			}

		}

		return distance;
	}

	@Override
	public void run() {
		while (true) {
			if (getFilteredData() < tolerance) {
				obstacle = true;
			} else {
				obstacle = false;
			}
			Delay.msDelay(20);
		}
	}
}

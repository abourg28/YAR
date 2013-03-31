package master;

import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;
import lejos.nxt.Sound;
import lejos.util.Delay;

/**
 * This class will detect when a black line intersection is crossed. At each
 * intersection it will send a message to the other NXT brick to poll the US to
 * detect obstacles.
 * 
 * @author alex
 * 
 */
public class LineDetector {
	private final double TOL = 5; // our tolerance of our heading, in degrees
	private static Odometer odometer;
	private final int SPEED = 70;
	private final int fast_SPEED = 250;
	LightSensor leftLS = new LightSensor(SensorPort.S1, true);
	LightSensor rightLS = new LightSensor(SensorPort.S2, true);
	private static NXTRegulatedMotor leftMotor;
	private static NXTRegulatedMotor rightMotor;
	LineDetectorThread leftLine;
	LineDetectorThread rightLine;
	IRobot robot;

	public LineDetector(Odometer odo, IRobot robot) {
		odometer = odo;
		this.robot = robot;
		leftMotor = robot.getLeftMotor();
		rightMotor = robot.getRightMotor();
		leftLine = new LineDetectorThread(leftLS, odometer,
				leftMotor);
		rightLine = new LineDetectorThread(rightLS, odometer,
				rightMotor);
		leftLine.start();
		rightLine.start();
	}
	
	public void advanceToIntersection() {
		advanceToIntersection(true);
	}
	
	public void advanceToIntersection(boolean delay) {
		//move a distance of 26 cm quickly first
		rightMotor.setSpeed(fast_SPEED);
		leftMotor.setSpeed(fast_SPEED);
		rightMotor.rotate(convertDistance(robot.LEFT_WHEEL_RADIUS,25),true);
		leftMotor.rotate(convertDistance(robot.LEFT_WHEEL_RADIUS,25),false);
		//then slow down
		rightMotor.setSpeed(SPEED);
		leftMotor.setSpeed(SPEED);
		rightMotor.forward();
		leftMotor.forward();
		
		if (delay) {
			// Advance a bit not to read the previous intersection
			Delay.msDelay(2500);
		}

		while (true) {
			if ((leftLine.onLine == true && rightLine.onLine == false)) {
				rightMotor.setSpeed(SPEED);
				leftMotor.setSpeed(0);

				while (rightLine.onLine == false) {
				}

				Sound.playTone(100, 100);
				leftMotor.setSpeed(0);
				rightMotor.setSpeed(0);
				Delay.msDelay(1000);
				updatePos(); // before exiting, update the position of the robot
				return;
			} else if ((leftLine.onLine == false && rightLine.onLine == true)) {
				leftMotor.setSpeed(SPEED);
				rightMotor.setSpeed(0);

				while (leftLine.onLine == false) {
				}

				Sound.playTone(100, 100);
				
				rightMotor.setSpeed(0);
				leftMotor.setSpeed(0);
				
				
				Delay.msDelay(1000);
				updatePos(); // before exiting, update the position of the robot
				return;
			}
			 else if (rightLine.onLine == true && leftLine.onLine == true) {
				updatePos(); // before exiting, update the position of the robot
				return;
			}
			Delay.msDelay(100);

		}
	}

	public void updatePos() {
		if (odometer.getTheta() <190 && odometer.getTheta()>170){
			odometer.setTheta(180);
		}
		else if (odometer.getTheta() <10  || odometer.getTheta()>350){
			odometer.setTheta(0);
		}
		else if (odometer.getTheta() <100 && odometer.getTheta()>80){
			odometer.setTheta(90);
		}
		else if (odometer.getTheta()<280 && odometer.getTheta()<280){
			odometer.setTheta(270);
		}

		double heading = odometer.getTheta(); // determine which direction we
												// are heading in

		if (Math.abs(heading - 0) < TOL || (Math.abs(heading - 180) < TOL)) // if
																			// heading
																			// in
																			// x
																			// direction,
																			// update
																			// x
		{
			odometer.setX(Math.round(odometer.getX() / 30) * 30); // use round
																	// function
																	// to round
																	// up or
																	// down the
																	// number of
																	// squares
																	// passed,
																	// then
																	// multiply
																	// by 30 for
																	// cm
																	// traveled
		}
		if (Math.abs(heading - 90) < TOL || (Math.abs(heading - 270) < TOL)) // same
																				// for
																				// y
																				// direction
		{
			odometer.setY(Math.round(odometer.getY() / 30) * 30);
		}
		

	}
	protected static int convertAngle(double radius, double width, double angle) {
		return convertDistance(radius, width * angle / 2);
	}

	protected static int convertDistance(double radius, double distance) {
		return (int) ((180.0 * distance) / (Math.PI * radius));
	}
}

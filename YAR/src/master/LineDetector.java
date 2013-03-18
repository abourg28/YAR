package master;

import java.io.IOException;

import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
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
public class LineDetector extends Thread {
	private static Odometer odometer;
	private static double Robotwidth;
	private static double wheelRadius;
	LightSensor leftLS;
	LightSensor rightLS;
	private int lightValueLeft;
	private int lightValueRight;
	private boolean LLPassed = false;
	private boolean RLPassed = false;
	private int distanceDifference;
	private int leftCount;
	private int rightCount;
	private int countDifference;
	private int countCorrection;
	private IRobot robot;

	public LineDetector(LightSensor leftLS, LightSensor rightLS, IRobot robot,
			Odometer odo) {
		this.robot = robot;
		this.odometer = odo;
		this.wheelRadius = YARRobot.LEFT_WHEEL_RADIUS;
		this.Robotwidth = YARRobot.WHEEL_WIDTH;
	}

	public void run() {
		while (true) { // infinite loop
			// takes in input light value of left and right light sensor
			// instaneously
			lightValueLeft = leftLS.getLightValue();
			lightValueRight = rightLS.getLightValue();
			// takes values for left light sensor value at certain time and
			// right sensor value and certain time
			Delay.msDelay(10);
			int a1 = leftLS.getLightValue();
			int a2 = rightLS.getLightValue();
			Delay.msDelay(10);
			int b1 = leftLS.getLightValue();
			int b2 = rightLS.getLightValue();
			Delay.msDelay(10);
			int c1 = leftLS.getLightValue();
			int c2 = rightLS.getLightValue();
			Delay.msDelay(10);
			int d1 = leftLS.getLightValue();
			int d2 = rightLS.getLightValue();
			// then takes the difference of those values
			int leftDelta1 = lightValueLeft - a1;
			int rightDelta1 = lightValueRight - a2;

			int leftDelta2 = lightValueLeft - b1;
			int rightDelta2 = lightValueRight - b2;

			int leftDelta3 = lightValueLeft - c1;
			int rightDelta3 = lightValueRight - c2;

			int leftDelta4 = lightValueLeft - d1;
			int rightDelta4 = lightValueRight - d2;

			LCD.drawString("LightLeft: " + lightValueLeft, 0, 4);
			LCD.drawString("LightLeft: " + lightValueLeft, 0, 4);

			if (leftDelta1 < 2 && (leftDelta2 > 2) || (leftDelta3 > 2)
					|| (leftDelta4 > 2)) {// jump in difference or derivative
				Sound.playTone(400, 1000);
				LLPassed = true;
			}

			if (rightDelta1 < 2 && (rightDelta2 > 2) || (rightDelta3 > 2)
					|| (rightDelta4 > 2)) {
				Sound.playTone(200, 1000);
				RLPassed = true;
			}

			if (LLPassed) {
				int currentLeftCount = leftCount;
			}
			if (RLPassed) {
				int currentRightCount = rightCount;
			}

			countDifference = leftCount - rightCount;
			if (countDifference < -3) {
				// TODO Shouldn't use motors directly
				// leftMotor.setSpeed(leftMotor.getSpeed()+Math.abs(countDifference));
				countCorrection++;
			} else if (countDifference > 3) {
				// TODO Shouldn't use motors directly
				// rightMotor.setSpeed(leftMotor.getSpeed()+Math.abs(countDifference));
				countCorrection++;
			}

			if (countCorrection == Math.abs(countDifference)
					&& countDifference < -3) {
				// TODO Shouldn't use motors directly
				// leftMotor.setSpeed(leftMotor.getSpeed()-Math.abs(countDifference));//everything
				// is corrected so set everything again to zero and change the
				// odometer

				if (odometer.getTheta() < 15 && odometer.getTheta() > 0) {
					odometer.setTheta(0);
				} else if (odometer.getTheta() < 105
						&& odometer.getTheta() > 75) {
					odometer.setTheta(90);
				} else if (odometer.getTheta() < 195
						&& odometer.getTheta() > 165) {
					odometer.setTheta(180);
				} else if (odometer.getTheta() < 285
						&& odometer.getTheta() > 255) {
					odometer.setTheta(270);
				}

				countDifference = 0;
				leftCount = 0;
				rightCount = 0;
			} else if (countCorrection == Math.abs(countDifference)
					&& countDifference > 3) {
				// TODO Shouldn't use motors directly
				// rightMotor.setSpeed(rightMotor.getSpeed()-Math.abs(countDifference));//everything
				// is correct so set it all to zero
				countDifference = 0;
				leftCount = 0;
				rightCount = 0;
			}

			leftCount++;
			rightCount++;

			Delay.msDelay(10);
		}
	}

}

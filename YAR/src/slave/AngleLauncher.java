/**
 * 
 */
package slave;

import common.Instructions;

import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;


/**
 * This class takes care of launching the projectile on an angle.
 * @author alex
 *
 */
public class AngleLauncher implements ILauncher {
	
	private double x;
	private double y;
	private double theta;
	private NXTRegulatedMotor leftMotor;
	private NXTRegulatedMotor rightMotor;
	private Instructions inst;
	private int range;
	
	public AngleLauncher (NXTRegulatedMotor leftMotor,
						   NXTRegulatedMotor rightMotor) {
		this.leftMotor = leftMotor;
		this.rightMotor = rightMotor;
		this.inst = new Instructions();
	}
	
	/**
	 * Rotates the catapult to launch projectile.
	 */
	public void launch() {
			int launchspeed = (150*range)-350;
			Motor.A.setSpeed(120);
			Motor.B.setSpeed(120);
			Motor.A.rotateTo(-20,false);
			Motor.C.rotateTo(-20,true);
			try {
			    Thread.sleep(500);		
			} catch(InterruptedException ex) {
			    Thread.currentThread().interrupt();
			}
			//fire the ball after 
			Motor.A.setSpeed(launchspeed);
			Motor.C.setSpeed(launchspeed);
			Motor.A.rotateTo(110,true);
			Motor.C.rotateTo(110,false);
			Sound.beepSequence();


			Motor.A.setSpeed(120);
			Motor.B.setSpeed(120);
			Motor.A.rotateTo(0,true);
			Motor.C.rotateTo(0,false);
			Motor.A.stop();
			Motor.B.stop();
	}

	/**
	 * Rotates the catapult back to original position
	 */
	public void retract() {
		Motor.A.setSpeed(120);
		Motor.B.setSpeed(120);
		Motor.A.rotateTo(0,true);
		Motor.C.rotateTo(0,false);
		Motor.A.stop();
		Motor.B.stop();
	}

	/**
	 * Update the x, y, and theta according to parameters.
	 */
	public void calculateLaunchPosition() 
	{
		// TODO Auto-generated method stub
		int xGoal = 6;
		int yGoal = 11;
		int distToHoop = inst.d1;
		int xCoord = xGoal*30;
		int yCoord = (yGoal-distToHoop)*30;
		x = xCoord;
		y = yCoord;
		theta = 90;
		range = distToHoop;
		
	}

	public double getLaunchX() {
		// TODO Auto-generated method stub
		return 0;
	}

	public double getLaunchY() {
		// TODO Auto-generated method stub
		return 0;
	}

	public double getLaunchTheta() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void updateInstructions(Instructions inst) {
		this.inst = inst;	
	}

}

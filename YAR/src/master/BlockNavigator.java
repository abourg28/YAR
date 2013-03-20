
// * 
 //*/
package master;

import common.Pos;
import lejos.nxt.*;
import lejos.nxt.comm.RConsole;
/**
 * This is a singleton implementation of a Navigator where the robot will
 * attempt to travel as though in a grid. For example the navigator will break
 * down the path to the destination in terms of number of vertical and
 * horizontal blocks.
 * 
 * @author alex
 * 
 */
public class BlockNavigator extends Navigator {

	private LineDetector detector;
	private static INavigator nav;
	private static Odometer odo;
	private IRobot robot;
	private NXTRegulatedMotor leftMotor = Motor.A;
	private NXTRegulatedMotor rightMotor = Motor.B;
	private USPoller us;
	double ROTATE_SPEED = 60;
	double FORWARD_SPEED = 90;
	double headNow = odo.getTheta();
	double xNow = odo.getX();
	double yNow = odo.getY();
	double xHead, yHead;
	double closeX;			        
	double closeY;

	private BlockNavigator(IRobot robot, Odometer odo) {
		super(robot, odo);
		this.odo = odo;
		this.robot = robot;
		this.us = us;
	
		// Need to create new LineDetector
	}

	/**
	 * Get the instance of the BlockNavigator.
	 * 
	 * @param robot Robot containing the corresponding motors for the wheels.
	 * @param odo Odometer that is used to guide the navigation and that will be corrected.
	 * @return
	 */
	public static INavigator getInstance(IRobot robot, Odometer odo) {
		if (nav == null) {
			nav = new BlockNavigator(robot, odo);
		}
		return nav;
	}

	@Override
	public void travelTo(double x, double y) {
		
		this.isNavigating = true;
		boolean atRow;
		boolean atColumn;
		double[] dDH;
		
		//TODO get to intersection
		
		
		
		// Calculate Destination row and column
		
		if((x%30)>=5){ 				   
			closeX = x + (10-(x%30));
		}
		else{
			closeX = x - (x%30);
		}
		if((y%30)>=5){			        
			closeY = y +(10-(y%30));
		}
		else{
			closeY = y - (y%30);
		}
		
		//we already there?
		atRow = ((odo.getX() > (closeX*0.95)) && (odo.getX() < (closeX + (closeX*0.05))));
		atColumn = ((odo.getY() > (closeY*0.95)) && (odo.getY() < (closeY + (closeY*0.05))));
		
		// Travel vertically loop (while not at destination row)
		while(!atRow){
			
			
			 // check if we need to go up or down
			if(xNow < closeX){ 
				xHead = 270;
			}
			else{
				xHead = 90;
			}
			
			//rotate 90 degrees until facing the right direction
			while((headNow > (xHead+(xHead*0.05))) || (headNow < (xHead*0.95))){ 
				turnNinety();
				headNow = odo.getTheta();
			}
			
			// If there is an obstacle within the next tile
			if((us.getFilteredData()) > 35){
				// While there is an obstacle
				while((us.getFilteredData()) > 35){
					turnNinety();
				}//end while there is an obstacle
				
			}//end if there is an obtacle
			 
			//move forward one tile
			else{ 
				this.robot.setSpeeds(FORWARD_SPEED, ROTATE_SPEED);
				leftMotor.rotate(convertDistance(robot.LEFT_WHEEL_RADIUS, 30.00), true);
				rightMotor.rotate(convertDistance(robot.RIGHT_WHEEL_RADIUS, 30.00), false);
				
			}
			
			//are we at the row?
			atRow = ((odo.getX() > (closeX*0.95)) && (odo.getX() < (closeX + (closeX*0.05))));
		}//end Travel vertically loop
			
			
			
	
		// Travel horizontally loop (while not at destination column)
		while(!atColumn){
			
			// check if we need to go left or right
			if(yNow < closeY){ 
				yHead = 180;
			}
			else{
				yHead = 0;
			}
			
			//rotate 90 degrees until facing the right direction
			while((headNow > (yHead+(yHead*0.05))) || (headNow < (yHead*0.95))){ 
				turnNinety();
				headNow = odo.getTheta();
			}//end while !facing proper direction
			
			// If there is an obstacle within the next tile
			if((us.getFilteredData()) > 35){
							
							// While there is an obstacle
				while((us.getFilteredData()) > 35){
								turnNinety();
							}//end while obstacle
							
			}//end if there is an obstacle
			
			//move forward one tile
			else{ 
				this.robot.setSpeeds(FORWARD_SPEED, ROTATE_SPEED);
				leftMotor.rotate(convertDistance(robot.LEFT_WHEEL_RADIUS, 30.00), true);
				rightMotor.rotate(convertDistance(robot.RIGHT_WHEEL_RADIUS, 30.00), false);
							
			}
			
			//are we at the column?
			atColumn = ((odo.getY() > (closeY*0.95)) && (odo.getY() < (closeY + (closeY*0.05))));
			
		}//end Travel horizontally loop
		
			
		
		this.isNavigating = false;
	}//end travelTo
	
	/**
	 * This navigation method does not make use of blocks.
	 * @param x
	 * @param y
	 */
	public void simpleTravelTo(double x, double y) {
		super.travelTo(x, y);
	}
	
	/**
	 * Calculates the position of the center of the tile encapsulating x and y.
	 * @param x X position indicating the tile of interest.
	 * @param y Y position indicating the tile of interest.
	 * @return The position of the center of the tile of interests.
	 */
	private Pos getCenterOfTile(double x, double y) {
		// TODO
		return null;
	}
	
	@Override
	public void turnTo(double angle) {
		super.turnTo(angle);
	}
	//method used for moving a specific distance
	private static int convertDistance(double radius, double distance) {
		return (int) ((180.0 * distance) / (Math.PI * radius));
	}
	//methode used for moving a specific distance
	private static int convertAngle(double radius, double width, double angle) {
		return convertDistance(radius, Math.PI * width * angle / 360.0);
	}
	
	//Methode for turning 90 degrees clockwise
	private void turnNinety(){
		this.robot.setSpeeds(FORWARD_SPEED, ROTATE_SPEED);
		leftMotor.rotate(convertAngle(robot.LEFT_WHEEL_RADIUS, robot.WHEEL_WIDTH, 90.0), true);
		rightMotor.rotate(-convertAngle(robot.RIGHT_WHEEL_RADIUS, robot.WHEEL_WIDTH, 90.0), false);
	}
	

}

import lejos.nxt.*;

public class Launcher 
{
  public static void main(String[] args)
	{
		
		//Initialize information on the LCD screen
		int buttonChoice;
		//350 for 5, 450 for 6, 650 for 7, 850 for 8
		int launchspeed = 750 ;
		do
		{
			//Clear the screen and dsiplay text
			LCD.clear();
			LCD.drawString("LAUNCH CATAPULT!!", 1, 1);
			LCD.drawString("Left for decrease",1,2);
			LCD.drawString("rightfor increase speed",1,3);
			LCD.drawString("Current speed = ",1,4);
			LCD.drawString(""+launchspeed, 1,5);
			buttonChoice = Button.waitForAnyPress();		//wait until a button is pressed
			if(buttonChoice == Button.ID_LEFT)
			{
				launchspeed = launchspeed - 10;
				LCD.drawString(""+launchspeed, 1,5);
			}
			if(buttonChoice == Button.ID_LEFT)
			{
				launchspeed = launchspeed +10;
				LCD.drawString(""+launchspeed, 1,5);
			}
		}
		while(buttonChoice!=Button.ID_ENTER);
		
		//after a button is pressed, load a ball
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
		
		
		System.exit(0);
	}
}

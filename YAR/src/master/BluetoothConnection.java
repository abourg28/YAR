package master;

/*
* @author Sean Lawlor
* @date November 3, 2011
* @class ECSE 211 - Design Principle and Methods
*/


import java.io.DataInputStream;
import java.io.IOException;

import lejos.nxt.LCD;
import lejos.nxt.comm.Bluetooth;
import lejos.nxt.comm.NXTConnection;

import common.Instructions;
import common.ParseInstructions;
/*
 * This class inits a bluetooth connection, waits for the data
 * and then allows access to the data after closing the BT channel.
 * 
 * It should be used by calling the constructor which will automatically wait for
 * data without any further user command
 * 
 * Then, once completed, it will allow access to an instance of the Transmission
 * class which has access to all of the data needed
 */
public class BluetoothConnection {
	private Instructions inst;
	
	public BluetoothConnection() {
		LCD.clear();
		LCD.drawString("Starting BT connection", 0, 0);
		
		NXTConnection conn = Bluetooth.waitForConnection();
		DataInputStream dis = conn.openDataInputStream();
		LCD.drawString("Opened DIS", 0, 1);
		this.inst = ParseInstructions.parse(dis);
		LCD.drawString("Finished Parsing", 0, 2);
		try {
			dis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		conn.close();
	}
	
	public Instructions getInstructions() {
		return this.inst;
	}
	
	public void printTransmission() {
		try {
			LCD.clear();
			LCD.drawString(("Transmitted Values"), 0, 0);
			LCD.drawString("Start: " + inst.startingCorner.toString(), 0, 1);
			LCD.drawString("Role: " + inst.role.toString(), 0, 2);
			LCD.drawString("bx: " + inst.bx, 0, 3);
			LCD.drawString("by: " + inst.by, 0, 4);
			LCD.drawString("w1: " + inst.w1, 0, 5);
			LCD.drawString("w2: " + inst.w2, 0, 6);
			LCD.drawString("d1: " + inst.d1, 0, 7);
		} catch (NullPointerException e) {
			LCD.drawString("Bad Trans", 0, 8);
		}
	}
	
}

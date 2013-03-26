/*
* @author Sean Lawlor
* @date November 3, 2011
* @class ECSE 211 - Design Principle and Methods
*/
package common;

import java.io.DataInputStream;
import java.io.IOException;



import lejos.nxt.LCD;

/**
 * Static parsers for parsing data off the communication channel
 * 
 * The order of data is defined in the Server's Transmission class
 */

public class ParseInstructions {
	
	public static Instructions parse (DataInputStream dis) {
		Instructions inst = null;
		try {
			
			while (dis.available() <= 0)
				Thread.sleep(10); // spin waiting for data
			
			inst = new Instructions();
			inst.role = PlayerRole.lookupRole(dis.readInt());
			ignore(dis);
			inst.startingCorner = StartCorner.lookupCorner(dis.readInt());
			ignore(dis);
			inst.bx = dis.readInt();
			ignore(dis);
			inst.by = dis.readInt();
			ignore(dis);
			inst.w1 = dis.readInt();
			ignore(dis);
			inst.w2 = dis.readInt();
			ignore(dis);
			inst.d1 = dis.readInt();
			
			return inst;
		} catch (IOException e) {
			// failed to read transmitted data
			LCD.drawString("IO Ex", 0, 7);
			return inst;
		} catch (InterruptedException e) {
			return inst;
		}
		
	}
	
	public static void ignore(DataInputStream dis) throws IOException {
		dis.readChar();
	}
	
}

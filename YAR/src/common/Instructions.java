/**
 * @author Sean Lawlor, Stepan Salenikovich
 * @date November 3, 2011
 * @class ECSE 211 - Design Principle and Methods
 */
package common;

/**
 * Skeleton class to hold datatypes needed for final project
 * 
 * Simply all public variables so can be accessed with Instructions t = new
 * Instructions(); int fx = t.fx;
 * 
 * and so on...
 * 
 * Also the role is an enum, converted from the char transmitted. (It should
 * never be Role.NULL)
 */

public class Instructions {

	public int goalX;
	public int goalY;

	public Instructions(int goalX, int goalY) {
		this.goalX = goalX;
		this.goalY = goalY;
	}

	public Instructions() {
	}
}

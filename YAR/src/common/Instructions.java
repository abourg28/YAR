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
	/**
	 * The role, Defender or Attacker
	 */
	public PlayerRole role;
	/**
	 * Ball dispenser X tile position
	 */
	public int bx;
	/**
	 * Ball dispenser Y tile position
	 */
	public int by;
	/**
	 * Defender zone dimension 1
	 */
	public int w1;
	/**
	 * Defender zone dimension 2
	 */
	public int w2;
	/**
	 * Forward line distance from goal
	 */
	public int d1;
	/**
	 * starting corner, 1 through 4
	 */
	public StartCorner startingCorner;
	
	public int getLoaderX() {
		return (bx + 1) * 30;
	}
	
	public int getLoaderY() {
		return (by + 1) * 30;
	}
	
	public int getDefenderZoneWidth() {
		return (w1 + 1) * 30;
	}
	
	public int getDefenderZoneDepth() {
		return (w2 + 1) * 30;
	}
	
	public int getOffensiveDepth() {
		return (d1 + 1) * 30;
	}
	
	public Pos getGoalPos() {
		Pos p = new Pos();
		p.x = (5 + 1) * 30;
		p.y = (10 + 1) * 30;
		return p;
	}
}

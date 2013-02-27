/**
 * 
 */
package yar;

/**
 * @author alex
 *
 */
public interface IDefender {
	
	public void orient();
	public void defend();
	public void stop();
	public void calculateDefenderPosition(double d1, double w1, double w2);

}

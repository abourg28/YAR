/**
 * 
 */
package slave;

/**
 * @author alex
 *
 */
public interface ILauncher {	
	public void launch();
	public void retract();
	public void calculateLaunchPosition(double d1, double w1);
	public double getLaunchX();
	public double getLaunchY();
	public double getLaunchTheta();

}

/**
 * 
 */
package slave;

import common.Instructions;

/**
 * @author alex
 *
 */
public interface ILauncher {	
	public void launch();
	public void retract();
	public void calculateLaunchPosition();
	public double getLaunchX();
	public double getLaunchY();
	public double getLaunchTheta();
	public void updateInstructions(Instructions inst);

}

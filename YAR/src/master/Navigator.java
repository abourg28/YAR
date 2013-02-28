package master;


/**
 * This is an abstract navigator. This is where we want to have common navigator code.
 * @author alex
 *
 */
public abstract class Navigator extends Thread implements INavigator {
	
	protected Odometer odo;
	
	public Navigator(Odometer odo) {
		// TODO Auto-generated method stub
	}

	@Override
	public void travelTo(double x, double y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void turnTo(double theta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isNavigating() {
		// TODO Auto-generated method stub
		return false;
	}

}

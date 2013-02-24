package master;

public interface INavigator {
	public void travelTo(double x, double y);
	public void turnTo(double theta);
	public boolean isNavigating();
}

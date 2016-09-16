package Controller;
/**
 * interface that defines a controller
 * @author Administrator
 *
 */
public interface Controller {
	/**
	 * notifies that a maze is ready
	 * @param name maze name
	 */
	void notifyMazeIsReady(String name);
	/**
	 * notifies that a solution is ready
	 * @param name maze name
	 */
	void notifySolutionIsReady(String name);
	/**
	 * notifies that need to exit, close all open files etc
	 */
	void notifyExit();
}

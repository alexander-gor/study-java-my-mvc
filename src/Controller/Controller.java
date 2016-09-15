package Controller;

public interface Controller {
	void notifyMazeIsReady(String name);
	void notifySolutionIsReady(String name);
	void notifyExit();
}

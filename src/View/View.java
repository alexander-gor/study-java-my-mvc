package View;

import java.util.HashMap;

import algorithms.mazeGenerators.Maze3d;
import Controller.Command;
import Controller.Controller;

public interface View {
	void notifyMazeIsReady(String name);
	void displayMaze(Maze3d maze);
	void setCommands(HashMap<String, Command> commands);
	void start();
	void setController(Controller controller);
	void notifySolutionIsReady(String name);
}

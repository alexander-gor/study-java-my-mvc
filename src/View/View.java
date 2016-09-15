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
	void notifyExit();
	void displayDir(String dirName);
	void displayCrossSectionBy(int index, String axis, Maze3d maze);
}

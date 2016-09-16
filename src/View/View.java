package View;

import java.util.HashMap;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import Controller.CommandBase;
import Controller.Controller;
/**
 * interface that defines the view
 * @author Administrator
 *
 */
public interface View {
	/**
	 * notifies when maze finished generation 
	 * @param name maze name
	 */
	void notifyMazeIsReady(String name);
	/**
	 * displays the maze
	 * @param maze the maze
	 */
	void displayMaze(Maze3d maze);
	/**
	 * sets command to the view
	 * @param commands command map
	 */
	void setCommands(HashMap<String, CommandBase> commands);
	/**
	 * starts the view
	 */
	void start();
	/**
	 * setter for the controller
	 * @param controller the controller
	 */
	void setController(Controller controller);
	/**
	 * notifies solution is ready
	 * @param name maze name
	 */
	void notifySolutionIsReady(String name);
	/**
	 * displays directory contents
	 * @param path path
	 */
	void displayDir(String path);
	/**
	 * displays the cross section of a maze
	 * @param index index of the cross section
	 * @param axis index on which axis X/Y/Z
	 * @param maze the maze
	 */
	void displayCrossSectionBy(int index, String axis, Maze3d maze);
	/**
	 * displays the solution
	 * @param solution the solution
	 */
	void displaySolution(Solution<Position> solution);
	/**
	 * show a text to the output 
	 * @param text text to show
	 */
	void showText(String text);
}

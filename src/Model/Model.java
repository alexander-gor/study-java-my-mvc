package Model;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import Controller.Controller;
/**
 * interface that define the model
 * @author Administrator
 *
 */
public interface Model {
	/**
	 * generates the maze
	 * @param name maze name
	 * @param x x axis length
	 * @param y y axis length
	 * @param z z axis length
	 */
	void generateMaze(String name, int x, int y, int z);
	/**
	 * solves the maze using an algorithm
	 * @param name maze name
	 * @param algorithm algorithm, BFS/DFS
	 */
	void solveMaze(String name, String algorithm);
	/**
	 * returns a maze
	 * @param name maze name
	 * @return the maze
	 */
	Maze3d getMaze(String name);
	/**
	 * returns the solution for a maze
	 * @param name maze name
	 * @return the solution for a maze
	 */
	Solution<Position> getSolution(String name);
	/**
	 * exits
	 */
	void exit();
	/**
	 * setter for the controller
	 * @param controller the controller
	 */
	void setController(Controller controller);
	/**
	 * saves the maze to a file
	 * @param name maze name
	 * @param fileName file name 
	 */
	void saveMaze(String name, String fileName);
	/**
	 * loads the maze from a file
	 * @param fileName file name
	 * @param name maze name
	 */
	void loadMaze(String fileName, String name);
	
}
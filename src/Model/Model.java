package Model;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import Controller.Controller;

public interface Model {
	void generateMaze(String name, int x, int y, int z);
	void solveMaze(String name, String algorithm);
	Maze3d getMaze(String name);
	Solution<Position> getSolution(String name);
	void exit();
	void setController(Controller controller);
	void saveMaze(String name, String fileName);
	void loadMaze(String name, String fileName);
	
}
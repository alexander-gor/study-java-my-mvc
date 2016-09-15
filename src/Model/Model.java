package Model;

import algorithms.mazeGenerators.Maze3d;
import Controller.Controller;

public interface Model {
	void generateMaze(String name, int x, int y, int z);
	Maze3d getMaze(String name);
	void exit();
	void setController(Controller controller);
	
}
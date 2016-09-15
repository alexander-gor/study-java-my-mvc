package Controller;

import java.util.HashMap;

import algorithms.mazeGenerators.Maze3d;
import Model.Model;
import View.View;

public class CommandsManager {
	
	private Model model;
	private View view;
		
	public CommandsManager(Model model, View view) {
		this.model = model;
		this.view = view;		
	}
	
	public HashMap<String, Command> getCommandsMap() {
		HashMap<String, Command> commands = new HashMap<String, Command>();
		commands.put("generate_maze", new GenerateMazeCommand());
		commands.put("display", new DisplayMazeCommand());
		
		return commands;
	}
	
	public class GenerateMazeCommand implements Command {

		@Override
		public void doCommand(String[] args) {
			String name = args[0];
			int x = Integer.parseInt(args[1]);
			int y = Integer.parseInt(args[2]);
			int z = Integer.parseInt(args[3]);
			model.generateMaze(name, x, y, z);
		}		
	}
	
	public class DisplayMazeCommand implements Command {

		@Override
		public void doCommand(String[] args) {
			String name = args[0];
			Maze3d maze = model.getMaze(name);
			view.displayMaze(maze);
		}
		
	}
	
	
}

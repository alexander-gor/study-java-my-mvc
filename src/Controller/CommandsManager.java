package Controller;

import java.util.HashMap;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
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
		commands.put("dir", new DisplayDirCommand());
		commands.put("generate_3d_maze", new GenerateMazeCommand());
		commands.put("display", new DisplayMazeCommand());
		commands.put("display_cross_section", new DisplayCrossSectionCommand());
		commands.put("save_maze", new SaveMazeCommand());
		commands.put("load_maze", new LoadMazeCommand());
		commands.put("solve_maze", new SolveMazeCommand());
		commands.put("display_solution", new DisplaySolutionCommand());
		commands.put("exit", new ExitCommand());
		
		
		return commands;
	}
	
	public class ExitCommand implements Command {

		@Override
		public void doCommand(String[] args) {
			model.exit();
		}
		
	}
	
	public class DisplaySolutionCommand implements Command {

		@Override
		public void doCommand(String[] args) {
			String name = args[0]; 
			Solution<Position> solution = model.getSolution(name);
			view.displaySolution(solution);
		}
		
	}
	
	public class SolveMazeCommand implements Command {

		@Override
		public void doCommand(String[] args) {
			String name = args[0];
			String algorithm = args[1];
			
			model.solveMaze(name, algorithm);
		}
		
	}
	
	public class SaveMazeCommand implements Command {

		@Override
		public void doCommand(String[] args) {
			String name = args[0];
			String fileName = args[1];
			
			model.saveMaze(name, fileName);
		}
		
	}
	
	public class LoadMazeCommand implements Command {

		@Override
		public void doCommand(String[] args) {
			String name = args[0];
			String fileName = args[1];
			
			model.loadMaze(name, fileName);
		}
		
	}
	
	public class DisplayCrossSectionCommand implements Command {

		@Override
		public void doCommand(String[] args) {
			int index = Integer.parseInt(args[0]);
			String axis = args[1];
			String name = args[2];
			Maze3d maze = model.getMaze(name);
			view.displayCrossSectionBy(index, axis, maze);
		}
		
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
	
	public class DisplayDirCommand implements Command {

		@Override
		public void doCommand(String[] args) {
			String dirName = args[0];
			view.displayDir(dirName);
		}
		
	}
	
	
}

package Controller;

import java.util.HashMap;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import Model.Model;
import View.View;
/**
 * class that defines which commands will be set
 * @author Administrator
 *
 */
public class CommandsManager {
	
	private Model model;
	private View view;
	private HashMap<String, CommandBase> commands;
	/**
	 * CommandsManager ctor
	 * @param model that is used
	 * @param view that is used
	 */
	public CommandsManager(Model model, View view) {
		this.model = model;
		this.view = view;		
	}
	/**
	 * initializes commands
	 * @return command map
	 */
	public HashMap<String, CommandBase> getCommandsMap() {
		commands = new HashMap<String, CommandBase>();
		
		commands.put("dir", new DisplayDirCommand());
		commands.get("dir").setSyntax("dir <path>\nprints directory structure");
		commands.put("generate_3d_maze", new GenerateMazeCommand());
		commands.get("generate_3d_maze").setSyntax("generate_3d_maze <name> <x> <y> <z>\ngenerates a maze");
		commands.put("display", new DisplayMazeCommand());
		commands.get("display").setSyntax("display <name>\ndisplays a maze");
		commands.put("display_cross_section", new DisplayCrossSectionCommand());
		commands.get("display_cross_section").setSyntax("display_cross_section <index> <x/y/z> <name>\ndisplays a cross section by X/Y/Z at a index");
		commands.put("save_maze", new SaveMazeCommand());
		commands.get("save_maze").setSyntax("save_maze <name> <fileName>\nsaves the maze in a file");
		commands.put("load_maze", new LoadMazeCommand());
		commands.get("load_maze").setSyntax("load_maze <fileName> <name>\nloads a maze from a file");
		commands.put("solve_maze", new SolveMazeCommand());
		commands.get("solve_maze").setSyntax("solve_maze <name> <BFS/DFS>\nsolves a maze using a certain algorithm");
		commands.put("display_solution", new DisplaySolutionCommand());
		commands.get("display_solution").setSyntax("display_solution <name>\ndisplays a solution to a maze");
		commands.put("help", new ExitCommand());
		commands.get("help").setSyntax("help\nprints help window");
		commands.put("exit", new ExitCommand());
		commands.get("exit").setSyntax("exit\n exits CLI");
		
		return commands;
	}
	/**
	 * class for the help command
	 * @author Administrator
	 *
	 */
	public class HelpCommand extends CommandBase {

		@Override
		public void doCommand(String[] args) {
			for (String command : commands.keySet()) {
				view.showText(commands.get(command).getSyntax());
			}
		}
		
	}
	/**
	 * class for the exit command
	 * @author Administrator
	 *
	 */
	public class ExitCommand extends CommandBase {

		@Override
		public void doCommand(String[] args) {
			model.exit();
		}
		
	}
	/**
	 * class for the display_solution command
	 * @author Administrator
	 *
	 */
	public class DisplaySolutionCommand extends CommandBase {

		@Override
		public void doCommand(String[] args) {
			String name = args[0]; 
			Solution<Position> solution = model.getSolution(name);
			view.displaySolution(solution);
		}
		
	}
	/**
	 * class for the solve command
	 * @author Administrator
	 *
	 */
	public class SolveMazeCommand extends CommandBase {

		@Override
		public void doCommand(String[] args) {
			String name = args[0];
			String algorithm = args[1];
			
			model.solveMaze(name, algorithm);
		}
		
	}
	/**
	 * class for the save_maze command
	 * @author Administrator
	 *
	 */
	public class SaveMazeCommand extends CommandBase {

		@Override
		public void doCommand(String[] args) {
			String name = args[0];
			String fileName = args[1];
			
			model.saveMaze(name, fileName);
		}
		
	}
	/**
	 * class for the load_maze command
	 * @author Administrator
	 *
	 */
	public class LoadMazeCommand extends CommandBase {

		@Override
		public void doCommand(String[] args) {
			String fileName = args[0];
			String name = args[1];
			
			model.loadMaze(fileName, name);
		}
		
	}
	/**
	 * class for the display_cross_section command
	 * @author Administrator
	 *
	 */
	public class DisplayCrossSectionCommand extends CommandBase {

		@Override
		public void doCommand(String[] args) {
			int index = Integer.parseInt(args[0]);
			String axis = args[1];
			String name = args[2];
			Maze3d maze = model.getMaze(name);
			view.displayCrossSectionBy(index, axis, maze);
		}
		
	}
	/**
	 * class for the generate_3d_maze command
	 * @author Administrator
	 *
	 */
	public class GenerateMazeCommand extends CommandBase {

		@Override
		public void doCommand(String[] args) {
			String name = args[0];
			int x = Integer.parseInt(args[1]);
			int y = Integer.parseInt(args[2]);
			int z = Integer.parseInt(args[3]);
			model.generateMaze(name, x, y, z);
		}		
	}
	/**
	 * class for the display command
	 * @author Administrator
	 *
	 */
	public class DisplayMazeCommand extends CommandBase {

		@Override
		public void doCommand(String[] args) {
			String name = args[0];
			Maze3d maze = model.getMaze(name);
			view.displayMaze(maze);
		}
		
	}
	/**
	 * class for the dir command
	 * @author Administrator
	 *
	 */
	public class DisplayDirCommand extends CommandBase {

		@Override
		public void doCommand(String[] args) {
			String dirName = args[0];
			view.displayDir(dirName);
		}
		
	}
	
	
}

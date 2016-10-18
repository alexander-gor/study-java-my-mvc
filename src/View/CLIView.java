package View;

import java.io.BufferedReader;
import java.io.File;
import java.io.PrintWriter;
import java.util.HashMap;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import Controller.CommandBase;
import Controller.Controller;

public class CLIView implements View {
	
	@SuppressWarnings("unused")
	//reader
	private BufferedReader in;
	//writer
	private PrintWriter out;
	//cli reference
	private CLI cli;
	@SuppressWarnings("unused")
	//controller
	private Controller controller;
	/**
	 * c'tor
	 * @param in reader
	 * @param out writer
	 */
	public CLIView(BufferedReader in, PrintWriter out) {
		this.in = in;
		this.out = out;
				
		cli = new CLI(in, out);
	}
	/**
	 * setter for the controller
	 * @param controller the controller
	 */
	public void setController(Controller controller) {
		this.controller = controller;
	}
	
	@Override
	/**
	 * notifies when maze finished generation 
	 * @param name maze name
	 */
	public void notifyMazeIsReady(String name) {
		out.println("maze " + name + " is ready");
		out.flush();
	}
	
	@Override
	/**
	 * notifies solution is ready
	 * @param name
	 */
	public void notifySolutionIsReady(String name) {
		out.println("solution for " + name + " is ready");
		out.flush();
	}

	@Override
	/**
	 * displays the maze
	 * @param maze the maze
	 */
	public void displayMaze(Maze3d maze) {
		out.println(maze);
		out.flush();
	}

	@Override
	/**
	 * sets command to the view
	 * @param commands command map
	 */
	public void setCommands(HashMap<String, CommandBase> commands) {
		cli.setCommands(commands);
	}

	@Override
	/**
	 * starts the view
	 */
	public void start() {
		// TODO Auto-generated method stub
		cli.start();
	}

	@Override
	/**
	 * displays directory contents
	 * @param path path
	 */
	public void displayDir(String dirName) {
		File folder = new File(dirName);
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				out.println("File " + listOfFiles[i].getName());
			} else if (listOfFiles[i].isDirectory()) {
				out.println("Directory " + listOfFiles[i].getName());
			}
		}	
	} 

	@Override
	/**
	 * displays the cross section of a maze
	 * @param index index of the cross section
	 * @param axis index on which axis X/Y/Z
	 * @param maze the maze
	 */
	public void displayCrossSectionBy(int index, String axis, Maze3d maze) {
		int[][] crossSection = null;
		switch (axis) {
		case "X":
		case "x":
			crossSection = maze.getCrossSectionByX(index);
			break;
		case "Y":
		case "y":
			crossSection = maze.getCrossSectionByY(index);
			break;
		case "Z":
		case "z":
			crossSection = maze.getCrossSectionByZ(index);
			break;

		default:
			break;
		}
		if (crossSection != null) {
			int xLen = crossSection.length;
			int yLen = crossSection[0].length;
			StringBuilder sb = new StringBuilder();
			for (int x = 0; x < xLen ; x++) {
				for (int y = 0; y < yLen ; y++) {
					sb.append(crossSection[x][y]);
				}
				sb.append("\n");
			}
			out.println(sb.toString());
			out.flush();
		}
	}

	@Override
	/**
	 * displays the solution
	 * @param solution the solution
	 */
	public void displaySolution(Solution<Position> solution) {
		out.println(solution);
		out.flush();
		
	}

	@Override
	/**
	 * show a text to the output 
	 * @param text text to show
	 */
	public void showText(String text) {
		out.println(text);
		out.flush();
	}

}

package View;

import java.io.BufferedReader;
import java.io.File;
import java.io.PrintWriter;
import java.util.HashMap;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import Controller.Command;
import Controller.Controller;

public class MyView implements View {
	
	private BufferedReader in;
	private PrintWriter out;
	private CLI cli;
	private Controller controller;

	public MyView(BufferedReader in, PrintWriter out) {
		this.in = in;
		this.out = out;
				
		cli = new CLI(in, out);
	}
	
	public void setController(Controller controller) {
		this.controller = controller;
	}
	
	@Override
	public void notifyMazeIsReady(String name) {
		out.println("maze " + name + " is ready");
		out.flush();
	}
	
	@Override
	public void notifySolutionIsReady(String name) {
		out.println("solution for " + name + " is ready");
		out.flush();
	}

	@Override
	public void displayMaze(Maze3d maze) {
		out.println(maze);
		out.flush();
	}

	@Override
	public void setCommands(HashMap<String, Command> commands) {
		cli.setCommands(commands);
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		cli.start();
	}

	@Override
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
	public void displaySolution(Solution<Position> solution) {
		out.println(solution);
		out.flush();
		
	}

}

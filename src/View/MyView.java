package View;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.HashMap;

import algorithms.mazeGenerators.Maze3d;
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
				
		cli = new CLI(in, out, this);
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
	public void notifyExit() {
		// TODO Auto-generated method stub
		controller.notifyExit();
	}

}

package Model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import algorithms.demo.MazeAdapter;
import algorithms.mazeGenerators.GrowingTreeGenerator;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.BFS;
import algorithms.search.CommonSearcher;
import algorithms.search.DFS;
import algorithms.search.Solution;
import io.MyCompressorOutputStream;
import io.MyDecompressorInputStream;
import Controller.Controller;

public class MyModel implements Model {
	
	private List<GenerateMazeRunnable> generateMazeTasks = new ArrayList<GenerateMazeRunnable>();
	
	class GenerateMazeRunnable implements Runnable {

		private int x, y, z;
		private String name;
		private GrowingTreeGenerator generator;
		public GenerateMazeRunnable(int x, int y, int z, String name) {
			this.x = x;
			this.y = y;
			this.z = z;
			this.name = name;
		}
		
		@Override
		public void run() {
			generator = new GrowingTreeGenerator();
			Maze3d maze = generator.generate(x, y, z);
			mazes.put(name, maze);
			
			controller.notifyMazeIsReady(name);			
		}
		
		public void terminate() {
			generator.setDone(true);
		}		
	}
	
	private List<SolveMazeRunnable> solveMazeTasks = new ArrayList<SolveMazeRunnable>();
	
	class SolveMazeRunnable implements Runnable {

		CommonSearcher<Position> searcher;
		
		private String algorithm;
		private MazeAdapter adapter;
		private String name;
		public SolveMazeRunnable(String name, Maze3d maze, String algorithm) {
			this.adapter = new MazeAdapter(maze);
			this.algorithm = algorithm;
			this.name = name;
		}
		
		@Override
		public void run() {
			if (algorithm.equals("BFS")) {
				searcher = new BFS<Position>();
			}
			else if (algorithm.equals("DFS")) {
				searcher = new DFS<Position>();
			} 
			
			Solution<Position> solution = searcher.search(adapter);
			
			solutions.put(name, solution);
			
			controller.notifySolutionIsReady(name);			
		}
		
		public void terminate() {
			searcher.setDone(true);
		}		
	}
	
	private Controller controller;	
	private Map<String, Maze3d> mazes = new ConcurrentHashMap<String, Maze3d>();
	private Map<String, Solution<Position>> solutions = new ConcurrentHashMap<String, Solution<Position>>();
	
	private List<Thread> threads = new ArrayList<Thread>();
	
	
	public void setController(Controller controller) {
		this.controller = controller;
	}
	
	@Override
	public void generateMaze(String name, int x, int y, int z) {
		GenerateMazeRunnable generateMaze = new GenerateMazeRunnable(x, y, z, name);
		generateMazeTasks.add(generateMaze);
		Thread thread = new Thread(generateMaze);
		thread.start();
		threads.add(thread);		
	}
	
	@Override
	public void solveMaze(String name, String algorithm) {
		Maze3d maze = getMaze(name);
		SolveMazeRunnable solveMaze = new SolveMazeRunnable( name, maze, algorithm);
		solveMazeTasks.add(solveMaze);
		Thread thread = new Thread(solveMaze);
		thread.start();
		threads.add(thread);		
	}

	@Override
	public Maze3d getMaze(String name) {
		return mazes.get(name);
	}
	
	public void exit() {
		for (GenerateMazeRunnable task : generateMazeTasks) {
			task.terminate();
		}
		for (SolveMazeRunnable task : solveMazeTasks) {
			task.terminate();
		}
	}

	@Override
	public void saveMaze(String name, String fileName) {
		// save it to a file
		Maze3d maze = getMaze(name);
		OutputStream out;
		try {
			out = new MyCompressorOutputStream(
					new FileOutputStream(fileName));
			byte[] arr = maze.toByteArray();
			
			out.write(arr.length);
			out.write(arr);
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
				
				
		
	}

	@Override
	public void loadMaze(String fileName, String name) {
		InputStream in;
		try {
			in = new MyDecompressorInputStream(
				new FileInputStream(fileName));
			int size = in.read();			
			byte b[]=new byte[size];
			in.read(b);
			in.close();	
			
			Maze3d loaded = new Maze3d(b);
			mazes.put(name, loaded);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public Solution<Position> getSolution(String name) {
		return solutions.get(name);
	}

}

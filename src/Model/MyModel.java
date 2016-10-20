package Model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import Controller.Controller;
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

/**
 * class that implements the model interface
 * @author Administrator
 *
 */
public class MyModel implements Model {
	private static final String RESOURCESDIR = "Resources\\";
	//controller reference
	private Controller controller;
	//cached mazes
	private Map<String, Maze3d> mazes = new ConcurrentHashMap<String, Maze3d>();
	//cached solutions
	private Map<String, Solution<Position>> solutions = new ConcurrentHashMap<String, Solution<Position>>();
	//list of all running threads
	private List<Thread> threads = new ArrayList<Thread>();
	//all tasks to generate a maze
	private List<GenerateMazeRunnable> generateMazeTasks = new ArrayList<GenerateMazeRunnable>();
	//all tasks to solve a maze
	private List<SolveMazeRunnable> solveMazeTasks = new ArrayList<SolveMazeRunnable>();
	//OutputStream reference to close if exit
	private OutputStream saveFile;
	//InputStream reference to close if exit
	private InputStream loadFile;
	/**
	 * class that defines a asynchronous task that generates a maze
	 * @author Administrator
	 *
	 */
	class GenerateMazeRunnable implements Runnable {
		//maze sizes
		private int x, y, z;
		//maze name
		private String name;
		//generator used
		private GrowingTreeGenerator generator;
		/**
		 * GenerateMazeRunnable c'tor
		 * @param x x axis length
		 * @param y y axis length
		 * @param z z axis length
		 * @param name maze name
		 */
		public GenerateMazeRunnable(int x, int y, int z, String name) {
			this.x = x;
			this.y = y;
			this.z = z;
			this.name = name;
		}
		
		@Override
		/**
		 * generates the maze, then notifies when ready
		 */
		public void run() {
			generator = new GrowingTreeGenerator();
			Maze3d maze = generator.generate(x, y, z);
			mazes.put(name, maze);
			
			controller.notifyMazeIsReady(name);			
		}
		//stops the generator if task need to finish
		public void terminate() {
			generator.setDone(true);
		}		
	}
	/**
	 * class that defines a asynchronous task that solves a maze
	 * @author Administrator
	 *
	 */
	class SolveMazeRunnable implements Runnable {
		//the searcher
		CommonSearcher<Position> searcher;
		//desired algorithm
		private String algorithm;
		//maze adapter
		private MazeAdapter adapter;
		//maze name
		private String name;
		/**
		 * c'tor
		 * @param name maze name 
		 * @param maze the maze
		 * @param algorithm deired algorithm, DFS/BFS
		 */
		public SolveMazeRunnable(String name, Maze3d maze, String algorithm) {
			this.adapter = new MazeAdapter(maze);
			this.algorithm = algorithm;
			this.name = name;
		}
		
		@Override
		/**
		 * solves the maze, then notifies when ready
		 */
		public void run() {
			if (algorithm.toLowerCase().equals("bfs")) {
				searcher = new BFS<Position>();
			}
			else if (algorithm.toLowerCase().equals("dfs")) {
				searcher = new DFS<Position>();
			} 
			
			Solution<Position> solution = searcher.search(adapter);
			
			solutions.put(name, solution);
			
			controller.notifySolutionIsReady(name);			
		}
		//stops the solver if task need to finish
		public void terminate() {
			searcher.setDone(true);
		}		
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
	 * generates the maze
	 * @param name maze name
	 * @param x x axis length
	 * @param y y axis length
	 * @param z z axis length
	 */
	public void generateMaze(String name, int x, int y, int z) {
		GenerateMazeRunnable generateMaze = new GenerateMazeRunnable(x, y, z, name);
		generateMazeTasks.add(generateMaze);
		Thread thread = new Thread(generateMaze);
		thread.start();
		threads.add(thread);		
	}
	
	@Override
	/**
	 * solves the maze using an algorithm
	 * @param name maze name
	 * @param algorithm algorithm, BFS/DFS
	 */
	public void solveMaze(String name, String algorithm) {
		Maze3d maze = getMaze(name);
		SolveMazeRunnable solveMaze = new SolveMazeRunnable( name, maze, algorithm);
		solveMazeTasks.add(solveMaze);
		Thread thread = new Thread(solveMaze);
		thread.start();
		threads.add(thread);		
	}

	@Override
	/**
	 * returns a maze
	 * @param name maze name
	 * @return the maze
	 */
	public Maze3d getMaze(String name) {
		return mazes.get(name);
	}
	/**
	 * stops all tasks, closes files
	 */
	public void exit() {
		for (GenerateMazeRunnable task : generateMazeTasks) {
			task.terminate();
		}
		for (SolveMazeRunnable task : solveMazeTasks) {
			task.terminate();
		}
		try {
			saveFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			loadFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	/**
	 * saves the maze to a file
	 * @param name maze name
	 * @param fileName file name 
	 */
	public void saveMaze(String name, String fileName) {
		// save it to a file
		Maze3d maze = getMaze(name);
		
		try {
			saveFile = new MyCompressorOutputStream(
					new FileOutputStream(RESOURCESDIR + fileName));
			byte[] arr = maze.toByteArray();
			
			saveFile.write(arr.length);
			saveFile.write(arr);
			saveFile.flush();
			saveFile.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	@Override
	/**
	 * loads the maze from a file
	 * @param fileName file name
	 * @param name maze name
	 */
	public void loadMaze(String fileName, String name) {
		
		try {
			loadFile = new MyDecompressorInputStream(
				new FileInputStream(RESOURCESDIR + fileName));
			int size = loadFile.read();			
			byte b[]=new byte[size];
			loadFile.read(b);
			loadFile.close();	
			
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
	/**
	 * returns the solution for a maze
	 * @param name maze name
	 * @return the solution for a maze
	 */
	public Solution<Position> getSolution(String name) {
		return solutions.get(name);
	}

}

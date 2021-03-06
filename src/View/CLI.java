package View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import Controller.CommandBase;
/**
 * class defines the CLI 
 * @author Administrator
 *
 */
public class CLI {
	//reader
	private BufferedReader in;
	//writer
	private PrintWriter out;
	//command map
	private HashMap<String, CommandBase> commands;
	/**
	 * CLI c'tor
	 * @param in reader
	 * @param out writer
	 */
	public CLI(BufferedReader in, PrintWriter out) {
		this.in = in;
		this.out = out;		
	}
	/**
	 * prints the menu
	 */
	private void printMenu() {
		out.print("Choose command: (");
		for (String command : commands.keySet()) {
			out.print(command + ",");
		}
		out.println(")");
		out.flush();
	}
	/**
	 * starts the CLI as a thread
	 */
	public void start() {
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
				
					printMenu();
					try {
						//read command as whole line
						String commandLine = in.readLine();
						String arr[] = commandLine.split(" ");
						String command = arr[0];			
						
						if(!commands.containsKey(command)) {
							out.println("Command doesn't exist");
						}
						else {
							String[] args = null;
							if (arr.length > 1) {
								String commandArgs = commandLine.substring(
										commandLine.indexOf(" ") + 1);
								args = commandArgs.split(" ");							
							}
							CommandBase cmd = commands.get(command);
							try{
								cmd.doCommand(args);	
							}
							//had exception, show correct syntax
							catch (Exception e) {
								e.printStackTrace();
								out.println(command + " syntax incorrect");
								out.println(cmd.getSyntax());
							}
							if (command.equals("exit"))
								break;
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
				}
			}			
		});
		thread.start();		
	}
	/**
	 * setter for the commands
	 * @param commands command map
	 */
	public void setCommands(HashMap<String, CommandBase> commands) {
		this.commands = commands;
	}
}

package Controller;

import Model.Model;
import View.View;
/**
 * class that implements the controller interface
 * @author Administrator
 *
 */
public class MyController implements Controller {
	//the view
	private View view;
	//the model
	private Model model;
	//commands manager
	private CommandsManager commandsManager;
	
	/**
	 * Controller ctor
	 * @param view the view 
	 * @param model the model
	 */
	public MyController(View view, Model model) {
		this.view = view;
		this.model = model;
		
		commandsManager = new CommandsManager(model, view);
		view.setCommands(commandsManager.getCommandsMap());
	}
		
	@Override
	/**
	 * notifies that a maze is ready
	 * @param name maze name
	 */
	public void notifyMazeIsReady(String name) {
		view.notifyMazeIsReady(name);
	}
	/**
	 * notifies that a solution is ready
	 * @param name maze name
	 */
	@Override
	public void notifySolutionIsReady(String name) {
		view.notifySolutionIsReady(name);
	}
	
	@Override
	/**
	 * notifies that need to exit, close all open files etc
	 */
	public void notifyExit() {
		model.exit();
	}

}

package Controller;
/**
 * abstract class that defines what properties a command should have
 * @author Administrator
 *
 */
public abstract class CommandBase implements Command {
	
	protected String syntax = "";
	/**
	 * setter for the syntax
	 * @param syntax help text
	 */
	public void setSyntax(String syntax) {
		this.syntax = syntax;
	}
	/**
	 * getter for the syntax
	 * @return help text
	 */
	public String getSyntax() {
		return syntax;
	}

}

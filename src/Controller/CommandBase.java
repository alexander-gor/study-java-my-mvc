package Controller;

public abstract class CommandBase implements Command {
	
	protected String syntax = "";
	public void setSyntax(String syntax) {
		this.syntax = syntax;
	}
	public String getSyntax() {
		return syntax;
	}

}

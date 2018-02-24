package dkeep.logic;

public class Lever extends Entity{
	
	private boolean active;
	
	public Lever(int x_pos, int y_pos, char rep)
	{
		super(x_pos, y_pos, rep);
		active = false;
	}
}

package dkeep.logic;

public class Lever extends Entity
{	
	private boolean active;
	
	public Lever(Position pos, char rep)
	{
		super(pos, rep);
		active = false;
	}
}

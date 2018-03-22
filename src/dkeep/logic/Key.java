package dkeep.logic;

public class Key extends Entity
{	
	private Entity owner;
	
	public Key(Position pos, char rep)
	{
		super(pos, rep);
	}
	
	public Entity owner()
	{
		return owner;
	}
}
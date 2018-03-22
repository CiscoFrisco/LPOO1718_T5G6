package dkeep.logic;

public class Hero extends Entity
{
	private boolean onLever;
	private boolean hasKey;
	
	public Hero(Position pos, char rep)
	{
		super(pos, rep);
		onLever = false;
		hasKey = false;
	}
	
	public void setLever()
	{
		onLever = !onLever;
	}
	
	public boolean lever()
	{
		return onLever;
	}
	
	public boolean key()
	{
		return hasKey;
	}
	
	public void setKey()
	{
		hasKey = true;
	}
}

package dkeep.logic;

public class Club extends Entity 
{
	public boolean onKey;
	
	public Club(Position pos, char rep)
	{
		super(pos, rep);
		onKey = false;
	}
	
	public boolean key()
	{
		return onKey;
	}
	
	public void setKey()
	{
		onKey = !onKey;
	}
	
	public void resetKey()
	{
		onKey = false;
	}
}

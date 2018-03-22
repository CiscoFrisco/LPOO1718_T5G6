package dkeep.logic;

public class Ogre extends Entity
{
	public Club club;
	private boolean onKey;
	private boolean stunned;
	private int stunCounter;
	
	public Ogre(Position pos, char rep)
	{
		super(pos, rep);
		onKey = false;
		stunned = false;
		stunCounter = 0;
		club = new Club(pos, '*');
	}
	
	public Club club()
	{
		return club;
	}
	
	public boolean key()
	{
		return onKey;
	}
	
	public void setStun()
	{
		stunned = true;
		representation = '8';
	}
	
	public void unStun()
	{
		stunned = false;
		stunCounter=0;
		representation = 'O';
	}
	
	public boolean stunned()
	{
		return stunned;
	}
	
	public void setKey()
	{
		onKey = !onKey;
	}
	
	public boolean updateStun()
	{
		if(stunCounter<2)
		{
			stunCounter++;
			return true;
		}
		else
		{
			unStun();
			return false;
		}
	}
}

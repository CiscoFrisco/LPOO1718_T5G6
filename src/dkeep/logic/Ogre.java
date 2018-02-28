package dkeep.logic;

public class Ogre extends Entity{
	
	public Club club;
	private boolean onKey;
	private boolean stunned;
	private int stunCounter;
	
	public Ogre(int x_pos, int y_pos, char rep)
	{
		super(x_pos, y_pos, rep);
		onKey = false;
		stunned = false;
		stunCounter = 0;
		club = new Club(x_pos,y_pos, '*');
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
	
	public void makeMov(int x_pos, int y_pos)
	{
		this.x_pos = x_pos;
		this.y_pos = y_pos;
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

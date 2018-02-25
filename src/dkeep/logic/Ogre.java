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
		stunned = !stunned;
	}
	
	public boolean stunned()
	{
		return stunned;
	}
	
	public void setKey()
	{
		onKey = !onKey;
	}
}

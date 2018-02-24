package dkeep.logic;

public class Ogre extends Entity{
	
	public Club club;
	private boolean onKey;
	
	public Ogre(int x_pos, int y_pos, Club club, char rep)
	{
		super(x_pos, y_pos, rep);
		this.club = club;
		onKey = false;
	}
	
	public Club club()
	{
		return club;
	}
	
	public boolean key()
	{
		return onKey;
	}
	
	public void setKey()
	{
		onKey = !onKey;
	}
}

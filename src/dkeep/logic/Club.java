package dkeep.logic;

public class Club extends Entity {
	
	public boolean onKey;
	
	public Club(int x_pos, int y_pos, char rep)
	{
		super(x_pos, y_pos, rep);
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

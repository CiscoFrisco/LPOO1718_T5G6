package dkeep.logic;

public class Hero extends Entity{
	
	private boolean onLever;
	private boolean hasKey;
	
	public Hero(int x_pos, int y_pos, char rep)
	{
		super(x_pos, y_pos, rep);
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

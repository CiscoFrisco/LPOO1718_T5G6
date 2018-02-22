package dkeep.logic;

public abstract class Entity {

	protected int x_pos;
	protected int y_pos;

	public Entity(int x_pos, int y_pos)
	{
		this.x_pos = x_pos;
		this.y_pos = y_pos;
	}
	
	public int x_pos()
	{
		return x_pos;
	}
	
	public int y_pos()
	{
		return y_pos;
	}
	
	public void makeMov(int x_pos, int y_pos)
	{
		this.x_pos = x_pos;
		this.y_pos = y_pos;
	}

}

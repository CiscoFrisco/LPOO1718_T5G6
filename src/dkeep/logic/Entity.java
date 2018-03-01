package dkeep.logic;

public abstract class Entity {

	protected int x_pos;
	protected int y_pos;
	protected char representation;

	public Entity(int x_pos, int y_pos, char representation)
	{
		this.x_pos = x_pos;
		this.y_pos = y_pos;
		this.representation = representation;
	}
	
	public void setRepresentation(char r)
	{
		representation = r;
	}
	
	public int x_pos()
	{
		return x_pos;
	}
	
	public int y_pos()
	{
		return y_pos;
	}
	
	public char representation()
	{
		return representation;
	}
	
	public void makeMov(int x_pos, int y_pos)
	{
		this.x_pos = x_pos;
		this.y_pos = y_pos;
	}

}

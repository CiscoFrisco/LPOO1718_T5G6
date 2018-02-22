package dkeep.logic;

public class Key extends Entity{
	
	private Entity owner;
	
	public Key(int x_pos, int y_pos)
	{
		super(x_pos, y_pos);
	}
	
	public Entity owner()
	{
		return owner;
	}
	
	

}
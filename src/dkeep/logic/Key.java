package dkeep.logic;

public class Key extends Entity{
	
	private Entity owner;
	
	public Key(int x_pos, int y_pos, char rep)
	{
		super(x_pos, y_pos, rep);
	}
	
	public Entity owner()
	{
		return owner;
	}
	
	

}
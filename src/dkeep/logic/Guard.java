package dkeep.logic;

public class Guard extends Entity{

	protected char[] guard_route = {'a','s','s','s','s','a','a','a','a','a','a','s','d','d','d','d','d','d','d','w','w','w','w','w'};
	protected int guard_movement = 0;
	protected char representation = 'G';

	public Guard(int x_pos, int y_pos)
	{
		super(x_pos, y_pos);
	}
	
	public void setRepresentation(char r)
	{
		representation = r;
	}

	public void move()
	{
		if(guard_movement == guard_route.length - 1)
			guard_movement = 0;
		else
			guard_movement++;
	}

	public char getMove()
	{
		return guard_route[guard_movement];
	}
}

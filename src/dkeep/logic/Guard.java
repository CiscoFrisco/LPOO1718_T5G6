package dkeep.logic;

public class Guard extends Entity{

	private static char[] guard_route = {'a','s','s','s','s','a','a','a','a','a','a','s','d','d','d','d','d','d','d','w','w','w','w','w'};
	private static int guard_movement = 0;

	public Guard(int x_pos, int y_pos)
	{
		super(x_pos, y_pos);
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

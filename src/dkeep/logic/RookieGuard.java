package dkeep.logic;

public class RookieGuard extends Guard{
	
	public RookieGuard(int x_pos, int y_pos, char rep)
	{
		super(x_pos, y_pos, rep);
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
	
	public boolean status()
	{
		return false;
	}
}

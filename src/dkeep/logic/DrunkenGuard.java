package dkeep.logic;

public class DrunkenGuard extends Guard{

	boolean asleep = false;
	
	public DrunkenGuard(int x_pos, int y_pos)
	{
		super(x_pos, y_pos);
	}
	
	public void move()
	{
		if(!asleep)
		{
			if(guard_movement == guard_route.length - 1)
				guard_movement = 0;
			else
				guard_movement++;
		}
	}
}

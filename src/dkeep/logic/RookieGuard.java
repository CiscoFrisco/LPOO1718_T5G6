package dkeep.logic;

public class RookieGuard extends Guard
{	
	/**
	 * Class constructor.
	 * 
	 * @param pos the coordinates of this rookie guard on the dungeon map
	 * @param rep the representation of this rookie guard on a char based map
	 */
	public RookieGuard(Position pos, char rep)
	{
		super(pos, rep);
	}
	
	/**
	 * Increments the index of this guard's movement array.
	 */
	public void move()
	{
		if(guard_movement == guard_route.length - 1)
			guard_movement = 0;
		else
			guard_movement++;
	}
	
	/**
	 * Returns the current movement of this guard.
	 */
	public char getMove()
	{	
		return guard_route[guard_movement];
	}
}

package dkeep.logic;

public abstract class Guard extends Entity
{
	protected char[] guard_route = {'a','s','s','s','s','a','a','a','a','a','a','s','d','d','d','d','d','d','d','w','w','w','w','w'};
	protected int guard_movement = 0;

	/**
	 * Class constructor.
	 * 
	 * @param pos the coordinates of this guard on the dungeon map
	 * @param rep the representation of this guard on a char based map
	 */
	public Guard(Position pos, char rep)
	{
		super(pos,rep);
	}
	
	/**
	 * Gets the current index of this guard's movement array.
	 * 
	 * @return this guard's current movement.
	 */
	public int getMovement() 
	{
		return guard_movement;
	}
	
	/**
	 * Sets the current index of this guard's movement array.
	 * 
	 * @param mov the new index for the guard's current movement.
	 */
	public void setMovement(int mov) 
	{
		guard_movement = mov;
	}
	
	/**
	 * Receives a movement and reverses it. For example, and up ('w') movement will be reversed to
	 * a down ('s') movement.
	 * 
	 * @param mov the movement to be reversed
	 * @return the reversed movement
	 */
	public char reverseMovement(char mov)
	{
		switch(mov)
		{
		case 'w':
			mov = 's';
			break;
		case'd':
			mov = 'a';
			break;
		case 's':
			mov = 'w';
			break;
		case 'a':
			mov = 'd';
			break;
		default:
			break;
		}
		
		return mov;
	}
	
	public abstract void move();

	public abstract char getMove();
	
	/**
	 * Returns the current status of the guard, regarding if
	 * he is able to harm the hero or not.
	 * 
	 * @return the current status of the guard
	 */
	public boolean status()
	{
		return false;
	}
}

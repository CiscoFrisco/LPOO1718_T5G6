package dkeep.logic;

import java.util.Random;

public class SuspiciousGuard extends Guard 
{
	private boolean reverse;
	
	/**
	 * Class constructor.
	 * 
	 * @param pos the coordinates of this suspicious guard on the dungeon map
	 * @param rep the representation of this suspicious guard on a char based map
	 */
	public SuspiciousGuard(Position pos, char rep)
	{
		super(pos, rep);
		reverse = false;
	}

	/**
	 * According to the current direction of the guard's movement, updates its index on the movement array.
	 */
	public void move()
	{
		if(reverse)
		{
			if(guard_movement == 0)
				guard_movement = guard_route.length - 1;
			else
				guard_movement--;
		}
		else
		{
			if(guard_movement == guard_route.length - 1)
				guard_movement = 0;
			else
				guard_movement++;
		}
	}
	
	/**
	 * Checks in which way this guard is heading and returns its current movement.
	 * Depending on the direction, the movement on the array might be reversed, or not.
	 */
	public char getMove()
	{	
		setReverse();
		
		char mov = guard_route[guard_movement];
		
		if(!reverse)
			return mov;
		else
		{
			if(guard_movement == 0)
				mov = guard_route[guard_route.length - 1];
			else
				mov = guard_route[guard_movement - 1];
		}	
		
		mov = reverseMovement(mov);
		
		return mov;
	}
	
	/**
	 * Sets the current state of the reverse value, thus indicating whether the guard should reverse
	 * its movement or not.
	 */
	public void setReverse()
	{
		Random number = new Random();
		int generated = number.nextInt(11); //generate a number between 0 and 10

		//guard has 30 % chance of reversing his movement
		reverse = generated>=7 ? true : false;
	}
}

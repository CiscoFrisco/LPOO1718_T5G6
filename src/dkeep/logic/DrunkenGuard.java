package dkeep.logic;

import java.util.Random;

public class DrunkenGuard extends Guard
{
	private boolean asleep;
	private boolean reverse;

	/**
	 * Class constructor.
	 * 
	 * @param pos the coordinates of this drunken guard on the dungeon map
	 * @param rep the representation of this drunken guard on a char based map
	 */
	public DrunkenGuard(Position pos, char rep)
	{
		super(pos, rep);
		asleep = false;
		reverse = false;
	}
	
	/**
	 * Reverses its current state. If it's asleep, it wakes up, and vice-versa.
	 */
	public void setAsleep()
	{
		asleep = !asleep;
	}
	
	/**
	 * According to the current direction of the guard's movement, updates its index on the movement array.
	 * If it's asleep, it doesn't move.
	 */
	public void move()
	{
		if(!asleep)
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
	}
	
	/**
	 * Changes the current representation of this guard, symbolizing its state (awake or asleep).
	 */
	public void setRepresentation()
	{
		if(representation == 'G')
			representation = 'g';
		else
			representation = 'G';
	}

	/**
	 * Returns the current movement of this guard, according to its asleep and reverse state.
	 * If its asleep, then it returns an invalid movement.
	 * Else, similar to the suspicious guard, checks the direction of the movement and 
	 * acts accordingly.
	 */
	public char getMove()
	{	
		Random number = new Random();
		int generated = number.nextInt(11);
		char movement = 'u';

		//guard has 30 % chance of reversing its asleep state
		if(generated >= 7)
		{
			setAsleep();
			setRepresentation();

			if(!asleep)
			{
				generated = number.nextInt(2);				
				reverse = generated == 0 ? true : false;
			}
		}

		if(!asleep)
		{				
			movement = guard_route[guard_movement];

			if(!reverse)
				return movement;
			else
			{
				if(guard_movement == 0)
					movement = guard_route[guard_route.length - 1];
				else
					movement = guard_route[guard_movement - 1];
			}	

			movement = reverseMovement(movement);
		}

		return movement;
	}
	
	/**
	 * Returns the current asleep status of this guard.
	 */
	public boolean status()
	{
		return asleep;
	}
}

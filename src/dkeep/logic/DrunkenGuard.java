package dkeep.logic;

import java.util.Random;

public class DrunkenGuard extends Guard
{
	private boolean asleep;
	private boolean reverse;

	public DrunkenGuard(Position pos, char rep)
	{
		super(pos, rep);
		asleep = false;
		reverse = false;
	}

	public void setAsleep()
	{
		asleep = !asleep;
	}

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

	public void setRepresentation()
	{
		if(representation == 'G')
			representation = 'g';
		else
			representation = 'G';
	}

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

	public boolean status()
	{
		return asleep;
	}
}

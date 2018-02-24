package dkeep.logic;

import java.util.Random;

public class DrunkenGuard extends Guard{

	private boolean asleep = false;
	private boolean reverse = false;

	public DrunkenGuard(int x_pos, int y_pos, char rep)
	{
		super(x_pos, y_pos, rep);
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
		int generated = number.nextInt(11); //generate a number between 0 and 10
		char movement = 'u';

		//guard has 30 % chance of reversing its asleep state
		if(generated >= 7)
		{
			setAsleep();
			setRepresentation();

			if(!asleep)
			{
				generated = number.nextInt(2);

				switch(generated)
				{
				case 0:
					reverse = true;
					break;
				case 1:
					reverse = false;
					break;
				default:
					break;
				}

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

			switch(movement)
			{
			case 'w':
				movement = 's';
				break;
			case'd':
				movement = 'a';
				break;
			case 's':
				movement = 'w';
				break;
			case 'a':
				movement = 'd';
				break;
			default:
				break;
			}

		}

		return movement;
	}

	public boolean status()
	{
		return asleep;
	}
}

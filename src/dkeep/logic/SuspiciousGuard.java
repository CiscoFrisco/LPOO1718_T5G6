package dkeep.logic;

import java.util.Random;

public class SuspiciousGuard extends Guard 
{
	private boolean reverse;

	public SuspiciousGuard(Position pos, char rep)
	{
		super(pos, rep);
		reverse = false;
	}

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
		
		reverseMovement(mov);
		
		return mov;
	}

	public void setReverse()
	{
		Random number = new Random();
		int generated = number.nextInt(11); //generate a number between 0 and 10

		//guard has 30 % chance of reversing his movement
		reverse = generated>=7 ? true : false;
	}
	
	public boolean status()
	{
		return false;
	}
}

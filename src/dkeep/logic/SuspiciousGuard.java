package dkeep.logic;

import java.util.Random;

public class SuspiciousGuard extends Guard {

	protected char[] guard_route_reversed = {'s','d','w','w','w','w','d','d','d','d','d','d','w','a','a','a','a','a','a','a','s','s','s','s'};

	boolean reverse = false;

	public SuspiciousGuard(int x_pos, int y_pos, char rep)
	{
		super(x_pos, y_pos, rep);
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
		reverseMov();
		
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

	public void reverseMov()
	{
		Random number = new Random();
		int generated = number.nextInt(11); //generate a number between 0 and 10

		//guard has 30 % chance of reversing his movement
		if(generated >= 7)
			reverse = true;
		else
			reverse = false;
	}
}

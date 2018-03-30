package dkeep.logic;

public abstract class Guard extends Entity
{
	protected char[] guard_route = {'a','s','s','s','s','a','a','a','a','a','a','s','d','d','d','d','d','d','d','w','w','w','w','w'};
	protected int guard_movement = 0;

	public Guard(Position pos, char rep)
	{
		super(pos,rep);
	}
	
	public int getMovement() 
	{
		return guard_movement;
	}
	
	public void setMovement(int mov) 
	{
		guard_movement = mov;
	}
	
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
	
	public abstract boolean status();
}

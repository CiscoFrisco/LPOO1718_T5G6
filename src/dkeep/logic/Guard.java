package dkeep.logic;

public abstract class Guard extends Entity{

	protected char[] guard_route = {'a','s','s','s','s','a','a','a','a','a','a','s','d','d','d','d','d','d','d','w','w','w','w','w'};
	protected int guard_movement = 0;

	public Guard(int x_pos, int y_pos, char rep)
	{
		super(x_pos, y_pos,rep);
	}

	abstract void move();

	abstract char getMove();
}

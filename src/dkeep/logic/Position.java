package dkeep.logic;

public class Position implements Comparable<Position> 
{	
	private int i;
	private int j;
	
	/**
	 * Class constructor.
	 * 
	 * @param i the i component of this position
	 * @param j the j component of this position
	 */
	public Position(int i, int j)
	{
		setPosition(i, j);
	}
	
	/**
	 * Sets the new values for i and j.
	 * 
	 * @param i the new i component of this position
	 * @param j the new j component of this position
	 */
	public void setPosition(int i, int j)
	{
		this.i = i;
		this.j = j;
	}
	
	/**
	 * Checks whether or not the two positions to be compared are equal.
	 * 
	 * @param o1 An object referring a game position.
	 * @return returns a boolean indicating if the two positions are equal or not.
	 */
	public boolean equals(Object o1)
	{
		return o1 != null && o1 instanceof Position && i==((Position)o1).i && j==((Position)o1).j;
	}

	@Override
	public int compareTo(Position p2)
	{
		if(i==p2.i && j==p2.j)
			return 0;
		
		if(i<p2.i  ||  (i==p2.i && j<p2.j))
			return -1;
		else 
			return 1;
	}
	
	/**
	 * Gets the current i coordinate.
	 * 
	 * @return the current i coordinate
	 */
	public int getI()
	{
		return i;
	}
	
	/**
	 * Gets the current j coordinate.
	 * 
	 * @return the current j coordinate
	 */
	public int getJ()
	{
		return j;
	}
	
	/**
	 * Increments i or j according to the received movement (up, down, left or right).
	 * 
	 * @param movement a movement between consecutive cells
	 */
	public void movePosition(char movement)
	{
		switch(movement)
		{
		case 'a':
			j--;
			break;
		case 'w':
			i--;
			break;
		case 'd':
			j++;
			break;
		case 's':
			i++;
		default:
			break;
		}
	}
	
	/**
	 * Compares this position to a given one and checks if they are adjacent 
	 * on a char based map, which means they need to have consecutive i or j
	 * coordinates.
	 * 
	 * @param pos2 the position that is being compared to 
	 * @return true or false according to the predicate
	 */
	public boolean isAdjacent(Position pos2)
	{
		boolean cond1 = Math.abs(i-pos2.i)<=1;
		boolean cond2 = j == pos2.j;
		boolean cond3 = Math.abs(j-pos2.j)<=1;
		boolean cond4 = i == pos2.i;
		
		return  (cond1 && cond2) || (cond3 && cond4);
	}
	
	/**
	 * Clones the current position.
	 * 
	 * @return a new position representing the position cloned. 
	 */
	public Position clone()
	{
		return new Position(i,j);
	}
}

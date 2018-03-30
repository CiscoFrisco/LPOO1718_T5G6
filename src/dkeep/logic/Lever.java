package dkeep.logic;

public class Lever extends Entity
{	
	/**
	 * Class constructor.
	 * 
	 * @param pos the coordinates of the lever on the dungeon map
	 * @param rep the representation of the lever on a char based map
	 */
	public Lever(Position pos, char rep)
	{
		super(pos, rep);
	}
}

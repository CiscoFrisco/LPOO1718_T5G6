package dkeep.logic;

public class Key extends Entity
{		
	/**
	 * Class constructor.
	 * 
	 * @param pos the coordinates of the key on the keep map
	 * @param rep the representation of the key on a char based map
	 */
	public Key(Position pos, char rep)
	{
		super(pos, rep);
	}
}
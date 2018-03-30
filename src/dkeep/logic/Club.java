package dkeep.logic;

public class Club extends Entity 
{
	private boolean onKey;
	
	/**
	 * Class constructor.
	 * 
	 * @param pos the coordinates of the club on the keep map
	 * @param rep the representation of the club on a char based map
	 */
	public Club(Position pos, char rep)
	{
		super(pos, rep);
		onKey = false;
	}
	
	/**
	 * Returns a boolean value meaning if the club has or hasn't the key.
	 * @return the state of its relation with the key
	 */
	public boolean key()
	{
		return onKey;
	}
	
	/**
	 * Reverses the owner relation with the key.
	 */
	public void setKey()
	{
		onKey = !onKey;
	}
	
	/**
	 * Removes the owner relation with the key.
	 */
	public void resetKey()
	{
		onKey = false;
	}
}

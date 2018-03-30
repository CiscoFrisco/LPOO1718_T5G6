package dkeep.logic;

public class Hero extends Entity
{
	private boolean onLever;
	private boolean hasKey;
	
	/**
	 * Class constructor.
	 * 
	 * @param pos the coordinates of this hero on the map
	 * @param rep the representation of this hero on a char based map
	 */
	public Hero(Position pos, char rep)
	{
		super(pos, rep);
		onLever = false;
		hasKey = false;
	}
	
	/**
	 * Changes the state of this hero's relation with the lever.
	 */
	public void setLever()
	{
		onLever = !onLever;
	}
	
	/**
	 * Returns whether this hero is or isn't on the lever's position.
	 * 
	 * @return the current onLever state.
	 */
	public boolean lever()
	{
		return onLever;
	}
	
	/**
	 * Returns whether this hero has or hasn't the key.
	 * 
	 * @return the current hasKey state.
	 */
	public boolean key()
	{
		return hasKey;
	}
	
	/**
	 * Acknowledges that this hero is in possession of the key. 
	 */
	public void setKey()
	{
		hasKey = true;
	}
}

package dkeep.logic;

public class Ogre extends Entity
{
	public Club club;
	private boolean onKey;
	private boolean stunned;
	private int stunCounter;
	
	/**
	 * Class constructor.
	 * 
	 * @param pos the coordinates of this ogre on the keep map
	 * @param rep the representation of this ogre  on a char based map
	 */
	public Ogre(Position pos, char rep)
	{
		super(pos, rep);
		onKey = false;
		stunned = false;
		stunCounter = 0;
		club = new Club(pos, '*');
	}
	
	/**
	 * Returns this ogre's club.
	 * 
	 * @return this ogre's club.
	 */
	public Club club()
	{
		return club;
	}
	
	/**
	 * Returns a boolean value meaning if this ogre has or hasn't the key.
	 * @return the state of its relation with the key
	 */
	public boolean key()
	{
		return onKey;
	}
	
	/**
	 * Stuns this ogre, changing its representation.
	 */
	public void setStun()
	{
		stunned = true;
		representation = '8';
	}
	
	/**
	 * Removes this ogre's stun effect, changing its representation to its usual.
	 */
	public void unStun()
	{
		stunned = false;
		stunCounter=0;
		representation = 'O';
	}
	
	/**
	 * Returns this ogre's current stun state.
	 * 
	 * @return a boolean value indicating if this ogre is stunned or not.
	 */
	public boolean stunned()
	{
		return stunned;
	}
	
	/**
	 * Reverses the owner relation with the key.
	 */
	public void setKey()
	{
		onKey = !onKey;
	}
	
	/**
	 * Updates this ogre's stun state counter. At two, makes it go back to normal.
	 * 
	 * @return a boolean value indicating if this ogre is still stunned or not.
	 */
	public boolean updateStun()
	{
		if(stunCounter<2)
		{
			stunCounter++;
			return true;
		}
		else
		{
			unStun();
			return false;
		}
	}
}

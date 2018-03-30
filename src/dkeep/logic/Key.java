package dkeep.logic;

public class Key extends Entity
{	
	private Entity owner;
	
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
	
	/**
	 * Returns the current owner of the key, be it the hero, an ogre, a club, or no one.
	 * @return the owner of the key
	 */
	public Entity owner()
	{
		return owner;
	}
}
package dkeep.logic;

public abstract class Entity
{
	protected Position pos;
	protected char representation;

	/**
	 * Class constructor.
	 * 
	 * @param pos the coordinates of the entity on the map
	 * @param representation the representation of the entity on a char based map
	 */
	public Entity(Position pos, char representation)
	{
		this.pos = pos;
		this.representation = representation;
	}
	
	/**
	 * Changes the representation of the entity to a new value.
	 * 
	 * @param r the new representation of the entity
	 */
	public void setRepresentation(char r)
	{
		representation = r;
	}
	
	/**
	 * Returns the current position of the entity on a char based map.
	 * 
	 * @return the current position of the entity
	 */
	public Position pos()
	{
		return pos;
	}
	
	/**
	 * Returns the current representation of the entity on a char based map.
	 * 
	 * @return the current representation of the entity
	 */
	public char representation()
	{
		return representation;
	}
	
	/**
	 * Changes the position of the entity to a new value.
	 * 
	 * @param pos the new position of the entity
	 */
	public void setPos(Position pos)
	{
		this.pos = pos;
	}
}

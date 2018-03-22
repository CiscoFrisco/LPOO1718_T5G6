package dkeep.logic;

public abstract class Entity
{
	protected Position pos;
	protected char representation;

	public Entity(Position pos, char representation)
	{
		this.pos = pos;
		this.representation = representation;
	}
	
	public void setRepresentation(char r)
	{
		representation = r;
	}
	
	public Position pos()
	{
		return pos;
	}
	
	public char representation()
	{
		return representation;
	}
	
	public void setPos(Position pos)
	{
		this.pos = pos;
	}
}

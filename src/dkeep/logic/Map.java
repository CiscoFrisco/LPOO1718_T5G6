package dkeep.logic;

import java.util.ArrayList;

public class Map
{
	private char[][] layout;
	private int width;
	private int height;
	
	/**
	 * Class constructor.
	 * Receives a 2d char array and stores it along with its width and height.
	 * 
	 * @param layout a char based representation of the map
	 */
	public Map(char[][] layout)
	{
		this.layout = layout;
		width = layout[0].length;
		height = layout.length;
	}

	/**
	 * Returns this map's layout.
	 * 
	 * @return this map's layout
	 */
	public char[][] layout()
	{
		return layout;
	}

	/**
	 * Receives a position and returns the character that's on that position.
	 * 
	 * @param pos the position on the map
	 * @return the current character on that position
	 */
	public char pos(Position pos)
	{
		return layout[pos.getI()][pos.getJ()];
	}
	
	/**
	 * Changes the representation of a given position.
	 * 
	 * @param pos the position that is being changed
	 * @param rep the new representation
	 */
	public void setPos(Position pos, char rep)
	{
		layout[pos.getI()][pos.getJ()]=rep;
	}

	/**
	 * Internally changes the representation of the exit doors, in case the lever was activated.
	 * 
	 * @param lever indicates whether the lever is activated or not
	 * @param exitDoors the doors that are being updated
	 */
	public void updateDoors(boolean lever, ArrayList<ExitDoor> exitDoors)
	{
		for(ExitDoor exitDoor : exitDoors)
			updateDoor(lever, exitDoor);
	}
	
	/**
	 * Internally changes the representation of an exit door, in case the lever was activated.
	 * 
	 * @param lever indicates whether the lever is activated or not
	 * @param exitDoor the door that is being updated
	 */
	public void updateDoor(boolean lever, ExitDoor exitDoor)
	{
		char new_char;

		if(!lever)
			new_char = 'I';
		else
			new_char = 'S';

		exitDoor.setRepresentation(new_char);
		updatePos(exitDoor.pos, exitDoor);
	}

	/**
	 * Updates two adjacent positions on this map
	 * 
	 * @param pos the new entity's position
	 * @param entity has the old position
	 * @param pos1 the new representation for pos
	 * @param pos2 the new representation for the old position
	 */
	public void update(Position pos, Entity entity, char pos1, char pos2)
	{	
		layout[pos.getI()][pos.getJ()] = pos1;
		layout[entity.pos.getI()][entity.pos.getJ()] = pos2;
	}
	
	/**
	 * Updates a single position on this map.
	 * 
	 * @param pos the position to be updated
	 * @param entity has the representation to be put on the position
	 */
	public void updatePos(Position pos, Entity entity)
	{
		layout[pos.getI()][pos.getJ()] = entity.representation;
	}

	/**
	 * Returns a string version of this map ready to be printed to the terminal.
	 * 
	 * @return a string containing this map
	 */
	public String getPrintable()
	{
		String map="";

		for(int i = 0; i<layout.length;i++)
		{
			for(int j = 0; j< layout[i].length; j++)
				map+=layout[i][j] + " ";

			map+=System.lineSeparator();
		}

		return map;
	}

	/**
	 * Returns a string version of this map ready to be sent to a text file.
	 * 
	 * @return a string containing this map
	 */
	public String getWritable()
	{
		String map="";

		for(int i = 0; i<layout.length;i++)
			map+=new String(layout[i]) + System.lineSeparator();
		
		map = map.replace('*', ' ');

		return map;
	}

	/**
	 * Returns this map's width.
	 * 
	 * @return this map's width.
	 */
	public int width()
	{
		return width;
	}

	/**
	 * Returns this map's height.
	 * 
	 * @return this map's height.
	 */
	public int height()
	{
		return height;
	}
}


package dkeep.logic;

import java.util.ArrayList;

public class Map
{
	private char[][] layout;
	private int width;
	private int height;

	public Map(char[][] layout)
	{
		this.layout = layout;
		width= layout[0].length;
		height = layout.length;
	}

	public char[][] layout()
	{
		return layout;
	}

	public char pos(Position pos)
	{
		return layout[pos.getI()][pos.getJ()];
	}

	public void setPos(Position pos, char rep)
	{
		layout[pos.getI()][pos.getJ()]=rep;
	}

	public void updateDoors(boolean lever, ArrayList<ExitDoor> exitDoors)
	{
		for(ExitDoor exitDoor : exitDoors)
			updateDoor(lever, exitDoor);
	}

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

	public void update(Position pos, Entity entity, char pos1, char pos2)
	{	
		layout[pos.getI()][pos.getJ()] = pos1;
		layout[entity.pos.getI()][entity.pos.getJ()] = pos2;
	}

	public void updatePos(Position pos, Entity entity)
	{
		layout[pos.getI()][pos.getJ()] = entity.representation;
	}

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

	public String getMapLine(char[] line) 
	{
		String res  = "";
		for(int i = 0; i < line.length; i++)
			res += line[i] + " ";

		return res;
	}

	public String getWritable()
	{
		String map="";

		for(int i = 0; i<layout.length;i++)
			map+=new String(layout[i]) + System.lineSeparator();

		return map;
	}

	public int width()
	{
		return width;
	}

	public int height()
	{
		return height;
	}
}


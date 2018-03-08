package dkeep.logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringJoiner;

public class Map {

	private char[][] layout;
	
	public Map(char[][] layout)
	{
		this.layout = layout;
	}
	
	public char[][] layout()
	{
		return layout;
	}
	
	public char pos(int x_pos, int y_pos)
	{
		return layout[x_pos][y_pos];
	}
	
	public void setPos(int x_pos, int y_pos, char pos)
	{
		layout[x_pos][y_pos]=pos;
	}
	
	public void updateDoors(boolean lever, ArrayList<ExitDoor> exitDoors)
	{
		char new_char;

		if(!lever)
			new_char = 'I';
		else
			new_char = 'S';
		
		
		for(ExitDoor exitDoor : exitDoors)
		{	
			exitDoor.setRepresentation(new_char);
			updatePos(exitDoor.x_pos, exitDoor.y_pos, exitDoor);
		}
	}
	
	public void update(int new_x_pos, int new_y_pos, Entity entity, char pos1, char pos2)
	{	
		layout[new_x_pos][new_y_pos] = pos1;
		layout[entity.x_pos][entity.y_pos] = pos2;
	}
	
	public void updatePos(int x_pos, int y_pos, Entity entity)
	{
		layout[x_pos][y_pos] = entity.representation;
	}

	public void printMap()
	{
		for(int i = 0; i < layout.length; i++)
		{
			for(int j = 0; j < layout[i].length; j++)
			{
				System.out.print(layout[i][j] + " ");
			}

			System.out.print('\n');
		}
	}
	
	public String getMap()
	{
		String map="";
		
		for(int i = 0; i<layout.length;i++)
			map+=new String(layout[i]) + "\n";
		
		return map;
	}
}


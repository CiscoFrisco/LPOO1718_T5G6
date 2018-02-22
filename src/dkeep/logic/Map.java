package dkeep.logic;
public class Map {

	private char[][] layout;
	
	public Map(char[][] layout)
	{
		this.layout = layout;
	}
	
	public char pos(int x_pos, int y_pos)
	{
		return layout[x_pos][y_pos];
	}
	
	public void setPos(int x_pos, int y_pos, char pos)
	{
		layout[x_pos][y_pos]=pos;
	}
	
	public void updateDoors(boolean lever)
	{
		int door1_y_pos = 5;
		int door2_y_pos = 6;
		int door_x_pos = 0;
		char new_char;

		if(!lever)
			new_char = 'I';
		else
			new_char = 'S';

		layout[door1_y_pos][door_x_pos] = new_char;
		layout[door2_y_pos][door_x_pos] = new_char;
	}
	
	public void update(int new_x_pos, int new_y_pos, Entity entity, char pos1, char pos2)
	{	
		layout[new_x_pos][new_y_pos] = pos1;
		layout[entity.x_pos][entity.y_pos] = pos2;
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
}


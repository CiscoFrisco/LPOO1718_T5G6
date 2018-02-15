


public class Map {

	private char[][] structure = {{'X','X','X','X','X','X','X','X','X','X'} , 
			{'X','H',' ',' ','I',' ','X',' ','G','X'} , 
			{'X','X','X',' ','X','X','X',' ',' ','X'} , 
			{'X',' ','I',' ','I',' ','X',' ',' ','X'} , 
			{'X','X','X',' ','X','X','X',' ',' ','X'} , 
			{'I',' ',' ',' ',' ',' ',' ',' ',' ','X'} , 
			{'I',' ',' ',' ',' ',' ',' ',' ',' ','X'} , 
			{'X','X','X',' ','X','X','X','X',' ','X'} , 
			{'X',' ','I',' ','I',' ','X','k',' ','X'} , 
			{'X','X','X','X','X','X','X','X','X','X'}};
	private int hero_x_pos = 1;
	private int hero_y_pos = 1;
	private int guard_x_pos = 1;
	private int guard_y_pos = 8;
	private boolean lever = false;
	private boolean escaped = false;



	public void updateDoors()
	{
		int door1_y_pos = 5;
		int door2_y_pos = 6;
		int door_x_pos = 0;
		char new_char;

		if(!lever)
		{
			lever = true;
			new_char = 'S';
		}

		else
		{
			lever = false;
			new_char = 'I';
		}

		structure[door1_y_pos][door_x_pos] = new_char;
		structure[door2_y_pos][door_x_pos] = new_char;
	}

	public boolean escaped()
	{
		if(escaped)
		{
			System.out.println("Congratulations, you escaped!");
			return true;
		}

		return false;
	}

	public boolean checkGuard()
	{
		if((Math.abs(hero_x_pos-guard_x_pos)==1 && hero_y_pos == guard_y_pos) || (Math.abs(hero_y_pos-guard_y_pos)==1 && hero_x_pos == guard_x_pos))
			return true;
		return false;
	}

	public boolean checkCell(int x_pos, int y_pos)
	{
		if(structure[x_pos][y_pos] == 'X' || structure[x_pos][y_pos] == 'I')
			return false;

		if(structure[x_pos][y_pos] == 'k')
		{
			updateDoors();
			printMap();
			return false;
		}

		if(structure[x_pos][y_pos] == 'S' && (x_pos == 0 || y_pos == 0))
		{
			escaped = true;
			return false;
		}

		return true;
	}

	public void updatePos(char entity, int x_pos, int y_pos)
	{
		switch(entity)
		{
		case 'H':
			hero_x_pos = x_pos;
			hero_y_pos = y_pos;
			break;
		case 'G':
			guard_x_pos = x_pos;
			guard_y_pos = y_pos;
		}
	}

	public boolean updateMap(char movement, char entity)
	{
		int x_pos = -1, y_pos = -1;

		switch(entity)
		{
		case 'H':
			x_pos = hero_x_pos;
			y_pos = hero_y_pos;
			break;
		case 'G':
			x_pos = guard_x_pos;
			y_pos = guard_y_pos;
		}

		switch(movement)
		{
		case 'w':
			if(checkCell(x_pos - 1,y_pos))
			{
				structure[x_pos][y_pos] = ' ';
				structure[x_pos - 1][y_pos] = entity;
				updatePos(entity,x_pos - 1,y_pos);
				return true;
			}
			break;
		case 'a':
			if(checkCell(x_pos,y_pos - 1))
			{
				structure[x_pos][y_pos] = ' ';
				structure[x_pos][y_pos - 1] = entity;
				updatePos(entity,x_pos,y_pos - 1);
				return true;
			}
			break;
		case 's':
			if(checkCell(x_pos + 1,y_pos))
			{
				structure[x_pos][y_pos] = ' ';
				structure[x_pos + 1][y_pos] = entity;
				updatePos(entity,x_pos + 1,y_pos);
				return true;
			}
			break;
		case 'd':
			if(checkCell(x_pos,y_pos + 1))
			{
				structure[x_pos][y_pos] = ' ';
				structure[x_pos][y_pos + 1] = entity;
				updatePos(entity,x_pos,y_pos + 1);	
				return true;
			}
			break;
		default:
			break;
		}

		return false;
	}

	public void printMap()
	{
		for(int i = 0; i < structure.length; i++)
		{
			for(int j = 0; j < structure[i].length; j++)
			{
				System.out.print(structure[i][j] + " ");
			}

			System.out.print('\n');
		}
	}
}

public class Map {

	private char[][] layout;
	private int hero_x_pos;
	private int hero_y_pos;
	private int guard_x_pos;
	private int guard_y_pos;
	private int lever_x_pos;
	private int lever_y_pos;
	private int key_x_pos;
	private int key_y_pos;
	public int club_x_pos;
	public int club_y_pos;
	private boolean lever = false;
	private boolean escaped = false;
	public boolean hasKey = false;

	public Map(char[][] layout, int h_x_pos,  int h_y_pos,  int g_x_pos,  int g_y_pos, int l_x_pos, int l_y_pos, int k_x_pos, int k_y_pos)
	{
		this.layout = layout;
		hero_x_pos = h_x_pos;
		hero_y_pos = h_y_pos;
		guard_x_pos = g_x_pos;
		guard_y_pos = g_y_pos;
		lever_x_pos = l_x_pos;
		lever_y_pos = l_y_pos;
		key_x_pos = k_x_pos;
		key_y_pos = k_y_pos;
	}



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

		layout[door1_y_pos][door_x_pos] = new_char;
		layout[door2_y_pos][door_x_pos] = new_char;
	}

	public boolean escaped()
	{
		if(escaped)
		{
			System.out.println("Congratulations, you escaped!");
			escaped = false;
			return true;
		}

		return false;
	}

	public boolean checkGuard()
	{
		if((Math.abs(hero_x_pos-guard_x_pos)<=1 && hero_y_pos == guard_y_pos) || (Math.abs(hero_y_pos-guard_y_pos)<=1 && hero_x_pos == guard_x_pos))
			return true;
		else if (hero_x_pos == guard_x_pos && hero_y_pos == guard_y_pos)
			return true;
		return false;
	}

	public boolean checkClub()
	{
		if((Math.abs(hero_x_pos-club_x_pos)<=1 && hero_y_pos == club_y_pos) || (Math.abs(hero_y_pos-club_y_pos)<=1 && hero_x_pos == club_x_pos))
			return true;
		else if (hero_x_pos == club_x_pos && hero_y_pos == club_y_pos)
			return true;
		return false;
	}

	public boolean checkCell(char entity, int x_pos, int y_pos, int level)
	{
		if(layout[x_pos][y_pos] == 'X' || layout[x_pos][y_pos] == 'I')
			return false;

		if(layout[x_pos][y_pos] == 'k')
		{	
			if(level == 1)
				updateDoors();

			if(level == 2 && entity == 'H')
				hasKey = true;

			return true;
		}

		if(layout[x_pos][y_pos] == 'S' && (x_pos == 0 || y_pos == 0))
		{
			escaped = true;
			return true;
		}

		return true;
	}

	public void updatePos(char entity, int x_pos, int y_pos)
	{
		if(entity == 'H' || entity == 'K')
		{
			hero_x_pos = x_pos;
			hero_y_pos = y_pos;
		}
		else if(entity == 'O' || entity == 'G')
		{
			guard_x_pos = x_pos;
			guard_y_pos = y_pos;
		}
		else if (entity == '*')
		{
			club_x_pos = x_pos;
			club_y_pos = y_pos;
		}
	}

	public boolean updateMap(char movement, char entity, int level)
	{
		int x_pos = -1, y_pos = -1;

		if(entity == 'H' || entity == 'K')
		{
			x_pos = hero_x_pos;
			y_pos = hero_y_pos;
		}
		else if(entity == 'O' || entity == 'G' || entity == '*')
		{
			x_pos = guard_x_pos;
			y_pos = guard_y_pos;
		}

		switch(movement)
		{
		case 'w':
			if(checkCell(entity, x_pos - 1,y_pos, level))
			{
				if(entity == 'H' && layout[x_pos - 1][y_pos] == 'k')
				{
					layout[x_pos][y_pos] = ' ';
					layout[x_pos - 1][y_pos] = 'K';
					updatePos(entity,x_pos - 1,y_pos);
				}
				else if(entity == 'O' && (layout[x_pos - 1][y_pos] == 'k' ||layout[x_pos - 1][y_pos] == '$') && level == 2)
				{
					layout[x_pos][y_pos] = ' ';
					layout[x_pos - 1][y_pos] = '$';
					updatePos(entity,x_pos - 1,y_pos);
				}
				else if(entity == '*' && level == 2)
				{					
					if(club_x_pos == guard_x_pos && club_y_pos == guard_y_pos)
					{
						if(layout[guard_x_pos][guard_y_pos] == '$')
							layout[club_x_pos][club_y_pos] = '$';
						else
							layout[club_x_pos][club_y_pos] = 'O';

						if(layout[guard_x_pos - 1][guard_y_pos]  == 'k')
							layout[guard_x_pos - 1][guard_y_pos] = '$';
						else
							layout[guard_x_pos - 1][guard_y_pos] = '*';
					}
					else
					{
						if(layout[club_x_pos][club_y_pos] == '$')
						{
							layout[club_x_pos][club_y_pos] = '$';
							layout[guard_x_pos - 1][guard_y_pos] = '*';
						}
						else if(layout[guard_x_pos - 1][guard_y_pos]  == 'k')
						{
							layout[guard_x_pos - 1][guard_y_pos] = '$';
							layout[club_x_pos][club_y_pos] = ' ';
						}
						else if(layout[club_x_pos][club_y_pos] == '*')
						{	
							layout[guard_x_pos - 1][guard_y_pos] = '*';
							layout[club_x_pos][club_y_pos] = ' ';
						}
					}

					updatePos(entity, guard_x_pos - 1, guard_y_pos);
				}
				else
				{
					layout[x_pos][y_pos] = ' ';
					layout[x_pos - 1][y_pos] = entity;
					updatePos(entity,x_pos - 1,y_pos);
				}
				return true;
			}
			break;
		case 'a':
			if(checkCell(entity, x_pos,y_pos - 1, level))
			{
				if(entity == 'O' && layout[x_pos][y_pos] == '$' && level == 2)
				{
					layout[x_pos][y_pos] = 'k';
					layout[x_pos][y_pos -1] = entity;
					updatePos(entity,x_pos,y_pos - 1);	
				}
				else if(entity == '*' && level == 2)
				{					
					if(club_x_pos == guard_x_pos && club_y_pos == guard_y_pos)
					{
						if(layout[guard_x_pos][guard_y_pos] == '$')
							layout[club_x_pos][club_y_pos] = '$';
						else
							layout[club_x_pos][club_y_pos] = 'O';

						if(layout[guard_x_pos][guard_y_pos - 1]  == 'k')
							layout[guard_x_pos][guard_y_pos - 1] = '$';
						else
							layout[guard_x_pos][guard_y_pos - 1] = '*';
					}
					else
					{
						if(layout[club_x_pos][club_y_pos] == '$')
						{
							layout[club_x_pos][club_y_pos] = 'k';
							layout[guard_x_pos][guard_y_pos - 1] = '*';
						}
						else if(layout[guard_x_pos][guard_y_pos - 1]  == 'k')
						{
							layout[club_x_pos][club_y_pos] = ' ';
							layout[guard_x_pos][guard_y_pos - 1] = '$';
						}
						else if(layout[club_x_pos][club_y_pos] == '*')
						{
							layout[club_x_pos][club_y_pos] = ' ';
							layout[guard_x_pos][guard_y_pos - 1] = '*';
						}
					}

					updatePos(entity, guard_x_pos, guard_y_pos - 1);
				}
				else
				{
					layout[x_pos][y_pos] = ' ';
					layout[x_pos][y_pos-1] = entity;
					updatePos(entity,x_pos,y_pos-1);
				}

				return true;
			}
			else if(layout[x_pos][y_pos-1] == 'I' && entity == 'K')
			{
				layout[x_pos][y_pos-1] = 'S';
				return true;
			}
			break;
		case 's':
			if(checkCell(entity, x_pos + 1,y_pos, level))
			{	
				if(entity == 'O' && layout[x_pos][y_pos] == '$' && level == 2)
				{
					layout[x_pos][y_pos] = 'k';
					layout[x_pos+1][y_pos] = entity;
					updatePos(entity,x_pos+1,y_pos);	
				}
				else if(entity == '*' && level == 2)
				{					
					if(club_x_pos == guard_x_pos && club_y_pos == guard_y_pos)
					{
						if(layout[guard_x_pos][guard_y_pos] == '$')
							layout[club_x_pos][club_y_pos] = '$';
						else
							layout[club_x_pos][club_y_pos] = 'O';

						if(layout[guard_x_pos + 1][guard_y_pos]  == 'k')
							layout[guard_x_pos + 1][guard_y_pos] = '$';
						else
							layout[guard_x_pos + 1][guard_y_pos] = '*';
					}
					else
					{
						if(layout[club_x_pos][club_y_pos] == '$')
						{
							layout[club_x_pos][club_y_pos] = 'k';
							layout[guard_x_pos + 1][guard_y_pos] = '*';
						}
						else if(layout[guard_x_pos + 1][guard_y_pos]  == 'k')
						{
							layout[club_x_pos][club_y_pos] = ' ';
							layout[guard_x_pos][guard_y_pos - 1] = '$';
						}
						else if(layout[club_x_pos][club_y_pos] == '*')
						{
							layout[club_x_pos][club_y_pos] = ' ';
							layout[guard_x_pos + 1][guard_y_pos] = '*';
						}
					}

					updatePos(entity, guard_x_pos + 1, guard_y_pos);
				}
				else
				{
					layout[x_pos][y_pos] = ' ';
					layout[x_pos+1][y_pos] = entity;
					updatePos(entity,x_pos + 1,y_pos);
				}
				return true;
			}
			break;
		case 'd':
			if(checkCell(entity, x_pos,y_pos + 1, level))
			{
				if(x_pos == lever_x_pos && y_pos == lever_y_pos)
				{
					layout[x_pos][y_pos] = 'k';
					layout[x_pos][y_pos + 1] = entity;
					updatePos(entity,x_pos,y_pos + 1);	
				}
				else if(entity == 'O' && (layout[x_pos][y_pos + 1] == 'k' || layout[x_pos][y_pos + 1] == '$') && level == 2)
				{
					layout[x_pos][y_pos] = ' ';
					layout[x_pos][y_pos + 1] = '$';
					updatePos(entity,x_pos,y_pos + 1);	
				}
				else if(entity == 'H' && layout[x_pos][y_pos + 1] == 'k')
				{
					layout[x_pos][y_pos] = ' ';
					layout[x_pos][y_pos + 1] = 'K';
					updatePos(entity,x_pos,y_pos + 1);	
				}
				else if(entity == '*' && level ==2)
				{					
					if(club_x_pos == guard_x_pos && club_y_pos == guard_y_pos)
					{
						if(layout[guard_x_pos][guard_y_pos] == '$')
							layout[club_x_pos][club_y_pos] = '$';
						else
							layout[club_x_pos][club_y_pos] = 'O';

						if(layout[guard_x_pos][guard_y_pos + 1]  == 'k')
							layout[guard_x_pos][guard_y_pos + 1] = '$';
						else
							layout[guard_x_pos][guard_y_pos + 1] = '*';

					}
					else
					{
						if(layout[club_x_pos][club_y_pos] == '$')
						{
							layout[club_x_pos][club_y_pos] = 'k';
							layout[guard_x_pos][guard_y_pos + 1] = '*';
						}
						else if(layout[guard_x_pos][guard_y_pos + 1]  == 'k')
						{
							layout[guard_x_pos][guard_y_pos + 1] = '$';
							layout[club_x_pos][club_y_pos] = ' ';
						}
						else if(layout[club_x_pos][club_y_pos] == '*')
						{	
							layout[guard_x_pos][guard_y_pos + 1] = '*';
							layout[club_x_pos][club_y_pos] = ' ';
						}
					}

					updatePos(entity, guard_x_pos, guard_y_pos + 1);
				}
				else
				{
					layout[x_pos][y_pos] = ' ';
					layout[x_pos][y_pos + 1] = entity;
					updatePos(entity,x_pos,y_pos + 1);
				}

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


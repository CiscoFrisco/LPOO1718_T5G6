package dkeep.logic;

import java.util.Random;

public abstract class Level 
{
	protected Map map;
	protected Hero hero;
	protected boolean escaped;
	protected boolean gameOver;
	
	public Map map()
	{
		return map;
	}
	
	public Hero hero()
	{
		return hero;
	}
	
	public boolean escaped()
	{
		return escaped;
	}
	
	public boolean gameOver()
	{
		return gameOver;
	}
	
	public char generateMovement()
	{
		Random number = new Random();
		int generated = number.nextInt(4);
		char movement = 'u';

		switch(generated)
		{
		case 0:
			movement = 'a';
			break;
		case 1:
			movement = 'w';
			break;
		case 2:
			movement = 'd';
			break;
		case 3:
			movement = 's';
			break;
		default:
			break;
		}

		return movement;
	}
	
	public boolean issueMov(char movement, Entity entity)
	{
		int x_pos = entity.x_pos, y_pos = entity.y_pos;


		if(checkCell(x_pos, y_pos, entity))
		{
			if(map.pos(x_pos,y_pos) != 'I')
			{
				update(x_pos, y_pos, entity);
				entity.makeMov(x_pos,y_pos);
			}
			else
				update(x_pos, y_pos, entity);

			return true;
		}

		return false;
	}

	public boolean checkCell(int x_pos, int y_pos, Entity entity)
	{
		if(map.pos(x_pos, y_pos) == 'X' || (map.pos(x_pos, y_pos) == 'I' && getLevel() == 1))
			return false;

		if(entity.equals(hero))
		{
			switch(getLevel())
			{
			case 1:
				if(map.pos(x_pos, y_pos) == 'k')
					hero.setLever();	
				else if(map.pos(x_pos, y_pos) == 'S')
					escaped = true;
				break;			
			case 2:
				if(map.pos(x_pos, y_pos) == 'k') //se o heroi se mover para a chave
					hero.setKey();
				else if(map.pos(x_pos, y_pos) == 'S') //se o heroi se mover para a porta de saida
					escaped = true;
				else if(map.pos(x_pos, y_pos) == 'I' && !hero.key()) // se o heroi se tentar mover para a porta de saida mas esta está fechada 
					return false;
				else if( map.pos(x_pos, y_pos) == '8' || map.pos(x_pos, y_pos) == 'O' || map.pos(x_pos, y_pos) == '$') // se o heroi se tentar mover para uma posição ja ocupada por um ogre
					return false;
				break;
			default:
				break;
			}
		}
		else if(entity.getClass() == Ogre.class)
		{
			for(Ogre ogre : ogres)
			{
				if(ogre.equals(entity))
				{
					if(map.pos(x_pos, y_pos) == 'k' || ogre.key())
						ogre.setKey();
					else if(map.pos(x_pos, y_pos) == 'S' || map.pos(x_pos, y_pos) == 'I' || map.pos(x_pos, y_pos) == map.pos(hero.x_pos, hero.y_pos))
						return false;

					break;
				}
			}
		}
		else if(entity.equals(guard))
		{
			return true;
		}
		else
		{
			for(Ogre ogre : ogres)
			{
				if(ogre.club.equals(entity))
				{
					if(map.pos(x_pos, y_pos) == 'k' || ogre.club().key())
						ogre.club().setKey();
					else if(map.pos(x_pos, y_pos) == 'S' || map.pos(x_pos, y_pos) == 'I' || map.pos(x_pos, y_pos) == 'O')
						return false;

					break;
				}
			}
		}

		return true;
	}
	
	public void update(int x_pos, int y_pos, Entity entity)
	{	
		char pos1 = ' ', pos2= ' ';

		if(entity.equals(hero))
		{
			if(hero.lever() && hero.x_pos != lever.x_pos && hero.y_pos != lever.y_pos)
			{
				pos1 = entity.representation;
				pos2 = ' ';
				map.updateDoors(hero.lever(), exitDoors);
			}
			else if(getLevel() == 1 && hero.x_pos == lever.x_pos && hero.y_pos == lever.y_pos)
			{
				pos1 = entity.representation;
				pos2 = lever.representation;
			}
			else if(hero.key())
			{
				entity.setRepresentation('K');
				if(map.pos(x_pos, y_pos)=='I')
				{
					pos1 = 'S';
					pos2 = entity.representation;
					map.updateDoors(hero.key(), exitDoors);
				}
				else {
					pos1 = entity.representation;
					pos2 = ' ';
				}

			}
			else
			{
				pos1 = entity.representation;
				pos2 = ' ';
			}
		}
		else if(entity.getClass() == Ogre.class)
		{
			for(Ogre ogre : ogres)
			{
				if(ogre.equals(entity))
				{
					if(ogre.key() && !hero.key())
					{
						entity.setRepresentation('$');
						pos1 = entity.representation;
						pos2 = ' ';
					}
					else if(ogre.x_pos == key.x_pos && ogre.y_pos == key.y_pos && !hero.key())
					{
						entity.setRepresentation('O');
						pos1 = entity.representation;
						pos2 = key.representation;
					}
					else {
						pos1 = entity.representation;
						pos2 = ' ';
					}

					break;
				}
			}
		}
		else if(entity.equals(guard))
		{
			pos1 = entity.representation;
			pos2 = ' ';
		}
		else
		{

			for(Ogre ogre : ogres)
			{
				if(ogre.club.equals(entity))
				{
					if(ogre.club().key() && !hero.key())
					{
						entity.setRepresentation('$');
						pos1 = entity.representation;
						pos2 = ogre.representation;
					}
					else if(ogre.x_pos == key.x_pos && ogre.y_pos == key.y_pos && !hero.key())
					{
						entity.setRepresentation('*');
						pos1 = entity.representation;
						ogre.setRepresentation('$');
						pos2 = ogre.representation;
					}
					else
					{
						pos1 = entity.representation;
						pos2 = ogre.representation;
					}

					break;
				}
			}
		}

		map.update(x_pos, y_pos, entity, pos1, pos2);
	}



}

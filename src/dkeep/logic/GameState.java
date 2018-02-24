package dkeep.logic;

import java.util.Random;

public class GameState {

	private Map map;
	private Hero hero;
	private Guard guard;
	private Ogre ogre;
	private Lever lever;
	private Key key;
	private int level;
	private boolean game_over;
	private boolean escaped;

	public GameState(Map map, Hero hero, Guard guard, Ogre ogre, Lever lever, Key key)
	{
		this.map = map;
		this.hero = hero;
		this.guard = guard;
		this.ogre = ogre;
		this.lever = lever;
		this.key = key;
		game_over = false;
		escaped = false;
		level = 1;
	}

	public void changeLevel(Map map, Hero hero)
	{
		level++;
		this.map = map;
		this.hero = hero;
		escaped = false;
		game_over = false;
	}

	public boolean issueMov(char movement, Entity entity)
	{
		int x_pos = entity.x_pos, y_pos = entity.y_pos;

		switch(movement)
		{
		case 'a':
			y_pos--;
			break;
		case 'w':
			x_pos--;
			break;
		case 'd':
			y_pos++;
			break;
		case 's':
			x_pos++;
			break;
		default:
			break;
		}

		if(checkCell(x_pos, y_pos, entity))
		{
			if(map.pos(x_pos,y_pos) != 'I')
			{
				update(x_pos, y_pos, entity);
				entity.makeMov(x_pos,y_pos);
			}
			else
			{
				update(x_pos, y_pos, entity);
			}

			return true;
		}

		return false;
	}


	public void update(int x_pos, int y_pos, Entity entity)
	{	
		char pos1 = ' ', pos2= ' ';

		if(entity.equals(hero))
		{
			if(hero.lever())
			{
				pos1 = entity.representation;
				pos2 = ' ';
				map.updateDoors(hero.lever());
			}
			else if(hero.x_pos == lever.x_pos && hero.y_pos == lever.y_pos && level == 1)
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
		else if(entity.equals(ogre))
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
		}
		else if(entity.equals(guard))
		{
			pos1 = entity.representation;
			pos2 = ' ';
		}
		else
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
		}

		map.update(x_pos, y_pos, entity, pos1, pos2);
	}

	public boolean gameOver()
	{
		return game_over;
	}

	public Hero hero()
	{
		return hero;
	}

	public boolean escaped()
	{
		return escaped;
	}

	public void printMap()
	{
		map.printMap();
	}


	public boolean checkGuard()
	{
		boolean cond1 = Math.abs(hero.x_pos()-guard.x_pos())<=1;
		boolean cond2 = hero.y_pos() == guard.y_pos();
		boolean cond3 = Math.abs(hero.y_pos()-guard.y_pos())<=1;
		boolean cond4 = hero.x_pos() == guard.x_pos();

		if(( cond1 && cond2) || (cond3 && cond4))
			return true;
		else if (hero.x_pos() == guard.x_pos() && hero.y_pos() == guard.y_pos())
			return true;
		return false;
	}

	public boolean checkClub()
	{
		boolean cond1 = Math.abs(hero.x_pos()-ogre.club().x_pos())<=1;
		boolean cond2 = hero.y_pos() == ogre.club().y_pos();
		boolean cond3 = Math.abs(hero.y_pos()-ogre.club().y_pos())<=1;
		boolean cond4 = hero.x_pos() == ogre.club().x_pos();

		if((cond1 && cond2) || (cond3 && cond4))
			return true;
		else if (hero.x_pos() == ogre.club().x_pos() && hero.y_pos() == ogre.club().y_pos())
			return true;
		return false;
	}

	public boolean checkOgre()
	{
		boolean cond1 = Math.abs(hero.x_pos()-ogre.x_pos())<=1;
		boolean cond2 = hero.y_pos() == ogre.y_pos();
		boolean cond3 = Math.abs(hero.y_pos()-ogre.y_pos())<=1;
		boolean cond4 = hero.x_pos() == ogre.x_pos();

		if((cond1 && cond2) || (cond3 && cond4))
			return true;
		else if (hero.x_pos() == ogre.x_pos() && hero.y_pos() == ogre.y_pos())
			return true;
		return false;
	}

	public boolean checkCell(int x_pos, int y_pos, Entity entity)
	{
		if(map.pos(x_pos, y_pos) == 'X' || (map.pos(x_pos, y_pos) == 'I' && level == 1))
			return false;

		if(entity.equals(hero))
		{
			switch(level)
			{
			case 1:
				if(map.pos(x_pos, y_pos) == 'k' || hero.lever())
					hero.setLever();	
				else if(map.pos(x_pos, y_pos) == 'S')
					escaped = true;
				break;			
			case 2:
				if(map.pos(x_pos, y_pos) == 'k')
					hero.setKey();
				else if(map.pos(x_pos, y_pos) == 'S')
					escaped = true;
				break;
			default:
				break;
			}
		}
		else if(entity.equals(ogre))
		{
			if(map.pos(x_pos, y_pos) == 'k' || ogre.key())
				ogre.setKey();
			else if(map.pos(x_pos, y_pos) == 'S' || map.pos(x_pos, y_pos) == 'I')
				return false;
		}
		else
		{
			if(map.pos(x_pos, y_pos) == 'k' || ogre.club().key())
				ogre.club().setKey();
			else if(map.pos(x_pos, y_pos) == 'S' || map.pos(x_pos, y_pos) == 'I')
				return false;
		}

		return true;
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

	public void setClub()
	{
		char clubSwing = generateMovement();

		while(!issueMov(clubSwing, ogre.club))
		{clubSwing = generateMovement();};
	}

	public boolean moveGuard()
	{
		if(level == 1)
		{
			issueMov(guard.getMove(),guard);
			guard.move();

			return true;
		}
		else
		{
			char guardMovement = generateMovement();

			if(issueMov(guardMovement, ogre))
			{	
				if(map.pos(ogre.club().x_pos,ogre.club().y_pos) == '$')
				{
					map.setPos(ogre.club().x_pos,ogre.club().y_pos,'k');
					ogre.club().setRepresentation('*');
				}
				else
					map.setPos(ogre.club().x_pos,ogre.club().y_pos,' ');

				ogre.club().x_pos = ogre.x_pos;
				ogre.club().y_pos = ogre.y_pos;



				return true;
			}

			return false;
		}
	}
}

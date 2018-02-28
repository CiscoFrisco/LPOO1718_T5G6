package dkeep.logic;

import java.util.Random;
import java.util.ArrayList;

public class GameState {

	private Map map;
	private Hero hero;
	private Guard guard;
	private Lever lever;
	private Key key;
	private int level;
	private boolean game_over;
	private boolean escaped;
	private ArrayList<Ogre> ogres;

	public GameState(Map map, Hero hero, Guard guard, Lever lever, Key key)
	{
		this.map = map;
		this.hero = hero;
		this.guard = guard;
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
		generateOgres();
		armOgres();
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
		case 'u':
			map.updatePos(entity.x_pos, entity.y_pos, entity);
			return true;
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

		if((( cond1 && cond2) || (cond3 && cond4)) && !guard.status())
			return true;
		else if (hero.x_pos() == guard.x_pos() && hero.y_pos() == guard.y_pos())
			return true;
		return false;
	}

	public boolean checkClub()
	{
		for(Ogre ogre: ogres)
		{
			boolean cond1 = Math.abs(hero.x_pos()-ogre.club().x_pos())<=1;
			boolean cond2 = hero.y_pos() == ogre.club().y_pos();
			boolean cond3 = Math.abs(hero.y_pos()-ogre.club().y_pos())<=1;
			boolean cond4 = hero.x_pos() == ogre.club().x_pos();

			if((cond1 && cond2) || (cond3 && cond4))
				return true;
			else if (hero.x_pos() == ogre.club().x_pos() && hero.y_pos() == ogre.club().y_pos())
				return true;
		}

		return false;
	}

	public boolean checkOgre(Ogre ogre)
	{		
		boolean cond1 = Math.abs(hero.x_pos()-ogre.x_pos())<=1;
		boolean cond2 = hero.y_pos() == ogre.y_pos();
		boolean cond3 = Math.abs(hero.y_pos()-ogre.y_pos())<=1;
		boolean cond4 = hero.x_pos() == ogre.x_pos();

		if((cond1 && cond2) || (cond3 && cond4))
			return true;
		else 
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
				else if(map.pos(x_pos, y_pos) == 'I' && !hero.key())
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
					else if(map.pos(x_pos, y_pos) == 'S' || map.pos(x_pos, y_pos) == 'I')
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

	public void setClub(Ogre ogre)
	{
		char clubSwing = generateMovement();

		while(!issueMov(clubSwing, ogre.club))
		{clubSwing = generateMovement();};
	}

	public void generateOgres()
	{
		Random random = new Random();
		int number = random.nextInt(3) + 1;
		int x_pos = 10, y_pos = 0;
		ogres = new ArrayList<Ogre>(number);
		System.out.println(number);
		for(int i = 0; i<number;i++)
		{	
			//Ogres nao podem comecar perto do hero
			while(x_pos>=5 && y_pos<=5)
			{
				x_pos = random.nextInt(8) + 1;
				y_pos = random.nextInt(8) + 1;
			}

			Ogre ogre = new Ogre(x_pos, y_pos,'O');
			ogres.add(ogre);

			x_pos = 10;
			y_pos = 0;
		}

		for(Ogre ogre : ogres)
		{
			map.updatePos(ogre.x_pos, ogre.y_pos, ogre);
		}
	}

	//da clubs aos ogres antes do jogo comecar
	public void armOgres()
	{
		for(Ogre ogre : ogres)
		{
			setClub(ogre);
		}
	}

	public void moveOgres()
	{
		for(Ogre ogre : ogres)
		{
			if(!ogre.stunned())
			{	
				char movement = generateMovement();

				issueMov(movement, ogre);

				if(map.pos(ogre.club().x_pos,ogre.club().y_pos) == '$')
				{
					map.setPos(ogre.club().x_pos,ogre.club().y_pos,'k');
					ogre.club().setRepresentation('*');
				}
				else
					map.setPos(ogre.club().x_pos,ogre.club().y_pos,' ');

				ogre.club().resetKey();
				ogre.club().x_pos = ogre.x_pos;
				ogre.club().y_pos = ogre.y_pos;	
			}
		}
	}

	public void checkStun()
	{
		for(Ogre ogre : ogres)
		{
			if(ogre.stunned())
			{
				if(!ogre.updateStun())
					map.updatePos(ogre.x_pos, ogre.y_pos, ogre);
			}
			else if(checkOgre(ogre))
			{
				ogre.setStun();
				map.updatePos(ogre.x_pos, ogre.y_pos, ogre);
			}
		}
	}

	public boolean moveGuard()
	{
		issueMov(guard.getMove(),guard);
		guard.move();

		return true;
	}
}

package dkeep.logic;

import java.util.ArrayList;

public class Dungeon extends Level
{
	private ArrayList<ExitDoor> exitDoors;
	private Guard guard;
	private Lever lever;

	public Dungeon(Map map, String guardType)
	{
		this.map = map;
		initDungeon(guardType);
		escaped = false;
		gameOver = false;
	}

	public Guard guard()
	{
		return guard;
	}

	public Lever lever()
	{
		return lever;
	}

	private void initDungeon(String guardType) 
	{
		exitDoors = new ArrayList<ExitDoor>();

		for(int i = 0;i<map.layout().length;i++)
			for(int j = 0;j<map.layout()[i].length;j++)
			{
				char rep = map.layout()[i][j];
				if(rep == 'H')
					hero = new Hero(new Position(i,j),'H');
				else if(rep == 'G')
					guard = generateGuard(new Position(i,j), guardType);
				else if(rep == 'k')
					lever = new Lever(new Position(i,j),'k');
				else if(rep == 'I' && (i == 0 || j == 0))
					exitDoors.add(new ExitDoor(new Position(i,j),'I'));
			}
	}

	public Guard generateGuard(Position pos, String guardType)
	{
		Guard guard = null;

		switch(guardType)
		{
		case "Rookie":
			guard = new RookieGuard(pos,'G');
			break;
		case "Drunken":
			guard = new DrunkenGuard(pos,'G');
			break;
		case "Suspicious":
			guard = new SuspiciousGuard(pos,'G');
			break;
		default:
			break;	
		}

		return guard;
	}

	public void moveGuard()
	{
		issueMov(guard.getMove(),guard);
		guard.move();
	}

	public boolean checkGuard()
	{
		if((hero.pos.isAdjacent(guard.pos) && !guard.status()) || hero.pos().equals(guard.pos()))
		{
			gameOver = true;
			return true;
		}

		return false;
	}


	public boolean checkCell(Position pos, Entity entity)
	{
		if(map.pos(pos) == 'X' || map.pos(pos) == 'I')
			return false;

		if(entity.equals(hero))
		{
			if(map.pos(pos) == 'k')
				hero.setLever();	
			else if(map.pos(pos) == 'S')
				escaped = true;
		}

		return true;
	}

	public void update(Position pos, Entity entity)
	{	
		char pos1 = ' ', pos2= ' ';

		if(entity.equals(hero))
		{
			if(hero.lever() && !hero.pos.equals(lever.pos))
			{
				pos1 = entity.representation;
				pos2 = ' ';
				map.updateDoors(hero.lever(), exitDoors);
			}
			else if(hero.pos.equals(lever.pos))
			{
				pos1 = entity.representation;
				pos2 = lever.representation;
			}
			else
			{
				pos1 = entity.representation;
				pos2 = ' ';
			}
		}
		else if(entity.equals(guard))
		{
			pos1 = entity.representation;
			pos2 = ' ';
		}

		map.update(pos, entity, pos1, pos2);
	}

	public void moveEnemy()
	{
		moveGuard();
	}

	@Override
	public boolean checkEnemy() 
	{
		return checkGuard();
	}
}

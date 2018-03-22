package dkeep.logic;

import java.util.ArrayList;

public class Dungeon extends Level
{
	private ArrayList<ExitDoor> exitDoors;
	private Guard guard;
	private Lever lever;
	
	public Dungeon(Map map, String guardType)
	{
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
					guard = generateGuard(new Position(i,j),guardType);
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
	
	public boolean moveGuard()
	{
		issueMov(guard.getMove(),guard);
		guard.move();

		return true;
	}
}

package dkeep.logic;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Dungeon extends Level
{
	private ArrayList<ExitDoor> exitDoors;
	private Guard guard;
	private Lever lever;
	private String guardType;
	
	/**
	 * Class constructor.
	 * 
	 * @param map this level's initial map
	 * @param guardType the personality of the guard (rookie, drunken or suspicious)
	 */
	public Dungeon(Map map, String guardType)
	{
		id = 1;
		this.map = map;
		this.guardType = guardType;
		initDungeon(guardType);
		escaped = false;
		gameOver = false;
		movements = new ArrayList<Character>();
		movements.add('w');	movements.add('a'); movements.add('s'); movements.add('d');
	}
	
	/**
	 * Returns this level's guard.
	 * 
	 * @return this level's guard
	 */
	public Guard guard()
	{
		return guard;
	}

	/**
	 * Returns this level's lever.
	 * 
	 * @return this level's lever
	 */
	public Lever lever()
	{
		return lever;
	}
	
	/**
	 * Initializes this dungeon, by sweeping the map and checking for the existence of
	 * its entities.
	 * 
	 * @param guardType the personality of the guard (rookie, drunken or suspicious)
	 */
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
	
	/**
	 * Generates this level's guard according to its specified type.
	 * 
	 * @param pos the position of the guard
	 * @param guardType the personality of the guard (rookie, drunken or suspicious)
	 * @return Returns the guard generated
	 */
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
	
	/**
	 * Performs a movement of the guard, also updating its internal values.
	 */
	public void moveGuard()
	{
		issueMov(guard.getMove(),guard);
		guard.move();
	}
	
	/**
	 * Checks if the guard is capable of harming the hero, thus ending the game.
	 * Not only checks if they're on consecutive positions, but also makes sure that the guard
	 * is not asleep.
	 * 
	 * @return a boolean value indicating if the game is over or not.
	 */
	public boolean checkGuard()
	{
		if((hero.pos.isAdjacent(guard.pos) && !guard.status()) || hero.pos().equals(guard.pos()))
		{
			gameOver = true;
			return true;
		}

		return false;
	}
	
	/**
	 * Checks if the cell the hero is trying to move is a valid cell.To be valid it needs to be a free cell (' ').
	 * It also checks if the hero is moving to the lever or to an open door updating the game status accordingly.
	 * 
	 * @return a boolean value indicating if the cell is valid or not.
	 */
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
	
	/**
	 * Updates the position of a certain character.
	 * 
	 * @param pos the position the character is moving.
	 * @param entity the character to be updated.
	 */
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

	/**
	 * Moves the enemy in this level (Guard).
	 */
	public void moveEnemy()
	{
		moveGuard();
	}

	/**
	 * Calls functions checkGuard() to see if the guard is capable of harming the hero.
	 * @return a boolean value indicating if the guard harmed the hero or not.
	 */
	@Override
	public boolean checkEnemy() 
	{
		return checkGuard();
	}

	/**
	 * Saves the relevant game information to a file.
	 * @param file to be saved.
	 */
	@Override
	public void saveToFile(File file) 
	{
		String writable = map.getWritable();
		int width = map.width();
		int height = map.height();

		try 
		{
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			writer.write(id + System.lineSeparator());
			writer.write(height + System.lineSeparator());
			writer.write(width  + System.lineSeparator());
			writer.write(guardType + System.lineSeparator());
			writer.write(guard.getMovement() + System.lineSeparator());

			writer.write(writable);

			writer.close();

		} catch (IOException e) 
		{
			e.printStackTrace();
		}
		
	}

	/**
	 * Reads from a file and creates a new Dungeon level.
	 * Has the ability to create the level at any state: the beginning, after the hero has moved,
	 * before/after the lever has been activated.
	 * 
	 * @param file the file to be read
	 * @return a new Dungeon level
	 */
	public static Dungeon readFromFile(File file)
	{
		char[][] reading = null;
		String guardPersonality = "";
		int index_mov = 0;
		try 
		{
			BufferedReader reader = new BufferedReader(new FileReader(file));
			reader.readLine();
			int height = Integer.parseInt(reader.readLine());
			int width = Integer.parseInt(reader.readLine());
			guardPersonality = reader.readLine();
			index_mov = Integer.parseInt(reader.readLine());

			reading = new char[height][width];

			for(int i=0;i<height; i++)
			{
				String line = reader.readLine();
				reading[i] = line.toCharArray();
			}

			reader.close();
		} catch (NumberFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Map newMap = new Map(reading);
		
		Dungeon dungeon = new Dungeon(newMap,guardPersonality);
		dungeon.guard().setMovement(index_mov);
		return dungeon;
	}
	
	
}

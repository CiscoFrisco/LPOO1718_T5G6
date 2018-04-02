package dkeep.logic;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Keep extends Level
{
	private ArrayList<Ogre> ogres;
	private Key key;
	private ExitDoor exitDoor;
	
	/**
	 * Class constructor.
	 * 
	 * @param map this level's current map
	 * @param numberOfOgres the number of ogres to be generated
	 */
	public Keep(Map map, int numberOfOgres)
	{
		id = 2;
		this.map = map;
		ogres = new ArrayList<Ogre>();
		initKeep();
		generateOgres(numberOfOgres);
		movements = new ArrayList<Character>();
		movements.add('w');	movements.add('a'); movements.add('s'); movements.add('d');
	}

	/**
	 * Class constructor
	 * 
	 * @param map this level's current map, already contaning the ogres
	 */
	public Keep(Map map)
	{
		id = 2;
		this.map = map;
		ogres = new ArrayList<Ogre>();
		initKeep();
		movements = new ArrayList<Character>();
		movements.add('w');	movements.add('a'); movements.add('s'); movements.add('d');
	}
	
	/**
	 * Returns this level's key.
	 * 
	 * @return this level's key
	 */
	public Key key()
	{
		return key;
	}

	/**
	 * Returns this level's exit doors.
	 * 
	 * @return this level's exit doors.
	 */
	public ExitDoor exitDoor()
	{
		return exitDoor;
	}
	
	/**
	 * Returns this level's ogres.
	 * 
	 * @return this level's ogres.
	 */
	public ArrayList<Ogre> ogres()
	{
		return ogres;
	}

	/**
	 * Initializes this keep, by sweeping the map and checking for the existence of
	 * its entities.
	 */
	private void initKeep() 
	{
		for(int i = 0;i<map.layout().length;i++)
			for(int j = 0;j<map.layout()[i].length;j++)
			{
				char rep = map.layout()[i][j];
				if(rep == 'A' || rep== 'K')
					hero = new Hero(new Position(i,j),rep);
				else if(rep == 'k')
					key = new Key(new Position(i,j),rep);
				else if(rep == 'I')
					exitDoor = new ExitDoor(new Position(i,j),rep);
				else if(rep == 'O' || rep == '8')
					ogres.add(new Ogre(new Position(i,j),rep));
			}

		if(key == null)
			key = new Key(new Position(0, 0),'k');

		if(hero.representation == 'K')
			hero.setKey();

		for(Ogre ogre : ogres)
			if(ogre.representation() == '8')
				ogre.setStun();
		
	}

	/**
	 * Generates this level's ogres, by creating random positions and checking if they're not
	 * close to the hero.
	 * 
	 * @param numberOfOgres this level's number of ogres
	 */
	public void generateOgres(int numberOfOgres)
	{
		Random random = new Random();
		int limit = map.layout().length/2;
		int x_pos = limit + 1, y_pos = 0;

		for(int i = 0; i<numberOfOgres;i++)
		{	
			while(x_pos>=limit && y_pos<=limit)
			{
				x_pos = random.nextInt(limit+1) + 1;
				y_pos = random.nextInt(limit+1) + 1;
			}

			Ogre ogre = new Ogre(new Position(x_pos,y_pos),'O');
			ogres.add(ogre);

			x_pos = limit+1;
			y_pos = 0;
		}

		for(Ogre ogre : ogres)
			map.updatePos(ogre.pos, ogre);
	}

	/**
	 * Updates the clubs for all ogres.
	 */
	public void armOgres()
	{
		for(Ogre ogre : ogres)
			setClub(ogre);
	}

	/**
	 * Puts the ogre's club on a new random position, adjacent to him.
	 * 
	 * @param ogre the ogre to be updated
	 */
	public void setClub(Ogre ogre)
	{
		char clubSwing = generateMovement();

		while(!issueMov(clubSwing, ogre.club))
			clubSwing = generateMovement();
	}
	
	/**
	 * Updates the ogre's old club position.
	 * 
	 * @param ogre the ogre to be updated
	 */
	public void updateOgre(Ogre ogre)
	{
		Position pos = ogre.club().pos();

		if(map.pos(pos) == '$')
		{
			map.setPos(pos,'k');
			ogre.club().setRepresentation('*');
		}
		else
			map.setPos(pos,' ');

		ogre.club().resetKey();
		ogre.club.setPos(ogre.pos);
	}
	
	/**
	 * Moves all of this level's ogres, if they're not stunned.
	 */
	public void moveOgres()
	{
		for(Ogre ogre : ogres)
		{
			if(!ogre.stunned())
			{	
				char movement = generateMovement();

				issueMov(movement, ogre);
			}

			updateOgre(ogre);
		}
	}
	
	/**
	 * Checks if the ogre is adjacent to the hero.
	 * 
	 * @param ogre the ogre that is being checked
	 * @return a boolean indicating if the ogre is or isn't adjacent to the hero
	 */
	public boolean checkOgre(Ogre ogre)
	{		
		return hero.pos.isAdjacent(ogre.pos);
	}

	/**
	 * Checks if any club is adjacent to the hero, thus ending the game.
	 * 
	 * @return a boolean indicating if any club is adjacent to the hero.
	 */
	public boolean checkClub()
	{
		for(Ogre ogre: ogres)
			if(hero.pos.isAdjacent(ogre.club.pos))
			{
				gameOver = true;
				return true;
			}

		return false;
	}
	
	/**
	 * Checks is any ogre is adjacent to the hero, thus stunning him.
	 */
	public void checkStun()
	{
		for(Ogre ogre : ogres)
		{
			if(ogre.stunned() && !ogre.updateStun())
				map.updatePos(ogre.pos, ogre);
			else if(checkOgre(ogre))
			{
				ogre.setStun();
				map.updatePos(ogre.pos, ogre);
			}
		}
	}

	/**
	 * Checks if the cell the hero is trying to move is a valid cell.To be valid it needs to be a free cell (' ').
	 * It also checks if the hero is moving to the lever or to an open door updating the game status accordingly.
	 * 
	 * @return a boolean value indicating if the cell is valid or not.
	 */
	public boolean checkCell(Position pos, Entity entity)
	{
		if(map.pos(pos) == 'X')
			return false;

		if(entity.equals(hero))
		{
			if(!checkHero(pos))
				return false;
		}
		else if(entity.getClass() == Ogre.class)
		{
			if(!checkOgres(pos, entity))
				return false;
		}
		else
		{
			if(!checkClubs(pos, entity))
				return false;
		}

		return true;
	}

	private boolean checkClubs(Position pos, Entity entity) {
		for(Ogre ogre : ogres)
			if(ogre.club.equals(entity))
			{
				if(map.pos(pos) == 'k' || ogre.club().key())
					ogre.club().setKey();
				else if(map.pos(pos) == 'S' || map.pos(pos) == 'I' || map.pos(pos) == 'O')
					return false;

				break;
			}

		return true;
	}

	private boolean checkOgres(Position pos, Entity entity) {
		for(Ogre ogre : ogres)
			if(ogre.equals(entity))
			{
				if(map.pos(pos) == 'k' || ogre.key())
					ogre.setKey();
				else if(map.pos(pos) == 'S' || map.pos(pos) == 'I' || map.pos(pos) == map.pos(hero.pos))
					return false;

				break;
			}

		return true;
	}

	private boolean checkHero(Position pos) {

		if(map.pos(pos) == 'k') 
			hero.setKey();
		else if(map.pos(pos) == 'S')
			escaped = true;
		else if(map.pos(pos) == 'I' && !hero.key())
			return false;
		else if( map.pos(pos) == '8' || map.pos(pos) == 'O' || map.pos(pos) == '$')
			return false;

		return true;
	}

	private void updateHero(Position pos, Entity entity)
	{
		char pos1 = ' ', pos2= ' ';

		if(hero.key())
		{
			entity.setRepresentation('K');
			if(map.pos(pos)=='I')
			{
				pos1 = 'S';
				pos2 = entity.representation;
				map.updateDoor(hero.key(), exitDoor);
			}
			else 
			{
				pos1 = entity.representation;
				pos2 = ' ';
			}

		}
		else
		{
			pos1 = entity.representation;
			pos2 = ' ';
		}

		map.update(pos, entity, pos1, pos2);
	}

	/**
	 * Updates the position of a certain character.
	 * 
	 * @param pos the position the character is moving.
	 * @param entity the character to be updated.
	 */
	public void update(Position pos, Entity entity)
	{	
		if(entity.equals(hero))
			updateHero(pos, entity);
		else if(entity.getClass() == Ogre.class)
			updateOgres(pos, entity);
		else
			updateClubs(pos, entity);

	}

	private void updateClubs(Position pos, Entity entity) {

		char pos1 = ' ', pos2= ' ';

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
				else if(ogre.pos.equals(key.pos) && !hero.key())
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

		map.update(pos, entity, pos1, pos2);
	}
	
	private void updateOgres(Position pos, Entity entity) {

		char pos1 = ' ', pos2= ' ';

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
				else if(ogre.pos.equals(key.pos) && !hero.key())
				{
					entity.setRepresentation('O');
					pos1 = entity.representation;
					pos2 = key.representation;
				}
				else 
				{
					pos1 = entity.representation;
					pos2 = ' ';
				}

				break;
			}
		}
		map.update(pos, entity, pos1, pos2);
	}
	
	/**
	 * Updates the position of every ogre as well as each of the clubs.
	 * It also checks whether or not the hero stunned any ogre.
	 */
	public void moveEnemy()
	{
		moveOgres();
		armOgres();
		checkStun();
	}

	/**
	 * Checks whether or not any club has hit the hero.
	 * @return returns a boolean indicating if the hero was hit by a club.
	 */
	@Override
	public boolean checkEnemy() 
	{
		return checkClub();
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
			writer.write(writable);

			writer.close();

		} catch (IOException e) 
		{
			e.printStackTrace();
		}

	}
	
	/**
	 * Reads from a file and creates a new Keep level.
	 * Has the ability to create the level at any state: the beginning, after the hero has moved,
	 * before/after the lever has been activated.
	 * 
	 * @param file the file to be read
	 * @return a new Keep level
	 */
	public static Keep readFromFile(File file)
	{
		char[][] reading = null;

		try 
		{
			BufferedReader reader = new BufferedReader(new FileReader(file));
			reader.readLine();
			int height = Integer.parseInt(reader.readLine());
			int width = Integer.parseInt(reader.readLine());

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

		Keep keep = new Keep(newMap);
		return keep;
	}
}

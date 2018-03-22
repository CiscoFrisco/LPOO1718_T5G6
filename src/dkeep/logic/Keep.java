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

	public Keep(Map map, int numberOfOgres)
	{
		id = 2;
		this.map = map;
		initKeep();
		generateOgres(numberOfOgres);
		movements = new ArrayList<Character>();
		movements.add('w');	movements.add('a'); movements.add('s'); movements.add('d');
	}
	
	public Keep(Map map)
	{
		id = 2;
		this.map = map;
		initKeep();
		movements = new ArrayList<Character>();
		movements.add('w');	movements.add('a'); movements.add('s'); movements.add('d');
	}

	public Key key()
	{
		return key;
	}

	public ExitDoor exitDoor()
	{
		return exitDoor;
	}

	private void initKeep() 
	{
		for(int i = 0;i<map.layout().length;i++)
			for(int j = 0;j<map.layout()[i].length;j++)
			{
				char rep = map.layout()[i][j];
				if(rep == 'A')
					hero = new Hero(new Position(i,j),'A');
				else if(rep == 'k')
					key = new Key(new Position(i,j),'k');
				else if(rep == 'I')
					exitDoor = new ExitDoor(new Position(i,j),'I');
				else if(rep == 'O' || rep == '8')
					ogres.add(new Ogre(new Position(i,j),rep));
			}	

	}

	public void generateOgres(int numberOfOgres)
	{
		Random random = new Random();
		int limit = map.layout().length/2;
		int x_pos = limit + 1, y_pos = 0;
		ogres = new ArrayList<Ogre>(numberOfOgres);

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

	public void armOgres()
	{
		for(Ogre ogre : ogres)
			setClub(ogre);
	}

	public void setClub(Ogre ogre)
	{
		char clubSwing = generateMovement();

		while(!issueMov(clubSwing, ogre.club))
			clubSwing = generateMovement();
	}

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

	public boolean checkOgre(Ogre ogre)
	{		
		if(hero.pos.isAdjacent(ogre.pos))
			return true;

		return false;
	}

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


	public  boolean checkCell(Position pos, Entity entity)
	{
		if(map.pos(pos) == 'X')
			return false;

		if(entity.equals(hero))
		{
			if(map.pos(pos) == 'k') 
				hero.setKey();
			else if(map.pos(pos) == 'S')
				escaped = true;
			else if(map.pos(pos) == 'I' && !hero.key())
				return false;
			else if( map.pos(pos) == '8' || map.pos(pos) == 'O' || map.pos(pos) == '$')
				return false;
		}
		else if(entity.getClass() == Ogre.class)
		{
			for(Ogre ogre : ogres)
				if(ogre.equals(entity))
				{
					if(map.pos(pos) == 'k' || ogre.key())
						ogre.setKey();
					else if(map.pos(pos) == 'S' || map.pos(pos) == 'I' || map.pos(pos) == map.pos(hero.pos))
						return false;

					break;
				}
		}
		else
		{
			for(Ogre ogre : ogres)
				if(ogre.club.equals(entity))
				{
					if(map.pos(pos) == 'k' || ogre.club().key())
						ogre.club().setKey();
					else if(map.pos(pos) == 'S' || map.pos(pos) == 'I' || map.pos(pos) == 'O')
						return false;

					break;
				}
		}

		return true;
	}

	public void update(Position pos, Entity entity)
	{	
		char pos1 = ' ', pos2= ' ';

		if(entity.equals(hero))
		{
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
		}

		map.update(pos, entity, pos1, pos2);
	}

	public void moveEnemy()
	{
		moveOgres();
		armOgres();
		checkStun();
	}

	@Override
	public boolean checkEnemy() 
	{
		return checkClub();
	}

	@Override
	public void saveToFile(File file) 
	{
		String writable = map.getWritable();
		int width = map.width();
		int height = map.height();

		try 
		{
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			writer.write(height + System.lineSeparator());
			writer.write(width  + System.lineSeparator());
			writer.write(2 + System.lineSeparator());

			writer.write(writable);

			writer.close();

		} catch (IOException e) 
		{
			e.printStackTrace();
		}
		
	}

	@Override
	public Keep readFromFile(File file)
	{
		char[][] reading = null;

		try 
		{
			BufferedReader reader = new BufferedReader(new FileReader(file));
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

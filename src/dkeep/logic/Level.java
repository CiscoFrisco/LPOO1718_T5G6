package dkeep.logic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public abstract class Level 
{
	protected int id;
	protected Map map;
	protected Hero hero;
	protected boolean escaped;
	protected boolean gameOver;
	protected ArrayList<Character> movements;
	
	/**
	 * Returns the current level map.
	 * 
	 * @return the current map
	 */
	public Map map()
	{
		return map;
	}
	
	/**
	 * Returns the level's hero.
	 * 
	 * @return the hero
	 */
	public Hero hero()
	{
		return hero;
	}
	
	/**
	 * Returns whether the hero has or hasn't escaped the level.
	 * 
	 * @return a boolean indicating if the hero has escaped
	 */
	public boolean escaped()
	{
		return escaped;
	}
	
	/**
	 * Returns whether the hero has or hasn't lost the level.
	 * 
	 * @return a boolean indicating if the hero has lost
	 */
	public boolean gameOver()
	{
		return gameOver;
	}
	
	/**
	 * Generates a random movement.
	 * 
	 * @return a random movement
	 */
	public char generateMovement()
	{
		Random number = new Random();
		int generated = number.nextInt(4);

		return movements.get(generated);
	}
	
	/**
	 * Issues a movement to a given entity, by checking if it's valid, and if it's possible to make.
	 * 
	 * @param movement the movement that is being issued
	 * @param entity the entity that is going to me moved
	 * @return a boolean value indicating whether the movement was performed or not
	 */
	public boolean issueMov(char movement, Entity entity)
	{
		Position pos = entity.pos().clone();
		
		if(movement!='u')
			pos.movePosition(movement);
		else
		{
			map.updatePos(entity.pos, entity);
			return true;
		}
		
		if(checkCell(pos, entity))
		{	
			if(map.pos(pos) != 'I')
			{
				update(pos, entity);
				entity.setPos(pos);
			}
			else
				update(pos, entity);		
			
			return true;
		}

		return false;
	}

	public abstract boolean checkCell(Position pos, Entity entity);
	
	public abstract void update(Position pos, Entity entity);
	
	public abstract void moveEnemy();

	public abstract boolean checkEnemy();
	
	public abstract void saveToFile(File file);
	
	/**
	 * Reads a text file and according to its content, creates a new level, Dungeon or Keep.
	 * 
	 * @param file the file to be read
	 * @return a new level 
	 */
	public static Level readFromFile(File file)
	{
		BufferedReader reader;
		int levelID = 0;
		try {
			reader = new BufferedReader(new FileReader(file));
			levelID = Integer.parseInt(reader.readLine());
			reader.close();
		} catch (NumberFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return levelID == 1 ? Dungeon.readFromFile(file) : Keep.readFromFile(file);
	}
	
	/**
	 * Checks if the level's door are open.
	 * 
	 * @return a boolean indicating if the level's doors are open.
	 */
	public boolean checkDoors()
	{
		for(int i = 0; i<map.height();i++)
			for(int j=0; j<map.width();j++)
			{
				if(map.pos(new Position(i,j))=='S')
					return true;
			}
		
		return false;
	}
}

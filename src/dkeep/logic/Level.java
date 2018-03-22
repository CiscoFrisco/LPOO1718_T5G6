package dkeep.logic;

import java.io.File;
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

		return movements.get(generated);
	}
	
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
	
	public abstract Level readFromFile(File file);
	
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

package dkeep.logic;

public class GameState 
{
	private Level level;

	public GameState(Level level)
	{
		this.level = level;
	}
	
	public void setLevel(Level level)
	{
		this.level = level;
	}

	public Level level() 
	{
		return level;
	}
	
	public boolean escaped()
	{
		return level.escaped;
	}

	public void moveEnemy() 
	{
		level.moveEnemy();	
	}

	public boolean checkEnemy()
	{
		return level.checkEnemy();
	}
	
	public Map getMap()
	{
		return level.map;
	}
	
	public int getLevel()
	{
		return level.id;
	}
}

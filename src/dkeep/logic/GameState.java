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
	
	public String getMap()
	{
		return level.map.getPrintable();
	}

	public boolean checkEnemy()
	{
		return level.checkEnemy();
	}
}

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
	
	public void issueMov(char movement, Entity entity)
	{
		level.issueMov(movement, entity);
	}
	
	public boolean escaped()
	{
		return level.escaped;
	}
	
	public boolean gameOver()
	{
		return level.gameOver;
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
	
	public Hero hero()
	{
		return level.hero;
	}
}

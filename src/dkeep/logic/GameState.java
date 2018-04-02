package dkeep.logic;

public class GameState 
{
	private Level level;

	/**
	 * Class constructor.
	 * 
	 * @param level the current level of this game
	 */
	public GameState(Level level)
	{
		this.level = level;
	}
	
	/**
	 * Sets the current level of this game.
	 * 
	 * @param level the new level of this game
	 */
	public void setLevel(Level level)
	{
		this.level = level;
	}

	/**
	 * Returns the current level of this game.
	 * 
	 * @return the current level of this game.
	 */
	public Level level() 
	{
		return level;
	}
	
	/**
	 * Issues a movement to a given entity, by checking if it's valid, and if it's possible to make.
	 * 
	 * @param movement the movement that is being issued
	 * @param entity the entity that is going to me moved
	 */
	public void issueMov(char movement, Entity entity)
	{
		level.issueMov(movement, entity);
	}
	
	/**
	 * Returns whether the hero has or hasn't escaped the current level.
	 * 
	 * @return a boolean indicating if the hero has escaped
	 */
	public boolean escaped()
	{
		return level.escaped;
	}
	
	/**
	 * Returns whether the hero has or hasn't lost the game.
	 * 
	 * @return a boolean indicating if the hero has lost
	 */
	public boolean gameOver()
	{
		return level.gameOver;
	}
	

	/**
	 * Moves the enemy within the level.
 	 */
	public void moveEnemy() 
	{
		level.moveEnemy();	
	}

	/**
	 * Checks whether or not the enemy can harm the hero.
	 * @return a boolean value indicating if the guard harmed the hero or not.
 	 */
	public boolean checkEnemy()
	{
		return level.checkEnemy();
	}

	/**
	 * Returns the current level's map.
	 * 
	 * @return the current level's map.
 	 */
	public Map getMap()
	{
		return level.map;
	}
	
	/**
	 * Returns the current level's id, meaning if it's the first or second level.
	 * 
	 * @return the current level's id.
 	 */
	public int getLevel()
	{
		return level.id;
	}
	

	/**
	 * Returns the current level's hero.
	 * 
	 * @return the current level's hero.
 	 */
	public Hero hero()
	{
		return level.hero;
	}
}

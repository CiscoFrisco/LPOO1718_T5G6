package dkeep.test;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

import dkeep.logic.GameState;
import dkeep.logic.Keep;
import dkeep.logic.Map;
import dkeep.logic.Ogre;
import dkeep.logic.Position;

public class TestKeep {

	char[][] level2 = {{'X','X','X','X','X','X','X','X','X','X'} , 
			{'I',' ',' ',' ',' ',' ',' ',' ','k','X'} , 
			{'X',' ',' ',' ',' ',' ',' ',' ','O','X'} , 
			{'X',' ',' ',' ',' ',' ',' ',' ',' ','X'} , 
			{'X',' ',' ',' ',' ',' ',' ',' ',' ','X'} , 
			{'X',' ',' ',' ',' ',' ',' ',' ',' ','X'} , 
			{'X',' ',' ',' ',' ',' ',' ',' ',' ','X'} , 
			{'X',' ',' ',' ',' ',' ',' ',' ',' ','X'} , 
			{'X','A',' ',' ',' ',' ',' ',' ',' ','X'} , 
			{'X','X','X','X','X','X','X','X','X','X'}};

	char [][] keep = {{'X','X','X','X','X','X'},
			{'X',' ',' ',' ',' ','X'},
			{'X',' ',' ',' ',' ','X'},
			{'X',' ',' ',' ',' ','X'},
			{'I',' ','A',' ','k','X'},
			{'X','X','X','X','X','X'},};
	
	@Test
	public void testKeyHeroMov() {

		Map gameMap = new Map(keep);

		GameState game = new GameState(new Keep(gameMap, 0)); 

		game.issueMov('d',game.hero());
		game.issueMov('d',game.hero());
		assertEquals('K', game.hero().representation());	
	}

	@Test
	public void testFailedExitKeep() {

		Map gameMap = new Map(keep);

		GameState game = new GameState(new Keep(gameMap, 1)); 

		game.issueMov('a',game.hero());
		game.issueMov('a',game.hero());

		assertEquals(new Position(4,1), game.hero().pos());

		assertFalse(game.level().checkDoors());
	}

	@Test
	public void testOpenDoorKeep() {

		Map gameMap = new Map(keep);

		GameState game = new GameState(new Keep(gameMap, 1)); 

		game.issueMov('d',game.hero());
		game.issueMov('d',game.hero());

		game.issueMov('a',game.hero());

		game.issueMov('a',game.hero());
		game.issueMov('a',game.hero());

		assertFalse(game.level().checkDoors());

		game.issueMov('a',game.hero());		

		assertTrue(game.level().checkDoors());
	}

	@Test
	public void testVictoryKeep() {

		Map gameMap = new Map(keep);

		GameState game = new GameState(new Keep(gameMap, 1)); 

		game.issueMov('d',game.hero());
		game.issueMov('d',game.hero());
		game.issueMov('a',game.hero());
		game.issueMov('a',game.hero());
		game.issueMov('a',game.hero());

		assertFalse(game.level().checkDoors());


		game.issueMov('a',game.hero());		

		assertTrue(game.level().checkDoors());

		game.issueMov('a',game.hero());		

		assertEquals(true, game.escaped());
	}

	@Test (timeout = 1000)
	public void testOgreRandomMov(){

		Map gameMap = new Map(level2);

		Keep keep = new Keep(gameMap);

		boolean left = false, right = false, up = false, down = false;

		while(!up || !down || !left || !right)
		{
			for(Ogre ogre : keep.ogres())
			{
				Position pos = ogre.pos();

				keep.issueMov(keep.generateMovement(), keep.hero());
				keep.moveOgres();
				
				if(pos.getI() == ogre.pos().getI() + 1)
				{
					up = true;
				}
				else if(pos.getI() == ogre.pos().getI() - 1)
				{
					down = true;
				}
				else if(pos.getJ() == ogre.pos().getJ() - 1)
				{
					right = true;
				}
				else if(pos.getJ() == ogre.pos().getJ() + 1)
				{
					left = true;
				}
				else if(pos.getI() == ogre.pos().getI() && pos.getJ() == ogre.pos().getJ())
				{
					continue;
				}
				else if(keep.gameOver() || keep.escaped())
				{
					continue;
				}
				else
					fail("Something's wrong!");
			}
		}
	}

	@Test
	public void testOgreKey()
	{
		Map gameMap = new Map(level2);
		Keep keep = new Keep(gameMap);
		
		keep.issueMov('w', keep.ogres().get(0));
		assertEquals('$', keep.ogres().get(0).representation());
	}
	
	@Test
	public void testClubKey()
	{
		Map gameMap = new Map(level2);
		Keep keep = new Keep(gameMap);
		
		keep.issueMov('w', keep.ogres().get(0).club());
		assertEquals('$', keep.ogres().get(0).club().representation());
	}
	
	@Test
	public void testStun()
	{
		Map gameMap = new Map(level2);
		Keep keep = new Keep(gameMap);
		
		
		for(int i= 0; i<5;i++)
		{
			keep.issueMov('d', keep.hero());
			keep.issueMov('s', keep.ogres().get(0));
		}
		
		keep.issueMov('d', keep.hero());
		keep.issueMov('d', keep.hero());
		
		keep.checkStun();
		assertTrue(keep.ogres().get(0).stunned());
		keep.moveEnemy();
		assertTrue(keep.ogres().get(0).stunned());
		keep.moveEnemy();
		assertTrue(keep.ogres().get(0).stunned());
		keep.moveEnemy();
		assertFalse(keep.ogres().get(0).stunned());
	}
	
	@Test
	public void testKeepSave() {
		Map gameMap = new Map(keep);
		Keep keep = new Keep(gameMap,1);
		keep.saveToFile(new File("src/dkeep/test/keep.txt"));
	}

	@Test
	public void testKeepLoad() {

		Map gameMap = new Map(keep);
		Keep keep = new Keep(gameMap,1);

		keep.saveToFile(new File("src/dkeep/test/keep.txt"));

		assertNotNull(Keep.readFromFile(new File("src/dkeep/test/keep.txt")));
	}
	
}

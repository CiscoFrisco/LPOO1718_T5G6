package dkeep.test;

import static org.junit.Assert.*;
import org.junit.Test;

import dkeep.logic.GameState;
import dkeep.logic.ExitDoor;
import dkeep.logic.Ogre;
import dkeep.logic.Map;

public class Test1 {

	char [][] dungeon = {{'X','X','X','X','X'},
					 {'X','H',' ','G','X'},
					 {'I',' ',' ',' ','X'},
					 {'I','k',' ',' ','X'},
					 {'X','X','X','X','X'},};
	
	char [][] keep = {{'X','X','X','X','X','X'},
					 {'X',' ',' ',' ',' ','X'},
					 {'X',' ',' ',' ',' ','X'},
					 {'X',' ',' ',' ',' ','X'},
					 {'I',' ','H',' ','k','X'},
					 {'X','X','X','X','X','X'},};

	
	/////////////////
	//Dungeon tests//
	/////////////////

	@Test
	public void testHeroMov() {

		Map gameMap = new Map(dungeon);
		GameState game = new GameState(gameMap,1, "Rookie"); 

		assertEquals(1, game.hero().x_pos());
		assertEquals(1, game.hero().y_pos());

		game.issueMov('s',game.hero());

		assertEquals(2, game.hero().x_pos());
		assertEquals(1, game.hero().y_pos());
	}

	@Test
	public void testFailMov() {

		Map gameMap = new Map(dungeon);		
		GameState game = new GameState(gameMap,1 , "Rookie"); 

		assertEquals(1, game.hero().x_pos());
		assertEquals(1, game.hero().y_pos());

		game.issueMov('a',game.hero());

		assertEquals(1, game.hero().x_pos());
		assertEquals(1, game.hero().y_pos());
	}

	@Test
	public void testDeathMov() {

		Map gameMap = new Map(dungeon);

		GameState game = new GameState(gameMap,1, "Rookie"); 

		assertEquals(1, game.hero().x_pos());
		assertEquals(1, game.hero().y_pos());

		game.issueMov('d',game.hero());
		game.checkGuard();

		assertEquals(true, game.gameOver());
	}

	@Test
	public void testFailedExitMov() {

		Map gameMap = new Map(dungeon);

		GameState game = new GameState(gameMap,1, "Rookie"); 

		assertEquals(1, game.hero().x_pos());
		assertEquals(1, game.hero().y_pos());

		game.issueMov('s',game.hero());
		game.issueMov('a',game.hero());

		assertEquals(2, game.hero().x_pos());
		assertEquals(1, game.hero().y_pos());
	}

	@Test
	public void testLeverMov() {

		Map gameMap = new Map(dungeon);

		GameState game = new GameState(gameMap,1, "Rookie"); 

		assertEquals(1, game.hero().x_pos());
		assertEquals(1, game.hero().y_pos());

		game.issueMov('s',game.hero());
		game.issueMov('s',game.hero());

		for(ExitDoor exitDoor : game.exitDoors())
			assertEquals('S', exitDoor.representation());
	}
	
	@Test
	public void testExit() {

		Map gameMap = new Map(dungeon);

		GameState game = new GameState(gameMap,1, "Rookie"); 

		assertEquals(1, game.hero().x_pos());
		assertEquals(1, game.hero().y_pos());

		game.issueMov('s',game.hero());
		game.issueMov('s',game.hero());
		game.issueMov('a', game.hero());
		
		for(ExitDoor exitDoor : game.exitDoors())
			assertEquals('S', exitDoor.representation());
		
		assertEquals(true, game.escaped());
	}
	
	//////////////
	//Keep tests//
	//////////////
	
	@Test
	public void testKeyHeroMov() {

		Map gameMap = new Map(keep);

		GameState game = new GameState(gameMap,2, "Rookie"); 

		game.issueMov('d',game.hero());
		game.issueMov('d',game.hero());
		assertEquals('K', game.hero().representation());	
	}
	
	@Test
	public void testFailedExitKeep() {

		Map gameMap = new Map(keep);

		GameState game = new GameState(gameMap,2, "Rookie"); 

		game.issueMov('a',game.hero());
		game.issueMov('a',game.hero());
		
		assertEquals(4,game.hero().x_pos());
		assertEquals(1,game.hero().y_pos());

		for(ExitDoor exitDoor : game.exitDoors())
			assertEquals('I', exitDoor.representation());
	}
	
	@Test
	public void testOpenDoorKeep() {

		Map gameMap = new Map(keep);

		GameState game = new GameState(gameMap,2, "Rookie"); 

		game.issueMov('d',game.hero());
		game.issueMov('d',game.hero());

		game.issueMov('a',game.hero());

		game.issueMov('a',game.hero());
		game.issueMov('a',game.hero());
		
		for(ExitDoor exitDoor : game.exitDoors())
			assertEquals('I', exitDoor.representation());
		
		game.issueMov('a',game.hero());		
		
		for(ExitDoor exitDoor : game.exitDoors())
			assertEquals('S', exitDoor.representation());

	}
	
	@Test
	public void testVictoryKeep() {

		Map gameMap = new Map(keep);

		GameState game = new GameState(gameMap,2, "Rookie"); 

		game.issueMov('d',game.hero());
		game.issueMov('d',game.hero());
		game.issueMov('a',game.hero());
		game.issueMov('a',game.hero());
		game.issueMov('a',game.hero());

		for(ExitDoor exitDoor : game.exitDoors())
			assertEquals('I', exitDoor.representation());
		
		game.issueMov('a',game.hero());		

		for(ExitDoor exitDoor : game.exitDoors())
			assertEquals('S', exitDoor.representation());
		
		game.issueMov('a',game.hero());		
		
		assertEquals(true, game.escaped());
	}
	
	
	//Code needed to be slightly changed in order to test this (remove randomness)
	@Test
	public void testStun() {
		Map gameMap = new Map(keep);

		GameState game = new GameState(gameMap,2, "Rookie");
		
		game.issueMov('w', game.hero());
		
		game.checkStun();
		
		boolean stunned = false;
		
		for(Ogre ogre : game.ogres())
			assertEquals('8', ogre.representation());
					
	}
	
	@Test
	public void testOgre() {
		Map gameMap = new Map(keep);
		
		GameState game = new GameState(gameMap, 2, "Rookie");
		
		
	}
}

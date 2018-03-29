package dkeep.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;

import dkeep.logic.Dungeon;
import dkeep.logic.GameState;
import dkeep.logic.Keep;
import dkeep.logic.Map;
import dkeep.logic.Position;

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
					 {'I',' ','A',' ','k','X'},
					 {'X','X','X','X','X','X'},};

	
	/////////////////
	//Dungeon tests//
	/////////////////

	@Test
	public void testHeroMov() {

		Map gameMap = new Map(dungeon);
		GameState game = new GameState(new Dungeon(gameMap, "Rookie")); 

		assertEquals(new Position(1,1), game.hero().pos());

		game.issueMov('s',game.level().hero());

		assertEquals(new Position(2,1), game.hero().pos());
	}

	@Test
	public void testFailMov() {

		Map gameMap = new Map(dungeon);		
		GameState game = new GameState(new Dungeon(gameMap, "Rookie")); 

		assertEquals(new Position(1,1), game.hero().pos());

		game.issueMov('a',game.level().hero());

		assertEquals(new Position(1,1), game.hero().pos());
	}

	@Test
	public void testDeathMov() {

		Map gameMap = new Map(dungeon);
		GameState game = new GameState(new Dungeon(gameMap, "Rookie")); 

		assertEquals(new Position(1,1), game.hero().pos());


		game.issueMov('d',game.hero());
		game.checkEnemy();

		assertEquals(true, game.gameOver());
	}

	@Test
	public void testFailedExitMov() {

		Map gameMap = new Map(dungeon);

		GameState game = new GameState(new Dungeon(gameMap, "Rookie")); 

		assertEquals(new Position(1,1), game.hero().pos());

		game.issueMov('s',game.hero());
		game.issueMov('a',game.hero());

		assertEquals(new Position(2,1), game.hero().pos());
	}

	@Test
	public void testLeverMov() {

		Map gameMap = new Map(dungeon);

		GameState game = new GameState(new Dungeon(gameMap, "Rookie")); 

		assertEquals(new Position(1,1), game.hero().pos());

		game.issueMov('s',game.hero());
		game.issueMov('s',game.hero());

		assertTrue(game.level().checkDoors());
	}
	
	@Test
	public void testExit() {

		Map gameMap = new Map(dungeon);

		GameState game = new GameState(new Dungeon(gameMap, "Rookie")); 

		assertEquals(new Position(1,1), game.hero().pos());

		game.issueMov('s',game.hero());
		game.issueMov('s',game.hero());
		game.issueMov('a', game.hero());
		
		assertTrue(game.level().checkDoors());
		
		assertEquals(true, game.escaped());
	}
	
	//////////////
	//Keep tests//
	//////////////
	
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
	
	@Test
	public void testDungeonSave() {
		Map gameMap = new Map(dungeon);
		Dungeon dungeon = new Dungeon(gameMap,"Rookie");
		
		dungeon.saveToFile(new File("dkeep/test/dungeon.txt"));
	}
	
	@Test
	public void testDungeonLoad() {
		Map gameMap = new Map(dungeon);
		Dungeon dungeon = new Dungeon(gameMap,"Rookie");
		
		dungeon.saveToFile(new File("dkeep/test/dungeon.txt"));
		
		assertNotNull(Dungeon.readFromFile(new File("dkeep/test/dungeon.txt")));
	}
	
	@Test
	public void testKeepSave() {
		Map gameMap = new Map(keep);
		Keep keep = new Keep(gameMap,1);
		
		keep.saveToFile(new File("dkeep/test/keep.txt"));
	}
	
	@Test
	public void testKeepLoad() {
		
		Map gameMap = new Map(keep);
		Keep keep = new Keep(gameMap,1);
		
		keep.saveToFile(new File("dkeep/test/keep.txt"));
		
		assertNotNull(Keep.readFromFile(new File("dkeep/test/keep.txt")));
	}
	
	/*
	//Code needed to be slightly changed in order to test this (remove randomness)
	@Test
	public void testStun() {
		Map gameMap = new Map(keep);

		GameState game = new GameState(new Keep(gameMap, 1)); 
		
		game.issueMov('w', game.hero());
		
		game.checkStun();
		
		boolean stunned = false;
		
		for(Ogre ogre : game.ogres())
			assertEquals('8', ogre.representation());
					
	}
	
	@Test
	public void testOgre() {
		Map gameMap = new Map(keep);
		
		GameState game = new GameState(new Keep(gameMap, 1)); 
		
		
	}
	*/
}

package dkeep.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;

import dkeep.logic.Dungeon;
import dkeep.logic.GameState;
import dkeep.logic.Map;
import dkeep.logic.Position;

public class TestDungeon {

	char [][] dungeon = {{'X','X','X','X','X'},
			{'X','H',' ','G','X'},
			{'I',' ',' ',' ','X'},
			{'I','k',' ',' ','X'},
			{'X','X','X','X','X'},};


	char[][] level1 = {{'X','X','X','X','X','X','X','X','X','X'} , 
			{'X','H',' ',' ','I',' ','X',' ','G','X'} , 
			{'X','X','X',' ','X','X','X',' ',' ','X'} , 
			{'X',' ','I',' ','I',' ','X',' ',' ','X'} , 
			{'X','X','X',' ','X','X','X',' ',' ','X'} , 
			{'I',' ',' ',' ',' ',' ',' ',' ',' ','X'} , 
			{'I',' ',' ',' ',' ',' ',' ',' ',' ','X'} , 
			{'X','X','X',' ','X','X','X','X',' ','X'} , 
			{'X',' ','I',' ','I',' ','X','k',' ','X'} , 
			{'X','X','X','X','X','X','X','X','X','X'}};


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
	public void testRookieGuardMov() {
		Map gameMap = new Map(level1);
		Dungeon dungeon = new Dungeon(gameMap, "Rookie");

		dungeon.moveEnemy();
		assertEquals(new Position(1,7), dungeon.guard().pos());
	}

	@Test
	public void testDungeonGameOver() {
		Map gameMap = new Map(level1);
		Dungeon dungeon = new Dungeon(gameMap, "Rookie");

		dungeon.issueMov('d', dungeon.hero());
		dungeon.moveEnemy();
		dungeon.issueMov('d', dungeon.hero());
		dungeon.moveEnemy();
		dungeon.issueMov('s', dungeon.hero());
		dungeon.moveEnemy();
		dungeon.issueMov('s', dungeon.hero());
		dungeon.moveEnemy();
		dungeon.issueMov('s', dungeon.hero());
		dungeon.moveEnemy();
		dungeon.issueMov('s', dungeon.hero());
		dungeon.moveEnemy();
		dungeon.issueMov('d', dungeon.hero());
		dungeon.moveEnemy();

		assertTrue(dungeon.checkEnemy());
	}

	@Test(timeout = 1000)
	public void testDrunkenGuard() {
		Map gameMap = new Map(level1);
		Dungeon dungeon = new Dungeon(gameMap, "Drunken");

		dungeon.moveEnemy();
		while(!dungeon.guard().status())
			dungeon.moveEnemy();

		while(dungeon.guard().status())
			dungeon.moveEnemy();
	}
	
	@Test(timeout = 1000)
	public void testSuspiciousGuard() {
		Map gameMap = new Map(level1);
		Dungeon dungeon = new Dungeon(gameMap, "Suspicious");
		int mov = 0;
		dungeon.moveEnemy();
		
		while(dungeon.guard().getMovement()>mov)
		{
			mov = dungeon.guard().getMovement();
			dungeon.moveEnemy();
		}
			

		while(dungeon.guard().getMovement()<mov)
		{
			mov = dungeon.guard().getMovement();
			dungeon.moveEnemy();
		}
	}
}

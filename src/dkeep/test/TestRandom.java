package dkeep.test;

import static org.junit.Assert.*;
import org.junit.Test;

import dkeep.logic.Keep;
import dkeep.logic.Map;
import dkeep.logic.Ogre;
import dkeep.logic.Position;

public class TestRandom {

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

	/*
	@Test (timeout = 1000)
	public void testClubRandomMov(){

		Map gameMap = new Map(level2);

		Keep keep = new Keep(gameMap);

		boolean left = false, right = false, up = false, down = false;

		while(!up || !down || !left || !right)
		{
			for(Ogre ogre : keep.ogres())
			{
				Position pos = ogre.pos();

				System.out.println(keep.map().getPrintable());

				keep.issueMov(keep.generateMovement(), keep.hero());
				keep.moveEnemy();

				if(pos.getI() == ogre.pos().getI() + 1)
				{
					up = true;
				}
				else if(pos.getI()  == ogre.pos().getJ() - 1)
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
				else if(pos.getI()  == ogre.pos().getI() && pos.getJ() == ogre.pos().getJ())
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
	*/
	@Test
	public void testOgreKey()
	{
		Map gameMap = new Map(level2);
		Keep keep = new Keep(gameMap);
		
		keep.issueMov('w', keep.ogres().get(0));
		assertEquals('$', keep.ogres().get(0).representation());
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
	
}

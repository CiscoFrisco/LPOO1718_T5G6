package dkeep.test;

import static org.junit.Assert.*;
import org.junit.Test;

import dkeep.logic.GameState;
import dkeep.logic.Map;
import dkeep.logic.Ogre;

public class TestRandom {

	char [][] keep = {{'X','X','X','X','X','X'},
			{'X',' ',' ',' ',' ','X'},
			{'X',' ',' ',' ',' ','X'},
			{'X',' ',' ',' ',' ','X'},
			{'I',' ','H',' ','k','X'},
			{'X','X','X','X','X','X'},};

	@Test (timeout = 1000)
	public void testOgreRandomMov(){

		Map gameMap = new Map(keep);

		GameState game = new GameState(gameMap, 2);

		boolean left = false, right = false, up = false, down = false;

		while(!up || !down || !left || !right)
		{
			for(Ogre ogre : game.ogres())
			{
				int ogre_x_pos = ogre.x_pos();
				int ogre_y_pos = ogre.y_pos();

				game.issueMov(game.generateMovement(), ogre);

				if(ogre_x_pos == ogre.x_pos() + 1)
				{
					up = true;
				}
				else if(ogre_x_pos == ogre.x_pos() - 1)
				{
					down = true;
				}
				else if(ogre_y_pos == ogre.y_pos() - 1)
				{
					right = true;
				}
				else if(ogre_y_pos == ogre.y_pos() + 1)
				{
					left = true;
				}
				else if(ogre_x_pos == ogre.x_pos() && ogre_y_pos == ogre.y_pos())
				{
					continue;
				}
				else
					fail("Well...shit!");
			}
		}
	}

	public void testClubRandomMov(){

		Map gameMap = new Map(keep);

		GameState game = new GameState(gameMap, 2);

		boolean left = false, right = false, up = false, down = false;

		while(!up || !down || !left || !right)
		{
			for(Ogre ogre : game.ogres())
			{
				int club_x_pos = ogre.x_pos();
				int club_y_pos = ogre.y_pos();

				game.issueMov(game.generateMovement(), ogre.club());

				if(club_x_pos == ogre.club().x_pos() + 1)
				{
					up = true;
				}
				else if(club_x_pos == ogre.club().x_pos() - 1)
				{
					down = true;
				}
				else if(club_y_pos == ogre.club().y_pos() - 1)
				{
					right = true;
				}
				else if(club_y_pos == ogre.club().y_pos() + 1)
				{
					left = true;
				}
				else if(club_x_pos == ogre.club().x_pos() && club_y_pos == ogre.club().y_pos())
				{
					continue;
				}
				else
					fail("Well...shit!");
			}
		}
	}
}

package dkeep.logic;

import java.util.Random;
import java.util.ArrayList;

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



	public boolean checkGuard()
	{
		boolean cond1 = Math.abs(hero.x_pos()-guard.x_pos())<=1;
		boolean cond2 = hero.y_pos() == guard.y_pos();
		boolean cond3 = Math.abs(hero.y_pos()-guard.y_pos())<=1;
		boolean cond4 = hero.x_pos() == guard.x_pos();

		if((( cond1 && cond2) || (cond3 && cond4)) && !guard.status())
		{
			game_over = true;
			return true;
		}
		else if (hero.x_pos() == guard.x_pos() && hero.y_pos() == guard.y_pos())
		{
			game_over = true;
			return true;
		}

		return false;
	}

	public boolean checkClub()
	{
		for(Ogre ogre: ogres)
		{
			boolean cond1 = Math.abs(hero.x_pos()-ogre.club().x_pos())<=1;
			boolean cond2 = hero.y_pos() == ogre.club().y_pos();
			boolean cond3 = Math.abs(hero.y_pos()-ogre.club().y_pos())<=1;
			boolean cond4 = hero.x_pos() == ogre.club().x_pos();

			if((cond1 && cond2) || (cond3 && cond4) || (cond4 && cond2))
			{
				game_over=true;
				return true;
			}
		}

		return false;
	}

	public boolean checkOgre(Ogre ogre)
	{		
		boolean cond1 = Math.abs(hero.x_pos()-ogre.x_pos())<=1;
		boolean cond2 = hero.y_pos() == ogre.y_pos();
		boolean cond3 = Math.abs(hero.y_pos()-ogre.y_pos())<=1;
		boolean cond4 = hero.x_pos() == ogre.x_pos();

		if((cond1 && cond2) || (cond3 && cond4))
			return true;
		else 
			return false;
	}


	public void updateOgre(Ogre ogre)
	{
		if(map.pos(ogre.club().x_pos,ogre.club().y_pos) == '$')
		{
			map.setPos(ogre.club().x_pos,ogre.club().y_pos,'k');
			ogre.club().setRepresentation('*');
		}
		else
			map.setPos(ogre.club().x_pos,ogre.club().y_pos,' ');

		ogre.club().resetKey();
		ogre.club().x_pos = ogre.x_pos;
		ogre.club().y_pos = ogre.y_pos;	
	}

	public void checkStun()
	{
		for(Ogre ogre : ogres)
		{
			if(ogre.stunned())
			{
				if(!ogre.updateStun())
					map.updatePos(ogre.x_pos, ogre.y_pos, ogre);
			}
			else if(checkOgre(ogre))
			{
				ogre.setStun();
				map.updatePos(ogre.x_pos, ogre.y_pos, ogre);
			}
		}
	}



	public Level getLevel() {
		return level;
	}

	
	public String getWritable()
	{
		return map.getWritable();
	}

	public String getGuardType() {
		return guardType;
	}
}

package dkeep.logic;

import java.util.ArrayList;
import java.util.Random;

public class Keep extends Level
{
	private ArrayList<Ogre> ogres;
	private Key key;
	private ExitDoor exitDoor;
	
	public Keep(Map map, int numberOfOgres)
	{
		initKeep(numberOfOgres);
	}
	
	public Key key()
	{
		return key;
	}
	
	public ExitDoor exitDoor()
	{
		return exitDoor;
	}

	private void initKeep(int numberOfOgres) 
	{
		for(int i = 0;i<map.layout().length;i++)
			for(int j = 0;j<map.layout()[i].length;j++)
			{
				char rep = map.layout()[i][j];
				if(rep == 'A')
					hero = new Hero(i,j,'A');
				else if(rep == 'k')
					key = new Key(i,j,'k');
				else if(rep == 'I')
					exitDoor = new ExitDoor(i,j,'I');
			}	
		
		generateOgres(numberOfOgres);
	}
	
	public void generateOgres(int numberOfOgres)
	{
		Random random = new Random();
		int limit = map.layout().length/2;
		int x_pos = limit + 1, y_pos = 0;
		ogres = new ArrayList<Ogre>(numberOfOgres);

		for(int i = 0; i<numberOfOgres;i++)
		{	
			while(x_pos>=limit && y_pos<=limit)
			{
				x_pos = random.nextInt(limit+1) + 1;
				y_pos = random.nextInt(limit+1) + 1;
			}

			Ogre ogre = new Ogre(new Position(x_pos,y_pos),'O');
			ogres.add(ogre);

			x_pos = limit+1;
			y_pos = 0;
		}

		for(Ogre ogre : ogres)
			map.updatePos(ogre.pos().getI(), ogre.pos.getJ(), ogre);
	}
	
	public void armOgres()
	{
		for(Ogre ogre : ogres)
			setClub(ogre);
	}
	
	public void setClub(Ogre ogre)
	{
		char clubSwing = generateMovement();

		while(!issueMov(clubSwing, ogre.club))
			clubSwing = generateMovement();
	}
	
	public void moveOgres()
	{
		for(Ogre ogre : ogres)
		{
			if(!ogre.stunned())
			{	
				char movement = generateMovement();

				issueMov(movement, ogre);
			}

			updateOgre(ogre);
		}
	}

}

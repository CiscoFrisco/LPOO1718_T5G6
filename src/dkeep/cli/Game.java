package dkeep.cli;
import java.util.Scanner;
import java.util.Vector;

import dkeep.logic.*;
import java.util.Random;

public class Game {

	public static boolean scanMove(char movement, GameState state, Entity entity)
	{
		if(movement == 'a' || movement == 'w' || movement == 's' || movement == 'd')
		{	
			if(state.issueMov(movement, entity))
				return true;

			return false;
		}

		else
			return false;
	}


	public static int gameplay(int level, GameState state, Scanner s)
	{
		char movement = 'u';

		while(!state.escaped())
		{
			movement = s.next().charAt(0);

			System.out.print('\n');

			if(scanMove(movement, state, state.hero()))
			{	
				if(state.moveGuard())
					if(level == 2)
						state.setClub();

				state.printMap();

				System.out.print("Insert your move: ");

				if((state.checkGuard() && level == 1) || (level == 2 && (state.checkClub() || state.checkOgre())))	
				{
					System.out.println("Game Over!");
					return 1;
				}	
			}
		}

		return 0;
	}


	public static void printInstructions()
	{	
		System.out.println("Welcome to Dungeon Keep!");
		System.out.println("Use WASD to move the character!" );
	}

	public static Guard generateGuard()
	{
		Random number = new Random();
		int generated = number.nextInt(3);
		Guard guard = null;

		switch(generated)
		{
		case 0:
			guard = new RookieGuard(1,8,'G');
			break;
		case 1:
			guard = new DrunkenGuard(1,8,'G');
			break;
		case 2:
			guard = new SuspiciousGuard(1,8,'G');
			break;
		default:
			break;	
		}

		return guard;
	}

	public static Vector<Ogre> generateOgres()
	{
		Vector<Ogre> ogres;
		Random random = new Random();
		int number = random.nextInt(3) + 1;
		int x_pos = 10, y_pos = 0;
		
		for(int i = 0; i<number;i++)
		{	
			//Ogres nao podem começar perto do hero
			while(x_pos>=5 && y_pos<=5)
			{
				x_pos = random.nextInt(8) + 1;
				y_pos = random.nextInt(8) + 1;
			}

			Ogre ogre = new Ogre(x_pos, y_pos,'O');
			ogres.add(ogre);
		}

		return ogres;
	}

	public static void main(String[] args) {

		Hero hero = new Hero(1,1,'H');
		Hero hero2 = new Hero(8,1, 'A');
		Guard guard = new DrunkenGuard(1,8,'G');//generateGuard();
		//Club club = new Club(1,6,'*');
		Vector<Ogre> ogres = generateOgres();
		Key key = new Key(1,8,'k');
		Lever lever = new Lever(8,7,'k');

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

		char[][] level2 = {{'X','X','X','X','X','X','X','X','X','X'} , 
				{'I',' ',' ',' ',' ','O','*',' ','k','X'} , 
				{'X',' ',' ',' ',' ',' ',' ',' ',' ','X'} , 
				{'X',' ',' ',' ',' ',' ',' ',' ',' ','X'} , 
				{'X',' ',' ',' ',' ',' ',' ',' ',' ','X'} , 
				{'X',' ',' ',' ',' ',' ',' ',' ',' ','X'} , 
				{'X',' ',' ',' ',' ',' ',' ',' ',' ','X'} , 
				{'X',' ',' ',' ',' ',' ',' ',' ',' ','X'} , 
				{'X','H',' ',' ',' ',' ',' ',' ',' ','X'} , 
				{'X','X','X','X','X','X','X','X','X','X'}};


		Map map = new Map(level1);
		Map map2 = new Map(level2);
		GameState state = new GameState(map, hero, guard, ogres, lever, key);

		printInstructions();


		state.printMap();
		System.out.print("Insert your move: ");

		Scanner s = new Scanner(System.in);

		if(gameplay(1, state, s) == 0) // if he goes through to the second level
			state.changeLevel(map2, hero2);
		else //if he dies 
		{
			s.close();
			return;

		}

		state.printMap();
		if(gameplay(2, state, s) == 0) //
		{
			s.close();
		}

		return;
	}

}
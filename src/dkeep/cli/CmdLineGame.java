package dkeep.cli;

import java.util.Random;
import java.util.Scanner;

import dkeep.logic.Dungeon;
import dkeep.logic.Entity;
import dkeep.logic.GameState;
import dkeep.logic.Keep;
import dkeep.logic.Level;
import dkeep.logic.Map;

public class CmdLineGame 
{
	public static boolean scanMove(char movement, GameState state, Entity entity)
	{
		if(movement == 'a' || movement == 'w' || movement == 's' || movement == 'd')
		{	
			if(state.level().issueMov(movement, entity))
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

			if(scanMove(movement, state, state.level().hero()))
			{	
				state.moveEnemy();

				System.out.print(state.getMap());

				if(state.checkEnemy())
				{
					System.out.println("Game Over!");
					return 1;
				}
				else if(state.escaped() && level == 2)
				{
					System.out.println("You won, congratulations!");
					return 0;
				}

				System.out.print("Insert your move: ");
			}
		}

		return 0;
	}


	public static void printInstructions()
	{	
		System.out.println("Welcome to Dungeon Keep!");
		System.out.println("Use WASD to move the character!" );
	}


	public static String guardPersonality()
	{
		String res ="";
		Random random = new Random();
		int number = random.nextInt(3);

		switch(number)
		{
		case 0:
			res = "Rookie";
			break;
		case 1:
			res = "Drunken";
			break;
		case 2:
			res = "Suspicious";
			break;
		default:
			break;
		}

		return res;
	}
	
	public static int numberOfOgres()
	{
		Random random = new Random();
		return random.nextInt(2) + 1;
	}

	public static void main(String[] args) 
	{
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
				{'I',' ',' ',' ',' ',' ',' ',' ','k','X'} , 
				{'X',' ',' ',' ',' ',' ',' ',' ',' ','X'} , 
				{'X',' ',' ',' ',' ',' ',' ',' ',' ','X'} , 
				{'X',' ',' ',' ',' ',' ',' ',' ',' ','X'} , 
				{'X',' ',' ',' ',' ',' ',' ',' ',' ','X'} , 
				{'X',' ',' ',' ',' ',' ',' ',' ',' ','X'} , 
				{'X',' ',' ',' ',' ',' ',' ',' ',' ','X'} , 
				{'X','A',' ',' ',' ',' ',' ',' ',' ','X'} , 
				{'X','X','X','X','X','X','X','X','X','X'}};

		Map map = new Map(level1);
		Map map2 = new Map(level2);
		Level dungeon = new Dungeon(map, guardPersonality());
		GameState state = new GameState(dungeon);

		printInstructions();
		System.out.print(state.getMap());
		System.out.print("Insert your move: ");

		Scanner s = new Scanner(System.in);

		if(gameplay(1, state, s) == 0)
			state.setLevel(new Keep(map2, numberOfOgres()));
		else
		{
			s.close();
			return;
		}

		System.out.print(state.getMap());
		if(gameplay(2, state, s) == 0)
			s.close();

		return;
	}

}
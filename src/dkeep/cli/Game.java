package dkeep.cli;
import java.util.Scanner;
import dkeep.logic.*;

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
		
		if(level == 1) return 0;
		
		while(!state.escaped())
		{
			movement = s.next().charAt(0);

			System.out.print('\n');

			if(scanMove(movement, state, state.hero()))
			{	
				if(level == 1)
					state.moveGuard();
				else
				{
					state.moveOgres();
					state.armOgres();
					state.checkStun();

				}						

				state.printMap();

				System.out.print("Insert your move: ");

				if((state.checkGuard() && level == 1) || (level == 2 && (state.checkClub())))	
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



	

	public static void main(String[] args) {
		
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
		GameState state = new GameState(map,1);
		
		printInstructions();
		state.printMap();
		System.out.print("Insert your move: ");

		Scanner s = new Scanner(System.in);
		
		if(gameplay(1, state, s) == 0) // if he goes through to the second level
			state.changeLevel(map2);
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
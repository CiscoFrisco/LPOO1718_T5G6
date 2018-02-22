import java.util.Scanner;
import java.util.Random;

public class Game {

	private static char[] guard_route = {'a','s','s','s','s','a','a','a','a','a','a','s','d','d','d','d','d','d','d','w','w','w','w','w'};
	private static int guard_movement = 0;

	public static boolean scanMove(char movement, Map map, int level)
	{
		if(movement == 'a' || movement == 'w' || movement == 's' || movement == 'd')
		{	
			if(map.hasKey)
			{
				if(map.updateMap(movement, 'K', level))	
					return true;
				
				return false;
			}
			
			if(map.updateMap(movement, 'H', level))	
				return true;
			
			return false;
		}
		
		else
			return false;
	}
	
	public static char generateMovement()
	{
		Random number = new Random();
		int generated = number.nextInt(4);
		char movement = 'u';
		
		switch(generated)
		{
		case 0:
			movement = 'a';
			break;
		case 1:
			movement = 'w';
			break;
		case 2:
			movement = 'd';
			break;
		case 3:
			movement = 's';
			break;
		default:
			break;
		}
		
		return movement;
	}
	
	public static void setClub(Map map, int level)
	{
		char clubSwing = generateMovement();
		
		while(!map.updateMap(clubSwing, '*', level))
			{clubSwing = generateMovement();};
	}
	
	public static boolean moveGuard(int level, Map map)
	{
		if(level == 1)
		{
			map.updateMap(guard_route[guard_movement],'G', level);

			if(guard_movement == guard_route.length - 1)
				guard_movement = 0;
			else
				guard_movement++;
			
			return true;
		}
		else
		{
			char guardMovement = generateMovement();
			//char guardMovement = 'd';

			if(map.updateMap(guardMovement, 'O', level))
				return true;
			
			return false;
		}
	}

	public static int gameplay(int level, Map map, Scanner s)
	{
		char movement = 'u';

		while(!map.escaped())
		{
			movement = s.next().charAt(0);

			System.out.print('\n');
			

			if(scanMove(movement, map, level))
			{	
				if(moveGuard(level, map))
					if(level == 2)
						setClub(map,level);

				map.printMap();

				if(map.checkGuard() || (level == 2 && map.checkClub()))	
				{
					System.out.println("Game Over!");
					return 1;
				}	

				printInstructions();

			}
		}

		return 0;
	}


	public static void printInstructions()
	{
		System.out.println("\nUse WASD to move the character!" );
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
				{'I',' ',' ',' ',' ','O',' ',' ','k','X'} , 
				{'X',' ',' ',' ',' ',' ',' ',' ',' ','X'} , 
				{'X',' ',' ',' ',' ',' ',' ',' ',' ','X'} , 
				{'X',' ',' ',' ',' ',' ',' ',' ',' ','X'} , 
				{'X',' ',' ',' ',' ',' ',' ',' ',' ','X'} , 
				{'X',' ',' ',' ',' ',' ',' ',' ',' ','X'} , 
				{'X',' ',' ',' ',' ',' ',' ',' ',' ','X'} , 
				{'X','H',' ',' ',' ',' ',' ',' ',' ','X'} , 
				{'X','X','X','X','X','X','X','X','X','X'}};

		Map gamemap_1 = new Map(level1, 1, 1, 1, 8, 8, 7, -1, -1);
		Map gamemap_2 = new Map(level2, 8, 1, 1, 5, -1, -1, 1, 8);
		
		gamemap_1.printMap();

		printInstructions();


		Scanner s = new Scanner(System.in);

		if(gameplay(1, gamemap_1, s) == 1)
			return;

		gamemap_2.club_x_pos = 1;
		gamemap_2.club_y_pos = 5;

		setClub(gamemap_2, 2);
		
		
		gamemap_2.printMap();

		printInstructions();

		gameplay(2, gamemap_2, s);

		s.close();


		return;
	}

}
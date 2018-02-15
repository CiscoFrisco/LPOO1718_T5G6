import java.util.Scanner;

public class Game {
	
	private static char[] guard_route = {'a','s','s','s','s','a','a','a','a','a','a','s','d','d','d','d','d','d','d','w','w','w','w','w'};
	private static int guard_movement = 0;
	
	public static boolean scanMove(char movement, Map map)
	{

		if(movement == 'a' || movement == 'w' || movement == 's' || movement == 'd')
		{
			if(map.updateMap(movement, 'H'))	
				return true;

			return false;
		}
		else
			return false;
	}

	public static void gameplay(Map map)
	{
		char movement = 'u';
		Scanner s = new Scanner(System.in);
		
		while(!map.escaped())
		{
			movement = s.next().charAt(0);

			map.updateMap(guard_route[guard_movement],'G');
			
			if(guard_movement == guard_route.length - 1)
				guard_movement = 0;
			else
				guard_movement++;
			
			map.printMap();
			System.out.print('\n');
			
			if(scanMove(movement, map))
			{	
				map.printMap();

				if(map.checkGuard())
				{
					System.out.println("Game Over!");
					break;
				}		

				printInstructions();

			}
		}
		s.close();

	}


	public static void printInstructions()
	{
		System.out.println("\nUse WASD to move the character!" );
	}

	public static void main(String[] args) {

		Map gamemap = new Map();

		gamemap.printMap();

		printInstructions();

		gameplay(gamemap);

	}

}

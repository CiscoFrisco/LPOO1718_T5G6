package dkeep.gui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.TreeMap;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import dkeep.logic.Map;

public class GameView extends JPanel
{
	private TreeMap<Character, BufferedImage> dungeon;
	private TreeMap<Character, BufferedImage> keep;
	private BufferedImage[][] graphics;
	private char[][] gameMap;
	private int width;
	private int height;
	private int level;

	public GameView(Map gameMap, int level) 
	{
		width = 10;
		height = 10;
		this.level = level;
		this.gameMap = new char[height][width];
		dungeon = new TreeMap<Character, BufferedImage>();
		keep = new TreeMap<Character, BufferedImage>();

		try
		{
			dungeon.put('H', ImageIO.read(new File("images/hero.png")));
			dungeon.put('G', ImageIO.read(new File("images/guard.png")));
			dungeon.put('g', ImageIO.read(new File("images/sleep.png")));
			dungeon.put('I', ImageIO.read(new File("images/door_closed.png")));
			dungeon.put('S', ImageIO.read(new File("images/door_open.png")));
			dungeon.put('k', ImageIO.read(new File("images/lever.png")));
			dungeon.put('X', ImageIO.read(new File("images/parede.png")));
			dungeon.put(' ', ImageIO.read(new File("images/espaco.png")));
			
			keep.put('O', ImageIO.read(new File("images/ogre.png")));
			keep.put('*', ImageIO.read(new File("images/club.png")));
			keep.put('K', ImageIO.read(new File("images/hero.png")));
			keep.put('A', ImageIO.read(new File("images/hero.png")));
			keep.put('$', ImageIO.read(new File("images/hero.png")));
			keep.put('k', ImageIO.read(new File("images/key.png")));
			keep.put('8', ImageIO.read(new File("images/hero.png")));
			keep.put('X', dungeon.get('X'));
			keep.put(' ', dungeon.get(' '));
			keep.put('S', dungeon.get('S'));
			keep.put('I', dungeon.get('I'));
		} 
		catch (IOException e) 
		{
			System.out.println(e.getMessage());
		}

		initGraphics(width,height);

		if(gameMap != null)
			updateMap(gameMap.layout());
	}


	public void initGraphics(int width, int height)
	{
		graphics = new BufferedImage[height][width];

		for(int i = 0; i < height;i++)
			for(int j = 0;j<width;j++)
				graphics[i][j] = dungeon.get(' ');
	}
	
	public void changeLevel()
	{
		level++;
	}

	public BufferedImage[][] getMap()
	{
		return graphics;
	}

	protected void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
		int x = 0;
		int y = 0;

		for(int i = 0; i < graphics.length;i++)
		{
			for(int j=0; j< graphics[i].length;j++)
			{
				g.drawImage(graphics[i][j],x,y,this);
				x += 32;
			}

			x = 0;
			y += 32;
		}
	}

	public void updatePos(int i, int j, char entity)
	{
		graphics[i][j] = level == 1 ? dungeon.get(entity) : keep.get(entity);
		gameMap[i][j] = entity;
	}

	public void updateMap(char[][] map)
	{		
		TreeMap<Character, BufferedImage> images = level == 1 ? dungeon : keep;
		
		for(int i = 0; i < map.length; i++)
		    gameMap[i] = map[i].clone();
		
		for(int i = 0; i < map.length;i++)
			for(int j=0; j<map[i].length;j++)
				graphics[i][j] = images.get(map[i][j]);
	}	
	
	public char[][] getGameMap()
	{
		return gameMap;
	}

}

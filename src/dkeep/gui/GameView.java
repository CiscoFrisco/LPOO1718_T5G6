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
	private TreeMap<Character, BufferedImage> images;
	private BufferedImage[][] graphics;
	private int width;
	private int height;

	public GameView(Map gameMap, int level) 
	{
		width = 10;
		height = 10;
		images = new TreeMap<Character, BufferedImage>();

		try
		{
			images.put('H', ImageIO.read(new File("images/hero.png")));
			images.put('K', ImageIO.read(new File("images/hero.png")));
			images.put('A', ImageIO.read(new File("images/hero.png")));
			images.put('$', ImageIO.read(new File("images/hero.png")));
			images.put('8', ImageIO.read(new File("images/hero.png")));
			images.put('G', ImageIO.read(new File("images/guard.png")));
			images.put('g', ImageIO.read(new File("images/sleep.png")));
			images.put('O', ImageIO.read(new File("images/ogre.png")));
			images.put('*', ImageIO.read(new File("images/club.png")));
			images.put('I', ImageIO.read(new File("images/door_closed.png")));
			images.put('S', ImageIO.read(new File("images/door_open.png")));
			images.put('k', ImageIO.read(new File("images/lever.png")));
			images.put('X', ImageIO.read(new File("images/parede.png")));
			images.put(' ', ImageIO.read(new File("images/espaco.png")));
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
				graphics[i][j] = images.get(' ');
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
		graphics[i][j] = images.get(entity);
	}

	public void updateMap(char[][] map)
	{		
		for(int i = 0; i < map.length;i++)
		{
			for(int j=0; j<map[i].length;j++)
			{
				graphics[i][j] = images.get(map[i][j]);
			}
		}	
	}	
}

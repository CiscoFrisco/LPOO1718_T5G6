package dkeep.gui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import dkeep.logic.Map;

public class GameView extends JPanel{

	private ArrayList<BufferedImage> images;
	private BufferedImage[][] graphics;

	public GameView(Map gameMap, int level) {

		images = new ArrayList<BufferedImage>();

		try {
			images.add(ImageIO.read(this.getClass().getResource("/images/hero.png")));
			images.add(ImageIO.read(this.getClass().getResource("/images/guard.png"))); //guarda
			images.add(ImageIO.read(this.getClass().getResource("/images/sleep.png"))); //guarda a dormir
			images.add(ImageIO.read(this.getClass().getResource("/images/ogre.png"))); //ogre
			images.add(ImageIO.read(this.getClass().getResource("/images/club.png"))); //taco
			images.add(ImageIO.read(this.getClass().getResource("/images/door_closed.png"))); //porta fechada
			images.add(ImageIO.read(this.getClass().getResource("/images/door_open.png"))); //porta aberta
			images.add(ImageIO.read(this.getClass().getResource("/images/lever.png"))); //alavanca
			images.add(ImageIO.read(this.getClass().getResource("/images/key.png"))); //chave
			images.add(ImageIO.read(this.getClass().getResource("/images/parede.png"))); //parede
			images.add(ImageIO.read(this.getClass().getResource("/images/espaco.png")));	//espaco livre
		} 
		catch (IOException e) {
			System.out.println("ola");
		}
		
		initGraphics(10,10);


		if(gameMap != null)
		{
			updateMap(gameMap.layout(), level);
		}
	}


	public void initGraphics(int width, int height)
	{
		graphics = new BufferedImage[height][width];

		for(int i = 0; i < height;i++)
			for(int j = 0;j<width;j++)
				graphics[i][j] = images.get(10);
	}
	
	public BufferedImage[][] getMap()
	{
		return graphics;
	}

	protected void paintComponent(Graphics g) {
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

	public void updatePos(int i, int j, int index)
	{
		graphics[i][j] = images.get(index);
	}

	public void updateMap(char[][] map, int level)
	{		
		for(int i = 0; i < map.length;i++)
		{
			for(int j=0; j<map[i].length;j++)
			{

				if(map[i][j] == 'H' || map[i][j] == 'A' || map[i][j] == 'K')
				{
					graphics[i][j] = images.get(0);
				}
				else if(map[i][j] == 'G')
				{
					graphics[i][j] = images.get(1);
				}
				else if(map[i][j] == 'g')
				{
					graphics[i][j] = images.get(2);
				}
				else if(map[i][j] == 'O')
				{
					graphics[i][j] = images.get(3);
				}
				else if(map[i][j] == '*')
				{
					graphics[i][j] = images.get(4);
				}
				else if(map[i][j] == 'I')
				{
					graphics[i][j] = images.get(5);
				}
				else if(map[i][j] == 'S')
				{
					graphics[i][j] = images.get(6);
				}
				else if(map[i][j] == 'k')
				{
					if(level == 1)
						graphics[i][j] = images.get(7);
					else
						graphics[i][j] = images.get(8);					
				}
				else if(map[i][j] == 'X')
				{
					graphics[i][j] = images.get(9);
				}
				else if(map[i][j] == ' ')
				{
					graphics[i][j] = images.get(10);
				}
			}
		}	
	}	
}

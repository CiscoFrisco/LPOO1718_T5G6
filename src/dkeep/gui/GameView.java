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
		graphics = new BufferedImage[10][10];
		
		try {
			images.add(ImageIO.read(this.getClass().getResource("/images/hero.png")));
			images.add(ImageIO.read(this.getClass().getResource("/images/guard.png"))); //guarda
			images.add(ImageIO.read(this.getClass().getResource("/images/hero.png"))); //guarda a dormir
			images.add(ImageIO.read(this.getClass().getResource("/images/ogre.png"))); //ogre
			images.add(ImageIO.read(this.getClass().getResource("/images/hero.png"))); //taco
			images.add(ImageIO.read(this.getClass().getResource("/images/espaco.png"))); //porta fechada
			images.add(ImageIO.read(this.getClass().getResource("/images/espaco.png"))); //porta aberta
			images.add(ImageIO.read(this.getClass().getResource("/images/hero.png"))); //alavanca
			images.add(ImageIO.read(this.getClass().getResource("/images/hero.png"))); //chave
			images.add(ImageIO.read(this.getClass().getResource("/images/parede.png"))); //parede
			images.add(ImageIO.read(this.getClass().getResource("/images/espaco.png")));	//espaco livre
		} 
		catch (IOException e) {
			System.out.println("ola");
		}
		
		updateMap(gameMap.layout(), level);
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
	
	public void updateMap(char[][] map, int level)
	{		
		for(int i = 0; i < map.length;i++)
		{
			for(int j=0; j<map[i].length;j++)
			{
				switch(map[i][j])
				{
				case 'H':
					graphics[i][j] = images.get(0);
					break;
				case 'G':
					graphics[i][j] = images.get(1);
					break;
				case 'g':
					graphics[i][j] = images.get(2);
					break;
				case 'O':
					graphics[i][j] = images.get(3);
					break;
				case 'X':
					graphics[i][j] = images.get(9);
					break;
				case 'I':
					graphics[i][j] = images.get(5);
					break;
				case 'S':
					graphics[i][j] = images.get(6);
					break;
				case 'k':
					if(level == 1)
						graphics[i][j] = images.get(7);
					else
						graphics[i][j] = images.get(8);	
					break;
				case '*':
					graphics[i][j] = images.get(4);
				case ' ':
					graphics[i][j] = images.get(10);
					break;
				default:
					break;
				}
			}
		}	
	}	
}

package dkeep.gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;

import dkeep.logic.GameState;
import dkeep.logic.Map;

public class Main implements KeyListener{
	
	private CustomKeep customKeep;
	private GameConfig configWindow;
	private JFrame frmDungeonKeep;
	private JButton btnDown;
	private JButton btnUp;
	private JButton btnLeft;
	private JButton btnRight;
	private JButton btnExitGame;
	private JButton btnNewGame;
	private JLabel lblGameStatus;
	private GameView gameView;
	private GameState game;
	private Map map1;
	private Map map2;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frmDungeonKeep.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main() {
		initialize();
	}


	private void disableButtons()
	{
		btnUp.setEnabled(false);
		btnDown.setEnabled(false);
		btnLeft.setEnabled(false);
		btnRight.setEnabled(false);
	}

	private void updateMap()
	{
		gameView.repaint();
	}

	private void generateStatus() {

		Random number = new Random();
		int status = number.nextInt(6);

		switch(status) 
		{
		case 0:
			lblGameStatus.setText("JUST DO IT!!");
			break;
		case 1:
			lblGameStatus.setText("DON'T LET YOUR DREAMS BE DREAMS!");
			break;
		case 2:
			lblGameStatus.setText("YESTERDAY YOU SAID TOMORROW SO... DO IT!!!!");
			break;
		case 3:
			lblGameStatus.setText("( ͡° ͜ʖ ͡°)");
			break;
		case 4:
			lblGameStatus.setText("Why do you even try??!!");
			break;
		case 5:
			lblGameStatus.setText("DISAPOINTEEEEEED!");
			break;
		default:
			break;


		}
	}

	private void gameIteration(char heroMov)
	{
		if(game.gameOver())
			return;
		
		boolean mov = game.issueMov(heroMov, game.hero());

		switch(game.getLevel())
		{
		case 1:
			if(mov)
				game.moveGuard();
			break;
		case 2:
			game.moveOgres();
			game.armOgres();
			game.checkStun();
			break;
		default:
			break;	
		}

		gameView.updateMap(game.getLayout(),game.getLevel());
		updateMap();

		if((game.getLevel() == 1 && game.checkGuard()) || (game.getLevel() == 2 && game.checkClub()))
			lblGameStatus.setText("You lost!");
		else if(game.escaped())
			lblGameStatus.setText("You escaped! Move to go to level 2!");


		if(game.gameOver())
		{
			disableButtons();
			
		}
		else if (game.escaped() && game.getLevel() == 1)
		{
			game.changeLevel(map2, configWindow.numberOfOgres());
		}
		else if(game.escaped() && game.getLevel() == 2)
		{
			lblGameStatus.setText("You won! Congratulations!");
			disableButtons();
		}
		else
		{
			generateStatus();
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmDungeonKeep = new JFrame();
		frmDungeonKeep.setTitle("Dungeon Keep");
		frmDungeonKeep.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				switch(e.getKeyCode())
				{
				case KeyEvent.VK_W:
					gameIteration('w');
					break;
				case KeyEvent.VK_A:
					gameIteration('a');
					break;
				case KeyEvent.VK_S:
					gameIteration('s');
					break;
				case KeyEvent.VK_D:
					gameIteration('d');
					break;
				default:
					break;
				}	
			}
		});
		frmDungeonKeep.setResizable(false);
		frmDungeonKeep.setBounds(100, 100, 611, 477);
		frmDungeonKeep.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmDungeonKeep.getContentPane().setLayout(null);
		
		configWindow = new GameConfig();
		configWindow.setVisible(true);
		customKeep = new CustomKeep();
		customKeep.setVisible(false);
		
		btnExitGame = new JButton("Exit Game");
		btnExitGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		btnExitGame.setBounds(444, 394, 118, 23);
		frmDungeonKeep.getContentPane().add(btnExitGame);


		btnUp = new JButton("Up");
		btnUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frmDungeonKeep.requestFocusInWindow();
				gameIteration('w');
			}
		});
		btnUp.setEnabled(false);
		btnUp.setBounds(424, 242, 89, 23);
		frmDungeonKeep.getContentPane().add(btnUp);

		btnLeft = new JButton("Left");
		btnLeft.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frmDungeonKeep.requestFocusInWindow();
				gameIteration('a');
			}
		});
		btnLeft.setEnabled(false);
		btnLeft.setBounds(374, 276, 89, 23);
		frmDungeonKeep.getContentPane().add(btnLeft);

		btnDown = new JButton("Down");
		btnDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frmDungeonKeep.requestFocusInWindow();
				gameIteration('s');
			}
		});
		btnDown.setEnabled(false);
		btnDown.setBounds(424, 322, 89, 23);
		frmDungeonKeep.getContentPane().add(btnDown);

		btnRight = new JButton("Right");
		btnRight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frmDungeonKeep.requestFocusInWindow();
				gameIteration('d');
			}
		});
		btnRight.setEnabled(false);
		btnRight.setBounds(473, 276, 89, 23);
		frmDungeonKeep.getContentPane().add(btnRight);

		lblGameStatus = new JLabel("Game Status");
		lblGameStatus.setBounds(8, 406, 339, 31);
		frmDungeonKeep.getContentPane().add(lblGameStatus);


		btnNewGame = new JButton("New Game");
		btnNewGame.setBounds(444, 30, 118, 23);
		frmDungeonKeep.getContentPane().add(btnNewGame);
		
		JButton btnConfigureGame = new JButton("Configure Game");
		btnConfigureGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmDungeonKeep.requestFocusInWindow();
				configWindow.setVisible(true);
			}
		});
		btnConfigureGame.setBounds(444, 64, 118, 23);
		frmDungeonKeep.getContentPane().add(btnConfigureGame);
		
		JButton btnEditMap = new JButton("Custom Keep");
		btnEditMap.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frmDungeonKeep.requestFocusInWindow();
				customKeep.setVisible(true);
			}
		});
		btnEditMap.setBounds(444, 102, 117, 23);
		frmDungeonKeep.getContentPane().add(btnEditMap);
		
		JButton btnSaveGame = new JButton("Save Game");
		btnSaveGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frmDungeonKeep.requestFocusInWindow();
				JFileChooser fileChooser = new JFileChooser();
				if (fileChooser.showSaveDialog(frmDungeonKeep) == JFileChooser.APPROVE_OPTION) {
				  File file = fileChooser.getSelectedFile();
				  
				  String map = game.getWritable();
				  int width = game.getLayout()[0].length;
				  int height = game.getLayout().length;
				  
				  try {
					BufferedWriter writer = new BufferedWriter(new FileWriter(file));
					writer.write(height + System.lineSeparator());
					writer.write(width  + System.lineSeparator());
					writer.write(game.getLevel()+ System.lineSeparator());
					writer.write(game.getGuardType() + System.lineSeparator());
					writer.write(game.getGuard().getMovement() + System.lineSeparator());

					writer.write(map);
				
					writer.close();

				} catch (IOException e) {
					e.printStackTrace();
				}
				  
				}
				
			}
		});
		btnSaveGame.setBounds(444, 136, 118, 23);
		frmDungeonKeep.getContentPane().add(btnSaveGame);
		
		JButton btnLoadGame = new JButton("Load Game");
		btnLoadGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frmDungeonKeep.requestFocusInWindow();
				JFileChooser fileChooser = new JFileChooser();
				if (fileChooser.showOpenDialog(frmDungeonKeep) == JFileChooser.APPROVE_OPTION) {
				  File file = fileChooser.getSelectedFile();
				  
				  try {
					BufferedReader reader = new BufferedReader(new FileReader(file));
					int height = Integer.parseInt(reader.readLine());
					int width = Integer.parseInt(reader.readLine());
					int level = Integer.parseInt(reader.readLine());
					String guardType = reader.readLine();
					int index_mov = Integer.parseInt(reader.readLine());
					
					char[][] reading = new char[height][width];
					
					for(int i=0;i<height; i++)
					{
						String line = reader.readLine();
						reading[i] = line.toCharArray();
					}
					
					reader.close();
					
					Map map = new Map(reading);
					gameView = new GameView(map,level);
					gameView.setBounds(18, 61, width*32, height*32);
					frmDungeonKeep.getContentPane().add(gameView);
					
					game = new GameState(map,level, guardType);
					game.getGuard().setMovement(index_mov);
					gameView.repaint();

				} catch (NumberFormatException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				  
				}
			}
		});
		btnLoadGame.setBounds(444, 174, 118, 23);
		frmDungeonKeep.getContentPane().add(btnLoadGame);
		
		
		btnNewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

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


				map1 = new Map(level1);
				gameView = new GameView(map1,1);
				gameView.setBounds(18, 61, 329, 350);
				frmDungeonKeep.getContentPane().add(gameView);
				gameView.repaint();
				map2 = new Map(level2);

				game = new GameState(map1, 1, configWindow.guardPersonality());
				btnUp.setEnabled(true);
				btnLeft.setEnabled(true);
				btnDown.setEnabled(true);
				btnRight.setEnabled(true);

				lblGameStatus.setText("Get ready to RUMBLEEEEE!");
				frmDungeonKeep.requestFocusInWindow();
				//txtrConsole.setText(game.getMap());
			}
		});

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}

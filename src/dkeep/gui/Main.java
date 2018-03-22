package dkeep.gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;

import dkeep.logic.Dungeon;
import dkeep.logic.GameState;
import dkeep.logic.Keep;
import dkeep.logic.Map;

public class Main implements KeyListener
{
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
	private Map map2;
	private int level = 1;
	
	private char[][] level1 = {{'X','X','X','X','X','X','X','X','X','X'} , 
			{'X','H',' ',' ','I',' ','X',' ','G','X'} , 
			{'X','X','X',' ','X','X','X',' ',' ','X'} , 
			{'X',' ','I',' ','I',' ','X',' ',' ','X'} , 
			{'X','X','X',' ','X','X','X',' ',' ','X'} , 
			{'I',' ',' ',' ',' ',' ',' ',' ',' ','X'} , 
			{'I',' ',' ',' ',' ',' ',' ',' ',' ','X'} , 
			{'X','X','X',' ','X','X','X','X',' ','X'} , 
			{'X',' ','I',' ','I',' ','X','k',' ','X'} , 
			{'X','X','X','X','X','X','X','X','X','X'}};

	private char[][] level2 = {{'X','X','X','X','X','X','X','X','X','X'} , 
			{'I',' ',' ',' ',' ',' ',' ',' ','k','X'} , 
			{'X',' ',' ',' ',' ',' ',' ',' ',' ','X'} , 
			{'X',' ',' ',' ',' ',' ',' ',' ',' ','X'} , 
			{'X',' ',' ',' ',' ',' ',' ',' ',' ','X'} , 
			{'X',' ',' ',' ',' ',' ',' ',' ',' ','X'} , 
			{'X',' ',' ',' ',' ',' ',' ',' ',' ','X'} , 
			{'X',' ',' ',' ',' ',' ',' ',' ',' ','X'} , 
			{'X','A',' ',' ',' ',' ',' ',' ',' ','X'} , 
			{'X','X','X','X','X','X','X','X','X','X'}};


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


	private void btnSetEnabled(boolean state)
	{
		btnUp.setEnabled(state);
		btnDown.setEnabled(state);
		btnLeft.setEnabled(state);
		btnRight.setEnabled(state);
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
		if(game.level().gameOver())
			return;

		game.level().issueMov(heroMov, game.level().hero());

		game.moveEnemy();

		gameView.updateMap(game.level().map().layout());
		updateMap();

		if(game.checkEnemy())
			lblGameStatus.setText("You lost!");
		else if(game.escaped())
			lblGameStatus.setText("You escaped! Move to go to level 2!");

		if(game.level().gameOver())
			btnSetEnabled(false);
		else if (game.escaped() && level  == 1)
		{
			game.setLevel(new Keep(map2, configWindow.numberOfOgres()));
			level++;
		}
		else if(game.escaped() && level == 2)
		{
			lblGameStatus.setText("You won! Congratulations!");
			btnSetEnabled(false);
		}
		else
			generateStatus();
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
					/*
					gameView = new GameView(map,level);
					gameView.setBounds(18, 61, width*32, height*32);
					frmDungeonKeep.getContentPane().add(gameView);

					game = new GameState(map,level, guardType);
					game.getGuard().setMovement(index_mov);
					gameView.repaint();*/

				}
			}
		});
		btnLoadGame.setBounds(444, 174, 118, 23);
		frmDungeonKeep.getContentPane().add(btnLoadGame);


		btnNewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				Map map1 = new Map(level1);
				gameView = new GameView(map1,1);
				gameView.setBounds(18, 61, 329, 350);
				frmDungeonKeep.getContentPane().add(gameView);
				gameView.repaint();
				map2 = new Map(level2);

				game = new GameState(new Dungeon(map1, configWindow.guardPersonality()));
				btnSetEnabled(true);

				lblGameStatus.setText("Get ready to RUMBLEEEEE!");
				frmDungeonKeep.requestFocusInWindow();
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

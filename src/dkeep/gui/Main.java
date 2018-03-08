package dkeep.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import dkeep.logic.GameState;
import dkeep.logic.Map;

import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.Random;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import java.awt.Font;
import java.awt.Color;

public class Main {

	private JFrame frame;
	private JTextField textNumberOfOgres;
	private JButton btnDown;
	private JButton btnUp;
	private JButton btnLeft;
	private JButton btnRight;
	private JButton btnExitGame;
	private JButton btnNewGame;
	private JLabel lblGameStatus;
	private JLabel lblNumberOfOgres;
	private JLabel lblGuardPersonality;
	private JComboBox comboBox;
	private JTextArea txtrConsole;
	private GameState game;
	private JLabel invalidNumber;
	private Map map1;
	private Map map2;
	private int numberOfOgres;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frame.setVisible(true);
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
		txtrConsole.setText(game.getMap());
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

		updateMap();

		if((game.getLevel() == 1 && game.checkGuard()) || (game.getLevel() == 2 && game.checkClub()))
			lblGameStatus.setText("You lost!");
		else if(game.escaped())
			lblGameStatus.setText("You escaped! Move to go to level 2!");


		if(game.gameOver())
			disableButtons();
		else if (game.escaped() && game.getLevel() == 1)
		{
			game.changeLevel(map2, numberOfOgres);
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
		frame = new JFrame();
		frame.setBounds(100, 100, 571, 384);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		lblNumberOfOgres = new JLabel("Number of Ogres");
		lblNumberOfOgres.setBounds(8, 11, 108, 14);
		lblNumberOfOgres.setHorizontalAlignment(SwingConstants.LEFT);
		frame.getContentPane().add(lblNumberOfOgres);

		textNumberOfOgres = new JTextField();
		textNumberOfOgres.setBounds(125, 8, 86, 20);
		frame.getContentPane().add(textNumberOfOgres);
		textNumberOfOgres.setColumns(10);

		lblGuardPersonality = new JLabel("Guard Personality");
		lblGuardPersonality.setBounds(8, 36, 108, 14);
		frame.getContentPane().add(lblGuardPersonality);

		String[] personalities = {"Rookie", "Drunken", "Suspicious"};

		comboBox = new JComboBox(personalities);
		comboBox.setBounds(125, 33, 86, 20);
		frame.getContentPane().add(comboBox);

		btnExitGame = new JButton("Exit Game");
		btnExitGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		btnExitGame.setBounds(395, 277, 118, 23);
		frame.getContentPane().add(btnExitGame);

		txtrConsole = new JTextArea();
		txtrConsole.setFont(new Font("Courier New", Font.PLAIN, 20));
		txtrConsole.setBounds(18, 61, 329, 248);
		frame.getContentPane().add(txtrConsole);

		btnUp = new JButton("Up");
		btnUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				gameIteration('w');
			}
		});
		btnUp.setEnabled(false);
		btnUp.setBounds(405, 131, 89, 23);
		frame.getContentPane().add(btnUp);

		btnLeft = new JButton("Left");
		btnLeft.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				gameIteration('a');
			}
		});
		btnLeft.setEnabled(false);
		btnLeft.setBounds(357, 165, 89, 23);
		frame.getContentPane().add(btnLeft);

		btnDown = new JButton("Down");
		btnDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				gameIteration('s');
			}
		});
		btnDown.setEnabled(false);
		btnDown.setBounds(405, 199, 89, 23);
		frame.getContentPane().add(btnDown);

		btnRight = new JButton("Right");
		btnRight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				gameIteration('d');
			}
		});
		btnRight.setEnabled(false);
		btnRight.setBounds(456, 165, 89, 23);
		frame.getContentPane().add(btnRight);

		lblGameStatus = new JLabel("Game Status");
		lblGameStatus.setBounds(8, 314, 339, 31);
		frame.getContentPane().add(lblGameStatus);


		btnNewGame = new JButton("New Game");
		btnNewGame.setBounds(395, 59, 118, 23);
		frame.getContentPane().add(btnNewGame);

		invalidNumber = new JLabel("Invalid number of ogres!");
		invalidNumber.setForeground(Color.RED);
		invalidNumber.setVisible(false);
		invalidNumber.setBounds(221, 11, 161, 14);

		frame.getContentPane().add(invalidNumber);
		btnNewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				numberOfOgres = Integer.parseInt(textNumberOfOgres.getText());

				if(numberOfOgres<1 || numberOfOgres>3)
				{
					invalidNumber.setVisible(true);
					return;
				}
				else
					invalidNumber.setVisible(false);	

				String personality = comboBox.getSelectedItem().toString();

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
				map2 = new Map(level2);

				game = new GameState(map1, 1, personality);

				btnUp.setEnabled(true);
				btnLeft.setEnabled(true);
				btnDown.setEnabled(true);
				btnRight.setEnabled(true);

				lblGameStatus.setText("Get ready to RUMBLEEEEE!");

				txtrConsole.setText(game.getMap());
			}
		});
	}
}

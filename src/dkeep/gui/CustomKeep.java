package dkeep.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import dkeep.logic.Map;
import dkeep.logic.Position;


public class CustomKeep extends JDialog {

	private int piece_width = 32;
	private final JPanel contentPanel = new JPanel();
	private int width = 10;
	private int height = 10;
	private int current_line;
	private int current_col;
	private char current_char = ' ';
	private int numOgres = 0;
	private GameView editable = new GameView(null, 2);
	private boolean signal;

	private Map gameMap;
	private TreeMap<Position, Character> positions = new TreeMap<Position, Character>();
	private JButton btnKey; 
	private JButton btnOgre; 
	private JButton btnHero; 
	private JButton btnDoor; 
	private JPanel buttonPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			CustomKeep dialog = new CustomKeep();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Map getMap()
	{		
		gameMap = new Map(editable.getGameMap());

		return gameMap;
	}

	public void generateMap() {
		editable.setBounds(20, 60, 320, 320);
		editable.initGraphics(width, height);


		for(int i=0;i<height;i++)
		{
			editable.updatePos(i, 0, 'X');
			editable.updatePos(i, width-1, 'X');
		}

		for(int j=0;j<width;j++)
		{
			editable.updatePos(0, j, 'X');
			editable.updatePos(height-1, j, 'X');
		}

		editable.repaint();

	}

	private void enableButton(char ent)
	{
		if(ent == 'A')
			btnHero.setEnabled(true);
		else if(ent == 'k')
			btnKey.setEnabled(true);
		else if(ent == 'I')
			btnDoor.setEnabled(true);
		else if(ent == 'O')
		{
			numOgres--;
			btnOgre.setEnabled(true);
		}
	}


	private void initWidthSel(Integer[] sizes)
	{
		JComboBox<Integer> widthSel = new JComboBox<Integer>();
		widthSel.setBounds(62, 11, 42, 20);
		widthSel.setModel(new DefaultComboBoxModel<Integer>(sizes));
		widthSel.setSelectedItem(10);
		widthSel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					width =  Integer.parseInt(widthSel.getSelectedItem().toString());
					generateMap();
				}catch(NumberFormatException ex){

				}
			}
		});
		contentPanel.add(widthSel);
	}

	private void initHeightSel(Integer[] sizes)
	{
		JComboBox<Integer> heightSel = new JComboBox<Integer>();
		heightSel.setBounds(165, 12, 47, 20);
		heightSel.setModel(new DefaultComboBoxModel<Integer>(sizes));
		heightSel.setSelectedItem(10);
		heightSel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					height =  Integer.parseInt(heightSel.getSelectedItem().toString());
					generateMap();
				}catch(NumberFormatException ex){

				}
			}
		});
		contentPanel.add(heightSel);
	}

	private void initBtnHero()
	{
		btnHero = new JButton("Hero");
		btnHero.setBounds(375, 60, 89, 23);
		btnHero.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				current_char = 'A';
			}
		});
		contentPanel.add(btnHero);
	}

	private void initBtnOgre()
	{
		btnOgre = new JButton("Ogre");
		btnOgre.setBounds(375, 137, 89, 23);
		btnOgre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				current_char = 'O';
			}
		});
		contentPanel.add(btnOgre);
	}

	private void initBtnDoor()
	{
		btnDoor = new JButton("Door");
		btnDoor.setBounds(375, 284, 89, 23);
		btnDoor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				current_char = 'I';
			}
		});
		contentPanel.add(btnDoor);
	}

	private void initBtnWall()
	{
		JButton btnWall = new JButton("Wall");
		btnWall.setBounds(375, 357, 89, 23);
		btnWall.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				current_char = 'X';
			}
		});
		contentPanel.add(btnWall);
	}

	private void initBtnKey()
	{
		btnKey = new JButton("Key");
		btnKey.setBounds(375, 215, 89, 23);
		btnKey.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				current_char = 'k';
			}
		});
		contentPanel.add(btnKey);
	}

	private boolean isInsidePerimeter()
	{
		if(current_col == 0 || current_col == width - 1 || current_line == 0 || current_line == height - 1)
			return false;
		else
			return true;
	}

	private boolean checkEntitiesPosition() 
	{
		if(current_char!='I' && isInsidePerimeter())
			return true;
		else if(current_char == 'I' && !isInsidePerimeter())
		{
			Position curr_pos = new Position(current_line, current_col);

			if(curr_pos.equals(new Position(0,0)) || curr_pos.equals(new Position(height-1,width-1)) ||
					curr_pos.equals(new Position(height-1,0)) || curr_pos.equals(new Position(0,width-1)))
				return false;
			else 
				return true;
		}

		return false;

	}
	
	public boolean signal()
	{
		return signal;
	}

	private void disableBtn()
	{
		if(current_char == 'A')
		{
			btnHero.setEnabled(false);
			current_char = ' ';
		}
		else if(current_char == 'k')
		{
			btnKey.setEnabled(false);
			current_char = ' ';

		}
		else if(current_char == 'I')
		{
			btnDoor.setEnabled(false);
			current_char = ' ';
		}
		else if(current_char == 'O')
		{
			numOgres++;

			if(numOgres==3)
			{
				btnOgre.setEnabled(false);
				current_char = ' ';
			}
		}
	}

	private boolean checkEntities()
	{
		boolean hero = false, ogre = false, key = false, door = false;

		for(Entry<Position, Character> entry : positions.entrySet())
		{
			Character ent = entry.getValue();

			if(ent == 'A')
				hero = true;
			else if (ent == 'O')
				ogre = true;
			else if(ent == 'I')
				door = true;
			else if(ent == 'k')
				key = true;
		}

		return hero && ogre && key && door;
	}

	private void initOkBtn()
	{
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if(!checkEntities())
					return;

				signal = true;
				dispose();
			}
		});
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);
	}

	private void initCancelBtn()
	{
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				signal = false;
				dispose();
			}
		});
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);
	}

	private void initLabels()
	{

		JLabel lblHeight = new JLabel("Height");
		lblHeight.setBounds(114, 12, 81, 20);
		contentPanel.add(lblHeight);

		JLabel lblNewLabel = new JLabel("Width");
		lblNewLabel.setBounds(10, 14, 42, 17);
		contentPanel.add(lblNewLabel);

	}
	
	private void dblClickHandler()
	{
		if(!isInsidePerimeter())
			editable.updatePos(current_line, current_col, 'X');
		else
			editable.updatePos(current_line, current_col, ' ');

		Character ent = positions.get(new Position(current_line, current_col));
		if(ent != null)
			enableButton(ent);

		editable.repaint();
	}
	
	private void sglClickHandler()
	{
		if(current_char!=' ')
		{
			if(!checkEntitiesPosition())
				return;

			editable.updatePos(current_line, current_col, current_char);
			editable.repaint();
		}

		Character ent = positions.get(new Position(current_line, current_col));
		
		if(ent != null && editable.getGameMap()[current_line][current_col]==ent)
			return;
			
		if(ent!= null)
			enableButton(ent);

		positions.put(new Position(current_line, current_col), current_char);

		disableBtn();
	}

	/**
	 * Create the dialog.
	 */
	public CustomKeep() {
		setModal(true);
		setTitle("Custom Keep");
		setBounds(100, 100, 513, 484);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		initLabels();

		Integer[] sizes = {5,6,7,8,9,10};
		initHeightSel(sizes);
		initWidthSel(sizes);

		initBtnDoor();
		initBtnHero();
		initBtnKey();
		initBtnOgre();
		initBtnWall();
		initEditable();
		initBtnPane();
	}

	private void initEditable() {
		editable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				current_line = e.getY()/piece_width;
				current_col = e.getX()/piece_width;

				if(e.getClickCount()==2)
					dblClickHandler();
				else if(e.getClickCount() == 1)
					sglClickHandler();
			}
		});
		generateMap();
		contentPanel.add(editable);
	}

	private void initBtnPane() {
		buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		initOkBtn();
		initCancelBtn();	
	}
}

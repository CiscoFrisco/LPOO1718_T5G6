package dkeep.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.TreeMap;
import java.util.ArrayList;
import java.util.Map.Entry;

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
	private char current_char;
	private int numOgres = 0;
	GameView editable = new GameView(null, 2);
	private TreeMap<Position, Character> positions = new TreeMap<Position, Character>();

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
		char[][] layout = new char[height][width];

		for(int i = 0; i< height; i++)
			for(int j = 0; j< width; j++)
			{
				layout[i][j] = positions.get(new Position(i,j));
				if(layout[i][j] == 'O')
					numOgres++;
			}

		return new Map(layout);
	}

	public int numberOfOgres()
	{
		return numOgres;
	}

	public void generateMap() {
		editable.initGraphics(width, height);
		editable.setBounds(20,60,width*32,height*32);


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

	/**
	 * Create the dialog.
	 */
	public CustomKeep() {
		setTitle("Custom Keep");
		setBounds(100, 100, 513, 484);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblHeight = new JLabel("Height");
			lblHeight.setBounds(114, 12, 81, 20);
			contentPanel.add(lblHeight);
		}
		{
			JLabel lblNewLabel = new JLabel("Width");
			lblNewLabel.setBounds(10, 14, 42, 17);
			contentPanel.add(lblNewLabel);
		}
		Integer[] sizes = {5,6,7,8,9,10};
		JComboBox<Integer> widthSel = new JComboBox<Integer>(sizes);
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
		widthSel.setBounds(62, 11, 42, 20);
		contentPanel.add(widthSel);

		JComboBox<Integer> heightSel = new JComboBox<Integer>(sizes);
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
		heightSel.setBounds(165, 12, 47, 20);
		contentPanel.add(heightSel);

		JButton btnHero = new JButton("Hero");
		btnHero.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				current_char = 'A';
			}
		});
		btnHero.setBounds(398, 70, 89, 23);
		contentPanel.add(btnHero);

		JButton btnOgre = new JButton("Ogre");
		btnOgre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				current_char = 'O';
			}
		});
		btnOgre.setBounds(398, 135, 89, 23);
		contentPanel.add(btnOgre);

		JButton btnKey = new JButton("Key");
		btnKey.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				current_char = 'k';
			}
		});
		btnKey.setBounds(398, 198, 89, 23);
		contentPanel.add(btnKey);

		JButton btnDoor = new JButton("Door");
		btnDoor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				current_char = 'I';
			}
		});
		btnDoor.setBounds(398, 257, 89, 23);
		contentPanel.add(btnDoor);

		JButton btnWall = new JButton("Wall");
		btnWall.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				current_char = 'X';
			}
		});
		btnWall.setBounds(398, 324, 89, 23);
		contentPanel.add(btnWall);

		editable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				current_line = e.getY()/piece_width;
				current_col = e.getX()/piece_width;

				if(e.getClickCount()==2)
				{
					editable.updatePos(current_line, current_col, ' ');
					Character ent = positions.get(new Position(current_line, current_col));
					if(ent != null)
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
					editable.repaint();
				}
				else if(e.getClickCount() == 1)
				{
					if(current_char!=' ')
					{
						editable.updatePos(current_line, current_col, current_char);
						editable.repaint();
					}

					Character ent = positions.get(new Position(current_line, current_col));

					if(ent!=null)
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

					positions.put(new Position(current_line, current_col), current_char);

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

			}
		});

		editable.setBounds(20, 60, 320, 320);
		generateMap();
		contentPanel.add(editable);

		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						boolean hero = false;
						boolean ogre = false;
						boolean key = false;
						boolean door = false;

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

						if(!(hero && ogre && key && door))
							return;

						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}

	}
}

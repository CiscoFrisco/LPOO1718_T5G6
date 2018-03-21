package dkeep.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;


public class CustomKeep extends JDialog {

	int piece_width = 32;
	private final JPanel contentPanel = new JPanel();
	int width;
	int height;
	int current_line;
	int current_col;
	int current_char;
	GameView editable = new GameView(null, 2);

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

	public void generateMap() {
		editable.initGraphics(width, height);
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
		String[] sizes = {"5","6","7","8","9","10"};
		JComboBox comboBox = new JComboBox(sizes);
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					JComboBox cb = (JComboBox) e.getSource();
					width =  Integer.parseInt(cb.getSelectedItem().toString());
					generateMap();
				}catch(NumberFormatException ex){

				}
			}
		});
		comboBox.setBounds(62, 11, 42, 20);
		contentPanel.add(comboBox);

		JComboBox comboBox_1 = new JComboBox(sizes);
		comboBox_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					JComboBox cb = (JComboBox) e.getSource();
					height =  Integer.parseInt(cb.getSelectedItem().toString());
					generateMap();
				}catch(NumberFormatException ex){

				}
			}
		});
		comboBox_1.setBounds(165, 12, 47, 20);
		contentPanel.add(comboBox_1);

		JButton btnHero = new JButton("Hero");
		btnHero.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				current_char = 0;
			}
		});
		btnHero.setBounds(398, 70, 89, 23);
		contentPanel.add(btnHero);

		JButton btnOgre = new JButton("Ogre");
		btnOgre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				current_char = 3;
			}
		});
		btnOgre.setBounds(398, 135, 89, 23);
		contentPanel.add(btnOgre);

		JButton btnKey = new JButton("Key");
		btnKey.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				current_char = 8;
			}
		});
		btnKey.setBounds(398, 198, 89, 23);
		contentPanel.add(btnKey);

		JButton btnDoor = new JButton("Door");
		btnDoor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				current_char = 5;
			}
		});
		btnDoor.setBounds(398, 257, 89, 23);
		contentPanel.add(btnDoor);

		JButton btnWall = new JButton("Wall");
		btnWall.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				current_char = 9;
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
					editable.updatePos(current_line, current_col, 10);
					editable.repaint();
				}
				else if(e.getClickCount() == 1)
				{
					editable.updatePos(current_line, current_col, current_char);
					editable.repaint();
				}

			}
		});

		editable.setBounds(20, 60, 320, 320);
		contentPanel.add(editable);

		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						//verificar mapa
						
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

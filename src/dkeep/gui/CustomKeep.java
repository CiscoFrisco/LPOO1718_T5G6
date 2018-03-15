package dkeep.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextField;

public class CustomKeep extends JDialog {

	private final JPanel contentPanel = new JPanel();

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
			lblHeight.setBounds(100, 11, 81, 20);
			contentPanel.add(lblHeight);
		}
		{
			JLabel lblNewLabel = new JLabel("Width");
			lblNewLabel.setBounds(10, 14, 42, 17);
			contentPanel.add(lblNewLabel);
		}
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(62, 11, 28, 20);
		contentPanel.add(comboBox);
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setBounds(151, 11, 28, 20);
		contentPanel.add(comboBox_1);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 55, 294, 315);
		contentPanel.add(panel);
		
		JButton btnHero = new JButton("Hero");
		btnHero.setBounds(398, 70, 89, 23);
		contentPanel.add(btnHero);
		
		JButton btnOgre = new JButton("Ogre");
		btnOgre.setBounds(398, 135, 89, 23);
		contentPanel.add(btnOgre);
		
		JButton btnKey = new JButton("Key");
		btnKey.setBounds(398, 198, 89, 23);
		contentPanel.add(btnKey);
		
		JButton btnDoor = new JButton("Door");
		btnDoor.setBounds(398, 257, 89, 23);
		contentPanel.add(btnDoor);
		
		JButton btnWall = new JButton("Wall");
		btnWall.setBounds(398, 324, 89, 23);
		contentPanel.add(btnWall);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}

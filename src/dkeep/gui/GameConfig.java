package dkeep.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GameConfig extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textNumberOfOgres;
	private int numberOfOgres = 1;
	private String guardPersonality = "Rookie";
	private JComboBox<String> comboBox;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			GameConfig dialog = new GameConfig();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int numberOfOgres()
	{
		return numberOfOgres;
	}
	
	public String guardPersonality()
	{
		return guardPersonality;
	}

	/**
	 * Create the dialog.
	 */
	public GameConfig() {
		setTitle("Configuration");
		setBounds(100, 100, 374, 256);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblNumberOfOgres = new JLabel("Number of ogres: ");
			lblNumberOfOgres.setFont(new Font("Tahoma", Font.PLAIN, 16));
			lblNumberOfOgres.setBounds(23, 40, 164, 52);
			contentPanel.add(lblNumberOfOgres);
		}
		{
			JLabel lblGuardPersonality = new JLabel("Guard personality: ");
			lblGuardPersonality.setFont(new Font("Tahoma", Font.PLAIN, 16));
			lblGuardPersonality.setBounds(23, 103, 164, 44);
			contentPanel.add(lblGuardPersonality);
		}
		{
			String[] personalities = {"Rookie", "Drunken", "Suspicious"};

			comboBox = new JComboBox<String>(personalities);
			comboBox.setBounds(197, 117, 88, 20);
			contentPanel.add(comboBox);
		}
		{
			textNumberOfOgres = new JTextField();
			textNumberOfOgres.setBounds(199, 58, 86, 20);
			contentPanel.add(textNumberOfOgres);
			textNumberOfOgres.setColumns(10);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {

						try {
							numberOfOgres = Integer.parseInt(textNumberOfOgres.getText());
						}
						catch(NumberFormatException ex){
							JOptionPane.showMessageDialog(contentPanel, "Insert a valid number of Ogres!");
							return;
						}

						if(numberOfOgres<1 || numberOfOgres>3)
						{
							JOptionPane.showMessageDialog(contentPanel, "Insert a valid number of Ogres!");
							return;
						}
						
						guardPersonality = comboBox.getSelectedItem().toString();
						
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

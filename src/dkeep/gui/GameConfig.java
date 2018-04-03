package dkeep.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import dkeep.logic.Map;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dialog.ModalExclusionType;
import javax.swing.SwingConstants;

public class GameConfig extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textNumberOfOgres;
	private int numberOfOgres = 1;
	private String guardPersonality = "Rookie";
	private JComboBox<String> comboBox;
	private JLabel lblNumberOfOgres;
	private CustomKeep customKeep;
	private JButton btnNormal;
	private JButton btnCustom;
	private JButton okButton; 
	private JPanel buttonPane;
	private boolean normal;
	
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
		setModal(true);
		setTitle("Configuration");
		setBounds(100, 100, 468, 344);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		initLabelNumber();
		initLabelGuard();
		initComboBox();
		initTextNumber();
		initLabelDungeon();
		
		customKeep = new CustomKeep();
		customKeep.setVisible(false);
		
		initLabelKeep();
		initBtnNormal();
		initBtnCustom();
		{
			buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			initOkBtn();
		}
	}

	private void initOkBtn() {
		okButton = new JButton("OK");
		okButton.setEnabled(false);
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				guardPersonality = comboBox.getSelectedItem().toString();
				
				if(normal)
				{
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
				}
				else if(!customKeep.signal())
				{
					JOptionPane.showMessageDialog(contentPanel, "No valid custom map!");
					return;
				}
				
				dispose();
			}
		});
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);
		
	}

	private void initBtnCustom() {
		btnCustom = new JButton("Custom");
		btnCustom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				normal = false;
				okButton.setEnabled(true);
				customKeep.setVisible(true);
			}
		});
		btnCustom.setBounds(278, 149, 89, 23);
		contentPanel.add(btnCustom);
	}

	private void initBtnNormal() {
		btnNormal = new JButton("Normal");
		btnNormal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				normal = true;
				okButton.setEnabled(true);
				lblNumberOfOgres.setVisible(true);
				textNumberOfOgres.setVisible(true);
			}
		});
		btnNormal.setBounds(58, 149, 89, 23);
		contentPanel.add(btnNormal);
	}

	private void initLabelKeep() {
		JLabel lblKeep = new JLabel("Keep");
		lblKeep.setHorizontalAlignment(SwingConstants.CENTER);
		lblKeep.setFont(new Font("Tw Cen MT Condensed Extra Bold", Font.PLAIN, 24));
		lblKeep.setBounds(155, 99, 117, 31);
		contentPanel.add(lblKeep);
	}

	private void initLabelDungeon() {
		JLabel lblNewLabel = new JLabel("Dungeon");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tw Cen MT Condensed Extra Bold", Font.PLAIN, 24));
		lblNewLabel.setBounds(155, 16, 117, 31);
		contentPanel.add(lblNewLabel);
	}

	private void initTextNumber() {
		textNumberOfOgres = new JTextField();
		textNumberOfOgres.setBounds(137, 191, 86, 20);
		contentPanel.add(textNumberOfOgres);
		textNumberOfOgres.setColumns(10);
		textNumberOfOgres.setVisible(false);
	}

	private void initComboBox() {
		String[] personalities = {"Rookie", "Drunken", "Suspicious"};

		comboBox = new JComboBox<String>();
		comboBox.setModel(new DefaultComboBoxModel<String>(personalities));
		comboBox.setBounds(223, 58, 86, 20);
		contentPanel.add(comboBox);
	}

	private void initLabelGuard() {
		JLabel lblGuardPersonality = new JLabel("Guard personality: ");
		lblGuardPersonality.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblGuardPersonality.setBounds(70, 44, 128, 44);
		contentPanel.add(lblGuardPersonality);
	}

	private void initLabelNumber() {
		lblNumberOfOgres = new JLabel("Number of ogres: ");
		lblNumberOfOgres.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNumberOfOgres.setBounds(10, 184, 117, 31);
		lblNumberOfOgres.setVisible(false);
		contentPanel.add(lblNumberOfOgres);
	}

	public Map getMap() 
	{
		return customKeep.getMap();
	}
	
	public boolean getMode()
	{
		return normal;
	}
}

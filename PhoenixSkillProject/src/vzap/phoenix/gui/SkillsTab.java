package vzap.phoenix.gui;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;

public class SkillsTab extends JPanel
{
	private JTextField textField;
	private JLabel lblTest;
	private JButton btnNewButton;

	/**
	 * Create the panel.
	 */
	public SkillsTab()
	{
		setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(228, 130, 86, 20);
		add(textField);
		textField.setColumns(10);
		
		lblTest = new JLabel("Test");
		lblTest.setBounds(72, 133, 46, 14);
		add(lblTest);
		
		btnNewButton = new JButton("New button");
		btnNewButton.setBounds(141, 206, 89, 23);
		add(btnNewButton);

	}
}

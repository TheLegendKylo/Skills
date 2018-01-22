package vzap.phoenix.testing;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.Font;

public class EmployeeLogin extends JPanel
{
	private static final long serialVersionUID = 1L;
	private EmployeeSkillGUI controller = null;

	private JPanel contentPanel = null, buttonPanel = null, headerPanel = null;
	private JLabel employeeIDLabel = null, passwordLabel; 
	private JButton submitButton = null, exitButton = null, clearButton = null;
	private JLabel screenName = null; 
	
	private JTextField employeeIDField = null, passwordField = null;
	String errorMsg = null;
	
	public EmployeeLogin(EmployeeSkillGUI controller)
	{
		this.controller = controller;

		employeeIDLabel = new JLabel("Enter EmployeeID: ");
		employeeIDLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		passwordLabel = new JLabel("Enter Password: ");
		passwordLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		this.employeeIDField = new JTextField(8);
		employeeIDField.setFont(new Font("Tahoma", Font.BOLD, 14));
		this.passwordField = new JTextField(8);
		passwordField.setFont(new Font("Tahoma", Font.BOLD, 14));

		
		GroupLayout gl_contentPanel = new GroupLayout(this);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(217)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING)
						.addComponent(employeeIDLabel)
						.addComponent(passwordLabel))
					.addGap(33)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING)
						.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(employeeIDField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(143))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(35)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(employeeIDLabel)
						.addComponent(employeeIDField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(27)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(passwordLabel)
						.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(421, Short.MAX_VALUE))
		);
		this.setLayout(gl_contentPanel);
		
	}
	public String getEmployeeID()
	{
		return this.employeeIDField.getText();
	}
	public String getPassword()
	{
		return this.passwordField.getText();
	}

}

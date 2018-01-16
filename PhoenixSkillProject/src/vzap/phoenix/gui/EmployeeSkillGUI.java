package vzap.phoenix.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import vzap.phoenix.Server.Employee.*;
import vzap.phoenix.client.EmpSkillClient;
import vzap.phoenix.client.EmpSkillClientController;

public class EmployeeSkillGUI extends JFrame implements ActionListener
{
	private JPanel contentPanel, headerPanel, buttonPanel;
	private Employee logonEmp;
	private EmpSkillClient empClient;
	private EmpSkillClientController empClientController;
	private ArrayList<Skill> skillList;
	private ArrayList<SkillStage> skillStageList;
	private ArrayList<EmployeeSkill> empSkillList;

	private EmployeeLogin loginPanel;
	private JButton baseButton, submitButton;
	private String errorMsg;
	
	public EmployeeSkillGUI()
	{
		this.setTitle("Employee Skills Management");
		this.setSize(800, 600);
		this.setLocation(150, 150);

		loginPanel = new EmployeeLogin(this);
		contentPanel.add(loginPanel);

		this.getContentPane().add(contentPanel, BorderLayout.CENTER);
		this.getContentPane().add(headerPanel, BorderLayout.NORTH);
		this.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		this.setVisible(true);		

		MyWindowAdapter mwl = new MyWindowAdapter();
		this.addWindowListener(mwl);
		
	}
	@Override
	public void actionPerformed(ActionEvent e)
	{
		// Trap LoginUser
		Object source = e.getSource();
		String selectedMenuItem = e.getActionCommand();
		baseButton = new JButton();
		if(source.getClass()==baseButton.getClass())
		{
			baseButton = (JButton)source;
		}
		if((e.getSource()==submitButton)
				|| (baseButton.getText()=="Submit"))
		{
			String employeeID = loginPanel.getEmployeeID();
			String passwordInput = loginPanel.getPassword();
			String checkPassword = "";
			if(employeeID.equals(""))
			{
				errorMsg = "Invalid EmployeeID entered.  Please reenter: ";
			} else {
				if (passwordInput.equals(""))
				{
					errorMsg = "Invalid Password entered.  Please reenter: ";
//					this.failedMessage(passwordField, errorMsg);
					return;
				} 
				empClientController = new EmpSkillClientController(employeeID, passwordInput);
				logonEmp = empClientController.getEmployee();
		
				if(logonEmp == null)
				{
					errorMsg = "Invalid EmployeeID entered.  Please reenter: ";
//					this.failedMessage(userIDField, errorMsg);
					return;
				}

				// Render profile screeen
				this.validate();
				this.repaint();	
				this.setVisible(true);	
			}
		}
	}
	
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		new EmployeeSkillGUI();
	}
	class MyWindowAdapter extends WindowAdapter
	{
		public void windowClosing(WindowEvent e)
		{
			setVisible(false);	
			System.exit(0);
		}
	}
}

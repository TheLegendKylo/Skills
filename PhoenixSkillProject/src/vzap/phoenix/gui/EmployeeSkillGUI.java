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
	private Employee logonEmployee;
	private EmpSkillClient empClient;
	private EmpSkillClientController empClientController;
	private EmpSkillClient loginSession;
	private ArrayList<Skill> skillList;
	private ArrayList<Level> levelList;
	private ArrayList<EmployeeSkill> empSkillList;
	
	private TestingStaticData staticDataPanel;

	private EmployeeLogin loginPanel;
	private JButton baseButton, submitButton;
	private String errorMsg;
	
	public EmployeeSkillGUI()
	{
		this.setTitle("Phoenix Skills Management");
		this.setSize(800, 600);
		this.setLocation(150, 50);

		contentPanel = new JPanel();
		headerPanel = new JPanel();
		buttonPanel = new JPanel();
		
		loginPanel = new EmployeeLogin(this);
		contentPanel.add(loginPanel);
		
		submitButton = new JButton("Submit");
		submitButton.addActionListener(this);
		buttonPanel.add(submitButton);
		
		this.getContentPane().add(contentPanel, BorderLayout.CENTER);
		this.getContentPane().add(headerPanel, BorderLayout.NORTH);
		this.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		this.validate();
		this.repaint();
		this.setVisible(true);		

		MyWindowAdapter mwl = new MyWindowAdapter();
		this.addWindowListener(mwl);
		
	}
	public void loadStaticDataPanel()
	{
		staticDataPanel = new TestingStaticData(loginSession);
		this.validate();
		this.repaint();
		contentPanel.removeAll();
		contentPanel.setLayout(new BorderLayout());
		contentPanel.add(staticDataPanel);
		
		this.validate();
		this.repaint();
		this.setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e)
	{
		// Trap LoginUser
System.out.println("Into actionPerformed");
		Object source = e.getSource();
		if((e.getSource()==submitButton))
		{
			System.out.println("Submit button pressed");
			String employeeID = loginPanel.getEmployeeID();
			String password = loginPanel.getPassword();
			String checkPassword = "";
			if(employeeID.equals(""))
			{
				errorMsg = "Invalid EmployeeID entered.  Please reenter: ";
			} else {
				if (password.equals(""))
				{
					errorMsg = "Invalid Password entered.  Please reenter: ";
//					this.failedMessage(passwordField, errorMsg);
					return;
				} 
				loginSession = new EmpSkillClient();
				String returnMessage = loginSession.loginEmployee(employeeID, password);
				System.out.println("returnMessage: "+returnMessage);
				
				if(returnMessage.equals("Login Successful"))
				{
					logonEmployee = loginSession.getLogonEmployee();
					System.out.println("logon Surname: "+logonEmployee.getSurname());
					this.loadStaticDataPanel();
				} else {
					System.out.println("Failure Message"+returnMessage);
					System.exit(0);
				}
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

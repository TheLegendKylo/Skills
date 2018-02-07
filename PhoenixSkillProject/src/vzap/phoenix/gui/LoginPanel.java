package vzap.phoenix.gui;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.Font;
import java.awt.Image;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.LayoutStyle.ComponentPlacement;

import vzap.phoenix.Server.Employee.Employee;
import vzap.phoenix.client.EmpSkillClient;
import vzap.phoenix.client.EmpSkillClientController;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import java.awt.Color;

public class LoginPanel extends JPanel implements ActionListener
{
	private JLabel lblUserId;
	private JLabel lblPassword;
	private JTextField tf_UserID;
	private JPasswordField passwordField;
	private JButton btnLogin;
	private JButton btnExit;
	private JPanel basePanel;
	private JLabel label;
	private JLabel lblRepeatPassword;
	private JPasswordField passwordFieldRepeat;
	private JButton btnSignUp;
	private String password = null,passRepeat=null;
	private JButton btnConfirmRegistration;
	private MainGui mainGui = null;
	private boolean newUser = false;
	private String loggedInUser = null;
	private EmpSkillClientController clientControl = null;
	private Employee emp = null, newEmp = null;
	private JButton btnRyan;
	/**
	 * Create the panel.
	 */
	public LoginPanel(JPanel basePanel, EmpSkillClientController clientControl) 
	{
		this.clientControl = clientControl;
		
		this.basePanel = basePanel;
		
		lblUserId = new JLabel("User ID : ");
		lblUserId.setFont(new Font("Arial", Font.BOLD, 15));
		lblUserId.setBounds(738, 261, 96, 31);
		//lblUserId.setFont(new Font("Tahoma", Font.BOLD, 20));
		
		lblPassword = new JLabel("Password :");
		lblPassword.setFont(new Font("Arial", Font.BOLD, 15));
		
		lblPassword.setBounds(738, 307, 116, 31);
		//lblPassword.setFont(new Font("Tahoma", Font.BOLD, 20));
		
		tf_UserID = new JTextField();
		tf_UserID.setFont(new Font("Arial", Font.BOLD, 15));
		tf_UserID.setBounds(893, 261, 224, 31);
		//tf_UserID.setFont(new Font("Tahoma", Font.BOLD, 20));
		tf_UserID.setColumns(10);
		tf_UserID.grabFocus();
		
		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Arial", Font.BOLD, 15));
		passwordField.setBounds(893, 307, 224, 31);
	//	passwordField.setFont(new Font("Tahoma", Font.BOLD, 20));
		
		btnRyan = new JButton("Ryan Login");
		btnRyan.setBounds(426, 148, 76, 23);
		btnRyan.addActionListener(this);
		add(btnRyan);
		
		btnLogin = new JButton("Login");
		btnLogin.setFont(new Font("Arial", Font.BOLD, 15));
		btnLogin.setBounds(738, 397, 128, 33);
		btnLogin.addActionListener(this);
		//btnLogin.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		btnExit = new JButton("Exit");
		btnExit.setFont(new Font("Arial", Font.BOLD, 15));
		btnExit.setBounds(738, 489, 379, 33);
		btnExit.addActionListener(this);
		//btnExit.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		setLayout(null);
		add(btnLogin);
		add(btnExit);
		add(lblUserId);
		add(lblPassword);
		add(passwordField);
		add(tf_UserID);
		
		lblRepeatPassword = new JLabel("Confrim Password :");
		lblRepeatPassword.setFont(new Font("Arial", Font.BOLD, 15));
		lblRepeatPassword.setBounds(738, 351, 144, 33);
		add(lblRepeatPassword);
			
		passwordFieldRepeat = new JPasswordField();
		passwordFieldRepeat.setFont(new Font("Arial", Font.BOLD, 15));
		passwordFieldRepeat.setBounds(893, 351, 224, 31);
		add(passwordFieldRepeat);
		passwordFieldRepeat.setVisible(false);
		lblRepeatPassword.setVisible(false);
		
		btnSignUp = new JButton("Sign Up");
		btnSignUp.setFont(new Font("Arial", Font.BOLD, 15));
		btnSignUp.setBounds(973, 398, 144, 31);
		btnSignUp.addActionListener(this);
		add(btnSignUp);
		
		btnConfirmRegistration = new JButton("Confirm Registration");
		btnConfirmRegistration.setFont(new Font("Arial", Font.BOLD, 15));
		
		btnConfirmRegistration.setBounds(738, 443, 379, 33);
		btnConfirmRegistration.addActionListener(this);
		add(btnConfirmRegistration);
		
		btnConfirmRegistration.setVisible(false);
		passwordFieldRepeat.setVisible(false);
		lblRepeatPassword.setVisible(false);
		
		//image code
		label = new JLabel("");
		label.setBounds(0,0, 1105, 970);
		
//		BufferedImage img = null;
//		try 
//		{
//		    img = ImageIO.read(new File("resources/images/Birds.JPG"));
//		}
//		catch (IOException e) 
//		{
//		    e.printStackTrace();
//		}
//		
//		Image dimg = img.getScaledInstance(label.getWidth(), label.getHeight(),
//		        Image.SCALE_SMOOTH);
//		
//		label.setIcon(new ImageIcon(dimg));
//		
//		add(label);

	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		Object source = e.getSource();
//		password = new String(passwordField.getPassword());
//		
//		loggedInUser = tf_UserID.getText();
		
		if(source == btnRyan)
		{
			tf_UserID.setText("A207532");
			passwordField.setText("1234");
			btnLogin.doClick();
		}
		
		if(source == btnLogin)
		{	
			System.out.println("login pressed");
			password = new String(passwordField.getPassword());
			//passRepeat = new String(passwordFieldRepeat.getPassword());
			loggedInUser = tf_UserID.getText();
			if(loggedInUser.equals("") || loggedInUser == null )
			{
				JOptionPane.showMessageDialog(this, "Please capture Your User ID ?");
				tf_UserID.grabFocus();
				return;
			}
			if(password.equals("") || password == null)
			{
				JOptionPane.showMessageDialog(this, "Please capture Your Password ?");
				passwordField.grabFocus();
				return;
			}
			//we will check if all details are correct here and move forward.	
			System.out.println("before loginEmployee");
			short loginCode = clientControl.loginEmployee(loggedInUser,password);
			System.out.println("we made it after the login");
			if(loginCode == 0)
			{
				emp = clientControl.getLogonEmployee();
				System.out.println("we made it after getting the employee");
				if(emp!=null)
				{
					mainGui = new MainGui(basePanel,newUser,emp,clientControl);
					this.basePanel.removeAll();
					this.basePanel.validate();
					this.basePanel.repaint();
					this.basePanel.add(mainGui);
					this.basePanel.validate();
					this.basePanel.repaint();
					this.basePanel.setVisible(true);
				}
				else
				{
					JOptionPane.showMessageDialog(this, "No Employee found!!!");
					return;
				}
			}
			if(loginCode == 1)
			{
				JOptionPane.showMessageDialog(this, "User Name not found you might want to SignUp?");
				//btnSignUp.doClick();
				return;
			}
			if(loginCode == 2)
			{
				JOptionPane.showMessageDialog(this, "Password Invalid!");
				passwordField.grabFocus();
				return;
			}
			
		}
		
		if(source == btnExit)
		{
			 int choice = JOptionPane.showConfirmDialog(this, "Are you sure you want to Exit ?",
					 				"Login Exit",JOptionPane.OK_CANCEL_OPTION);
			 if(JOptionPane.OK_OPTION == choice)
			 {
				 clientControl.closeConnections();
				 System.exit(0);
			 }
		}
		
		if(source == btnSignUp)
		{
			
			btnConfirmRegistration.setVisible(true);
			passwordFieldRepeat.setVisible(true);
			lblRepeatPassword.setVisible(true);
			
		}
		
		if(source == btnConfirmRegistration)
		{
			password = new String(passwordField.getPassword());
			passRepeat = new String(passwordFieldRepeat.getPassword());
			loggedInUser = tf_UserID.getText();
			
			 
			if(loggedInUser.equals("") || loggedInUser == null )
			{
				JOptionPane.showMessageDialog(this, "Please capture Your User ID ?");
				tf_UserID.grabFocus();
				return;
			}
			if(password.equals("") || password == null)
			{
				JOptionPane.showMessageDialog(this, "Please capture Your Password ?");
				passwordField.grabFocus();
				return;
			}
			if(passRepeat.equals("") || passRepeat == null)
			{
				JOptionPane.showMessageDialog(this, "Please capture confirm Password ?");
				passwordFieldRepeat.grabFocus();
				return;
			}
			if(!(passRepeat.equals(password)))
			{
				JOptionPane.showMessageDialog(this, "Your Passwords must Match!");
				passwordField.grabFocus();
				return;
			}
			//check if user name exists and go to the next screen where they will capture their full profile
			//send new user command to screen.
			newEmp = new Employee();
			newEmp.setEmployeeID(loggedInUser);
			newEmp.setPassword(password);
			
			
			//once successful
			newUser=true;
			mainGui = new MainGui(basePanel,newUser,newEmp,clientControl);
			this.basePanel.removeAll();
			this.basePanel.validate();
			this.basePanel.repaint();
			this.basePanel.add(mainGui);
			this.basePanel.validate();
			this.basePanel.repaint();
			this.basePanel.setVisible(true);
			
		}
		
	}
}

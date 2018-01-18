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
	private JLabel lblMediaApplicationLibrary;
	private JLabel label;
	private JLabel lblRepeatPassword;
	private JPasswordField passwordFieldRepeat;
	private JButton btnSignUp;
	private String password = null,passRepeat=null;
	private JButton btnConfirmRegistration;
	private MainGui mainGui = null;
	private boolean newUser = false;
	private String loggedInUser = null;
	private EmpSkillClient esc = null;
	private Employee emp = null;
	/**
	 * Create the panel.
	 */
	public LoginPanel(JPanel basePanel) 
	{
		esc = new EmpSkillClient();
		
		this.basePanel = basePanel;
		
		lblUserId = new JLabel("User ID : ");
		lblUserId.setForeground(Color.MAGENTA);
		lblUserId.setBounds(350, 332, 96, 25);
		lblUserId.setFont(new Font("Tahoma", Font.BOLD, 20));
		
		lblPassword = new JLabel("Password :");
		lblPassword.setForeground(Color.MAGENTA);
		lblPassword.setBounds(350, 397, 116, 25);
		lblPassword.setFont(new Font("Tahoma", Font.BOLD, 20));
		
		tf_UserID = new JTextField();
		tf_UserID.setBounds(479, 332, 224, 31);
		tf_UserID.setFont(new Font("Tahoma", Font.BOLD, 20));
		tf_UserID.setColumns(10);
		tf_UserID.grabFocus();
		
		passwordField = new JPasswordField();
		passwordField.setBounds(479, 391, 224, 31);
		passwordField.setFont(new Font("Tahoma", Font.BOLD, 20));
		
		btnLogin = new JButton("Login");
		btnLogin.setBounds(350, 502, 128, 33);
		btnLogin.addActionListener(this);
		btnLogin.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		btnExit = new JButton("Exit");
		btnExit.setBounds(350, 594, 128, 33);
		btnExit.addActionListener(this);
		btnExit.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		lblMediaApplicationLibrary = new JLabel("Skillz ");
		lblMediaApplicationLibrary.setForeground(new Color(0, 0, 255));
		lblMediaApplicationLibrary.setBounds(350, 218, 353, 86);
		lblMediaApplicationLibrary.setFont(new Font("Tahoma", Font.BOLD, 25));
		lblMediaApplicationLibrary.setHorizontalAlignment(SwingConstants.CENTER);
		setLayout(null);
		add(btnLogin);
		add(btnExit);
		add(lblMediaApplicationLibrary);
		add(lblUserId);
		add(lblPassword);
		add(passwordField);
		add(tf_UserID);
		
		lblRepeatPassword = new JLabel("Repeat Password :");
		lblRepeatPassword.setForeground(Color.MAGENTA);
		lblRepeatPassword.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblRepeatPassword.setBounds(350, 435, 197, 54);
		add(lblRepeatPassword);
		
		
		passwordFieldRepeat = new JPasswordField();
		passwordFieldRepeat.setFont(new Font("Tahoma", Font.BOLD, 20));
		passwordFieldRepeat.setBounds(559, 447, 144, 31);
		add(passwordFieldRepeat);
		passwordFieldRepeat.setVisible(false);
		lblRepeatPassword.setVisible(false);
		
		btnSignUp = new JButton("Sign Up");
		btnSignUp.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnSignUp.setBounds(575, 502, 128, 33);
		btnSignUp.addActionListener(this);
		add(btnSignUp);
		
		btnConfirmRegistration = new JButton("Confirm Registration");
		btnConfirmRegistration.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnConfirmRegistration.setBounds(350, 548, 353, 33);
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
		password = new String(passwordField.getPassword());
		passRepeat = new String(passwordFieldRepeat.getPassword());
		loggedInUser = tf_UserID.getText();
		
		if(source == btnLogin)
		{	
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
			
			esc.loginEmployee(loggedInUser,password);
			//success
			
			emp = esc.getLogonEmployee();
			// once successful bring up the screen
			
			mainGui = new MainGui(basePanel,newUser,emp);
			this.basePanel.removeAll();
			this.basePanel.validate();
			this.basePanel.repaint();
			this.basePanel.add(mainGui);
			this.basePanel.validate();
			this.basePanel.repaint();
			this.basePanel.setVisible(true);
			 
		}
		if(source == btnExit)
		{
			 int choice = JOptionPane.showConfirmDialog(this, "Are you sure you want to Exit ?",
					 				"Login Exit",JOptionPane.OK_CANCEL_OPTION);
			 if(JOptionPane.OK_OPTION == choice)
			 {
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
				JOptionPane.showMessageDialog(this, "Please capture Your confirm Password ?");
				passwordFieldRepeat.grabFocus();
				return;
			}
			if(!(passRepeat.equals(password)))
			{
				JOptionPane.showMessageDialog(this, "Your Password and Repeat Password must Match ?");
				passwordField.grabFocus();
				return;
			}
			//check if user name exists and go to the next screen where they will capture their full profile
			//send new user command to screen.
			
			//once successful
			newUser=true;
			mainGui = new MainGui(basePanel,newUser,emp);
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

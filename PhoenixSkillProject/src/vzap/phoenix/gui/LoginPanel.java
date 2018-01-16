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

	/**
	 * Create the panel.
	 */
	public LoginPanel(JPanel basePanel) 
	{
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
		btnLogin.setBounds(350, 452, 128, 33);
		btnLogin.addActionListener(this);
		btnLogin.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		btnExit = new JButton("Exit");
		btnExit.setBounds(575, 452, 128, 33);
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
		if(source == btnLogin)
		{
			String pass = new String(passwordField.getPassword());
			JOptionPane.showMessageDialog(this, "Hello Login in");

//	
//					 this.basePanel.removeAll();
//					 this.basePanel.validate();
//					 this.basePanel.repaint();
//					 this.basePanel.add(guestHome);
//					 this.basePanel.validate();
//					 this.basePanel.repaint();
//					 this.basePanel.setVisible(true);
			
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
		
	}

}

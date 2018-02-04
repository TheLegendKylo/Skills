package vzap.phoenix.gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.text.StyledEditorKit.BoldAction;

import vzap.phoenix.client.EmpSkillClientController;

import java.awt.color.*;


public class GuiControl extends JFrame
{
	private LoginPanel logP;
	private JPanel basePanel = null,panelNorth = null,panelSouth=null;
	private EmpSkillClientController clientControl =null;
	private JLabel lblHeading=null,lblSouthImg=null;
	
	public GuiControl()
	{
		clientControl = new EmpSkillClientController();
		this.setTitle("Skills Application");
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds(100, 100, (int) dim.getWidth(), (int) dim.getHeight());
	    setLocationRelativeTo(null);
		
		this.addWindowListener(new java.awt.event.WindowAdapter() 
		{
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) 
		    {
		        if (JOptionPane.showConfirmDialog
		        	(null, 
		            "Do you really want to exit the program ?", "Skills", 
		            JOptionPane.YES_NO_OPTION,
		            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
		        {
		        	clientControl.closeConnections();
		        	System.exit(0);
		        }
		    }
		});	
		
		basePanel = new JPanel();
		panelNorth = new JPanel();
		panelSouth = new JPanel();
		
		lblHeading = new JLabel(" Skills  Matrix ");
		lblHeading.setFont(new Font("Arial", Font.BOLD, 60));
		lblHeading.setForeground(Color.BLUE);
		
		//lblNewLabel.setBounds(286, 103, 56, 16);
		panelNorth.add(lblHeading);
		lblSouthImg = new JLabel();
		lblSouthImg.setIcon(new ImageIcon("resources/SB.png"));
		panelSouth.add(lblSouthImg);
		
		basePanel.setBackground(new Color(220,220,220));
		//basePanel.setBorder(new EmptyBorder(15,15,15,15));
		basePanel.setLayout(new GridLayout(1, 1));
		this.add(basePanel, BorderLayout.CENTER); // adding the panel to the frame
		this.add(panelNorth, BorderLayout.NORTH);
		this.add(panelSouth, BorderLayout.SOUTH);
		logP = new LoginPanel(basePanel,clientControl);
		basePanel.add(logP);
		//this.setContentPane(basePanel);

	}
	public static void main(String[] args)
	{
		GuiControl gc = new GuiControl();
		gc.setVisible(true);				
	}
}

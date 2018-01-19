package vzap.phoenix.gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.color.*;


public class GuiControl extends JFrame
{
	private LoginPanel logP;
	private JPanel basePanel = null;
	//ryan
	

	public GuiControl()
	{
		this.setTitle("Skills Application");
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setSize(1200,770);
		//this.setResizable(false);
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
		        	System.exit(0);
		        }
		    }
		});
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		
		basePanel = new JPanel();
		basePanel.setBackground(new Color(220,220,220));
		basePanel.setBorder(new EmptyBorder(15,15,15,15));
		basePanel.setLayout(new GridLayout(1, 1));
		logP = new LoginPanel(basePanel);
		basePanel.add(logP);
		this.setContentPane(basePanel);

	}
	public static void main(String[] args)
	{
		GuiControl gc = new GuiControl();
		gc.setVisible(true);
				
	}
}

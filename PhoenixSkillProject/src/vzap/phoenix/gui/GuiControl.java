package vzap.phoenix.gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.color.*;


public class GuiControl extends JFrame
{
	private LoginPanel logP;
	private JPanel basePanel = null;
	

	public GuiControl()
	{
		this.setTitle("Skills Application");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(1105,970);
		this.setResizable(false);
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

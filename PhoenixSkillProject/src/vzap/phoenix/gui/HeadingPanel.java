package vzap.phoenix.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class HeadingPanel extends JPanel
{
    int x = 0, y = 75;

    public void paint(Graphics g)
    {
        super.paint(g);
        Graphics2D g2 = (Graphics2D)g;
        Font font = new Font("Arial", Font.BOLD, 60);
        
        g2.setFont(font);
        g2.setColor(Color.BLUE);
        g2.drawString("Skills Matrix",x,y);
        this.setSize(new Dimension(2000, 100));
        
        try{Thread.sleep(100);}catch(Exception ex){}
        x+=10;
        if(x>this.getWidth ())
        {
            x=0;
        } 
        repaint();
    }
    
}

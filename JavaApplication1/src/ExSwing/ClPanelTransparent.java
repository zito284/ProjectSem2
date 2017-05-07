package ExSwing;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D; 
import java.awt.RenderingHints;
import javax.swing.JPanel; 

@SuppressWarnings("serial")
public class ClPanelTransparent extends JPanel
{
	private Color clr;
	
        private Color shadowColor = Color.BLACK;
        private boolean shady  = true;
        private boolean HQ = true;
        private Dimension arcs = new Dimension(20,20);
        private int shadowGap = 5;
        private int shadowOffset = 4;
        private int shadowAlpha = 150;
	public ClPanelTransparent()
	{
		setOpaque(false);
		clr = new Color(getBackground().getRed(), getBackground().getRed(), getBackground().getGreen(),100);
	}	
	
	public void setBackground(Color bg)
	{
		super.setBackground(bg);
		clr = new Color(getBackground().getRed(), getBackground().getRed(), getBackground().getGreen(),100);
		repaint();
	}
	
	protected void paintComponent(Graphics graph)
	{
		super.paintComponent(graph);
                
		Graphics2D g2d = (Graphics2D) graph.create();
                
                if(HQ){
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);                    
                }
		g2d.setColor(clr);
                g2d.fillRoundRect(0, 0
                            , getWidth() - shadowGap
                            , getHeight() - shadowGap, arcs.width, arcs.height);                
		
                g2d.drawRoundRect(0, 0, getWidth() - shadowGap, getHeight() - shadowGap, arcs.width, arcs.width);
		
                g2d.dispose();
	}
	
}

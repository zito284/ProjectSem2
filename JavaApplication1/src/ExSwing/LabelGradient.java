package ExSwing;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JLabel;



/**
 *
 * @author usu
 */
public class LabelGradient extends JLabel {
    private Color start;
    private Color end;
    public LabelGradient(String text) {
		super(text);				
		start = Color.GREEN;
                end = Color.YELLOW;
    }
    
    public LabelGradient(String text,Color start,Color end) {
		super(text);				
		this.start = start;
                this.end = end;
    }
        
    @Override
    protected void paintComponent(Graphics g) {
        FontMetrics metric = getFontMetrics(getFont());

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setPaint(new GradientPaint(0, 0,start, getWidth(), 0, end,true));
        g2.drawString(getText(), 0, metric.getHeight() - metric.getDescent());
    }
}

package ExSwing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

/**
 *
 * @author usu
 */
public class GlassPaneProgress extends JPanel {

    private boolean showProgress;
    private JProgressBar progress;

    public GlassPaneProgress() {
        super();
        progress = new JProgressBar();
        progress.setPreferredSize(new Dimension(200, progress.getPreferredSize().height));
        progress.setOpaque(false);
        progress.setBorderPainted(false);

        setLayout(new FlowLayout());
        setOpaque(false);
        add(progress);
        
        addMouseListener(new MouseAdapter() {
        });
        addKeyListener(new KeyAdapter() {
        });
    }

    public boolean isShowProgress() {
        return showProgress;
    }

    public void setShowProgress(boolean showProgress) {
        this.showProgress = showProgress;
        setVisible(showProgress);
    }

    public void setValue(int n) {
        progress.setValue(n);
    }

    public void setStringPainted(boolean b) {
        progress.setStringPainted(b);
    }

    public void setString(String s) {
        progress.setString(s);
    }

    public void setMinimum(int n) {
        progress.setMinimum(n);
    }

    public void setMaximum(int n) {
        progress.setMaximum(n);
    }

    public void setIndeterminate(boolean newValue) {
        progress.setIndeterminate(newValue);
    }

    public boolean isStringPainted() {
        return progress.isStringPainted();
    }

    public boolean isIndeterminate() {
        return progress.isIndeterminate();
    }

    public int getMinimum() {
        return progress.getMinimum();
    }

    public int getMaximum() {
        return progress.getMaximum();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (isShowProgress()) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setColor(new Color(1F, 0F, 0F, 0.5F));
            g2.fillRect(0, 0, getWidth(), getHeight());
            g2.dispose();
        }
    }
}

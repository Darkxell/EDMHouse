package fr.edmhouse.display;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class ShadowPane extends JPanel {
    private static final long serialVersionUID = 1L;

    private BufferedImage shadow;

    public ShadowPane() {
	setOpaque(false);
	setBorder(new EmptyBorder(10, 10, 10, 10));
    }

    @Override
    public void invalidate() {
	shadow = null;
	super.invalidate();
    }

    @Override
    protected void paintComponent(Graphics g) {
	Insets insets = getInsets();
	int x = insets.left;
	int y = insets.top;
	int width = getWidth() - (insets.left + insets.right);
	int height = getHeight() - (insets.top + insets.bottom);
	if (shadow == null) {
	    int shadowWidth = Math.min(Math.min(insets.left, insets.top),
		    Math.min(insets.right, insets.bottom));
	    shadow = new BufferedImage(width, height,
		    BufferedImage.TYPE_INT_ARGB);
	    Graphics2D g2d = shadow.createGraphics();
	    g2d.setColor(getBackground());
	    g2d.fillRect(0, 0, width, height);
	    g2d.dispose();
	    shadow = ShadowUtil.generateShadow(shadow, shadowWidth,
		    Color.BLACK, 0.5f);// TODO : add the shadow color to
				       // layout.edm and the shadowstrength
	}
	g.drawImage(shadow, 0, 0, this);
	g.clearRect(x, y, width, height);
    }

}

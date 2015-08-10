package fr.edmhouse.display;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JWindow;

import fr.edmhouse.res.Res;

public class ShadowWindow extends JWindow {
    private static final long serialVersionUID = 1L;

    public ShadowWindow() {
	super();
	setSize(Res.background.getWidth() + 20,
		Res.background.getHeight() + 20);
	setBackground(new Color(0, 0, 0, 0));
	setContentPane(new ShadowPane());
	setLayout(new BorderLayout());
	setLocationRelativeTo(null);
    }
    
}

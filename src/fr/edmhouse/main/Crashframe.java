package fr.edmhouse.main;

import java.awt.Label;

import javax.swing.JFrame;

/**
 * A crashframe object will be summoned if an error occurs in any thread. This
 * allows the user to have feedback on possible crashes. Closing the crashframe
 * will result in terminating the whole software.
 */
public class Crashframe {

    /** The frame holding the message. */
    private JFrame frame;

    /** Constructs a crashframe with the default error message. */
    public Crashframe() {
	this(
		"Unknown Error",
		"Sorry, an unknown error happened and the software had to be  "
			+ "terminated. If you keep seeing this message please give  "
			+ "feedback at darkxell.mc@gmail.com and I will do my best "
			+ "to fix it as soon as possible.");
    }

    /** Construcs a crashframe. */
    public Crashframe(String errorName, String content) {
	this.frame = new JFrame();
	this.frame.setTitle(errorName);
	this.frame.setResizable(false);
	this.frame.setSize(377, 265);
	this.frame.setLocationRelativeTo(null);
	this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	this.frame.add(new Label(content));
    }

    /** Displays the frame. This will cause the software to be terminated. */
    public void launch() {
	this.frame.setVisible(true);
	for(;;){}
    }

}

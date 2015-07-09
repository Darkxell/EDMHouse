package fr.edmhouse.display;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import fr.edmhouse.main.EDMHouse;
import fr.edmhouse.res.CustomList;
import fr.edmhouse.res.Res;

public class CCanvas extends Canvas {
    private static final long serialVersionUID = 1L;

    public boolean isonrandom;
    public boolean isonlist;
    public boolean isonskip;
    public boolean isonclose;
    public boolean isonmini;
    public boolean isonbutton;
    /** Is true if the random mode for song pickups is on. */
    public boolean random_on;
    /** true if the player is running (disp pause), false otherwise (disp play) */
    public boolean state;
    /** the progression of the music, in miliseconds. */
    public int progression;
    /** the horisontal offset of the text. Might be negative. */
    public double textoffset;

    /**
     * Override paint method. Reinitializes the canvas to default with play
     * button if called.
     */
    public void paint(Graphics g) {
	g.drawImage(Res.background, 0, 0, null);
	g.drawImage(Res.hud_cross_white, CustomList.pos_close_x,
		CustomList.pos_close_y, null);
	g.drawImage(Res.hud_mini_white, CustomList.pos_mini_x,
		CustomList.pos_mini_y, null);
	g.drawImage(Res.hud_play, CustomList.pos_button_x,
		CustomList.pos_button_y, null);
    }

    /**
     * Additional, not required constructor part. Initializing the canvas allows
     * it to display directly a play button for exemple.
     */
    public void initialize() {
	this.isonbutton = false;
	this.isonmini = false;
	this.isonclose = false;
	this.isonskip = false;
	this.isonrandom = false;
	this.isonlist = false;
	this.random_on = true;
	this.state = true;
	this.progression = 0;
	this.textoffset = 0;
    }

    public void update(Graphics gr) {
	BufferStrategy bs = this.getBufferStrategy();
	Graphics g = bs.getDrawGraphics();
	g.clearRect(0, 0, this.getWidth(), this.getWidth());
	// Draws the background
	g.drawImage(Res.background, 0, 0, null);
	// Draws the HUD
	if (this.isonclose)
	    g.drawImage(Res.hud_cross_red, CustomList.pos_close_x,
		    CustomList.pos_close_y, null);
	else
	    g.drawImage(Res.hud_cross_white, CustomList.pos_close_x,
		    CustomList.pos_close_y, null);
	if (this.isonrandom)
	    if(this.random_on)
		g.drawImage(Res.hud_random_on_active, CustomList.pos_random_x,
			    CustomList.pos_random_y, null);
		else
	    g.drawImage(Res.hud_random_active, CustomList.pos_random_x,
		    CustomList.pos_random_y, null);
	else
	    if(this.random_on)
		g.drawImage(Res.hud_random_on, CustomList.pos_random_x,
			    CustomList.pos_random_y, null);
		else
	    g.drawImage(Res.hud_random, CustomList.pos_random_x,
		    CustomList.pos_random_y, null);
	if (this.isonmini)
	    g.drawImage(Res.hud_mini_grey, CustomList.pos_mini_x,
		    CustomList.pos_mini_y, null);
	else
	    g.drawImage(Res.hud_mini_white, CustomList.pos_mini_x,
		    CustomList.pos_mini_y, null);
	if (this.isonlist)
	    g.drawImage(Res.hud_list_active, CustomList.pos_list_x,
		    CustomList.pos_list_y, null);
	else
	    g.drawImage(Res.hud_list, CustomList.pos_list_x,
		    CustomList.pos_list_y, null);
	if (this.isonskip)
	    g.drawImage(Res.hud_skip_active, CustomList.pos_skip_x,
		    CustomList.pos_skip_y, null);
	else
	    g.drawImage(Res.hud_skip, CustomList.pos_skip_x,
		    CustomList.pos_skip_y, null);
	// Draws the progress bar
	g.setColor(CustomList.color_progress);
	int progress = (int) (((float) ((float) this.progression / (float) EDMHouse.BGM
		.getlength())) * CustomList.size_progress_width);
	g.fillRect(41, 60, progress, CustomList.size_progress_height);
	// Draws the play/pause button
	if (this.state) {
	    if (this.isonbutton)
		g.drawImage(Res.hud_play_active, CustomList.pos_button_x,
			CustomList.pos_button_y, null);
	    else
		g.drawImage(Res.hud_play, CustomList.pos_button_x,
			CustomList.pos_button_y, null);
	} else {
	    if (this.isonbutton)
		g.drawImage(Res.hud_pause_active, CustomList.pos_button_x,
			CustomList.pos_button_y, null);
	    else
		g.drawImage(Res.hud_pause, CustomList.pos_button_x,
			CustomList.pos_button_y, null);
	    this.progression += 10;
	    // TODO : manage the framerate here.
	}
	// draws the text
	g.setColor(Color.WHITE);
	g.setFont(Res.font);
	FontMetrics metric = g.getFontMetrics();
	char[] songtitle = (EDMHouse.BGM.getdisplayname() + "  -  " + EDMHouse.BGM
		.getdisplayname()).toCharArray();
	g.drawChars(songtitle, 0, songtitle.length,
		(int) (CustomList.pos_text_x + this.textoffset),
		CustomList.pos_text_y);
	this.textoffset -= CustomList.size_textoffset;
	if (metric.stringWidth(EDMHouse.BGM.getdisplayname() + "  -  ") <= Math
		.abs(this.textoffset))
	    this.textoffset = 0;
	// draws the foreground
	g.drawImage(Res.foreground, 0, 0, null);
	// prints the buffer to the canvas
	bs.show();
	g.dispose();
    }

    /** Resets the progress bar to the begining. */
    public void resetprogress() {
	this.progression = 0;
    }

}

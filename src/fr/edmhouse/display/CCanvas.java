package fr.edmhouse.display;

import java.awt.Canvas;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import fr.edmhouse.main.EDMHouse;
import fr.edmhouse.res.Layout_common;
import fr.edmhouse.res.Layout_list;
import fr.edmhouse.res.Res;

public class CCanvas extends Canvas {
    private static final long serialVersionUID = 1L;

    /**
     * The content of the frame. <br/>
     * <strong>0 :</strong> the default play/pause overlay<br/>
     * <strong>1 : </strong> the list of songs
     */
    public int content;

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
    /** the vertical offset of the list. */
    public double listoffset;

    /**
     * Override paint method. Reinitializes the canvas to default with play
     * button if called.
     */
    public void paint(Graphics g) {
	// TODO : change this to a loading screen
	g.drawImage(Res.background, 0, 0, null);
	g.drawImage(Res.hud_cross_white, Layout_common.pos_close_x,
		Layout_common.pos_close_y, null);
	g.drawImage(Res.hud_mini_white, Layout_common.pos_mini_x,
		Layout_common.pos_mini_y, null);
	g.drawImage(Res.hud_play, Layout_common.pos_button_x,
		Layout_common.pos_button_y, null);
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

    @Override
    public void update(Graphics gr) {
	if (!this.state)
	    this.progression += 10;
	// TODO : manage the framerate here.
	if (this.content != 1)
	    this.listoffset = 0;
	switch (this.content) {
	case 0:
	    this.updateCommon();
	    break;
	case 1:
	    this.updateList();
	    break;
	default:
	    break;
	}
    }

    private void updateCommon() {
	BufferStrategy bs = this.getBufferStrategy();
	Graphics g = bs.getDrawGraphics();
	g.clearRect(0, 0, this.getWidth(), this.getWidth());
	// Draws the background
	g.drawImage(Res.background, 0, 0, null);
	// Draws the HUD
	if (this.isonclose)
	    g.drawImage(Res.hud_cross_red, Layout_common.pos_close_x,
		    Layout_common.pos_close_y, null);
	else
	    g.drawImage(Res.hud_cross_white, Layout_common.pos_close_x,
		    Layout_common.pos_close_y, null);
	if (this.isonrandom)
	    if (this.random_on)
		g.drawImage(Res.hud_random_on_active,
			Layout_common.pos_random_x, Layout_common.pos_random_y,
			null);
	    else
		g.drawImage(Res.hud_random_active, Layout_common.pos_random_x,
			Layout_common.pos_random_y, null);
	else if (this.random_on)
	    g.drawImage(Res.hud_random_on, Layout_common.pos_random_x,
		    Layout_common.pos_random_y, null);
	else
	    g.drawImage(Res.hud_random, Layout_common.pos_random_x,
		    Layout_common.pos_random_y, null);
	if (this.isonmini)
	    g.drawImage(Res.hud_mini_grey, Layout_common.pos_mini_x,
		    Layout_common.pos_mini_y, null);
	else
	    g.drawImage(Res.hud_mini_white, Layout_common.pos_mini_x,
		    Layout_common.pos_mini_y, null);
	if (this.isonlist)
	    g.drawImage(Res.hud_list_active, Layout_common.pos_list_x,
		    Layout_common.pos_list_y, null);
	else
	    g.drawImage(Res.hud_list, Layout_common.pos_list_x,
		    Layout_common.pos_list_y, null);
	if (this.isonskip)
	    g.drawImage(Res.hud_skip_active, Layout_common.pos_skip_x,
		    Layout_common.pos_skip_y, null);
	else
	    g.drawImage(Res.hud_skip, Layout_common.pos_skip_x,
		    Layout_common.pos_skip_y, null);
	// Draws the progress bar
	g.setColor(Layout_common.color_progress);
	int progress = (int) (((float) ((float) this.progression / (float) EDMHouse.BGM
		.getlength())) * Layout_common.size_progress_width);
	g.fillRect(41, 60, progress, Layout_common.size_progress_height);
	// Draws the play/pause button
	if (this.state) {
	    if (this.isonbutton)
		g.drawImage(Res.hud_play_active, Layout_common.pos_button_x,
			Layout_common.pos_button_y, null);
	    else
		g.drawImage(Res.hud_play, Layout_common.pos_button_x,
			Layout_common.pos_button_y, null);
	} else {
	    if (this.isonbutton)
		g.drawImage(Res.hud_pause_active, Layout_common.pos_button_x,
			Layout_common.pos_button_y, null);
	    else
		g.drawImage(Res.hud_pause, Layout_common.pos_button_x,
			Layout_common.pos_button_y, null);
	}
	// draws the text
	g.setColor(Layout_common.color_text);
	g.setFont(Res.font);
	FontMetrics metric = g.getFontMetrics();
	char[] songtitle = (EDMHouse.BGM.getdisplayname() + "  -  " + EDMHouse.BGM
		.getdisplayname()).toCharArray();
	g.drawChars(songtitle, 0, songtitle.length,
		(int) (Layout_common.pos_text_x + this.textoffset),
		Layout_common.pos_text_y);
	this.textoffset -= Layout_common.size_textoffset;
	if (metric.stringWidth(EDMHouse.BGM.getdisplayname() + "  -  ") <= Math
		.abs(this.textoffset))
	    this.textoffset = 0;
	// draws the foreground
	g.drawImage(Res.foreground, 0, 0, null);
	// prints the buffer to the canvas
	bs.show();
	g.dispose();
    }

    private void updateList() {
	BufferStrategy bs = this.getBufferStrategy();
	Graphics g = bs.getDrawGraphics();
	g.clearRect(0, 0, this.getWidth(), this.getWidth());
	// Draws the background
	g.drawImage(Res.list_background, 0, 0, null);
	// Draws the componnents
	g.setColor(Layout_list.color_text);
	g.setFont(Res.font);
	this.listoffset += EDMHouse.frame.wheelvelocity;
	if (this.listoffset < 0)
	    this.listoffset = 0;
	int maximumoffset = Layout_list.pos_componnent_y
		+ (EDMHouse.songs.getSongAmmount() * Res.list_componnent
			.getHeight()) - Layout_common.size_frame_height;
	if (this.listoffset > maximumoffset)
	    this.listoffset = maximumoffset;
	for (int i = 0; i < EDMHouse.songs.getSongAmmount(); i++) {
	    int height = (int) (Layout_list.pos_componnent_y - this.listoffset + (Res.list_componnent
		    .getHeight() * i));
	    g.drawImage(Res.list_componnent, Layout_list.pos_componnent_x,
		    height, null);
	    char[] songtitle = EDMHouse.songs.getSongById(i).getdipsplayname()
		    .toCharArray();
	    g.drawChars(songtitle, 0, songtitle.length, Layout_list.pos_text_x,
		    height + Layout_list.pos_text_y);
	    g.drawImage(Res.list_play, Layout_list.pos_componnent_x
		    + Layout_list.pos_play_x, height + Layout_list.pos_play_y,
		    null);
	}
	// Draws the HUD
	if (this.isonclose)
	    g.drawImage(Res.hud_cross_red, Layout_common.pos_close_x,
		    Layout_common.pos_close_y, null);
	else
	    g.drawImage(Res.hud_cross_white, Layout_common.pos_close_x,
		    Layout_common.pos_close_y, null);
	if (this.isonrandom)
	    if (this.random_on)
		g.drawImage(Res.hud_random_on_active,
			Layout_common.pos_random_x, Layout_common.pos_random_y,
			null);
	    else
		g.drawImage(Res.hud_random_active, Layout_common.pos_random_x,
			Layout_common.pos_random_y, null);
	else if (this.random_on)
	    g.drawImage(Res.hud_random_on, Layout_common.pos_random_x,
		    Layout_common.pos_random_y, null);
	else
	    g.drawImage(Res.hud_random, Layout_common.pos_random_x,
		    Layout_common.pos_random_y, null);
	if (this.isonmini)
	    g.drawImage(Res.hud_mini_grey, Layout_common.pos_mini_x,
		    Layout_common.pos_mini_y, null);
	else
	    g.drawImage(Res.hud_mini_white, Layout_common.pos_mini_x,
		    Layout_common.pos_mini_y, null);
	if (this.isonlist)
	    g.drawImage(Res.hud_list_active, Layout_common.pos_list_x,
		    Layout_common.pos_list_y, null);
	else
	    g.drawImage(Res.hud_list, Layout_common.pos_list_x,
		    Layout_common.pos_list_y, null);
	if (this.isonskip)
	    g.drawImage(Res.hud_skip_active, Layout_common.pos_skip_x,
		    Layout_common.pos_skip_y, null);
	else
	    g.drawImage(Res.hud_skip, Layout_common.pos_skip_x,
		    Layout_common.pos_skip_y, null);
	// Draws the list foreground
	g.drawImage(Res.list_foreground, 0, 0, null);
	// prints the buffer to the canvas
	bs.show();
	g.dispose();
    }

    /** Resets the progress bar to the begining. */
    public void resetprogress() {
	this.progression = 0;
    }

    /**
     * Returns true if this component is painted to an offscreen image
     * ("buffer") that's copied to the screen later. Component subclasses that
     * support double buffering should override this method to return true if
     * double buffering is enabled.
     * 
     * @return True for the CCanvas class
     */
    @Override
    public boolean isDoubleBuffered() {
	return true;
    }
}

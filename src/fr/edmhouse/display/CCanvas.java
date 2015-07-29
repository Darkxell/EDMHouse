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
    /** Is true if the random mode for song pickups is on. */
    public boolean random_on;
    /** true if the player is running (disp pause), false otherwise (disp play) */
    public boolean state;
    /** the progression of the music, in miliseconds. */
    public int progression;
    /** the volume (0-100). Marks the progression of the volume ki thingy. */
    public int volume;
    /** the horisontal offset of the text. Might be negative. */
    public double textoffset;
    /** the vertical offset of the list. */
    public double listoffset;
    /**
     * The hovered play button id in the list. Is -1 if no play button is
     * hovered or if you are not in the list.
     */
    public int hoveredSongButtonID;

    /**
     * Override paint method. Reinitializes the canvas to loading screen if
     * called. Should not be called manually, please use the update(Graphics gr)
     * method instead.
     */
    public void paint(Graphics g) {
	g.drawImage(Res.loading, 0, 0, null);
    }

    /** Additional, not required constructor part. */
    public void initialize() {
	this.random_on = true;
	this.state = true;
	this.progression = 0;
	this.textoffset = 0;
	this.listoffset = 0;
	this.hoveredSongButtonID = -1;
	this.volume = Layout_common.value_volumestart;
    }

    @Override
    public void update(Graphics gr) {
	if (this.content != 1)
	    this.listoffset = 0;
	this.hoveredSongButtonID = -1;
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
	if (this.isonclose())
	    g.drawImage(Res.hud_cross_red, Layout_common.pos_close_x,
		    Layout_common.pos_close_y, null);
	else
	    g.drawImage(Res.hud_cross_white, Layout_common.pos_close_x,
		    Layout_common.pos_close_y, null);
	if (this.isonrandom())
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
	if (this.isonmini())
	    g.drawImage(Res.hud_mini_grey, Layout_common.pos_mini_x,
		    Layout_common.pos_mini_y, null);
	else
	    g.drawImage(Res.hud_mini_white, Layout_common.pos_mini_x,
		    Layout_common.pos_mini_y, null);
	if (this.isonlist())
	    g.drawImage(Res.hud_list_active, Layout_common.pos_list_x,
		    Layout_common.pos_list_y, null);
	else
	    g.drawImage(Res.hud_list, Layout_common.pos_list_x,
		    Layout_common.pos_list_y, null);
	if (this.isonskip())
	    g.drawImage(Res.hud_skip_active, Layout_common.pos_skip_x,
		    Layout_common.pos_skip_y, null);
	else
	    g.drawImage(Res.hud_skip, Layout_common.pos_skip_x,
		    Layout_common.pos_skip_y, null);
	g.drawImage(Res.hud_volume, Layout_common.pos_volume_x,
		Layout_common.pos_volume_y, null);
	int volpos = (int) (((float) this.volume)
		* (Res.hud_volume.getWidth() - (Res.hud_ki.getWidth() / 2) * 2) / 100);
	if (this.isonvolume()) {
	    g.drawImage(Res.hud_ki_active, Layout_common.pos_volume_x + volpos,
		    Layout_common.pos_volume_y, null);
	} else {
	    g.drawImage(Res.hud_ki, Layout_common.pos_volume_x + volpos,
		    Layout_common.pos_volume_y, null);
	}
	// Draws the progress bar
	g.setColor(Layout_common.color_progress);
	int progress = (int) (((float) ((float) this.progression / (float) EDMHouse.BGM
		.getlength())) * Layout_common.size_progress_width);
	g.fillRect(41, 60, progress, Layout_common.size_progress_height);
	// Draws the play/pause button
	if (this.state) {
	    if (this.isonbutton())
		g.drawImage(Res.hud_play_active, Layout_common.pos_button_x,
			Layout_common.pos_button_y, null);
	    else
		g.drawImage(Res.hud_play, Layout_common.pos_button_x,
			Layout_common.pos_button_y, null);
	} else {
	    if (this.isonbutton())
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
	char[] songtitle = (EDMHouse.BGM.getdisplayname() + "  -  "
		+ EDMHouse.BGM.getdisplayname() + "  -  " + EDMHouse.BGM
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
	    if (isMouseOnPlayInComponent(i)) {
		g.drawImage(Res.list_play_active, Layout_list.pos_componnent_x
			+ Layout_list.pos_play_x, height
			+ Layout_list.pos_play_y, null);
		this.hoveredSongButtonID = i;
	    } else
		g.drawImage(Res.list_play, Layout_list.pos_componnent_x
			+ Layout_list.pos_play_x, height
			+ Layout_list.pos_play_y, null);
	}
	// Draws the scroll bar
	int scroll_padding = Layout_list.size_slider_height / 10;
	int scrollVerticalOffest = (int) (this.listoffset
		* (Layout_list.size_slider_height - (scroll_padding) * 2) / maximumoffset);
	g.setColor(Layout_list.color_scroll);
	g.fillRect(Layout_list.pos_slider_x, Layout_list.pos_slider_y
		+ scrollVerticalOffest, 10, scroll_padding * 2);

	// Draws the HUD
	if (this.isonclose()) {
	    g.drawImage(Res.hud_cross_red, Layout_common.pos_close_x,
		    Layout_common.pos_close_y, null);
	} else
	    g.drawImage(Res.hud_cross_white, Layout_common.pos_close_x,
		    Layout_common.pos_close_y, null);
	if (this.isonrandom()) {
	    if (this.random_on)
		g.drawImage(Res.hud_random_on_active,
			Layout_common.pos_random_x, Layout_common.pos_random_y,
			null);
	    else
		g.drawImage(Res.hud_random_active, Layout_common.pos_random_x,
			Layout_common.pos_random_y, null);
	} else if (this.random_on)
	    g.drawImage(Res.hud_random_on, Layout_common.pos_random_x,
		    Layout_common.pos_random_y, null);
	else
	    g.drawImage(Res.hud_random, Layout_common.pos_random_x,
		    Layout_common.pos_random_y, null);
	if (this.isonmini()) {
	    g.drawImage(Res.hud_mini_grey, Layout_common.pos_mini_x,
		    Layout_common.pos_mini_y, null);
	} else
	    g.drawImage(Res.hud_mini_white, Layout_common.pos_mini_x,
		    Layout_common.pos_mini_y, null);
	if (this.isonlist()) {
	    g.drawImage(Res.hud_list_active, Layout_common.pos_list_x,
		    Layout_common.pos_list_y, null);
	} else
	    g.drawImage(Res.hud_list, Layout_common.pos_list_x,
		    Layout_common.pos_list_y, null);
	if (this.isonskip()) {
	    g.drawImage(Res.hud_skip_active, Layout_common.pos_skip_x,
		    Layout_common.pos_skip_y, null);
	} else
	    g.drawImage(Res.hud_skip, Layout_common.pos_skip_x,
		    Layout_common.pos_skip_y, null);
	g.drawImage(Res.hud_volume, Layout_common.pos_volume_x,
		Layout_common.pos_volume_y, null);
	int volpos = (int) (((float) this.volume)
		* (Res.hud_volume.getWidth() - (Res.hud_ki.getWidth() / 2) * 2) / 100);
	if (this.isonvolume()) {
	    g.drawImage(Res.hud_ki_active, Layout_common.pos_volume_x + volpos,
		    Layout_common.pos_volume_y, null);
	} else {
	    g.drawImage(Res.hud_ki, Layout_common.pos_volume_x + volpos,
		    Layout_common.pos_volume_y, null);
	}
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

    /**
     * Predicates that returns true if the mouse is hovering the play button in
     * the container n.<code>containerNumber</code> in the list.
     * <code>containerNumber</code> must start at 0.
     */
    public boolean isMouseOnPlayInComponent(int containerNumber) {
	if (this.isMouseOnScrollbar() || this.isonclose() || this.isonlist()
		|| this.isonmini() || this.isonrandom() || this.isonskip())
	    return false;
	int buttonheight = Layout_list.pos_componnent_y
		+ Layout_list.pos_play_y
		+ (containerNumber * Res.list_componnent.getHeight())
		- (int) this.listoffset;
	return (EDMHouse.frame.isOnPos(Layout_list.pos_componnent_x
		+ Layout_list.pos_play_x, buttonheight,
		Layout_list.pos_componnent_x + Layout_list.pos_play_x
			+ Res.list_play.getWidth(), buttonheight
			+ Res.list_play.getHeight()));
    }

    /**
     * Predicate that returns true if the mouse is hovering the scrollbar
     * componnent and if the frame is in the list state.
     */
    public boolean isMouseOnScrollbar() {
	return (EDMHouse.frame.isOnPos(Layout_list.pos_slider_x,
		Layout_list.pos_slider_y, Layout_list.pos_slider_x + 10,
		Layout_list.pos_slider_y + Layout_list.size_slider_height) && this.content == 1);
    }

    /**
     * predicate that returns true if the mouse is over the random button, no
     * matters the state of the frame.
     */
    public boolean isonrandom() {
	return EDMHouse.frame.isOnPos(Layout_common.pos_random_x,
		Layout_common.pos_random_y, Layout_common.pos_random_x
			+ Res.hud_random.getWidth(), Layout_common.pos_random_y
			+ Res.hud_random.getHeight());
    }

    /**
     * predicate that returns true if the mouse is over the list button, no
     * matters the state of the frame.
     */
    public boolean isonlist() {
	return EDMHouse.frame.isOnPos(Layout_common.pos_list_x,
		Layout_common.pos_list_y, Layout_common.pos_list_x
			+ Res.hud_list.getWidth(), Layout_common.pos_list_y
			+ Res.hud_list.getHeight());
    }

    /**
     * predicate that returns true if the mouse is over the skip button, no
     * matters the state of the frame.
     */
    public boolean isonskip() {
	return EDMHouse.frame.isOnPos(Layout_common.pos_skip_x,
		Layout_common.pos_skip_y, Layout_common.pos_skip_x
			+ Res.hud_skip.getWidth(), Layout_common.pos_skip_y
			+ Res.hud_skip.getHeight());
    }

    /**
     * predicate that returns true if the mouse is over the close button, no
     * matters the state of the frame.
     */
    public boolean isonclose() {
	return EDMHouse.frame.isOnPos(Layout_common.pos_close_x,
		Layout_common.pos_close_y, Layout_common.pos_close_x
			+ Res.hud_cross_white.getWidth(),
		Layout_common.pos_close_y + Res.hud_cross_white.getHeight());
    }

    /**
     * predicate that returns true if the mouse is over the minimize button, no
     * matters the state of the frame.
     */
    public boolean isonmini() {
	return EDMHouse.frame.isOnPos(Layout_common.pos_mini_x,
		Layout_common.pos_mini_y, Layout_common.pos_mini_x
			+ Res.hud_mini_white.getWidth(),
		Layout_common.pos_mini_y + Res.hud_mini_white.getHeight());
    }

    /**
     * predicate that returns true if the mouse is over the play/pause button,
     * in the play state only.
     */
    public boolean isonbutton() {
	if (this.content != 0)
	    return false;
	return EDMHouse.frame.isOnPos(Layout_common.pos_button_x,
		Layout_common.pos_button_y, Layout_common.pos_button_x
			+ Res.hud_play.getWidth(), Layout_common.pos_button_y
			+ Res.hud_play.getHeight());
    }

    /**
     * predicate that returns true if the mouse is over the volume bar, in any
     * state.
     */
    public boolean isonvolume() {
	return EDMHouse.frame.isOnPos(Layout_common.pos_volume_x,
		Layout_common.pos_volume_y, Layout_common.pos_volume_x
			+ Res.hud_volume.getWidth(), Layout_common.pos_volume_y
			+ Res.hud_volume.getHeight());
    }
}

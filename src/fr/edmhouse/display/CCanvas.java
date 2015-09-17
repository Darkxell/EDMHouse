package fr.edmhouse.display;

import java.awt.Canvas;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import fr.edmhouse.audio.playlists.PlaylistHolder;
import fr.edmhouse.main.EDMHouse;
import fr.edmhouse.main.SkinsHolder;
import fr.edmhouse.res.Layout_common;
import fr.edmhouse.res.Layout_list;
import fr.edmhouse.res.Layout_options;
import fr.edmhouse.res.Res;
import fr.edmhouse.res.ResLayout;
import fr.edmhouse.res.SongFolderHolder;

public class CCanvas extends Canvas {
    private static final long serialVersionUID = 1L;

    public static final int STATE_DEFAULT = 0;
    public static final int STATE_LIST = 1;
    public static final int STATE_OPTIONS = 2;
    public static final int STATE_PLAYLISTS = 3;
    public static final int STATE_SKINS = 4;
    public static final int STATE_PLAYLISTEDITOR = 5;
    public static final int STATE_PLAYLISTSONGADDER = 6;
    /**
     * The content of the frame. <br/>
     * <strong>0 :</strong> the default play/pause overlay<br/>
     * <strong>1 :</strong> the list of songs <br/>
     * <strong>2 :</strong> the options menu<br/>
     * <strong>3 :</strong> the playlist/folder chooser screen<br/>
     * <strong>4 :</strong> the skin list where you can select the skin you want<br/>
     * <strong>5 :</strong> the playlist editor. Where you see the playlist
     * content and can add or remove songs.<br/>
     * <strong>6 :</strong> the song selector interface. Displays the list of
     * songs in the default folder to choose from.<br/>
     */
    public int content;
    /** Is true if the random mode for song pickups is on. */
    public boolean random_on;
    /** true if the player is running (disp pause), false otherwise (disp play) */
    public boolean state;
    /** the progression of the music, in miliseconds. */
    public int progression;
    /** The current editing playlist. Is changed each time you open a playlist. */
    public int editingList;
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
     * The hovered swap button id in the skin list. Is -1 if no swap button is
     * hovered or if you are not in the skin list.
     */
    public int hoveredSwapButtonID;
    /**
     * The hovered select button id in the playlist list. Is -1 if no select
     * button is hovered or if you are not in the playlist list.
     */
    public int hoveredSelectButtonID;
    /**
     * The hovered edit button id in the playlist list. Is -1 if no edit button
     * is hovered or if you are not in the playlist list.
     */
    public int hoveredEditButtonID;
    /**
     * The hovered remove button id in the playlist editor. Is -1 if no remove
     * button is hovered or if you are not in a playlist edition mode.
     */
    public int hoveredPlaylistRemoveButtonID;
    /**
     * The hovered add button id in the playlist song adder. Is -1 if no add
     * button is hovered or if you are not in a playlist song adder mode.
     */
    public int hoveredPlaylistAddButtonID;

    /**
     * Override paint method. Reinitializes the canvas to loading screen if
     * called. Should not be called manually, please use the update(Graphics gr)
     * method instead.
     */
    public void paint(Graphics g) {
	g.drawImage(Res.loading, 0, 0, null);
    }

    /**
     * Additional, not required constructor part. I don't even know why it's
     * here anymore, it's probably totaly useless, exept the line that sets the
     * volume.
     */
    public void initialize() {
	this.content = STATE_DEFAULT;
	this.random_on = true;
	this.state = true;
	this.progression = 0;
	this.textoffset = 0;
	this.listoffset = 0;
	this.volume = Layout_common.value_volumestart;
    }

    @Override
    public void update(Graphics gr) {
	if (this.content == STATE_DEFAULT || this.content == STATE_OPTIONS)
	    this.listoffset = 0;
	if (this.content != STATE_LIST)
	    this.hoveredSongButtonID = -1;
	if (this.content != STATE_SKINS)
	    this.hoveredSwapButtonID = -1;
	if (this.content != STATE_PLAYLISTS) {
	    this.hoveredSelectButtonID = -1;
	    this.hoveredEditButtonID = -1;
	}
	if (this.content != STATE_PLAYLISTEDITOR)
	    this.hoveredPlaylistRemoveButtonID = -1;
	if (this.content != STATE_PLAYLISTSONGADDER)
	    this.hoveredPlaylistAddButtonID = -1;
	switch (this.content) {
	case STATE_DEFAULT:
	    this.updateCommon();
	    break;
	case STATE_LIST:
	    this.updateList();
	    break;
	case STATE_OPTIONS:
	    this.updateOptions();
	    break;
	case STATE_PLAYLISTS:
	    this.updatePlaylists();
	    break;
	case STATE_SKINS:
	    this.updateSkins();
	    break;
	case STATE_PLAYLISTEDITOR:
	    this.updateEditor();
	    break;
	case STATE_PLAYLISTSONGADDER:
	    this.updateAdder();
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
	addButtonsToBuffer(g);
	// Draws the volume bar
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
	g.fillRect(Layout_common.pos_progress_x, Layout_common.pos_progress_y,
		progress, Layout_common.size_progress_height);
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
	int maximumoffset = Layout_list.pos_componnent_y
		+ (EDMHouse.songs.getSongAmmount() * Res.list_componnent
			.getHeight()) - Res.background.getHeight();
	if (this.listoffset > maximumoffset)
	    this.listoffset = maximumoffset;
	if (this.listoffset < 0)
	    this.listoffset = 0;
	int temphover = -1;
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
		temphover = i;
	    } else
		g.drawImage(Res.list_play, Layout_list.pos_componnent_x
			+ Layout_list.pos_play_x, height
			+ Layout_list.pos_play_y, null);
	}
	this.hoveredSongButtonID = temphover;
	// Draws the scroll bar
	int scroll_padding = Layout_list.size_slider_height / 10;
	int scrollVerticalOffest = (int) (this.listoffset
		* (Layout_list.size_slider_height - (scroll_padding) * 2) / maximumoffset);
	g.setColor(Layout_list.color_scroll);
	g.fillRect(Layout_list.pos_slider_x, Layout_list.pos_slider_y
		+ scrollVerticalOffest, 10, scroll_padding * 2);

	// Draws the HUD
	addButtonsToBuffer(g);

	if (Layout_list.value_showvolume == ResLayout.TRUE) {
	    // Draws the volumebar.
	    g.drawImage(Res.hud_volume, Layout_common.pos_volume_x,
		    Layout_common.pos_volume_y, null);
	    int volpos = (int) (((float) this.volume)
		    * (Res.hud_volume.getWidth() - (Res.hud_ki.getWidth() / 2) * 2) / 100);
	    if (this.isonvolume()) {
		g.drawImage(Res.hud_ki_active, Layout_common.pos_volume_x
			+ volpos, Layout_common.pos_volume_y, null);
	    } else {
		g.drawImage(Res.hud_ki, Layout_common.pos_volume_x + volpos,
			Layout_common.pos_volume_y, null);
	    }
	}
	// Draws the back button
	if (this.isonback()) {
	    g.drawImage(Res.hud_back_active, Layout_list.pos_back_x,
		    Layout_list.pos_back_y, null);
	} else {
	    g.drawImage(Res.hud_back, Layout_list.pos_back_x,
		    Layout_list.pos_back_y, null);
	}
	// Draws the list foreground
	g.drawImage(Res.list_foreground, 0, 0, null);
	// prints the buffer to the canvas
	bs.show();
	g.dispose();
    }

    private void updateOptions() {
	BufferStrategy bs = this.getBufferStrategy();
	Graphics g = bs.getDrawGraphics();
	g.clearRect(0, 0, this.getWidth(), this.getWidth());
	// Draws the background
	g.drawImage(Res.options_background, 0, 0, null);
	// Draws the buttons
	if (this.isonoption_skin())
	    g.drawImage(Res.options_skin_active,
		    Layout_options.pos_option_skin_x,
		    Layout_options.pos_option_skin_y, null);
	else
	    g.drawImage(Res.options_skin, Layout_options.pos_option_skin_x,
		    Layout_options.pos_option_skin_y, null);
	if (this.isonoption_playlists())
	    g.drawImage(Res.options_playlists_active,
		    Layout_options.pos_option_playlists_x,
		    Layout_options.pos_option_playlists_y, null);
	else
	    g.drawImage(Res.options_playlists,
		    Layout_options.pos_option_playlists_x,
		    Layout_options.pos_option_playlists_y, null);
	// Draws the HUD
	addButtonsToBuffer(g);
	if (Layout_options.value_showvolume == ResLayout.TRUE) {
	    // Draws the volumebar if needed
	    g.drawImage(Res.hud_volume, Layout_common.pos_volume_x,
		    Layout_common.pos_volume_y, null);
	    int volpos = (int) (((float) this.volume)
		    * (Res.hud_volume.getWidth() - (Res.hud_ki.getWidth() / 2) * 2) / 100);
	    if (this.isonvolume()) {
		g.drawImage(Res.hud_ki_active, Layout_common.pos_volume_x
			+ volpos, Layout_common.pos_volume_y, null);
	    } else {
		g.drawImage(Res.hud_ki, Layout_common.pos_volume_x + volpos,
			Layout_common.pos_volume_y, null);
	    }
	}
	// Draws the back button
	if (this.isonback()) {
	    g.drawImage(Res.hud_back_active, Layout_options.pos_back_x,
		    Layout_options.pos_back_y, null);
	} else {
	    g.drawImage(Res.hud_back, Layout_options.pos_back_x,
		    Layout_options.pos_back_y, null);
	}
	// prints the buffer to the canvas
	bs.show();
	g.dispose();
    }

    private void updatePlaylists() {
	BufferStrategy bs = this.getBufferStrategy();
	Graphics g = bs.getDrawGraphics();
	g.clearRect(0, 0, this.getWidth(), this.getWidth());
	// Draws the background
	g.drawImage(Res.list_background, 0, 0, null);
	// Draws the list of playlists
	g.setColor(Layout_list.color_text);
	g.setFont(Res.font);
	this.listoffset += EDMHouse.frame.wheelvelocity;
	int maximumoffset = Layout_list.pos_componnent_y
		+ ((PlaylistHolder.playlists.length + 2) * Res.list_componnent
			.getHeight()) - Res.background.getHeight();
	if (this.listoffset > maximumoffset)
	    this.listoffset = maximumoffset;
	if (this.listoffset < 0)
	    this.listoffset = 0;
	int temphover = -1;
	int temphoveredit = -1;
	int height1 = (int) (Layout_list.pos_componnent_y - this.listoffset);
	g.drawImage(Res.list_componnent, Layout_list.pos_componnent_x, height1,
		null);
	char[] optiontitle = "Open a songs folder".toCharArray();
	g.drawChars(optiontitle, 0, optiontitle.length, Layout_list.pos_text_x,
		height1 + Layout_list.pos_text_y);
	if (isMouseOnEditInComponent(0)) {
	    g.drawImage(Res.list_edit_active, Layout_list.pos_componnent_x
		    + Layout_list.pos_edit_x, height1 + Layout_list.pos_edit_y,
		    null);
	    temphoveredit = 0;
	} else
	    g.drawImage(Res.list_edit, Layout_list.pos_componnent_x
		    + Layout_list.pos_edit_x, height1 + Layout_list.pos_edit_y,
		    null);
	height1 = (int) (Layout_list.pos_componnent_y - this.listoffset + (Res.list_componnent
		.getHeight()));
	g.drawImage(Res.list_componnent, Layout_list.pos_componnent_x, height1,
		null);
	optiontitle = "Draft a new playlist".toCharArray();
	g.drawChars(optiontitle, 0, optiontitle.length, Layout_list.pos_text_x,
		height1 + Layout_list.pos_text_y);
	if (isMouseOnEditInComponent(1)) {
	    g.drawImage(Res.list_edit_active, Layout_list.pos_componnent_x
		    + Layout_list.pos_edit_x, height1 + Layout_list.pos_edit_y,
		    null);
	    temphoveredit = 1;
	} else
	    g.drawImage(Res.list_edit, Layout_list.pos_componnent_x
		    + Layout_list.pos_edit_x, height1 + Layout_list.pos_edit_y,
		    null);
	for (int i = 2; i < PlaylistHolder.playlists.length + 2; i++) {
	    int height = (int) (Layout_list.pos_componnent_y - this.listoffset + (Res.list_componnent
		    .getHeight() * i));
	    g.drawImage(Res.list_componnent, Layout_list.pos_componnent_x,
		    height, null);
	    char[] listtitle = PlaylistHolder.playlists[i - 2].getname()
		    .toCharArray();
	    g.drawChars(listtitle, 0, listtitle.length, Layout_list.pos_text_x,
		    height + Layout_list.pos_text_y);
	    if (isMouseOnSelectInComponent(i)) {
		g.drawImage(
			Res.list_select_active,
			Layout_list.pos_componnent_x + Layout_list.pos_select_x,
			height + Layout_list.pos_select_y, null);
		temphover = i;
	    } else
		g.drawImage(Res.list_select, Layout_list.pos_componnent_x
			+ Layout_list.pos_select_x, height
			+ Layout_list.pos_select_y, null);
	    if (isMouseOnEditInComponent(i)) {
		g.drawImage(Res.list_edit_active, Layout_list.pos_componnent_x
			+ Layout_list.pos_edit_x, height
			+ Layout_list.pos_edit_y, null);
		temphoveredit = i;
	    } else
		g.drawImage(Res.list_edit, Layout_list.pos_componnent_x
			+ Layout_list.pos_edit_x, height
			+ Layout_list.pos_edit_y, null);
	}
	this.hoveredSelectButtonID = temphover;
	this.hoveredEditButtonID = temphoveredit;
	// Draws the scroll bar
	int scroll_padding = Layout_list.size_slider_height / 10;
	int scrollVerticalOffest = (int) (this.listoffset
		* (Layout_list.size_slider_height - (scroll_padding) * 2) / maximumoffset);
	g.setColor(Layout_list.color_scroll);
	g.fillRect(Layout_list.pos_slider_x, Layout_list.pos_slider_y
		+ scrollVerticalOffest, 10, scroll_padding * 2);
	// Draws the HUD
	addButtonsToBuffer(g);
	if (Layout_list.value_showvolume == ResLayout.TRUE) {
	    // Draws the volumebar if needed
	    g.drawImage(Res.hud_volume, Layout_common.pos_volume_x,
		    Layout_common.pos_volume_y, null);
	    int volpos = (int) (((float) this.volume)
		    * (Res.hud_volume.getWidth() - (Res.hud_ki.getWidth() / 2) * 2) / 100);
	    if (this.isonvolume()) {
		g.drawImage(Res.hud_ki_active, Layout_common.pos_volume_x
			+ volpos, Layout_common.pos_volume_y, null);
	    } else {
		g.drawImage(Res.hud_ki, Layout_common.pos_volume_x + volpos,
			Layout_common.pos_volume_y, null);
	    }
	}
	// Draws the back button
	if (this.isonback()) {
	    g.drawImage(Res.hud_back_active, Layout_list.pos_back_x,
		    Layout_list.pos_back_y, null);
	} else {
	    g.drawImage(Res.hud_back, Layout_list.pos_back_x,
		    Layout_list.pos_back_y, null);
	}
	// prints the buffer to the canvas
	bs.show();
	g.dispose();
    }

    private void updateSkins() {
	BufferStrategy bs = this.getBufferStrategy();
	Graphics g = bs.getDrawGraphics();
	g.clearRect(0, 0, this.getWidth(), this.getWidth());
	// Draws the background
	g.drawImage(Res.list_background, 0, 0, null);
	// Draws the componnents
	g.setColor(Layout_list.color_text);
	g.setFont(Res.font);
	this.listoffset += EDMHouse.frame.wheelvelocity;
	int maximumoffset = Layout_list.pos_componnent_y
		+ (SkinsHolder.skins.length * Res.list_componnent.getHeight())
		- Res.background.getHeight();
	if (this.listoffset > maximumoffset)
	    this.listoffset = maximumoffset;
	if (this.listoffset < 0)
	    this.listoffset = 0;
	int temphover = -1;
	for (int i = 0; i < SkinsHolder.skins.length; i++) {
	    int height = (int) (Layout_list.pos_componnent_y - this.listoffset + (Res.list_componnent
		    .getHeight() * i));
	    g.drawImage(Res.list_componnent, Layout_list.pos_componnent_x,
		    height, null);
	    char[] skintitle = SkinsHolder.skins[i].getName().toCharArray();
	    g.drawChars(skintitle, 0, skintitle.length, Layout_list.pos_text_x,
		    height + Layout_list.pos_text_y);
	    if (isMouseOnSwapInComponent(i)) {
		g.drawImage(Res.list_swap_active, Layout_list.pos_componnent_x
			+ Layout_list.pos_swap_x, height
			+ Layout_list.pos_swap_y, null);
		temphover = i;
	    } else
		g.drawImage(Res.list_swap, Layout_list.pos_componnent_x
			+ Layout_list.pos_swap_x, height
			+ Layout_list.pos_swap_y, null);
	}
	this.hoveredSwapButtonID = temphover;
	// Draws the scroll bar
	int scroll_padding = Layout_list.size_slider_height / 10;
	int scrollVerticalOffest = (int) (this.listoffset
		* (Layout_list.size_slider_height - (scroll_padding) * 2) / maximumoffset);
	g.setColor(Layout_list.color_scroll);
	g.fillRect(Layout_list.pos_slider_x, Layout_list.pos_slider_y
		+ scrollVerticalOffest, 10, scroll_padding * 2);
	// Draws the HUD
	addButtonsToBuffer(g);

	if (Layout_list.value_showvolume == ResLayout.TRUE) {
	    // Draws the volumebar if needed.
	    g.drawImage(Res.hud_volume, Layout_common.pos_volume_x,
		    Layout_common.pos_volume_y, null);
	    int volpos = (int) (((float) this.volume)
		    * (Res.hud_volume.getWidth() - (Res.hud_ki.getWidth() / 2) * 2) / 100);
	    if (this.isonvolume()) {
		g.drawImage(Res.hud_ki_active, Layout_common.pos_volume_x
			+ volpos, Layout_common.pos_volume_y, null);
	    } else {
		g.drawImage(Res.hud_ki, Layout_common.pos_volume_x + volpos,
			Layout_common.pos_volume_y, null);
	    }
	}
	// Draws the back button
	if (this.isonback()) {
	    g.drawImage(Res.hud_back_active, Layout_list.pos_back_x,
		    Layout_list.pos_back_y, null);
	} else {
	    g.drawImage(Res.hud_back, Layout_list.pos_back_x,
		    Layout_list.pos_back_y, null);
	}
	// Draws the list foreground
	g.drawImage(Res.list_foreground, 0, 0, null);
	// prints the buffer to the canvas
	bs.show();
	g.dispose();
    }

    private void updateEditor() {
	BufferStrategy bs = this.getBufferStrategy();
	Graphics g = bs.getDrawGraphics();
	g.clearRect(0, 0, this.getWidth(), this.getWidth());
	// Draws the background
	g.drawImage(Res.list_background, 0, 0, null);
	// Draws the componnents
	g.setColor(Layout_list.color_text);
	g.setFont(Res.font);
	this.listoffset += EDMHouse.frame.wheelvelocity;
	int maximumoffset = Layout_list.pos_componnent_y
		+ ((PlaylistHolder.playlists[this.editingList].getSongs().length + 1) * Res.list_componnent
			.getHeight()) - Res.background.getHeight();
	if (this.listoffset > maximumoffset)
	    this.listoffset = maximumoffset;
	if (this.listoffset < 0)
	    this.listoffset = 0;
	int temphover = -1;

	int uheight = (int) (Layout_list.pos_componnent_y - this.listoffset);
	g.drawImage(Res.list_componnent, Layout_list.pos_componnent_x, uheight,
		null);
	char[] adds = "Add new song".toCharArray();
	g.drawChars(adds, 0, adds.length, Layout_list.pos_text_x, uheight
		+ Layout_list.pos_text_y);
	if (isMouseOnRemoveInComponent(0)) {
	    g.drawImage(Res.list_add_active, Layout_list.pos_componnent_x
		    + Layout_list.pos_remove_x, uheight
		    + Layout_list.pos_remove_y, null);
	    temphover = 0;
	} else
	    g.drawImage(Res.list_add, Layout_list.pos_componnent_x
		    + Layout_list.pos_remove_x, uheight
		    + Layout_list.pos_remove_y, null);
	for (int i = 1; i < PlaylistHolder.playlists[this.editingList]
		.getSongs().length + 1; i++) {
	    int height = (int) (Layout_list.pos_componnent_y - this.listoffset + (Res.list_componnent
		    .getHeight() * i));
	    g.drawImage(Res.list_componnent, Layout_list.pos_componnent_x,
		    height, null);
	    char[] sgtitle = PlaylistHolder.playlists[this.editingList]
		    .getSongs()[i - 1].getdipsplayname().toCharArray();
	    g.drawChars(sgtitle, 0, sgtitle.length, Layout_list.pos_text_x,
		    height + Layout_list.pos_text_y);
	    if (isMouseOnRemoveInComponent(i)) {
		g.drawImage(
			Res.list_remove_active,
			Layout_list.pos_componnent_x + Layout_list.pos_remove_x,
			height + Layout_list.pos_remove_y, null);
		temphover = i;
	    } else
		g.drawImage(Res.list_remove, Layout_list.pos_componnent_x
			+ Layout_list.pos_remove_x, height
			+ Layout_list.pos_remove_y, null);
	}
	this.hoveredPlaylistRemoveButtonID = temphover;
	// Draws the scroll bar
	int scroll_padding = Layout_list.size_slider_height / 10;
	int scrollVerticalOffest = (int) (this.listoffset
		* (Layout_list.size_slider_height - (scroll_padding) * 2) / maximumoffset);
	g.setColor(Layout_list.color_scroll);
	g.fillRect(Layout_list.pos_slider_x, Layout_list.pos_slider_y
		+ scrollVerticalOffest, 10, scroll_padding * 2);
	// Draws the HUD
	addButtonsToBuffer(g);

	if (Layout_list.value_showvolume == ResLayout.TRUE) {
	    // Draws the volumebar if needed.
	    g.drawImage(Res.hud_volume, Layout_common.pos_volume_x,
		    Layout_common.pos_volume_y, null);
	    int volpos = (int) (((float) this.volume)
		    * (Res.hud_volume.getWidth() - (Res.hud_ki.getWidth() / 2) * 2) / 100);
	    if (this.isonvolume()) {
		g.drawImage(Res.hud_ki_active, Layout_common.pos_volume_x
			+ volpos, Layout_common.pos_volume_y, null);
	    } else {
		g.drawImage(Res.hud_ki, Layout_common.pos_volume_x + volpos,
			Layout_common.pos_volume_y, null);
	    }
	}
	// Draws the back button
	if (this.isonback()) {
	    g.drawImage(Res.hud_back_active, Layout_list.pos_back_x,
		    Layout_list.pos_back_y, null);
	} else {
	    g.drawImage(Res.hud_back, Layout_list.pos_back_x,
		    Layout_list.pos_back_y, null);
	}
	// Draws the list foreground
	g.drawImage(Res.list_foreground, 0, 0, null);
	bs.show();
	g.dispose();
    }

    private void updateAdder() {
	BufferStrategy bs = this.getBufferStrategy();
	Graphics g = bs.getDrawGraphics();
	g.clearRect(0, 0, this.getWidth(), this.getWidth());
	// Draws the background
	g.drawImage(Res.list_background, 0, 0, null);
	// Draws the componnents
	g.setColor(Layout_list.color_text);
	g.setFont(Res.font);
	this.listoffset += EDMHouse.frame.wheelvelocity;
	int maximumoffset = Layout_list.pos_componnent_y
		+ (SongFolderHolder.folderContent.length * Res.list_componnent
			.getHeight()) - Res.background.getHeight();
	if (this.listoffset > maximumoffset)
	    this.listoffset = maximumoffset;
	if (this.listoffset < 0)
	    this.listoffset = 0;
	int temphover = -1;
	for (int i = 0; i < SongFolderHolder.folderContent.length; i++) {
	    int height = (int) (Layout_list.pos_componnent_y - this.listoffset + (Res.list_componnent
		    .getHeight() * i));
	    g.drawImage(Res.list_componnent, Layout_list.pos_componnent_x,
		    height, null);
	    char[] songtitle = SongFolderHolder.folderContent[i].getdipsplayname()
		    .toCharArray();
	    g.drawChars(songtitle, 0, songtitle.length, Layout_list.pos_text_x,
		    height + Layout_list.pos_text_y);
	    if (isMouseOnAddInComponent(i)) {
		g.drawImage(Res.list_add_active, Layout_list.pos_componnent_x
			+ Layout_list.pos_add_x, height
			+ Layout_list.pos_add_y, null);
		temphover = i;
	    } else
		g.drawImage(Res.list_add, Layout_list.pos_componnent_x
			+ Layout_list.pos_add_x, height
			+ Layout_list.pos_add_y, null);
	}
	this.hoveredPlaylistAddButtonID = temphover;
	// Draws the scroll bar
	int scroll_padding = Layout_list.size_slider_height / 10;
	int scrollVerticalOffest = (int) (this.listoffset
		* (Layout_list.size_slider_height - (scroll_padding) * 2) / maximumoffset);
	g.setColor(Layout_list.color_scroll);
	g.fillRect(Layout_list.pos_slider_x, Layout_list.pos_slider_y
		+ scrollVerticalOffest, 10, scroll_padding * 2);

	// Draws the HUD
	addButtonsToBuffer(g);

	if (Layout_list.value_showvolume == ResLayout.TRUE) {
	    // Draws the volumebar.
	    g.drawImage(Res.hud_volume, Layout_common.pos_volume_x,
		    Layout_common.pos_volume_y, null);
	    int volpos = (int) (((float) this.volume)
		    * (Res.hud_volume.getWidth() - (Res.hud_ki.getWidth() / 2) * 2) / 100);
	    if (this.isonvolume()) {
		g.drawImage(Res.hud_ki_active, Layout_common.pos_volume_x
			+ volpos, Layout_common.pos_volume_y, null);
	    } else {
		g.drawImage(Res.hud_ki, Layout_common.pos_volume_x + volpos,
			Layout_common.pos_volume_y, null);
	    }
	}
	// Draws the back button
	if (this.isonback()) {
	    g.drawImage(Res.hud_back_active, Layout_list.pos_back_x,
		    Layout_list.pos_back_y, null);
	} else {
	    g.drawImage(Res.hud_back, Layout_list.pos_back_x,
		    Layout_list.pos_back_y, null);
	}
	// Draws the list foreground
	g.drawImage(Res.list_foreground, 0, 0, null);
	// prints the buffer to the canvas
	bs.show();
	g.dispose();
    }

    /**
     * Takes a pointer to a graphics object and uses it to print the main
     * buttons to the graphic displayable content (most likely a buffer).<br/>
     * The added buttons are conditionnal of the mouse position registered by
     * the canvas and the layouts button positions. THe buttons are : close,
     * options, random, skip, list and minimize.
     * 
     * @param g
     *            the graphics object that will be used to print the buttons.
     */
    private void addButtonsToBuffer(Graphics g) {
	if (this.isonclose()) {
	    g.drawImage(Res.hud_cross_red, Layout_common.pos_close_x,
		    Layout_common.pos_close_y, null);
	} else
	    g.drawImage(Res.hud_cross_white, Layout_common.pos_close_x,
		    Layout_common.pos_close_y, null);
	if (this.isonoptions())
	    g.drawImage(Res.hud_options_active, Layout_common.pos_options_x,
		    Layout_common.pos_options_y, null);
	else
	    g.drawImage(Res.hud_options, Layout_common.pos_options_x,
		    Layout_common.pos_options_y, null);
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
     * the container n°<code>containerNumber</code> in the list.
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
     * Predicates that returns true if the mouse is hovering the swap button in
     * the container n°<code>containerNumber</code> in the skin list.
     * <code>containerNumber</code> must start at 0.
     */
    public boolean isMouseOnSwapInComponent(int containerNumber) {
	if (this.isMouseOnScrollbar() || this.isonclose() || this.isonlist()
		|| this.isonmini() || this.isonrandom() || this.isonskip())
	    return false;
	int buttonheight = Layout_list.pos_componnent_y
		+ Layout_list.pos_swap_y
		+ (containerNumber * Res.list_componnent.getHeight())
		- (int) this.listoffset;
	return (EDMHouse.frame.isOnPos(Layout_list.pos_componnent_x
		+ Layout_list.pos_swap_x, buttonheight,
		Layout_list.pos_componnent_x + Layout_list.pos_swap_x
			+ Res.list_swap.getWidth(), buttonheight
			+ Res.list_swap.getHeight()));
    }

    /**
     * Predicates that returns true if the mouse is hovering the edit button in
     * the container n°<code>containerNumber</code> in the playlist list.
     * <code>containerNumber</code> must start at 0.
     */
    public boolean isMouseOnEditInComponent(int containerNumber) {
	if (this.isMouseOnScrollbar() || this.isonclose() || this.isonlist()
		|| this.isonmini() || this.isonrandom() || this.isonskip())
	    return false;
	int buttonheight = Layout_list.pos_componnent_y
		+ Layout_list.pos_edit_y
		+ (containerNumber * Res.list_componnent.getHeight())
		- (int) this.listoffset;
	return (EDMHouse.frame.isOnPos(Layout_list.pos_componnent_x
		+ Layout_list.pos_edit_x, buttonheight,
		Layout_list.pos_componnent_x + Layout_list.pos_edit_x
			+ Res.list_edit.getWidth(), buttonheight
			+ Res.list_edit.getHeight()));
    }

    /**
     * Predicates that returns true if the mouse is hovering the select button
     * in the container n°<code>containerNumber</code> in the playlist list.
     * <code>containerNumber</code> must start at 0.
     */
    public boolean isMouseOnSelectInComponent(int containerNumber) {
	if (this.isMouseOnScrollbar() || this.isonclose() || this.isonlist()
		|| this.isonmini() || this.isonrandom() || this.isonskip())
	    return false;
	int buttonheight = Layout_list.pos_componnent_y
		+ Layout_list.pos_select_y
		+ (containerNumber * Res.list_componnent.getHeight())
		- (int) this.listoffset;
	return (EDMHouse.frame.isOnPos(Layout_list.pos_componnent_x
		+ Layout_list.pos_select_x, buttonheight,
		Layout_list.pos_componnent_x + Layout_list.pos_select_x
			+ Res.list_select.getWidth(), buttonheight
			+ Res.list_select.getHeight()));
    }

    /**
     * Predicates that returns true if the mouse is hovering the remove button
     * in the container n°<code>containerNumber</code> in the edit playlist
     * list. <code>containerNumber</code> must start at 0.
     */
    public boolean isMouseOnRemoveInComponent(int containerNumber) {
	if (this.isMouseOnScrollbar() || this.isonclose() || this.isonlist()
		|| this.isonmini() || this.isonrandom() || this.isonskip())
	    return false;
	int buttonheight = Layout_list.pos_componnent_y
		+ Layout_list.pos_remove_y
		+ (containerNumber * Res.list_componnent.getHeight())
		- (int) this.listoffset;
	return (EDMHouse.frame.isOnPos(Layout_list.pos_componnent_x
		+ Layout_list.pos_remove_x, buttonheight,
		Layout_list.pos_componnent_x + Layout_list.pos_remove_x
			+ Res.list_remove.getWidth(), buttonheight
			+ Res.list_remove.getHeight()));
    }
    
    /**
     * Predicates that returns true if the mouse is hovering the add button
     * in the container n°<code>containerNumber</code> in the playlist
     * song adder. <code>containerNumber</code> must start at 0.
     */
    public boolean isMouseOnAddInComponent(int containerNumber) {
	if (this.isMouseOnScrollbar() || this.isonclose() || this.isonlist()
		|| this.isonmini() || this.isonrandom() || this.isonskip())
	    return false;
	int buttonheight = Layout_list.pos_componnent_y
		+ Layout_list.pos_add_y
		+ (containerNumber * Res.list_componnent.getHeight())
		- (int) this.listoffset;
	return (EDMHouse.frame.isOnPos(Layout_list.pos_componnent_x
		+ Layout_list.pos_add_x, buttonheight,
		Layout_list.pos_componnent_x + Layout_list.pos_add_x
			+ Res.list_add.getWidth(), buttonheight
			+ Res.list_add.getHeight()));
    }

    /**
     * Predicate that returns true if the mouse is hovering the scrollbar
     * componnent and if the frame is in a list state.
     */
    public boolean isMouseOnScrollbar() {
	if (this.content != STATE_LIST && this.content != STATE_SKINS
		&& this.content != STATE_PLAYLISTS
		&& this.content != STATE_PLAYLISTEDITOR
		&& this.content != STATE_PLAYLISTSONGADDER)
	    return false;
	return (EDMHouse.frame.isOnPos(Layout_list.pos_slider_x,
		Layout_list.pos_slider_y, Layout_list.pos_slider_x + 10,
		Layout_list.pos_slider_y + Layout_list.size_slider_height));
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
     * predicate that returns true if the mouse is over the back button,
     * depending on the state of the frame. Always returns false if the canvas
     * content is the default state.
     */
    public boolean isonback() {
	if (this.content == STATE_LIST || this.content == STATE_SKINS
		|| this.content == STATE_PLAYLISTS
		|| this.content == STATE_PLAYLISTEDITOR
		|| this.content == STATE_PLAYLISTSONGADDER)
	    return EDMHouse.frame.isOnPos(Layout_list.pos_back_x,
		    Layout_list.pos_back_y, Layout_list.pos_back_x
			    + Res.hud_back.getWidth(), Layout_list.pos_back_y
			    + Res.hud_back.getHeight());
	if (this.content == STATE_OPTIONS)
	    return EDMHouse.frame.isOnPos(Layout_options.pos_back_x,
		    Layout_options.pos_back_y, Layout_options.pos_back_x
			    + Res.hud_back.getWidth(),
		    Layout_options.pos_back_y + Res.hud_back.getHeight());
	return false;
    }

    /**
     * predicate that returns true if the mouse is over the play/pause button,
     * in the play state only.
     */
    public boolean isonbutton() {
	if (this.content != STATE_DEFAULT)
	    return false;
	return EDMHouse.frame.isOnPos(Layout_common.pos_button_x,
		Layout_common.pos_button_y, Layout_common.pos_button_x
			+ Res.hud_play.getWidth(), Layout_common.pos_button_y
			+ Res.hud_play.getHeight());
    }

    /**
     * predicate that returns true if the mouse is over the progress bar, in the
     * play state only.
     */
    public boolean isonprogress() {
	if (this.content != STATE_DEFAULT)
	    return false;
	return EDMHouse.frame.isOnPos(Layout_common.pos_progress_x,
		Layout_common.pos_progress_y, Layout_common.pos_progress_x
			+ Layout_common.size_progress_width,
		Layout_common.pos_progress_y
			+ Layout_common.size_progress_height);
    }

    /**
     * predicate that returns true if the mouse is over the volume bar, in any
     * state.
     */
    public boolean isonvolume() {
	if (this.content == STATE_LIST && Layout_list.value_showvolume == 0)
	    return false;
	if (this.content == STATE_OPTIONS
		&& Layout_options.value_showvolume == 0)
	    return false;
	return EDMHouse.frame.isOnPos(Layout_common.pos_volume_x,
		Layout_common.pos_volume_y, Layout_common.pos_volume_x
			+ Res.hud_volume.getWidth(), Layout_common.pos_volume_y
			+ Res.hud_volume.getHeight());
    }

    /**
     * predicate that returns true if the mouse is over the options button, in
     * any state.
     */
    public boolean isonoptions() {
	return EDMHouse.frame.isOnPos(Layout_common.pos_options_x,
		Layout_common.pos_options_y, Layout_common.pos_options_x
			+ Res.hud_options.getWidth(),
		Layout_common.pos_options_y + Res.hud_options.getHeight());
    }

    /**
     * predicate that returns true if the mouse is over the option_skin button,
     * in the options state only.
     */
    public boolean isonoption_skin() {
	if (this.content != STATE_OPTIONS)
	    return false;
	return EDMHouse.frame
		.isOnPos(
			Layout_options.pos_option_skin_x,
			Layout_options.pos_option_skin_y,
			Layout_options.pos_option_skin_x
				+ Res.options_skin.getWidth(),
			Layout_options.pos_option_skin_y
				+ Res.options_skin.getHeight());
    }

    /**
     * predicate that returns true if the mouse is over the option_playlists
     * button, in the options state only.
     */
    public boolean isonoption_playlists() {
	if (this.content != STATE_OPTIONS)
	    return false;
	return EDMHouse.frame.isOnPos(
		Layout_options.pos_option_playlists_x,
		Layout_options.pos_option_playlists_y,
		Layout_options.pos_option_playlists_x
			+ Res.options_playlists.getWidth(),
		Layout_options.pos_option_playlists_y
			+ Res.options_playlists.getHeight());
    }
}

package fr.edmhouse.display;

import java.awt.Component;
import java.awt.Frame;
import java.awt.HeadlessException;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.File;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

import fr.edmhouse.audio.AudioList;
import fr.edmhouse.audio.SoundMeter;
import fr.edmhouse.audio.playlists.PlaylistHolder;
import fr.edmhouse.main.EDMHouse;
import fr.edmhouse.main.SkinsHolder;
import fr.edmhouse.res.Layout_common;
import fr.edmhouse.res.Layout_list;
import fr.edmhouse.res.Layout_options;
import fr.edmhouse.res.Res;

public class CFrame {

    /**
     * The velocity of current scrolling on the mousewheel. Shrinks down to zero
     * automaticly and is auto increased or decreased if the mouse wheel is
     * rolled. Can be negative.
     */
    public double wheelvelocity;
    /** Is true if the mouse is inside the frame. */
    private boolean isMouseInside;
    /**
     * The x positon of the mouse on the frame. is 0 if the mouse is not inside.
     */
    private int ix;
    /**
     * The y positon of the mouse on the frame. is 0 if the mouse is not inside.
     */
    private int iy;
    /** A transparent JFrame representing shadows for the current frame. */
    private ShadowWindow shadows;
    /** The Jframe object used to display stuff. Should be left untouched. */
    private JFrame frame;
    /** The canvas object used in the frame. */
    public CCanvas canvas;

    /**
     * Creates a CustomFrame object. Note that this object doesn't extends
     * JFrame.
     */
    public CFrame() {

	this.frame = new JFrame();
	this.frame.setResizable(false);
	this.frame.setUndecorated(true);
	this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	this.frame.setSize(Res.background.getWidth(),
		Res.background.getHeight());
	this.frame.setLocationRelativeTo(null);
	this.frame.setIconImage(Res.icon);
	this.frame.setTitle("EDMhouse");

	this.shadows = new ShadowWindow();
	this.shadows.setVisible(true);

	this.canvas = new CCanvas();
	this.canvas.setSize(Res.background.getWidth(),
		Res.background.getHeight());
	this.frame.add(this.canvas);
	this.canvas.addMouseListener(new MouseListener() {
	    @SuppressWarnings("deprecation")
	    @Override
	    public void mouseReleased(MouseEvent e) {
		int hoveredID = canvas.hoveredSongButtonID;
		int hoveredID2 = canvas.hoveredSwapButtonID;
		int hoveredID3 = canvas.hoveredSelectButtonID;
		int hoveredID4 = canvas.hoveredEditButtonID;
		if (canvas.isonclose())
		    System.exit(0);
		else if (canvas.isonmini()) {
		    frame.setState(Frame.ICONIFIED);
		    Res.flush();
		} else if (canvas.isonbutton()) {
		    canvas.state = !canvas.state;
		    if (canvas.state)
			EDMHouse.bgmthread.suspend();
		    else
			EDMHouse.bgmthread.resume();
		} else if (canvas.isonskip()) {
		    EDMHouse.bgmthread.resume();
		    EDMHouse.BGM.instantstop();
		    if (canvas.random_on)
			EDMHouse.BGM.changemusic(EDMHouse.songs.getRandomUrl());
		    else
			EDMHouse.BGM.changemusic(EDMHouse.songs.getNextUrl());
		    if (canvas.state)
			EDMHouse.bgmthread.suspend();
		} else if (canvas.isonrandom())
		    canvas.random_on = !canvas.random_on;
		else if (canvas.isonback()) {
		    canvas.content = CCanvas.STATE_DEFAULT;
		} else if (canvas.isonlist())
		    if (canvas.content != CCanvas.STATE_LIST)
			canvas.content = CCanvas.STATE_LIST;
		    else
			canvas.content = CCanvas.STATE_DEFAULT;
		else if (canvas.isonoptions())
		    if (canvas.content != CCanvas.STATE_OPTIONS)
			canvas.content = CCanvas.STATE_OPTIONS;
		    else
			canvas.content = CCanvas.STATE_DEFAULT;
		else if (hoveredID != -1) {
		    EDMHouse.bgmthread.resume();
		    EDMHouse.BGM.instantstop();
		    EDMHouse.BGM.changemusic(EDMHouse.songs
			    .getWantedUrl(hoveredID));
		    if (canvas.state)
			EDMHouse.bgmthread.suspend();
		} else if (hoveredID2 != -1) {
		    Res.currentSkinPath = SkinsHolder.skins[hoveredID2]
			    .getFilepath() + "\\";
		    Res.isInitialized = false;
		    Res.initialize();
		    Layout_common.initializeFromFile(Res.currentSkinPath
			    + "common\\layout.edm");
		    Layout_list.initializeFromFile(Res.currentSkinPath
			    + "list\\layout.edm");
		    Layout_options.initializeFromFile(Res.currentSkinPath
			    + "options\\layout.edm");
		    frame.setSize(Res.background.getWidth(),
			    Res.background.getHeight());
		    canvas.setSize(Res.background.getWidth(),
			    Res.background.getHeight());
		    shadows.setSize(Res.background.getWidth() + 20,
			    Res.background.getHeight() + 20);
		    frame.setIconImage(Res.icon);
		} else if (canvas.isonprogress()) {
		    int mouseXonBar = ix - Layout_common.pos_progress_x;
		    canvas.progression = (int) ((((float) mouseXonBar) / ((float) Layout_common.size_progress_width)) * EDMHouse.BGM
			    .getlength());
		    EDMHouse.BGM.needjump = true;
		} else if (canvas.hoveredEditButtonID == 0) {
		    // Pretty simple for what it does right? ^^
		    JFileChooser fileChooser = new JFileChooser() {
			private static final long serialVersionUID = 1L;

			@Override
			protected JDialog createDialog(Component parent)
				throws HeadlessException {
			    JDialog dialog = super.createDialog(parent);
			    dialog.setIconImage(Res.icon);
			    return dialog;
			}
		    };
		    fileChooser
			    .setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		    if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			File folder = fileChooser.getSelectedFile();
			EDMHouse.songs = new AudioList(folder.getAbsolutePath());
		    }
		} else if (hoveredID3 >= 2) {
		    EDMHouse.songs = new AudioList(PlaylistHolder.playlists[hoveredID3-2]);
		} else if (canvas.isonoption_skin()) {
		    canvas.content = CCanvas.STATE_SKINS;
		} else if (canvas.isonoption_playlists()) {
		    canvas.content = CCanvas.STATE_PLAYLISTS;
		}
	    }

	    @Override
	    public void mousePressed(MouseEvent e) {
		shadows.setVisible(true);
		frame.setVisible(true);
	    }

	    @Override
	    public void mouseExited(MouseEvent e) {
		isMouseInside = false;
	    }

	    @Override
	    public void mouseEntered(MouseEvent e) {
		isMouseInside = true;
	    }

	    @Override
	    public void mouseClicked(MouseEvent e) {
	    }
	});
	this.canvas.addMouseMotionListener(new MouseMotionListener() {
	    @Override
	    public void mouseMoved(MouseEvent e) {
		ix = e.getX();
		iy = e.getY();
	    }

	    @Override
	    public void mouseDragged(MouseEvent e) {
		if (canvas.isMouseOnScrollbar()) {
		    double mouseYOnBar = e.getY()
			    - (Layout_list.pos_slider_y + (Layout_list.size_slider_height / 10));
		    double maximumListOffset = Layout_list.pos_componnent_y
			    + (EDMHouse.songs.getSongAmmount() * Res.list_componnent
				    .getHeight()) - Res.background.getHeight();
		    canvas.listoffset = (int) ((mouseYOnBar / (Layout_list.size_slider_height * 0.8)) * maximumListOffset);
		} else if (canvas.isonvolume()) {
		    double mouseXOnBar = e.getX() - Layout_common.pos_volume_x
			    - (Res.hud_ki.getWidth() / 2);
		    int volValue = (int) (mouseXOnBar
			    / (Res.hud_volume.getWidth() - Res.hud_ki
				    .getWidth()) * 100);
		    if (volValue > 100)
			volValue = 100;
		    if (volValue < 0)
			volValue = 0;
		    canvas.volume = volValue;
		    SoundMeter.setSystemVolume(((float) volValue) / 100);
		} else {
		    if (isMouseInside) {
			int xs = e.getXOnScreen();
			int ys = e.getYOnScreen();
			shadows.setLocation(xs - ix - 10, ys - iy - 10);
			frame.setLocation(xs - ix, ys - iy);
		    }
		}
	    }
	});
	this.canvas.addMouseWheelListener(new MouseWheelListener() {
	    @Override
	    public void mouseWheelMoved(MouseWheelEvent e) {
		wheelvelocity += e.getPreciseWheelRotation() * 10;
		// TODO : add this to a layout.edm file?
	    }
	});
	this.canvas.initialize();
	this.frame.setVisible(true);
	this.canvas.createBufferStrategy(3);
    }

    public void update() {
	if (Math.abs(this.wheelvelocity) > 0.1)
	    this.wheelvelocity = wheelvelocity / 1.1;
	else
	    this.wheelvelocity = 0;
	// TODO : change the wheelvelocity decreasement to something fixed in
	// time? or perhaps make it customisable in the layout.edm file?
	if (!this.canvas.state)
	    this.canvas.progression += 10;
	if (this.isVisible()) {
	    if (!this.shadows.isVisible()) {
		shadows.setVisible(true);
		frame.setVisible(true);
	    }
	    Res.restore();
	    this.canvas.update(this.canvas.getGraphics());
	} else {
	    shadows.setVisible(false);
	}
    }

    /**
     * Predicate that returns true if the mouse is inside the specified
     * rectangle area. Always return false if the mouse is not inside.
     */
    public boolean isOnPos(int x1, int y1, int x2, int y2) {
	if (EDMHouse.frame.isMouseInside)
	    return (ix > x1 && iy > y1 && ix <= x2 && iy <= y2);
	else
	    return false;
    }

    /**
     * Predicate that returns true if the mouse is inside the frame. Is auto
     * updated by the mouse listener in the canvas.
     */
    public boolean isMouseInside() {
	return this.isMouseInside;
    }

    /** predicate that returns true if the frame is visible (not iconified). */
    public boolean isVisible() {
	return (this.frame.getExtendedState() != JFrame.ICONIFIED);
    }

    /**
     * Changes the size of the frame the the desired value. Be aware that using
     * this method to change the frame size to a bigger value than the
     * background will result in grey borders.
     */
    public void changeSize(int width, int height) {
	this.frame.setSize(width, height);
	this.shadows.setSize(width + 20, height + 20);
    }
}

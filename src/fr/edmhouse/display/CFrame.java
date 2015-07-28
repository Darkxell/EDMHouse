package fr.edmhouse.display;

import java.awt.Frame;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JFrame;

import fr.edmhouse.main.EDMHouse;
import fr.edmhouse.main.SoundMeter;
import fr.edmhouse.res.Layout_common;
import fr.edmhouse.res.Layout_list;
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
	this.frame.setSize(Layout_common.size_frame_width,
		Layout_common.size_frame_height);
	this.frame.setLocationRelativeTo(null);
	this.frame.setIconImage(Res.icon);
	this.frame.setTitle("EDMhouse");
	//this.frame.setBackground(new Color(0, 0, 0, 0));
	//this.frame.setContentPane(new ShadowPane());
        //this.frame.setLayout(new BorderLayout());
	// TODO : Work on this later on.
	this.canvas = new CCanvas();
	this.canvas.setSize(Layout_common.size_frame_width,
		Layout_common.size_frame_height);
	this.frame.add(this.canvas);
	this.canvas.addMouseListener(new MouseListener() {
	    @SuppressWarnings("deprecation")
	    @Override
	    public void mouseReleased(MouseEvent e) {
		int hoveredID = canvas.hoveredSongButtonID;
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
		else if (canvas.isonlist())
		    if (canvas.content == 0)
			canvas.content = 1;
		    else
			canvas.content = 0;
		else if (hoveredID != -1) {
		    EDMHouse.bgmthread.resume();
		    EDMHouse.BGM.instantstop();
		    EDMHouse.BGM.changemusic(EDMHouse.songs
			    .getWantedUrl(hoveredID));
		    if (canvas.state)
			EDMHouse.bgmthread.suspend();
		}
	    }

	    @Override
	    public void mousePressed(MouseEvent e) {
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
				    .getHeight())
			    - Layout_common.size_frame_height;
		    canvas.listoffset = (int) ((mouseYOnBar / (Layout_list.size_slider_height * 0.8)) * maximumListOffset);
		} else if (canvas.isonvolume()) {
		    double mouseXOnBar = e.getX() - Layout_common.pos_volume_x
			    - (Res.hud_ki.getWidth() / 2);
		    int volValue = (int) (mouseXOnBar
			    / (Res.hud_volume.getWidth()-Res.hud_ki.getWidth()) * 100);
		    if (volValue > 100)
			volValue = 100;
		    if (volValue < 0)
			volValue = 0;
		    canvas.volume = volValue;
		    SoundMeter.setSystemVolume(((float)volValue)/100);
		} else {
		    if (isMouseInside) {
			int xs = e.getXOnScreen();
			int ys = e.getYOnScreen();
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
	    Res.restore();
	    this.canvas.update(this.canvas.getGraphics());
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
}

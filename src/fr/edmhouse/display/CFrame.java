package fr.edmhouse.display;

import java.awt.Frame;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JFrame;

import fr.edmhouse.main.EDMHouse;
import fr.edmhouse.res.Layout_common;
import fr.edmhouse.res.Res;

public class CFrame {

    /**
     * The velocity of current scrolling on the mousewheel. Shrinks down to zero
     * automaticly and is auto increased or decreased if the mouse wheel is
     * rolled. Can be negative.
     */
    public double wheelvelocity;
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
	this.canvas = new CCanvas();
	this.canvas.setSize(Layout_common.size_frame_width,
		Layout_common.size_frame_height);
	this.frame.add(this.canvas);
	this.canvas.addMouseListener(new MouseListener() {
	    @SuppressWarnings("deprecation")
	    @Override
	    public void mouseReleased(MouseEvent e) {
		if (canvas.isonclose)
		    System.exit(0);
		if (canvas.isonmini)
		    frame.setState(Frame.ICONIFIED);
		if (canvas.isonbutton && canvas.content == 0) {
		    canvas.state = !canvas.state;
		    if (canvas.state)
			EDMHouse.bgmthread.suspend();
		    else
			EDMHouse.bgmthread.resume();
		}
		if (canvas.isonskip) {
		    EDMHouse.bgmthread.resume();
		    EDMHouse.BGM.instantstop();
		    if (canvas.random_on)
			EDMHouse.BGM.changemusic(EDMHouse.songs.getRandomUrl());
		    else
			EDMHouse.BGM.changemusic(EDMHouse.songs.getNextUrl());
		    if (canvas.state)
			EDMHouse.bgmthread.suspend();
		}
		if (canvas.isonrandom)
		    canvas.random_on = !canvas.random_on;
		if (canvas.isonlist)
		    if (canvas.content == 0)
			canvas.content = 1;
		    else
			canvas.content = 0;
	    }

	    @Override
	    public void mousePressed(MouseEvent e) {
	    }

	    @Override
	    public void mouseExited(MouseEvent e) {
		ix = 0;
		iy = 0;
	    }

	    @Override
	    public void mouseEntered(MouseEvent e) {
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
		int xs = e.getXOnScreen();
		int ys = e.getYOnScreen();
		frame.setLocation(xs - ix, ys - iy);
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
	this.canvas.isonrandom = this.isOnPos(Layout_common.pos_random_x,
		Layout_common.pos_random_y, Layout_common.pos_random_x
			+ Res.hud_random.getWidth(), Layout_common.pos_random_y
			+ Res.hud_random.getHeight());
	this.canvas.isonlist = this.isOnPos(Layout_common.pos_list_x,
		Layout_common.pos_list_y, Layout_common.pos_list_x
			+ Res.hud_list.getWidth(), Layout_common.pos_list_y
			+ Res.hud_list.getHeight());
	this.canvas.isonskip = this.isOnPos(Layout_common.pos_skip_x,
		Layout_common.pos_skip_y, Layout_common.pos_skip_x
			+ Res.hud_skip.getWidth(), Layout_common.pos_skip_y
			+ Res.hud_skip.getHeight());
	this.canvas.isonclose = this.isOnPos(Layout_common.pos_close_x,
		Layout_common.pos_close_y, Layout_common.pos_close_x
			+ Res.hud_cross_white.getWidth(),
		Layout_common.pos_close_y + Res.hud_cross_white.getHeight());
	this.canvas.isonmini = this.isOnPos(Layout_common.pos_mini_x,
		Layout_common.pos_mini_y, Layout_common.pos_mini_x
			+ Res.hud_mini_white.getWidth(),
		Layout_common.pos_mini_y + Res.hud_mini_white.getHeight());
	this.canvas.isonbutton = this.isOnPos(Layout_common.pos_button_x,
		Layout_common.pos_button_y, Layout_common.pos_button_x
			+ Res.hud_play.getWidth(), Layout_common.pos_button_y
			+ Res.hud_play.getHeight());
	this.canvas.update(this.canvas.getGraphics());
    }

    public boolean isOnPos(int x1, int y1, int x2, int y2) {
	return (ix > x1 && iy > y1 && ix <= x2 && iy <= y2);
    }
}

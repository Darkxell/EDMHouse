package fr.edmhouse.display;

import java.awt.Frame;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;

import fr.edmhouse.main.EDMHouse;
import fr.edmhouse.res.CustomList;
import fr.edmhouse.res.Res;

public class CFrame {

    /**
     * The x positon of the mouse on the frame. is 0 if the mouse is not inside.
     */
    private int ix;
    /**
     * The y positon of the mouse on the frame. is 0 if the mouse is not inside.
     */
    private int iy;
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
	this.frame.setSize(CustomList.size_frame_width,
		CustomList.size_frame_height);
	this.frame.setLocationRelativeTo(null);
	this.frame.setIconImage(Res.icon);
	this.frame.setTitle("EDMhouse");
	this.canvas = new CCanvas();
	this.canvas.setSize(CustomList.size_frame_width,
		CustomList.size_frame_height);
	this.frame.add(this.canvas);
	this.canvas.addMouseListener(new MouseListener() {
	    @SuppressWarnings("deprecation")
	    @Override
	    public void mouseReleased(MouseEvent e) {
		if (canvas.isonclose)
		    System.exit(0);
		if (canvas.isonmini)
		    frame.setState(Frame.ICONIFIED);
		if (canvas.isonbutton) {
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
		    canvas.random_on = !canvas.random_on ;
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
	this.canvas.initialize();
	this.frame.setVisible(true);
	this.canvas.createBufferStrategy(3);
    }

    public void update() {
	this.canvas.isonrandom = this.isOnPos(CustomList.pos_random_x,
		CustomList.pos_random_y, CustomList.pos_random_x
			+ Res.hud_random.getWidth(), CustomList.pos_random_y
			+ Res.hud_random.getHeight());
	this.canvas.isonlist = this.isOnPos(CustomList.pos_list_x,
		CustomList.pos_list_y, CustomList.pos_list_x
			+ Res.hud_list.getWidth(), CustomList.pos_list_y
			+ Res.hud_list.getHeight());
	this.canvas.isonskip = this.isOnPos(CustomList.pos_skip_x,
		CustomList.pos_skip_y, CustomList.pos_skip_x
			+ Res.hud_skip.getWidth(), CustomList.pos_skip_y
			+ Res.hud_skip.getHeight());
	this.canvas.isonclose = this.isOnPos(CustomList.pos_close_x,
		CustomList.pos_close_y, CustomList.pos_close_x
			+ Res.hud_cross_white.getWidth(),
		CustomList.pos_close_y + Res.hud_cross_white.getHeight());
	this.canvas.isonmini = this.isOnPos(CustomList.pos_mini_x,
		CustomList.pos_mini_y, CustomList.pos_mini_x
			+ Res.hud_mini_white.getWidth(), CustomList.pos_mini_y
			+ Res.hud_mini_white.getHeight());
	this.canvas.isonbutton = this.isOnPos(CustomList.pos_button_x,
		CustomList.pos_button_y,
		CustomList.pos_button_x + Res.hud_play.getWidth(),
		CustomList.pos_button_y + Res.hud_play.getHeight());
	this.canvas.update(this.canvas.getGraphics());
    }

    public boolean isOnPos(int x1, int y1, int x2, int y2) {
	return (ix > x1 && iy > y1 && ix <= x2 && iy <= y2);
    }
}

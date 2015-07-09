package fr.edmhouse.res;

import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

/**
 * Static class that holds ressources as Java objects for font and images. Make
 * sure to initialize it before using it.
 */
public class Res {

    /** Path to the main project folder. Should be null in the final product. */
    public static final String FOLDER_PATH = "C:\\Users\\Darkxell_mc\\Desktop\\EDMhouse\\";
    // public static final String FOLDER_PATH = "";
    // TODO : switch this when relasing.

    /** The font used in the frame. */
    public static Font font;

    /* The images used in the frame. */
    public static BufferedImage background;
    public static BufferedImage foreground;
    public static BufferedImage icon;

    public static BufferedImage hud_cross_red;
    public static BufferedImage hud_cross_white;
    public static BufferedImage hud_mini_grey;
    public static BufferedImage hud_mini_white;
    public static BufferedImage hud_skip;
    public static BufferedImage hud_skip_active;
    public static BufferedImage hud_random;
    public static BufferedImage hud_random_active;
    public static BufferedImage hud_random_on;
    public static BufferedImage hud_random_on_active;
    public static BufferedImage hud_list;
    public static BufferedImage hud_list_active;
    public static BufferedImage hud_play;
    public static BufferedImage hud_pause;
    public static BufferedImage hud_play_active;
    public static BufferedImage hud_pause_active;

    public static void initialize() {
	try {
	    font = FontBuilder.createfont(FOLDER_PATH + "ressources\\font.otf");
	    font = font.deriveFont(CustomList.size_text);
	    icon = ImageIO.read(new File(FOLDER_PATH + "ressources\\icon.png"));

	    background = ImageIO.read(new File(FOLDER_PATH
		    + "ressources\\background.png"));
	    foreground = ImageIO.read(new File(FOLDER_PATH
		    + "ressources\\foreground.png"));

	    hud_cross_red = ImageIO.read(new File(FOLDER_PATH
		    + "ressources\\cross_red.png"));
	    hud_cross_white = ImageIO.read(new File(FOLDER_PATH
		    + "ressources\\cross_white.png"));
	    hud_mini_grey = ImageIO.read(new File(FOLDER_PATH
		    + "ressources\\mini_grey.png"));
	    hud_mini_white = ImageIO.read(new File(FOLDER_PATH
		    + "ressources\\mini_white.png"));
	    hud_play = ImageIO.read(new File(FOLDER_PATH
		    + "ressources\\hud_play.png"));
	    hud_pause = ImageIO.read(new File(FOLDER_PATH
		    + "ressources\\hud_pause.png"));
	    hud_play_active = ImageIO.read(new File(FOLDER_PATH
		    + "ressources\\hud_play_active.png"));
	    hud_pause_active = ImageIO.read(new File(FOLDER_PATH
		    + "ressources\\hud_pause_active.png"));
	    hud_random = ImageIO.read(new File(FOLDER_PATH
		    + "ressources\\random_white.png"));
	    hud_random_active = ImageIO.read(new File(FOLDER_PATH
		    + "ressources\\random_grey.png"));
	    hud_random_on = ImageIO.read(new File(FOLDER_PATH
		    + "ressources\\random_white_on.png"));
	    hud_random_on_active = ImageIO.read(new File(FOLDER_PATH
		    + "ressources\\random_grey_on.png"));
	    hud_list = ImageIO.read(new File(FOLDER_PATH
		    + "ressources\\list_white.png"));
	    hud_list_active = ImageIO.read(new File(FOLDER_PATH
		    + "ressources\\list_grey.png"));
	    hud_skip = ImageIO.read(new File(FOLDER_PATH
		    + "ressources\\skip_white.png"));
	    hud_skip_active = ImageIO.read(new File(FOLDER_PATH
		    + "ressources\\skip_grey.png"));
	} catch (Exception e) {
	    System.err.println("Ressources haven't been successfuly loaded.");
	    e.printStackTrace();
	}
    }
}

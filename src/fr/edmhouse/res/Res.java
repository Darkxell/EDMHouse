package fr.edmhouse.res;

import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import fr.edmhouse.main.Crashframe;

/**
 * Static class that holds ressources as Java objects for font and images. Make
 * sure to initialize it before using it.
 */
public class Res {

    /** Path to the main project folder. Should be null in the final product. */
    public static final String FOLDER_PATH = "C:\\Users\\Darkxell_mc\\Desktop\\EDMhouse\\";
    //public static final String FOLDER_PATH = "";
    // TODO : switch this when relasing.

    /** Boolean that is true ONLY if the images are loaded */
    public static boolean isInitialized;

    /** The font used in the frame. */
    public static Font font;

    /* The images used in the frame. */
    public static BufferedImage background;
    public static BufferedImage foreground;
    public static BufferedImage icon;
    public static BufferedImage loading;

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
    public static BufferedImage hud_volume;
    public static BufferedImage hud_ki;
    public static BufferedImage hud_ki_active;

    public static BufferedImage hud_play;
    public static BufferedImage hud_pause;
    public static BufferedImage hud_play_active;
    public static BufferedImage hud_pause_active;

    /* The images used in the list */
    public static BufferedImage list_play;
    public static BufferedImage list_play_active;
    public static BufferedImage list_componnent;
    public static BufferedImage list_background;
    public static BufferedImage list_foreground;

    /** Initializes all the images and the font from the ressources folder. */
    public static void initialize() {
	try {
	    icon = ImageIO.read(new File(FOLDER_PATH + "ressources\\icon.png"));
	    loading = ImageIO.read(new File(FOLDER_PATH
		    + "ressources\\loading.png"));
	    restore();
	} catch (Exception e) {
	    Crashframe cf = new Crashframe(
		    "Ressource error.",
		    "Sorry, an error happened while loading ressources."
			    + " Make sure that all the ressources are in "
			    + "the \"ressources\" folder next to the .jar file."
			    + " If you keep seeing this message, you might be "
			    + "missing some ressources or the skin you are "
			    + "using don't have them all.");
	    cf.launch();
	}

    }

    /**
     * Restore the ressources in the ram. Similar as the initialize method but
     * without loading the loading image and the icon that always stays in the
     * ram. If the Ressources are already initialized, doesn't do anything.
     */
    public static void restore() {
	if (!isInitialized) {
	    try {
		try {
		    font = FontBuilder.createfont(FOLDER_PATH
			    + "ressources\\font.otf");
		} catch (Exception e) {
		    font = FontBuilder.createfont(FOLDER_PATH
			    + "ressources\\font.ttf");
		}
		font = font.deriveFont(Layout_common.size_text);
		background = ImageIO.read(new File(FOLDER_PATH
			+ "ressources\\common\\background.png"));
		foreground = ImageIO.read(new File(FOLDER_PATH
			+ "ressources\\common\\foreground.png"));
		hud_cross_red = ImageIO.read(new File(FOLDER_PATH
			+ "ressources\\cross_red.png"));
		hud_cross_white = ImageIO.read(new File(FOLDER_PATH
			+ "ressources\\cross_white.png"));
		hud_mini_grey = ImageIO.read(new File(FOLDER_PATH
			+ "ressources\\mini_grey.png"));
		hud_mini_white = ImageIO.read(new File(FOLDER_PATH
			+ "ressources\\mini_white.png"));
		hud_play = ImageIO.read(new File(FOLDER_PATH
			+ "ressources\\common\\hud_play.png"));
		hud_pause = ImageIO.read(new File(FOLDER_PATH
			+ "ressources\\common\\hud_pause.png"));
		hud_play_active = ImageIO.read(new File(FOLDER_PATH
			+ "ressources\\common\\hud_play_active.png"));
		hud_pause_active = ImageIO.read(new File(FOLDER_PATH
			+ "ressources\\common\\hud_pause_active.png"));
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
		hud_volume = ImageIO.read(new File(FOLDER_PATH
			+ "ressources\\volume.png"));
		hud_ki = ImageIO.read(new File(FOLDER_PATH
			+ "ressources\\volume_ki.png"));
		hud_ki_active = ImageIO.read(new File(FOLDER_PATH
			+ "ressources\\volume_ki_active.png"));

		/* list starts here */

		list_play = ImageIO.read(new File(FOLDER_PATH
			+ "ressources\\list\\play.png"));
		list_play_active = ImageIO.read(new File(FOLDER_PATH
			+ "ressources\\list\\play_active.png"));
		list_componnent = ImageIO.read(new File(FOLDER_PATH
			+ "ressources\\list\\container.png"));
		list_background = ImageIO.read(new File(FOLDER_PATH
			+ "ressources\\list\\background.png"));
		list_foreground = ImageIO.read(new File(FOLDER_PATH
			+ "ressources\\list\\foreground.png"));
		isInitialized = true;
	    } catch (Exception e) {
		Crashframe cf = new Crashframe(
			"Ressource error.",
			"Sorry, an error happened while loading ressources."
				+ " Make sure that all the ressources are in "
				+ "the \"ressources\" folder next to the .jar file."
				+ " If you keep seeing this message, you might be "
				+ "missing some ressources or the skin you are "
				+ "using don't have them all.");
		cf.launch();
	    }
	}
    }

    /**
     * Flushes the ressources and sets the isInitialized value to false. Flushes
     * everything but the loading screen and the icon.
     */
    public static void flush() {
	font = null;
	background = null;
	foreground = null;
	hud_cross_red = null;
	hud_cross_white = null;
	hud_mini_grey = null;
	hud_mini_white = null;
	hud_play = null;
	hud_pause = null;
	hud_play_active = null;
	hud_pause_active = null;
	hud_random = null;
	hud_random_active = null;
	hud_random_on = null;
	hud_random_on_active = null;
	hud_list = null;
	hud_list_active = null;
	hud_skip = null;
	hud_skip_active = null;
	hud_volume = null;
	hud_ki = null;
	hud_ki_active = null;
	list_play = null;
	list_play_active = null;
	list_componnent = null;
	list_background = null;
	list_foreground = null;
	isInitialized = false;
    }
}

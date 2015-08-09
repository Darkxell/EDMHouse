package fr.edmhouse.res;

import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import fr.edmhouse.main.Crashframe;

/**
 * Static class that holds ressources as Java objects for font and images. Make
 * sure to initialize it before using it. Contained data can be flushed to
 * improve RAM usage when ressources are not needed. Make sure to check if the
 * data is set before using it, otherwise it will be null.
 */
public class Res {

    /** Path to the main project folder. Should be empty in the final product. */
    public static final String FOLDER_PATH = "C:\\Users\\Darkxell_mc\\Desktop\\EDMhouse\\";
    // public static final String FOLDER_PATH = "";
    // TODO : switch this when relasing.

    /**
     * Boolean that is true ONLY if the images are loaded. Should be left
     * untouched from outside this class.
     */
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
    public static BufferedImage hud_options;
    public static BufferedImage hud_options_active;

    public static BufferedImage hud_play;
    public static BufferedImage hud_pause;
    public static BufferedImage hud_play_active;
    public static BufferedImage hud_pause_active;

    /* The images used in the list */
    public static BufferedImage list_play;
    public static BufferedImage list_play_active;
    public static BufferedImage list_swap;
    public static BufferedImage list_swap_active;
    public static BufferedImage list_componnent;
    public static BufferedImage list_background;
    public static BufferedImage list_foreground;

    /* The images used in the option menu */
    public static BufferedImage options_background;
    public static BufferedImage options_songs;
    public static BufferedImage options_songs_active;
    public static BufferedImage options_skin;
    public static BufferedImage options_skin_active;

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
			+ "ressources\\cross_active.png"));
		hud_cross_white = ImageIO.read(new File(FOLDER_PATH
			+ "ressources\\cross.png"));
		hud_mini_grey = ImageIO.read(new File(FOLDER_PATH
			+ "ressources\\mini_active.png"));
		hud_mini_white = ImageIO.read(new File(FOLDER_PATH
			+ "ressources\\mini.png"));
		hud_play = ImageIO.read(new File(FOLDER_PATH
			+ "ressources\\common\\hud_play.png"));
		hud_pause = ImageIO.read(new File(FOLDER_PATH
			+ "ressources\\common\\hud_pause.png"));
		hud_play_active = ImageIO.read(new File(FOLDER_PATH
			+ "ressources\\common\\hud_play_active.png"));
		hud_pause_active = ImageIO.read(new File(FOLDER_PATH
			+ "ressources\\common\\hud_pause_active.png"));
		hud_random = ImageIO.read(new File(FOLDER_PATH
			+ "ressources\\random.png"));
		hud_random_active = ImageIO.read(new File(FOLDER_PATH
			+ "ressources\\random_active.png"));
		hud_random_on = ImageIO.read(new File(FOLDER_PATH
			+ "ressources\\random_on.png"));
		hud_random_on_active = ImageIO.read(new File(FOLDER_PATH
			+ "ressources\\random_active_on.png"));
		hud_list = ImageIO.read(new File(FOLDER_PATH
			+ "ressources\\list.png"));
		hud_list_active = ImageIO.read(new File(FOLDER_PATH
			+ "ressources\\list_active.png"));
		hud_skip = ImageIO.read(new File(FOLDER_PATH
			+ "ressources\\skip.png"));
		hud_skip_active = ImageIO.read(new File(FOLDER_PATH
			+ "ressources\\skip_active.png"));
		hud_volume = ImageIO.read(new File(FOLDER_PATH
			+ "ressources\\volume.png"));
		hud_ki = ImageIO.read(new File(FOLDER_PATH
			+ "ressources\\volume_ki.png"));
		hud_ki_active = ImageIO.read(new File(FOLDER_PATH
			+ "ressources\\volume_ki_active.png"));
		hud_options = ImageIO.read(new File(FOLDER_PATH
			+ "ressources\\options.png"));
		hud_options_active = ImageIO.read(new File(FOLDER_PATH
			+ "ressources\\options_active.png"));

		/* list starts here */

		list_play = ImageIO.read(new File(FOLDER_PATH
			+ "ressources\\list\\play.png"));
		list_play_active = ImageIO.read(new File(FOLDER_PATH
			+ "ressources\\list\\play_active.png"));
		list_swap = ImageIO.read(new File(FOLDER_PATH
			+ "ressources\\list\\swap.png"));
		list_swap_active = ImageIO.read(new File(FOLDER_PATH
			+ "ressources\\list\\swap_active.png"));
		list_componnent = ImageIO.read(new File(FOLDER_PATH
			+ "ressources\\list\\container.png"));
		list_background = ImageIO.read(new File(FOLDER_PATH
			+ "ressources\\list\\background.png"));
		list_foreground = ImageIO.read(new File(FOLDER_PATH
			+ "ressources\\list\\foreground.png"));

		/* options starts here */

		options_background = ImageIO.read(new File(FOLDER_PATH
			+ "ressources\\options\\background.png"));
		options_songs = ImageIO.read(new File(FOLDER_PATH
			+ "ressources\\options\\option_songs.png"));
		options_songs_active = ImageIO.read(new File(FOLDER_PATH
			+ "ressources\\options\\option_songs_active.png"));
		options_skin = ImageIO.read(new File(FOLDER_PATH
			+ "ressources\\options\\option_skin.png"));
		options_skin_active = ImageIO.read(new File(FOLDER_PATH
			+ "ressources\\options\\option_skin_active.png"));

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
	hud_options = null;
	hud_options_active = null;
	list_play = null;
	list_play_active = null;
	list_swap = null;
	list_swap_active = null;
	list_componnent = null;
	list_background = null;
	list_foreground = null;
	options_background = null;
	options_songs = null;
	options_songs_active = null;
	options_skin = null;
	options_skin_active = null;
	isInitialized = false;
    }
}

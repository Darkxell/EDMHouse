package fr.edmhouse.res;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;

import fr.edmhouse.main.Crashframe;
import fr.edmhouse.main.StringMatcher;

/**
 * Static class that holds the position of the different componnents in the
 * frame, and the default progress bar color.
 */
public class Layout_common implements ResLayout{

    /* buttons positions */
    public static int pos_mini_x = 0;
    public static int pos_mini_y = 0;
    public static int pos_close_x = 0;
    public static int pos_close_y = 0;
    public static int pos_list_x = 0;
    public static int pos_list_y = 0;
    public static int pos_skip_x = 0;
    public static int pos_skip_y = 0;
    public static int pos_random_x = 0;
    public static int pos_random_y = 0;
    public static int pos_button_x = 0;
    public static int pos_button_y = 0;
    /* other positions */
    public static int pos_progress_x = 0;
    public static int pos_progress_y = 0;
    public static int pos_text_x = 0;
    public static int pos_text_y = 0;
    /* items size */
    public static float size_text = 0;
    public static int size_progress_height = 0;
    public static int size_progress_width = 0;
    public static int size_frame_height = 0;
    public static int size_frame_width = 0;
    public static float size_textoffset = 0;
    /* Colors */
    public static Color color_progress = null;
    public static Color color_text = null;
    
    public static void initializeFromFile(String filepath) {
	String filestring = "";
	StringBuilder builder = new StringBuilder();
	try {
	    BufferedReader br = new BufferedReader(new FileReader(filepath));
	    String line;
	    while ((line = br.readLine()) != null)
		builder.append(line + "\n");
	    br.close();
	} catch (Exception e) {
	    Crashframe cf = new Crashframe("Ressource error.",
		    "Sorry, we couldn't find the layout.edm file in the ressources folder."
			    + "Make sure that the file is present.");
	    cf.launch();
	}
	filestring = builder.toString();
	filestring = filestring.toLowerCase();
	String[] array = filestring.split("\n");
	// Starts to use the values.
	int value, color_r = 0, color_g = 0, color_b = 0, color_a = 0, color_r1 = 0, color_g1 = 0, color_b1 = 0, color_a1 = 0;
	for (int i = 0; i < array.length; i++) {
	    try {
		value = Integer.parseInt(StringMatcher
			.getAbsoluteContent(StringMatcher.getBracketsContent(
				array[i], 1)));
	    } catch (Exception e) {
		value = 0;
	    }
	    switch (i) {
	    case 1:
		pos_mini_x = value;
		break;
	    case 2:
		pos_mini_y = value;
		break;
	    case 3:
		pos_close_x = value;
		break;
	    case 4:
		pos_close_y = value;
		break;
	    case 5:
		pos_list_x = value;
		break;
	    case 6:
		pos_list_y = value;
		break;
	    case 7:
		pos_skip_x = value;
		break;
	    case 8:
		pos_skip_y = value;
		break;
	    case 9:
		pos_random_x = value;
		break;
	    case 10:
		pos_random_y = value;
		break;
	    case 11:
		pos_button_x = value;
		break;
	    case 12:
		pos_button_y = value;
		break;
	    case 15:
		pos_progress_x = value;
		break;
	    case 16:
		pos_progress_y = value;
		break;
	    case 17:
		pos_text_x = value;
		break;
	    case 18:
		pos_text_y = value;
		break;
	    case 21:
		size_text = ((float) value) / 100;
		break;
	    case 22:
		size_progress_height = value;
		break;
	    case 23:
		size_progress_width = value;
		break;
	    case 24:
		size_frame_height = value;
		break;
	    case 25:
		size_frame_width = value;
		break;
	    case 26:
		size_textoffset = ((float) value) / 100;
		break;
	    case 29:
		color_r = value;
		break;
	    case 30:
		color_g = value;
		break;
	    case 31:
		color_b = value;
		break;
	    case 32:
		color_a = value;
		break;
	    case 33:
		color_r1 = value;
		break;
	    case 34:
		color_g1 = value;
		break;
	    case 35:
		color_b1 = value;
		break;
	    case 36:
		color_a1 = value;
		break;
	    default:
		break;
	    }
	}
	color_progress = new Color(color_r, color_g, color_b, color_a);
	color_text = new Color(color_r1, color_g1, color_b1, color_a1);
    }
}

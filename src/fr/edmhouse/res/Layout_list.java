package fr.edmhouse.res;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;

import fr.edmhouse.main.Crashframe;
import fr.edmhouse.main.StringMatcher;

public class Layout_list implements ResLayout {

    /* List componnents positions */
    public static int pos_componnent_x = 0;
    public static int pos_componnent_y = 0;
    public static int pos_play_x = 0;
    public static int pos_play_y = 0;
    public static int pos_text_x = 0;
    public static int pos_text_y = 0;
    public static int pos_slider_x = 0;
    public static int pos_slider_y = 0;
    /* List items sizes */
    public static float size_text = 0;
    public static float size_textoffset = 0;
    public static int size_slider_height = 0;
    /* Colors */
    public static Color color_scroll = null;
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
		    "Sorry, we couldn't find the layout.edm file in the ressources\\list folder."
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
		pos_componnent_x = value;
		break;
	    case 2:
		pos_componnent_y = value;
		break;
	    case 3:
		pos_slider_x = value;
		break;
	    case 4:
		pos_slider_y = value;
		break;
	    case 7:
		pos_play_x = value;
		break;
	    case 8:
		pos_play_y = value;
		break;
	    case 9:
		pos_text_x = value;
		break;
	    case 10:
		pos_text_y = value;
		break;
	    case 13:
		size_text = ((float) value) / 100;
		break;
	    case 14:
		size_textoffset = ((float) value) / 100;
		break;
	    case 15:
		size_slider_height = value;
		break;
	    case 18:
		color_r = value;
		break;
	    case 19:
		color_g = value;
		break;
	    case 20:
		color_b = value;
		break;
	    case 21:
		color_a = value;
		break;
	    case 22:
		color_r1 = value;
		break;
	    case 23:
		color_g1 = value;
		break;
	    case 24:
		color_b1 = value;
		break;
	    case 25:
		color_a1 = value;
		break;
	    default:
		break;
	    }
	}
	color_scroll = new Color(color_r, color_g, color_b, color_a);
	color_text = new Color(color_r1, color_g1, color_b1, color_a1);
    }

}

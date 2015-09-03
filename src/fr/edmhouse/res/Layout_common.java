package fr.edmhouse.res;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;

import fr.edmhouse.main.StringMatcher;

/**
 * Static class that holds the position of the different componnents in the
 * frame, and the default progress bar color.
 */
public class Layout_common implements ResLayout {

    /* buttons positions */
    public static int pos_mini_x = 0;
    public static int pos_mini_y = 0;
    public static int pos_close_x = 0;
    public static int pos_close_y = 0;
    public static int pos_list_x = 0;
    public static int pos_list_y = 0;
    public static int pos_skip_x = 0;
    public static int pos_skip_y = 0;
    public static int pos_options_x = 0;
    public static int pos_options_y = 0;
    public static int pos_random_x = 0;
    public static int pos_random_y = 0;
    public static int pos_button_x = 0;
    public static int pos_button_y = 0;
    public static int pos_volume_x = 0;
    public static int pos_volume_y = 0;
    /* other positions */
    public static int pos_progress_x = 0;
    public static int pos_progress_y = 0;
    public static int pos_text_x = 0;
    public static int pos_text_y = 0;
    /* items size */
    public static float size_text = 0;
    public static int size_progress_height = 0;
    public static int size_progress_width = 0;
    public static float size_textoffset = 0;
    /* Colors */
    public static Color color_progress = null;
    public static Color color_text = null;
    /* Others */
    public static int value_volumestart = 0;

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
	    e.printStackTrace();
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
	    if (array[i].startsWith("pos_mini_x(")) {
		pos_mini_x = value;
	    } else if (array[i].startsWith("pos_mini_y(")) {
		pos_mini_y = value;
	    } else if (array[i].startsWith("pos_close_x(")) {
		pos_close_x = value;
	    } else if (array[i].startsWith("pos_close_y(")) {
		pos_close_y = value;
	    } else if (array[i].startsWith("pos_list_x(")) {
		pos_list_x = value;
	    } else if (array[i].startsWith("pos_list_y(")) {
		pos_list_y = value;
	    } else if (array[i].startsWith("pos_skip_x(")) {
		pos_skip_x = value;
	    } else if (array[i].startsWith("pos_skip_y(")) {
		pos_skip_y = value;
	    } else if (array[i].startsWith("pos_random_x(")) {
		pos_random_x = value;
	    } else if (array[i].startsWith("pos_random_y(")) {
		pos_random_y = value;
	    } else if (array[i].startsWith("pos_button_x(")) {
		pos_button_x = value;
	    } else if (array[i].startsWith("pos_button_y(")) {
		pos_button_y = value;
	    } else if (array[i].startsWith("pos_volume_x(")) {
		pos_volume_x = value;
	    } if (array[i].startsWith("pos_options_x(")) {
		 pos_options_x = value;
	    } if (array[i].startsWith("pos_options_y(")) {
		 pos_options_y = value;
	    } else if (array[i].startsWith("pos_volume_y(")) {
		pos_volume_y = value;
	    } else if (array[i].startsWith("pos_progress_x(")) {
		pos_progress_x = value;
	    } else if (array[i].startsWith("pos_progress_y(")) {
		pos_progress_y = value;
	    } else if (array[i].startsWith("pos_text_x(")) {
		pos_text_x = value;
	    } else if (array[i].startsWith("pos_text_y(")) {
		pos_text_y = value;
	    } else if (array[i].startsWith("size_text(")) {
		size_text = ((float) value) / 100;
	    } else if (array[i].startsWith("size_progress_height(")) {
		size_progress_height = value;
	    } else if (array[i].startsWith("size_progress_width(")) {
		size_progress_width = value;
	    } else if (array[i].startsWith("size_textoffset(")) {
		size_textoffset = ((float) value) / 100;
	    } else if (array[i].startsWith("color_progress_red(")) {
		color_r = value;
	    } else if (array[i].startsWith("color_progress_green(")) {
		color_g = value;
	    } else if (array[i].startsWith("color_progress_blue(")) {
		color_b = value;
	    } else if (array[i].startsWith("color_progress_alpha(")) {
		color_a = value;
	    } else if (array[i].startsWith("color_text_red(")) {
		color_r1 = value;
	    } else if (array[i].startsWith("color_text_green(")) {
		color_g1 = value;
	    } else if (array[i].startsWith("color_text_blue(")) {
		color_b1 = value;
	    } else if (array[i].startsWith("color_text_alpha(")) {
		color_a1 = value;
	    } else if (array[i].startsWith("value_volumestart(")) {
		value_volumestart = value;
	    }
	}
	color_progress = new Color(color_r, color_g, color_b, color_a);
	color_text = new Color(color_r1, color_g1, color_b1, color_a1);
    }
}

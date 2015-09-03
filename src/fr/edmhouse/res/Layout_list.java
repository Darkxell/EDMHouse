package fr.edmhouse.res;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;

import fr.edmhouse.main.StringMatcher;

public class Layout_list implements ResLayout {
    
    /* List componnents positions */
    public static int pos_componnent_x = 0;
    public static int pos_componnent_y = 0;
    public static int pos_play_x = 0;
    public static int pos_play_y = 0;
    public static int pos_select_x = 0;
    public static int pos_select_y = 0;
    public static int pos_edit_x = 0;
    public static int pos_edit_y = 0;
    public static int pos_swap_x = 0;
    public static int pos_swap_y = 0;
    public static int pos_text_x = 0;
    public static int pos_text_y = 0;
    public static int pos_slider_x = 0;
    public static int pos_slider_y = 0;
    public static int pos_back_x = 0;
    public static int pos_back_y = 0;
    /* List items sizes */
    public static float size_textoffset = 0;
    public static int size_slider_height = 0;
    /* Colors */
    public static Color color_scroll = null;
    public static Color color_text = null;
    /* Other values */
    public static int value_showvolume = FALSE;
    
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
	    if (array[i].startsWith("pos_component_x(")) {
		pos_componnent_x = value;
	    } else if (array[i].startsWith("pos_component_y(")) {
		pos_componnent_y = value;
	    } else if (array[i].startsWith("pos_slider_x(")) {
		pos_slider_x = value;
	    } else if (array[i].startsWith("pos_slider_y(")) {
		pos_slider_y = value;
	    } else if (array[i].startsWith("pos_play_x(")) {
		pos_play_x = value;
	    } else if (array[i].startsWith("pos_play_y(")) {
		pos_play_y = value;
	    } else if (array[i].startsWith("pos_select_x(")) {
		pos_select_x = value;
	    } else if (array[i].startsWith("pos_select_y(")) {
		pos_select_y = value;
	    } else if (array[i].startsWith("pos_edit_x(")) {
		pos_edit_x = value;
	    } else if (array[i].startsWith("pos_edit_y(")) {
		pos_edit_y = value;
	    } else if (array[i].startsWith("pos_swap_x(")) {
		pos_swap_x = value;
	    } else if (array[i].startsWith("pos_swap_y(")) {
		pos_swap_y = value;
	    } else if (array[i].startsWith("pos_text_x(")) {
		pos_text_x = value;
	    } else if (array[i].startsWith("pos_text_y(")) {
		pos_text_y = value;
	    }  else if (array[i].startsWith("pos_back_y(")) {
		pos_back_y = value;
	    }  else if (array[i].startsWith("pos_back_x(")) {
		pos_back_x = value;
	    } else if (array[i].startsWith("size_textoffset(")) {
		size_textoffset = value;
	    } else if (array[i].startsWith("size_slider_height(")) {
		size_slider_height = value;
	    } else if (array[i].startsWith("color_scroll_red(")) {
		color_r = value;
	    } else if (array[i].startsWith("color_scroll_green(")) {
		color_g = value;
	    } else if (array[i].startsWith("color_scroll_blue(")) {
		color_b = value;
	    } else if (array[i].startsWith("color_scroll_alpha(")) {
		color_a = value;
	    }else if (array[i].startsWith("color_text_red(")) {
		color_r1 = value;
	    } else if (array[i].startsWith("color_text_green(")) {
		color_g1 = value;
	    } else if (array[i].startsWith("color_text_blue(")) {
		color_b1 = value;
	    } else if (array[i].startsWith("color_text_alpha(")) {
		color_a1 = value;
	    } else if (array[i].startsWith("value_showvolume(")) {
		value_showvolume = value;
	    }
	}
	color_scroll = new Color(color_r, color_g, color_b, color_a);
	color_text = new Color(color_r1, color_g1, color_b1, color_a1);
    }

}

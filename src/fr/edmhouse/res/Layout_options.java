package fr.edmhouse.res;

import java.io.BufferedReader;
import java.io.FileReader;

import fr.edmhouse.main.StringMatcher;

public class Layout_options implements ResLayout {

    /* Options buttons positions */
    public static int pos_option_skin_x = 0;
    public static int pos_option_skin_y = 0;
    public static int pos_option_playlists_x = 0;
    public static int pos_option_playlists_y = 0;
    public static int pos_back_x = 0;
    public static int pos_back_y = 0;

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
	int value;
	for (int i = 0; i < array.length; i++) {
	    try {
		value = Integer.parseInt(StringMatcher
			.getAbsoluteContent(StringMatcher.getBracketsContent(
				array[i], 1)));
	    } catch (Exception e) {
		value = 0;
	    }
	    if (array[i].startsWith("pos_option_skin_x(")) {
		pos_option_skin_x = value;
	    } else if (array[i].startsWith("pos_option_skin_y(")) {
		pos_option_skin_y = value;
	    } else if (array[i].startsWith("pos_option_playlists_x(")) {
		pos_option_playlists_x = value;
	    } else if (array[i].startsWith("pos_option_playlists_y(")) {
		pos_option_playlists_y = value;
	    } else if (array[i].startsWith("value_showvolume(")) {
		value_showvolume = value;
	    } else if (array[i].startsWith("pos_back_y(")) {
		pos_back_y = value;
	    } else if (array[i].startsWith("pos_back_x(")) {
		pos_back_x = value;
	    }
	}
    }

}

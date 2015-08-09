package fr.edmhouse.main;

import java.io.File;

import fr.edmhouse.res.Res;

/**
 * Static class that holds the name and filepath to the different available
 * skins.
 */
public class SkinsHolder {

    /** An array containing the skin objects. */
    public static Skin[] skins;

    public static void initialize() {
	File folder = new File(Res.FOLDER_PATH + "skins");
	File[] content = folder.listFiles();
	skins = new Skin[content.length];
	for (int i = 0; i < content.length; i++) {
	    skins[i] = new Skin(content[i]);
	}
    }
}

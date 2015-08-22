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
	skins = new Skin[content.length + 1];
	for (int i = 0; i < content.length + 1; i++) {
	    if (i == 0) {
		skins[i] = new Skin("Default", Res.FOLDER_PATH + "ressources");
	    } else
		skins[i] = new Skin(content[i-1]);
	}
    }
}

package fr.edmhouse.res;

import java.io.File;

import fr.edmhouse.audio.Song;

/**
 * Abstract class that holds an array of songs that represents the phisical
 * songs folder.
 */
public abstract class SongFolderHolder {

    public static Song[] folderContent;

    /** Initializes the songs array to match the actual songs folder. */
    public static void initialize() {
	File tempfolder = new File(Res.FOLDER_PATH + "songs");
	File[] fContent = tempfolder.listFiles();
	tempfolder = null;
	Song[] tointit = new Song[fContent.length];
	for (int i = 0; i < fContent.length; i++)
	    tointit[i] = new Song(fContent[i]);
	folderContent = tointit;
    }

}

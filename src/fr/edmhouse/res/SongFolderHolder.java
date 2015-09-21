package fr.edmhouse.res;

import java.io.File;
import java.util.Vector;

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
	folderContent = getAudioListFromFolder(tempfolder);
    }

    /**
     * Gets an array of <code>Song</code> objects form a folder. This filters
     * the mp3 files form the other files, and only return Songs builded using
     * actual mp3 files.
     */
    public static Song[] getAudioListFromFolder(File folder) {
	File[] content = folder.listFiles();
	File[] sortedcontent = sortMP3(content);
	Song[] s = new Song[sortedcontent.length];
	for (int i = 0; i < s.length; i++)
	    s[i] = new Song(sortedcontent[i].getAbsolutePath());
	return s;
    }

    /**
     * Gets an array of files and sort them. Note that this method leaves
     * pointers to the original file array.
     * 
     * @return An array of files containing the mp3 files of the array in
     *         parameters.
     * */
    private static File[] sortMP3(File[] content) {
	int ammount = 0;
	Vector<File> newcontent = new Vector<File>();
	for (int i = 0; i < content.length; i++)
	    if (content[i].getName().endsWith(".mp3")) {
		newcontent.add(content[i]);
		++ammount;
	    }
	File[] toreturn = new File[ammount];
	for (int i = 0; i < toreturn.length; i++)
	    toreturn[i] = newcontent.elementAt(i);
	return toreturn;
    }
}

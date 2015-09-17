package fr.edmhouse.audio.playlists;

import java.io.File;
import java.io.PrintWriter;

import fr.edmhouse.res.Res;

/** Static class that holds a list of playlists. */
public class PlaylistHolder {

    public static Playlist[] playlists;

    /**
     * Initializes the <code>playlists</code> variable from what's inside the
     * folder.
     */
    public static void initialize() {
	File folder = new File(Res.FOLDER_PATH + "playlists");
	File[] content = folder.listFiles();
	Playlist[] tempPlaylists = new Playlist[content.length];
	for (int i = 0; i < tempPlaylists.length; i++) {
	    tempPlaylists[i] = readFile(content[i]);
	}
	playlists = tempPlaylists;
    }

    /** Reads the wanted file and returns a playlist object from it. */
    private static Playlist readFile(File toRead) {
	return new Playlist(toRead);
    }

    /**
     * Tests the current playlists to test if one needs to be saved.
     * 
     * @return True if one or more of the playlists needs saving, false
     *         otherwise.
     * */
    public boolean doOneNeedSave() {
	for (int i = 0; i < playlists.length; i++)
	    if (playlists[i].needSave())
		return true;
	return false;
    }

    /** Saves the playlists that needs saving. */
    public void saveAll() {
	for (int i = 0; i < playlists.length; i++)
	    if (playlists[i].needSave())
		playlists[i].save();
    }

    /**
     * Forces all the contaied playlists to be saved. High CPU usage, use
     * carefully.
     * 
     * @see <code>saveAll();</code>
     * */
    public void forceSaveAll() {
	for (int i = 0; i < playlists.length; i++)
	    playlists[i].save();
    }

    /**
     * Creates a text file using the given string.
     * 
     * @author Cubi (I'm lazy.)
     * */
    public static void writeFile(String path, String text) {
	try {
	    File file = new File(path);
	    file.mkdirs();
	    if (file.exists())
		file.delete();
	    PrintWriter writer = new PrintWriter(file);
	    writer.write(text);
	    writer.close();
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }
}

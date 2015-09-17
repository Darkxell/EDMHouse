package fr.edmhouse.audio.playlists;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import fr.edmhouse.audio.Song;
import fr.edmhouse.main.StringMatcher;
import fr.edmhouse.res.Res;

/**
 * A playlist object is a list of songs objects. You can add some and delete
 * some, save them into a file, and get playlists from files.
 */
public class Playlist {

    /** The name of the playlist. Should be the same as the name of the file. */
    private String name;
    /** the list of songs of the playlist. */
    private Song[] songs;
    /**
     * is true only if the object has been modified since last saving. This
     * means that the phisical Playlist file is unsinced with the actual object
     * and therefore it needs modifiing.
     */
    private boolean needSave;

    /** Creates an empty playlist with the appropriate name. */
    public Playlist(String name) {
	this.name = name;
	this.songs = new Song[0];
	this.needSave = true;
    }

    /** Creates a playlist from saved data of the playlist file. */
    public Playlist(File playlist) {
	String filestring = "";
	StringBuilder builder = new StringBuilder();
	try {
	    BufferedReader br = new BufferedReader(new FileReader(playlist));
	    String line;
	    while ((line = br.readLine()) != null)
		builder.append(line);
	    br.close();
	} catch (Exception e) {
	    e.printStackTrace();
	}
	filestring = builder.toString();
	filestring = filestring.toLowerCase();
	String[] array = filestring.split("\"");
	Song[] tempsongs = new Song[array.length];
	for (int i = 0; i < tempsongs.length; i++)
	    tempsongs[i] = new Song(array[i]);
	this.songs = tempsongs;
	this.name = StringMatcher.getRawFilename(playlist.getName());
	this.needSave = false;
    }

    /**
     * Adds the selected song to the playlist object. Also this is a change, is
     * it sets the "needsave" argument of the playlist to true.
     */
    public void addSong(Song toAdd) {
	Song[] temparray = new Song[this.songs.length + 1];
	for (int i = 0; i < this.songs.length; i++) {
	    temparray[i + 1] = this.songs[i];
	}
	temparray[0] = toAdd;
	this.songs = temparray;
	this.needSave = true;
    }

    /**
     * Removes the wanted song form the playlist.
     * 
     * @param songID
     *            the ID of the song to remove in the array.
     * */
    public void removeSong(int songID) {
	Song[] temparray = new Song[this.songs.length - 1];
	for (int i = 0; i < temparray.length; i++)
	    temparray[i] = this.songs[(i < songID) ? i : i + 1];
	this.songs = temparray;
	this.needSave = true;
    }

    /** Gets the songs of the playlist object. */
    public Song[] getSongs() {
	return this.songs;
    }

    /** Gets the name of the playlist */
    public String getname() {
	return this.name;
    }

    /** Returns true if the playlist object has been modified since last saving. */
    public boolean needSave() {
	return this.needSave;
    }

    /**
     * Creates/overrides a playlist file in the default playlist folder using
     * this playlist object.
     */
    public void save() {
	String filestring = "";
	for (int i = 0; i < songs.length; i++) {
	    if (i != 0)
		filestring += "\"";
	    filestring += songs[i].getfilepath();
	}
	PlaylistHolder.writeFile(Res.FOLDER_PATH + "playlists//" + this.name
		+ ".edm", filestring);
	this.needSave = false;
    }

}

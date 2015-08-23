package fr.edmhouse.audio.playlists;

import java.io.File;

import fr.edmhouse.audio.Song;

/**
 * A playlist object is a list of songs objects. You can add some and delete
 * some, save them into a file, and get playlists from files.
 */
public class Playlist {

    /** The name of the playlist. Should be the same as the name of the file. */
    private String name;
    /** the list of songs of the playlist. */
    private Song[] songs;
    /** is true only if the object has been modified since last saving. */
    private boolean needSave;

    /** Creates an empty playlist with the appropriate name. */
    public Playlist(String name) {
	this.name = name;
	this.songs = new Song[0];
	this.needSave = true;
    }

    /** Creates a playlist from saved data of the playlist file. */
    public Playlist(File playlist) {
	// TODO
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

    /** Creates/overrides a playlist file using this playlist object */
    public void save() {
	// TODO
	this.needSave = false;
    }

}

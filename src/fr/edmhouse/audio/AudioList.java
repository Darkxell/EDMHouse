package fr.edmhouse.audio;

import java.io.File;

/** An object that holds file objects */
public class AudioList {

    private Song[] songs;
    /**
     * the index of the last song picked by getRandomUrl() or getNextUrl() .
     * Equals -1 if no songs were picked.
     */
    public int lastindex;

    /**
     * Constructs an AudioList object, holding the different mp3 files of the
     * song folder.
     */
    public AudioList(String filepath) {
	this.lastindex = -1;
	File folder = new File(filepath);
	File[] content = folder.listFiles();
	// TODO : filter the real mp3 and the other files here.
	Song[] s = new Song[content.length];
	for (int i = 0; i < s.length; i++) {
	    s[i] = new Song(content[i].getAbsolutePath());
	}
	this.songs = s;
    }

    /**
     * Gets a random mp3 file url from the list. Can't return the last picked
     * song from the list. Will crash if there is only one song, obviously.
     */
    public Song getRandomUrl() {
	int rand = (int) (Math.random() * this.songs.length);
	while (rand == this.lastindex)
	    rand = (int) (Math.random() * this.songs.length);
	this.lastindex = rand;
	return this.songs[rand];
    }

    /**
     * Gets the next mp3 file url from the list. Records the pick. if the last
     * picked one was the last one on the list, returns the first one.
     */
    public Song getNextUrl() {
	try {
	    this.lastindex++;
	    return this.songs[this.lastindex];
	} catch (Exception e) {
	    this.lastindex = 0;
	    return this.songs[0];
	}
    }

    /** Gets the wanted mp3 file url from the list. Records the pick. */
    public Song getWantedUrl(int id) {
	this.lastindex = id;
	return this.songs[id];
    }

    /** Gets the number of songs in the list */
    public int getSongAmmount() {
	return this.songs.length;
    }

    /**
     * Gets a song from it's ID. Only gets the song, the Audiolist don't record
     * the information given away. Intended for display only.
     */
    public Song getSongById(int id) {
	return this.songs[id];
    }
}

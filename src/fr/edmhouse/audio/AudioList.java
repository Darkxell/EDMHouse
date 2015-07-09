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
	while(rand == this.lastindex)
	    rand = (int) (Math.random() * this.songs.length);
	this.lastindex = rand;
	return this.songs[rand];
    }

    /** Gets the next mp3 file url from the list. */
    public Song getNextUrl() {
	this.lastindex++;
	return this.songs[this.lastindex];
    }

}

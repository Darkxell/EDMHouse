package fr.edmhouse.audio;

import java.io.File;

import fr.edmhouse.audio.playlists.Playlist;
import fr.edmhouse.res.SongFolderHolder;

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
     * song folder. The audiolist object is builded using the folder path and
     * only contains the mp3 files.
     */
    public AudioList(String filepath) {
	this.lastindex = -1;
	this.songs = SongFolderHolder
		.getAudioListFromFolder(new File(filepath));
    }

    /** Constructs an <code>Audiolist</code> object using a playlist. */
    public AudioList(Playlist playlist) {
	this.lastindex = -1;
	this.songs = playlist.getSongs();
    }

    /**
     * Gets a random mp3 file url from the list. Can't return the last picked
     * song from the list. Will return the last picked song if it's the only one
     * in the list.
     */
    public Song getRandomUrl() {
	int rand = (int) (Math.random() * this.songs.length);
	int max = 0;
	while (rand == this.lastindex && max < 20) {
	    rand = (int) (Math.random() * this.songs.length);
	    ++max;
	}
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

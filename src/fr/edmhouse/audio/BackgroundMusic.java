package fr.edmhouse.audio;

import java.io.FileInputStream;

import fr.edmhouse.main.EDMHouse;

/** An object that constently plays a BGM in the player. Can be paused and such */
public class BackgroundMusic implements Runnable {

    /**
     * The current playing AdvancedPlayer object (from Javazoom.jl, a bit
     * modified.)
     */
    private CustomPlayer currentplayer;
    /** The current song being played (even if paused). */
    private Song currentsong;

    /**
     * is true if the Music is curently changing, preventing from playing or
     * pausing.
     */
    private boolean ischanging;

    /** Creates a BGM object. */
    public BackgroundMusic() {
	this.currentplayer = null;
	this.currentsong = null;
	this.ischanging = true;
	this.changemusic(EDMHouse.songs.getRandomUrl());
    }

    @Override
    public void run() {
	while (true) {
	    // Tries 10 times a second, not counting lags. Lags are not a big
	    // deal here, checking for them will just create more.
	    EDMHouse.wait(0.1f);
	    try {
		if (this.currentplayer.isComplete()) {
		    this.currentplayer.close();
		    this.changemusic(EDMHouse.songs.getRandomUrl());
		}
		this.currentplayer.play();
	    } catch (Exception e) {
	    }
	}
    }

    /**
     * Changes the music in the player to the one in parametters.
     */
    public void changemusic(Song song) {
	this.ischanging = true;
	this.currentsong = song;
	EDMHouse.frame.canvas.resetprogress(); // Nice code here.
	try {
	    this.currentplayer.close();
	} catch (Exception e) {
	}
	try {
	    FileInputStream fis = new FileInputStream(song.getfilepath());
	    this.currentplayer = new CustomPlayer(fis);
	} catch (Exception e) {
	    if (song.getfilepath() != null) {
		System.err
			.println("Erreur lors de l'ouverture du fichier son.");
		e.printStackTrace();
	    }
	} finally {
	    this.ischanging = false;
	}
    }

    /** Gets the length of the playing song in the player. */
    public int getlength() {
	return this.currentsong.length;
    }

    /**
     * Gets the display name of the song. For correctly specified files it is
     * Author - Title , but for ones that doesn't specify a title it is just the
     * name of the file without the mp3 extention.
     */
    public String getdisplayname() {
	return this.currentsong.getdipsplayname();
    }

    /** Gets the state (changing or not) of the BGM object. */
    public boolean ischanging() {
	return this.ischanging;
    }

    /**
     * Instant stops the playing music. Optimized, but you can't start it back
     * after stopping it this way.
     */
    public void instantstop() {
	this.currentplayer.close();
    }
}
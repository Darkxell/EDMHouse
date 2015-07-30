package fr.edmhouse.audio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import fr.edmhouse.main.EDMHouse;
import javazoom.jl.decoder.Bitstream;
import javazoom.jl.decoder.BitstreamException;
import javazoom.jl.decoder.Decoder;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.AudioDevice;
import javazoom.jl.player.FactoryRegistry;
import javazoom.jl.player.advenced.AdvancedPlayer;
import javazoom.jl.player.advenced.PlaybackEvent;

/**
 * A custom recursive version of JLayer's AdvancedPlayer that skip to desired
 * location when it should. Used to be able to pause but not anymore.
 */
public class CustomPlayer extends AdvancedPlayer {

    /**
     * A backup of the FilePath used when constructing the player. Comes handy
     * when you need to reset the Bitstream of the player.
     */
    private String pathBackup;

    /**
     * Is true if the player created a new player inside of him to play an other
     * part of the song for him. In this case the
     * <code>inceptionnedPlayer</code> var will point to the player playing.
     * Keep in mind that this player can also be inceptionned any number of
     * times.
     */
    private boolean isinceptionned = false;
    /** COntains the inceptionnedPlayer of the current one if it exists. */
    private CustomPlayer inceptionnedPlayer;

    /**
     * Creates a new <code>Player</code> instance.
     */
    public CustomPlayer(String path) throws JavaLayerException {
	this.pathBackup = path;
	try {
	    FileInputStream fis = new FileInputStream(path);
	    super.bitstream = new Bitstream(fis);
	} catch (FileNotFoundException e) {
	}
	super.audio = FactoryRegistry.systemRegistry().createAudioDevice();
	super.audio.open(super.decoder = new Decoder());
    }

    /**
     * Creates a new <code>Player</code> instance.
     */
    public CustomPlayer(InputStream stream) throws JavaLayerException {
	this(stream, null);
    }

    /**
     * Creates a new <code>Player</code> instance.
     */
    public CustomPlayer(InputStream stream, AudioDevice device)
	    throws JavaLayerException {
	super.bitstream = new Bitstream(stream);
	if (device != null)
	    super.audio = device;
	else
	    super.audio = FactoryRegistry.systemRegistry().createAudioDevice();
	super.audio.open(super.decoder = new Decoder());
    }

    @Override
    public void play() throws JavaLayerException {
	play(Integer.MAX_VALUE);
    }

    /**
     * Plays a number of MPEG audio frames.
     *
     * @param frames
     *            The number of frames to play.
     * @return true if the last frame was played, or false if there are more
     *         frames.
     */
    @Override
    public boolean play(int frames) throws JavaLayerException {
	boolean ret = true, shouldendfunction = true;
	if (listener != null)
	    listener.playbackStarted(createEvent(PlaybackEvent.STARTED));
	for (; frames > 0 && ret && shouldendfunction;) {
	    if (EDMHouse.BGM.needjump) {
		shouldendfunction = false;
		EDMHouse.BGM.needjump = false;
		this.isinceptionned = true;
		this.inceptionnedPlayer = new CustomPlayer(this.pathBackup);
		int position = (int) ((((float) EDMHouse.frame.canvas.progression)) / 26);
		// Because a mp3 frame length is always 0.026 sec.
		this.inceptionnedPlayer.play(position, Integer.MAX_VALUE);
		complete = true;
	    } else {
		ret = decodeFrame();
		frames--;
	    }
	}
	if (shouldendfunction) {
	    AudioDevice out = audio;
	    if (out != null) {
		out.flush();
		synchronized (this) {
		    complete = (!closed);
		    close();
		}
		if (listener != null)
		    listener.playbackFinished(createEvent(out,
			    PlaybackEvent.STOPPED));
	    }
	}
	return ret;
    }

    /**
     * Plays a range of MPEG audio frames
     * 
     * @param start
     *            The first frame to play
     * @param end
     *            The last frame to play
     * @return true if the last frame was played, or false if there are more
     *         frames.
     */
    @Override
    public boolean play(final int start, final int end)
	    throws JavaLayerException {
	boolean ret = true;
	int offset = start;
	while (offset-- > 0 && ret)
	    ret = skipFrame();
	return play(end - start);
    }

    /**
     * Cloases this player. Any audio currently playing is stopped immediately.
     */
    public synchronized void close() {
	AudioDevice out = audio;
	if (out != null) {
	    closed = true;
	    audio = null;
	    out.close();
	    try {
		bitstream.close();
	    } catch (BitstreamException ex) {
	    }
	    if (this.isinceptionned)
		this.inceptionnedPlayer.close();
	}
    }
}

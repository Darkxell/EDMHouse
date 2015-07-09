package fr.edmhouse.audio;

import java.io.InputStream;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.AudioDevice;
import javazoom.jl.player.advenced.AdvancedPlayer;
import javazoom.jl.player.advenced.PlaybackEvent;

/**
 * A custom version of JLayer's AdvancedPlayer that pause accorded to the canvas
 * state. 
 */
public class CustomPlayer extends AdvancedPlayer {

    public CustomPlayer(InputStream stream) throws JavaLayerException {
	super(stream);
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
	boolean ret = true;

	// report to listener
	if (listener != null)
	    listener.playbackStarted(createEvent(PlaybackEvent.STARTED));

	for (; frames > 0 && ret;) {

	    if (true/* TODO !EDMHouse.frame.canvas.state */) {
		ret = decodeFrame();
		frames--;
	    }
	}
	{
	    // last frame, ensure all data flushed to the audio device.
	    AudioDevice out = audio;
	    if (out != null) {
		out.flush();
		synchronized (this) {
		    complete = (!closed);
		    close();
		}

		// report to listener
		if (listener != null)
		    listener.playbackFinished(createEvent(out,
			    PlaybackEvent.STOPPED));
	    }
	}
	return ret;
    }
}

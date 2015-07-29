package fr.edmhouse.audio;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;

public class SoundMeter {

    /**
     * Sets the Volume of all the Lines of all the Mixers to the
     * <code>volume</code> value.
     */
    public static void setSystemVolume(float volume) {
	javax.sound.sampled.Mixer.Info[] mixers = AudioSystem.getMixerInfo();
	for (int i = 0; i < mixers.length; i++) {
	    Mixer.Info mixerInfo = mixers[i];
	    Mixer mixer = AudioSystem.getMixer(mixerInfo);
	    Line.Info[] lineinfos = mixer.getTargetLineInfo();
	    for (Line.Info lineinfo : lineinfos) {
		try {
		    Line line = mixer.getLine(lineinfo);
		    line.open();
		    if (line.isControlSupported(FloatControl.Type.VOLUME)) {
			FloatControl control = (FloatControl) line
				.getControl(FloatControl.Type.VOLUME);
			// Sets everything here.
			control.setValue((float) volume);
		    }
		} catch (LineUnavailableException e) {
		}
	    }
	}
    }
}
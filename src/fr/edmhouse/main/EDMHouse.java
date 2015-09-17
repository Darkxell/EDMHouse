package fr.edmhouse.main;

import fr.edmhouse.audio.AudioList;
import fr.edmhouse.audio.BackgroundMusic;
import fr.edmhouse.audio.SoundMeter;
import fr.edmhouse.audio.playlists.PlaylistHolder;
import fr.edmhouse.display.CFrame;
import fr.edmhouse.res.Layout_common;
import fr.edmhouse.res.Layout_list;
import fr.edmhouse.res.Layout_options;
import fr.edmhouse.res.Res;
import fr.edmhouse.res.SongFolderHolder;

public class EDMHouse {

    /** The audio reader object. */
    public static BackgroundMusic BGM;
    /**
     * The list of songs in the folder. Can be changed at any time without
     * interrupting the current audio playing.
     */
    public static AudioList songs;
    /** The frame object. */
    public static CFrame frame;
    /** The thread containing the audio player */
    public static Thread bgmthread;

    /** Main method. Called from the .jar scource. */
    @SuppressWarnings("deprecation")
    public static void main(String[] args) {
	SongFolderHolder.initialize();
	PlaylistHolder.initialize();
	Layout_common.initializeFromFile(Res.FOLDER_PATH
		+ "ressources\\common\\layout.edm");
	Layout_list.initializeFromFile(Res.FOLDER_PATH
		+ "ressources\\list\\layout.edm");
	Layout_options.initializeFromFile(Res.FOLDER_PATH
		+ "ressources\\options\\layout.edm");
	Res.initialize();
	frame = new CFrame();
	songs = new AudioList(Res.FOLDER_PATH + "songs");
	SkinsHolder.initialize();
	BGM = new BackgroundMusic();
	Thread t = new Thread(BGM);
	bgmthread = t;
	bgmthread.start();
	t.suspend();
	SoundMeter
		.setSystemVolume(((float) Layout_common.value_volumestart) / 100);
	long launchingTime = System.currentTimeMillis();
	for (int i = 1;; i++) {
	    for (; launchingTime + (i * 20) > System.currentTimeMillis();) {
		try {
		    Thread.sleep(10);
		} catch (Exception e) {
		}
		if (System.currentTimeMillis() > launchingTime + (i * 20) + 200) {
		    launchingTime = System.currentTimeMillis();
		    i = 1;
		    System.err
			    .println("Error on framerate: More than 10 frames behind! \n"
				    + "Skipped to current frame. This might happen if you"
				    + " have a CPU overload or if the process has been "
				    + "frozen for some reason.");
		}
	    } // Waits till the next update
	    frame.update();
	}
    }

    /**
     * Waits in the current thread for <code>time</code> seconds. Pretty high
     * CPU usage while running tho. Consider using Thread.sleep() instead, but
     * this doesn't use it.
     */
    public static void wait(float time) {
	int militime = (int) (time * 1000);
	long start = System.currentTimeMillis();
	long release = start + militime;
	for (; release > System.currentTimeMillis();)
	    ;
    }

}

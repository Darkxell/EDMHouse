package fr.edmhouse.main;

import fr.edmhouse.audio.AudioList;
import fr.edmhouse.audio.BackgroundMusic;
import fr.edmhouse.display.CFrame;
import fr.edmhouse.res.Layout_common;
import fr.edmhouse.res.Layout_list;
import fr.edmhouse.res.Res;

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

    /** Main method. */
    @SuppressWarnings("deprecation")
    public static void main(String[] args) {
	songs = new AudioList(Res.FOLDER_PATH + "songs");
	Layout_common.initializeFromFile(Res.FOLDER_PATH
		+ "ressources\\common\\layout.edm");
	Layout_list.initializeFromFile(Res.FOLDER_PATH
		+ "ressources\\list\\layout.edm");
	Res.initialize();
	frame = new CFrame();
	BGM = new BackgroundMusic();
	Thread t = new Thread(BGM);
	bgmthread = t;
	bgmthread.start();
	t.suspend();
	long launchingTime = System.currentTimeMillis();
	for (int i = 1;; i++) {
	    try {
		for(;launchingTime+(i*10)>System.currentTimeMillis();){
		}
		frame.update();
	    } catch (Exception e) {
		Crashframe cf = new Crashframe("Internal error.",
			"Sorry, an internal error happened. Error :"
				+ e.toString());
		cf.launch();
	    }
	}

    }

    /** Waits in the current thread for <code>time</code> seconds. */
    public static void wait(float time) {
	int militime = (int) (time * 1000);
	long start = System.currentTimeMillis();
	long release = start + militime;
	for (; release > System.currentTimeMillis();)
	    ;
    }

}

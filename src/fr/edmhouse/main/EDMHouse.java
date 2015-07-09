package fr.edmhouse.main;

import fr.edmhouse.audio.AudioList;
import fr.edmhouse.audio.BackgroundMusic;
import fr.edmhouse.display.CFrame;
import fr.edmhouse.res.CustomList;
import fr.edmhouse.res.Res;

public class EDMHouse {

    /**The audio reader object.*/
    public static BackgroundMusic BGM;
    /**The list of songs in the folder.*/
    public static AudioList songs;
    /**The frame object.*/
    public static CFrame frame;

    public static Thread bgmthread;

    /** Main method. */
    @SuppressWarnings("deprecation")
    public static void main(String[] args) {
	songs = new AudioList(Res.FOLDER_PATH + "songs");
	CustomList.initializeFromFile(Res.FOLDER_PATH
		+ "ressources\\layout.edm");
	Res.initialize();
	frame = new CFrame();
	BGM = new BackgroundMusic();
	Thread t = new Thread(BGM);
	bgmthread = t;
	bgmthread.start();
	t.suspend();
	while (true) {
	    frame.update();
	    wait(0.01f);
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

package fr.edmhouse.main;

import java.io.File;

public class Skin {

    /** The name of the skin */
    private String name;
    /** The filepath that leads to the skin */
    private String filepath;

    /** Constructs a Skin object using a File object leading to the */
    public Skin(File skinfolder) {
	this.name = skinfolder.getName();
	this.filepath = skinfolder.getAbsolutePath();
    }

    public String getFilepath() {
	return filepath;
    }

    public String getName() {
	return name;
    }

}

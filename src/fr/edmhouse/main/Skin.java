package fr.edmhouse.main;

import java.io.File;

public class Skin {

    /** The name of the skin */
    private String name;
    /** The filepath that leads to the skin */
    private String filepath;

    /** Constructs a Skin object using the path of the skin and it's name. */
    public Skin(String name, String path) {
	this.name = name;
	this.filepath = path;
    }

    /**
     * Constructs a Skin object using a File object leading to the skin
     * ressources
     */
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

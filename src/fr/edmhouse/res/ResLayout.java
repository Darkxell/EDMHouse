package fr.edmhouse.res;

/**
 * Any Layout ressources should be stored in an object of a class implementing
 * this.
 */
public interface ResLayout {

    public static final int FALSE = 0;
    public static final int TRUE = 1; 
    
    /** Initializes the data from a .edm file. */
    public static void initializeFromFile(String filepath) {
    }
    
}

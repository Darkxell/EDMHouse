package fr.edmhouse.res;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class FontBuilder {

    /**
     * Builds a font object using a link to a .ttf or .otf file. Static method.
     * The returned font is plain, and has a height of 20 px.
     * 
     * @param url
     *            The Absolute/Relative url to the file. Do not include the
     *            "file:" in the URL.
     * */
    public static Font createfont(String url) throws FontFormatException,
	    IOException, FileNotFoundException, MalformedURLException {
	URL fontUrl;
	fontUrl = new URL("file:" + url);
	Font font = Font.createFont(Font.TRUETYPE_FONT, fontUrl.openStream());
	font = font.deriveFont(Font.PLAIN, 20);
	return font;
    }

}

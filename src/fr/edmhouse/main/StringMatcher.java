package fr.edmhouse.main;

/**
 * Static class holding various string related methods and predicates. Extracted
 * from DAKreader project.
 */
public class StringMatcher {

    /**
     * Predicate to test if a String matches a pattern of charsstored in a char
     * array. <br/>
     * <br/>
     * <strong>Syntax : </strong> The char array should have the following
     * syntax: [a,b,*,c,#] use * as a blankcard of any length and # as blankcard
     * for only one char.
     * 
     * @param string
     *            the string you want to test.
     * @param matcher
     *            an array of chars. Use the following syntax.
     * 
     * */
    public static boolean stringMatch(String string, char[] matcher) {
	char[] totest = string.toCharArray();
	int i = 0, j = 0;
	try {
	    while (i < matcher.length) {
		switch (matcher[i]) {
		case '#':
		    i++;
		    j++;
		    break;
		case '*':
		    j++;
		    if (matcher[i + 1] == totest[j])
			i++;
		    break;
		default:
		    if (matcher[i] == totest[j]) {
			i++;
			j++;
		    } else {
			return false;
		    }
		    break;
		}
	    }
	} catch (Exception e) {
	    return false;
	}
	return (j == totest.length);
    }

    /**
     * Predicate to test if a String matches a pattern of chars stored in a
     * String. <br/>
     * <br/>
     * <strong>Syntax : </strong> The String should have the following syntax:
     * ab*c# use * as a blankcard of any length and # as blankcard for only one
     * char.
     * 
     * @param string
     *            the string you want to test.
     * @param pattern
     *            a String containing the pattern. Use the following syntax.
     * 
     * */
    public static boolean stringMatch(String toBeTested, String pattern) {
	char[] temp = pattern.toCharArray();
	return stringMatch(toBeTested, temp);
    }

    /**
     * Static method to get the content of a bracket occurence in a String. May
     * return white spaces at the begining or at the end, keep that in mind.
     */
    public static String getBracketsContent(String line, int bracketOccurence) {
	char[] chararray = line.toCharArray();
	int counter = 0, i = 0;
	try {
	    while (counter != bracketOccurence) {
		if (chararray[i] == '(')
		    counter++;
		i++;
	    }
	} catch (Exception e) {
	}
	counter = 0;
	String toReturn = "";
	while (counter != 0 || chararray[i] != ')') {
	    if (chararray[i] == '(')
		counter++;
	    if (chararray[i] == ')')
		counter--;
	    toReturn += chararray[i];
	    i++;
	}
	return toReturn;
    }

    /**
     * Returns the same string without blank spaces at the begining nor the end.
     */
    public static String getAbsoluteContent(String text) {
	char[] chararray = text.toCharArray();
	char[] temp;
	while (chararray[0] == ' ') {
	    temp = new char[chararray.length - 1];
	    for (int i = 0; i < temp.length; i++)
		temp[i] = chararray[i + 1];
	    chararray = temp;
	}
	while (chararray[chararray.length - 1] == ' ') {
	    temp = new char[chararray.length - 1];
	    for (int i = 0; i < temp.length; i++)
		temp[i] = chararray[i];
	    chararray = temp;
	}
	String toReturn = "";
	for (int i = 0; i < chararray.length; i++) {
	    toReturn += chararray[i];
	}
	return toReturn;
    }

    /**
     * Takes a string and returns the executable code inside it. <br/>
     * <strong>Syntax</strong> : <br/>
     * "* [Identifier Name] : [code] ;"<br/>
     * 
     * @return Code Using this syntax : "[code]"
     * */
    public static String getCode(String line) {
	char[] array = line.toCharArray();
	int counter = 0;
	String toreturn = "";
	while (array[counter] != ':')
	    counter++;
	counter++;
	while (array[counter] != ';') {
	    toreturn += array[counter];
	    counter++;
	}
	return getAbsoluteContent(toreturn);
    }

    /**
     * splits the parametters from ',' and returns the absolute content in a
     * string array.
     */
    public static String[] getparametters(String params) {
	String[] array = params.split(",");
	for (int i = 0; i < array.length; i++) {
	    array[i] = getAbsoluteContent(array[i]);
	}
	return array;
    }

    /** Predicate that returns true if the char is present in the String */
    public static boolean contains(String s, char c) {
	char[] a = s.toCharArray();
	for (int i = 0; i < a.length; i++) {
	    if (c == a[i])
		return true;
	}
	return false;
    }

    /**
     * Same as String.split(String) , but works with any given char, including +
     * , * and / .
     */
    public static String[] split(String s, char c) {
	char[] testarray = s.toCharArray();
	int occurences = 0;
	for (int i = 0; i < testarray.length; i++)
	    if (testarray[i] == c)
		occurences++;
	String[] retarray = new String[occurences + 1];
	for (int i = 0; i < retarray.length; i++)
	    retarray[i] = "";
	occurences = 0;
	for (int i = 0; i < testarray.length; i++)
	    if (testarray[i] == c)
		occurences++;
	    else
		retarray[occurences] += testarray[i];
	return retarray;
    }

    /**
     * Returns the filename of a file without it's extention. May return
     * "Unknown name" in case of error. error might happen if the file don't
     * have any extention for exemple.
     */
    public static String getRawFilename(String completeName) {
	try {
	    String[] array = split(completeName, '.');
	    String rawname = "";
	    for (int i = 1; i < array.length; i++) {
		rawname += array[i - 1];
	    }
	    return rawname; // TODO : this. urgent.
	} catch (Exception e) {
	    return "Unknown name";
	}
    }
}
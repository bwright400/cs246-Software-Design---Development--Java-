import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.lang.NullPointerException;
import java.lang.String;
import java.util.regex.Pattern;
import java.util.Scanner;

/*****************************
 * TOPICS: Reads a file of Topics
 * and stores them in a list of
 * strings.
 ****************************/
class Topics {
    private List<String> list;

    // Non-Default Constructor
    public Topics(String file) {
	list = parseTopics(file);
    }
    
    // Copy-Constructor
    public Topics(Topics copy) {
	this.list = new ArrayList<String>(copy.list);
    }

    // Public method to return the list of topics
    public List<String> names() {
	return list;
    }

    // Parse the topics and keywords and store in a list
    private List<String> parseTopics(String file) {
	try {
	    BufferedReader reader = new BufferedReader(new FileReader(file));
	    list = new ArrayList<String>();
	    String data;
	    
	    while ((data = reader.readLine()) != null) {
		list.add(data);
	    }
	}
	
	catch (Exception error) {
	    System.err.println("Error in Topic: " + error);
	}
	
	return list;
    }
}

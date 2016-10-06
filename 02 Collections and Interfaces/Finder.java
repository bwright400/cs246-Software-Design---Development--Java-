import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.NullPointerException;
import java.lang.String;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;
import java.util.Scanner;

/******************************************************
 * FINDER: Extracts the topics files and the directory 
 * of scriptures from the properties file. Searches through
 * the directory of scriptures and matches it with the
 * appropriate topics. 
 ******************************************************/
class Finder {
    private Topics topicList;
    private List <String> fileList;
    private String[] topicsFound;

   void run(String property) {
      try {
            // create a properties file
	    String[] fileDir1 = createProperties(property);
	    
	    fileList = sortFilesInFolder(fileDir1[0]);
	    topicList = new Topics(fileDir1[1]);
	    
	    readFiles();
	    loadTopics();
   	}
	catch (Exception error) {
	    System.err.println(error);
	}
   }

    /**********************************************************
     * SORT FILES IN FOLDER: 
     * Store each file in the given directory in a List of Strings.
     ********************************************************/
    private List<String> sortFilesInFolder(String name) throws Exception {
	File folder = new File(name);
	
	File[] fileEntries = folder.listFiles();
	List <String> filenames = new ArrayList<String>();

	for (int i = 0; i < fileEntries.length; i++) {
	    if (fileEntries[i].isDirectory()) {
		throw new Exception("Entry is not a file");
	    }
	    else {
		filenames.add(fileEntries[i].getName());
	    }
	}

	// sort all the files in order
	Collections.sort(filenames);

	return filenames;
    }

    /********************************************************
     * READFILES: Search through all files in the directory.
     * Match them with there appropriate topics/
     ********************************************************/
    private void readFiles() {
	try {
	    topicsFound = new String[fileList.size()];

	    // Loop through each scripture file in the file list.
	    for (int i = 0; i < fileList.size(); i++) {

		// Create a reader object to read through a scripture file.
		FileReader scriptureFile = new FileReader("files/" + fileList.get(i));
		BufferedReader reader = new BufferedReader(scriptureFile);
		String line;
		String topic = fileList.get(i) + ": ";

		// Read each scripture verse
		while ((line = reader.readLine()) != null) {

		    // Ignore all escape characters in each verse.
		    line = line.replaceAll("\\W", "");
		    Scanner scan = new Scanner(line);

		    // Loop through each topic and its keywords during
		    // each verse being read.
		    for (int j = 0; j < topicList.names().size(); j++) {
			String topicName = topicList.names().get(j);
			String[] keyWords = topicName.split("[\\:,]");

			// Find the keywords in the verse. Return the topic
			for (int k = 1; k < keyWords.length; k++) {
			    if (scan.findInLine(keyWords[k]) != null) {
				Scanner scanScripture = new Scanner(topic);
				if (scanScripture.findInLine(keyWords[0] + ". ") == null)
				    topic += (keyWords[0] + ". ");
			    }
			}
		    }
		}
		scriptureFile.close();
		reader.close();
		topicsFound[i] = topic;
	    }
	}
	catch(Exception error) {
	    error.printStackTrace();
	}
    }

    /*********************************************
     * LOAD TOPICS: Loads all files with the topics
     * contained in them
     *********************************************/
    private void loadTopics() {
	System.out.println("FILE      CONTAINS");    
	for (int i = 0; i < topicsFound.length; i++) {
	    System.out.println(topicsFound[i]);
	}
    }

    /**********************************************
     * CREATE PROPERTIES: Creates a properties list
     * and returns the values of each key word
     **********************************************/
    private String[] createProperties(String name) {
	String [] fileDir = new String[2];
	try {
	    // Create a properties object
	    
	    Properties properties = new Properties();

	    // Set the needed properties
	    properties.setProperty("Topic", "topic.txt");
	    properties.setProperty("Direct", "files");

	    // Write the properties to a file
    	    File file = new File(name);
    	    FileOutputStream fout = new FileOutputStream(file);
	    properties.store(fout, "The Properties");
	    fout.close();

	    // Read the properties from the new file
	    FileInputStream fin = new FileInputStream(name);
	    properties.load(fin);
	    fin.close();

	    // Get the key values that need to be read
	    Enumeration enuKeys = properties.keys();
	    int i = 0;
	    while (enuKeys.hasMoreElements()) {
		String key = (String) enuKeys.nextElement();
		String value = properties.getProperty(key);
		fileDir[i++] = value;
	    }
    	}

    	catch (Exception error) {
    	    System.err.println(error);
    	}

	// Return the directory and the topic file
	return fileDir;
   
    }

    /*************************************************
     * MAIN: Main method to start the Finder Program.
     *************************************************/
    public static void main(String args[]) {
	try {
	    if (args.length == 0) {
	    	throw new Exception("Please include the properties file");	
	    }
	 
	    Finder load = new Finder();
            load.run(args[0]);
	    
	}
	
	catch(Exception error) {
	    System.err.println("Error in Main: " + error);
	}
    }
}


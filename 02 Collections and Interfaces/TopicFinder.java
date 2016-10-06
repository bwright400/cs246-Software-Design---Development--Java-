import java.util.List;
import java.util.ArrayList;
import java.nio.file.Files;
import java.nio.charset.Charset;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;


class Finder {

    // All main does is create a new instance of the Finder class
    // and then call the run method. 
    //
    // Some people will shorten this syntax to:
    // new Finder().run(args[0], args[1]);
    public static void main(String[] args) {
	Finder f = new Finder();
	f.run(args[0], args[1]);
    }

    // Note that I'm storing a List, and not an ArrayList
    // One of the key principles of good software design is:
    //    "Program to an interface, not an implementation."
    List<Topic> topics;

    public void run(String topicsFile, String directory) {
	// Even though we're storing it as the interface type (List)
	// When we instantiate it, we have to choose a specific implementation.
	// Here we choose the ArrayList implementation. 
	//
	// The advantage of programming to an interface is that since the rest of our
	// code only deals with the List interface, if we later decide we would have
	// been better off using a Stack or LinkedList, we can make that one change here
	// without affecting anything else in the code.
	topics = new ArrayList<Topic>();

	// This program has two phases. Phase 1 is load the list of topics.
	readTopics(topicsFile);

	// Phase 2 is to go through the files in each directory and see which topics
	// match up with each file.
	searchDirectory(directory);
    }

    // Note the separation of concerns here. This function is only responsible for loading
    // the lines of the topic file. It calls another function to actual parse that line.
    // This makes for easier debugging later.
    private void readTopics(String topicsFile) {
	try(BufferedReader buffer = new BufferedReader(new FileReader(topicsFile))) {
	    String line;
	    while((line = buffer.readLine()) != null) {
		parseTopic(line);
	    }					
	} catch (Exception e) {
	    System.out.println("Error reading '" + topicsFile + "'");
	    System.out.println(e);
	}
    }

    private void parseTopic(String topicLine) {
	String[] topicParts = topicLine.split(":");
	String[] keywords = topicParts[1].split(",");

	// Once we have the various parts of the topic (the name and list of keywords),
	// we create a new instance of the Topic class to represent that topic, and 
	// store it in our Topics list for later.
	Topic theTopic = new Topic(topicParts[0], keywords);
	topics.add(theTopic);
    }

    // Again, note the principle of separation of concerns here. This function's only
    // job is to get a list of files in a directory and iterate through them.
    // The processFile function is called to do the actual topic checking.
    private void searchDirectory(String directory) {
	File dir = new File(directory);
	File[] allFiles = dir.listFiles();
	for(File file : allFiles) {
	    processFile(file);
	}
    }

    private void processFile(File theFile) {
	System.out.print(theFile.getName() + ": ");
	try {
	    List<String> lines = Files.readAllLines(theFile.toPath(), Charset.defaultCharset());

	    // We now need to see which topics have keywords that are found in this file.
	    // There are lots of ways to solve this part of the problem. In this version
	    // we take the approach of looping through every topic, and then looping through every line of the file
	    // to see if that line contains any of the keywords for that topic. 
	    //
	    // We could have done it the other way around, but doing it this way allows us to optimize and say that once 
	    // we've found a given topic in a file, we can use the break statement to stop searching that file for that topic.
	    // There are other optimizations we could have taken had we done it the other way. 
	    Boolean printedFirstTopic = false;
	    for(Topic t : topics) {
		for(String line : lines) {

		    // Notice that we're letting the topic class do the actual searching for the keywords. This is another example
		    // of separation of concerns. 
		    if(t.hasKeywordInString(line)) {
						
			// Note our little hack here for making sure we don't end up with a trailing comma after the last item.
			// If you are using Java 8, you could store all of the matching topic names in a string array and
			// then use the String.join() function to combine those together into a comma-separated list. 
			// Note that like our discussion with split(), such an approach doesn't really save us any time,
			// and actually takes up more memory, even though it looks more efficient because it has fewer
			// lines of code. 
			if(printedFirstTopic) {
			    System.out.print(", ");
			}

			System.out.print(t.getName());
			printedFirstTopic = true;
			break;
		    }
		}
	    }

	    System.out.print("\n");
	}
	catch(Exception e) {
	    System.out.println("Error reading '" + theFile.getName() + "'");
	    System.out.println(e);
	}
    }
}

// ----------------------------------------------
// The following code should be in a file called:
// Topic.java
// ----------------------------------------------

// The Topic class is mostly a container to hold the topic name and keyword list. 
// It does have one intersting method though.
class Topic {
    private String topic;
    private String[] keywords;

    public String getName() {
	return topic;
    }

    public String[] getKeywords() {
	return keywords;
    }

    public Topic(String name, String[] terms) {
	topic = name;
	keywords = terms;
    }

    public boolean hasKeywordInString(String theString) {

	String theLowerString = theString.toLowerCase();
		
	// Note the use of the "for each" loop here rather than the standard for loop.
	// This kind of loop is also called "advanced for-loop", "enhanced for-loop", and "fast enumeration".
	// Though it can look cleaner and be less error-prone, there are times you need the
	// item index. Also note, that despite its name, there is no performance benefit.
	// See (http://stackoverflow.com/questions/256859/is-there-a-performance-difference-between-a-for-loop-and-a-for-each-loop)
	for(String keyword : keywords) {
	    // Note that we don't split the line up into words and check each word against each keyword. We just see if
	    // the line of text contains our keyword. Also note the conversion to lower case so that Faith and faith will match.
	    if(theLowerString.contains(keyword.toLowerCase())) {
		// Another slight optimization used by search algorithms: 
		// Exit the loop as soon as you find the result. No need to keep looking through 
		// the rest of the line.
		return true;
	    }
	}

	return false;
    } 
}

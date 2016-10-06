// Note that we're not using
// import java.io.* 
// here, but being explicit about the classes we use.
//
// See the first answer here for a good discussion on why: 
// http://stackoverflow.com/questions/147454/why-is-using-a-wild-card-with-a-java-import-statement-bad
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.File;
import java.io.*;

class WordCounter {
	
   public WordCounter() {
		
   }
	
   // This method does the actual file reading. Note that it doesn't print any error messages
   // It reports back to the caller via a boolean and exception throwing.
   // We could also use an int status code and let the caller convert that code to an error message
   public Boolean ReadFile(String filename) throws Exception{
		
      // Check if we can read the file
      if(!IsGoodFile(filename)) {
         return false;
      }
			
      // Using the "try with resources" syntax so that we don't have to 
      // jump through hoops to explicitly close the readers.
      //
      // See: http://stackoverflow.com/questions/17739362/java7-try-with-resources-statement-advantage
      // and: https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html
      try(BufferedReader buffer = new BufferedReader(new FileReader(filename))) {
			
            // Unlike C++, we have to declare this variable outside of the while loop 
            // in order to assign and compare in the same line
            String line;
			
            // readLine() returns null when the file ends.
            while((line = buffer.readLine()) != null) {
               int wordCount = CountWords(line);
               System.out.println(wordCount + ": " + line);
            }		
         } 
      catch (FileNotFoundException e) {
         // We can't do anything with this exception, so we'll just wrap it
         // in a new exception with a custom message and throw it back up the 
         // Call stack
         Exception ex = new Exception("Exception while reading file", e);
         throw ex;
      }
      catch (IOException e) {
         // We can't do anything with this exception, so we'll just wrap it
         // in a new exception with a custom message and throw it back up the 
         // Call stack
         Exception ex = new Exception("Exception while reading file", e);
         throw ex;
      }
      // Since both of the above catch clauses are the same, we could have 
      // handled them both with catch(Exception e), but I broke it out here
      // to show you how to do it explicitly. 
		
      return true;
   }
	
   // Try to circumvent some file exceptions by checking
   // that the file exists and we can read it
   private Boolean IsGoodFile(String filename) {
      File theFile = new File(filename);
		
      if(theFile.exists() && theFile.canRead()) {
         return true;
      }
      else {
         return false;
      }
   }
	
   // Uses split() to count the words.
   private int CountWords(String line) {
      int words = line.split(" ").length;
      return words;
   }
	
   // Main's only job is to check the validity of the command line arguments,
   // instantiate the primary class of our program, and call the appropriate
   // method to start the program. 
   public static void main(String[] args) {
	
      if(args.length < 1) {
         System.out.println("Usage: java WordCounter <fileToRead>");
         System.exit(-1);
      }
		
      WordCounter counter = new WordCounter();
		
      try{
         System.out.println("Opening file '" + args[0] + "'");
         if(!counter.ReadFile(args[0])) {
            System.out.println("Error reading '" + args[0] + "'");
         }
      }
      catch(Exception e) {
         System.out.println("Error reading '" + args[0] + "'");
         System.out.println(e.getMessage());
      }
		
   }

}
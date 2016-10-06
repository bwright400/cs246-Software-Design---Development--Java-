import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.w3c.dom.Attr;
import java.io.File;

/*************************************
 * JOURNAL
 * Journal class that parses a journal
 * contained in a XML file
 ************************************/
public class Journal {
   
   public static void main(String args[]) {
      Journal create = new Journal(args[0]);
   }

   // NON-Default Constructor for Journal
   // 
   public Journal(String file) {
      try {
         // Pass the Journal in for reading
         Entry reader = new Entry(getJournal(file));
      }
      catch(Exception error) {
         error.printStackTrace();
      }
   }

   // Pass the journal file in and create a document
   private Document getJournal(String file) throws Exception {
      File input = new File(file);

      DocumentBuilderFactory docIt = DocumentBuilderFactory.newInstance();
      DocumentBuilder build = docIt.newDocumentBuilder();
      Document doc = build.parse(input);

      doc.getDocumentElement().normalize();

      Node firstTag = doc.getDocumentElement();
      System.out.println("Your" + firstTag.getNodeName());
            
      return doc;
   }
}

/***************************************************
 * ENTRY
 * Class reads in every Entry in
 * the journal. Checks all of the
 * child nodes it contains and
 * displays there attributes.
 **************************************************/
class Entry {
   private String attributes;
   
   // NON-Default Constructor
   public Entry(Document doc) {
      read(doc);
   }

   // Parses each entry in the journal
   private void read(Document doc) {
      doc.getDocumentElement().normalize();
      NodeList entryList = doc.getElementsByTagName("entry");

      // Display each entry
      for (int i = 0; i < entryList.getLength(); i++) {
         Element e1 = (Element) entryList.item(i);
         System.out.println("");
         System.out.println(e1.getNodeName().toUpperCase());
         System.out.println("-------");

         // Display all the other tag names in each entry
         System.out.println("Annotations:");
         NodeList innerTag = e1.getElementsByTagName("*");
         for (int j = 0; j < innerTag.getLength(); j++) {
            Element e2 = (Element) innerTag.item(j);
            checkNames(e2);
         }
      }
   }

   // Read each tag name under every entry
   // Display all of the attributes and the content
   private void checkNames(Element element) {
      if (element.getNodeName() == "scripture") {
         Scripture script = new Scripture(element);
         System.out.println(script.getDisplayText());
      }
      else if (element.getNodeName() == "topic") {
         Topic topic = new Topic(element);
         System.out.println(topic.getDisplayText());
      }
      else if (element.getNodeName() == "content") {
         Content cont = new Content(element);
         System.out.println("-------");
         System.out.print("Content:");
         System.out.println(cont.getDisplayText());
      }
   }
}

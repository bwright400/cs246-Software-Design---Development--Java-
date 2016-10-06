

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

public class ReadXMLFile {
   public static void main(String[] args) throws Exception {
      ReadXMLFile parse = new ReadXMLFile();
      parse.read();
   }

   private Document file() throws Exception {
         File input = new File("conference.xml");

         DocumentBuilderFactory docIt = DocumentBuilderFactory.newInstance();
         DocumentBuilder build = docIt.newDocumentBuilder();
         Document doc = build.parse(input);
                 
         return doc;
   }

   public void read() throws Exception {
      Document doc = file();
      doc.getDocumentElement().normalize();

      Node firstTag = doc.getDocumentElement();
      Element e = (Element) firstTag;

      System.out.print("Speakers for the ");
      System.out.print(e.getAttribute("month") + ", ");
      System.out.print(e.getAttribute("year") + " ");
      System.out.println(firstTag.getNodeName() + " are:");
      
      NodeList list = doc.getElementsByTagName("session");

      for (int i = 0; i < list.getLength(); i++) {

         Element parent = (Element) list.item(i);
         System.out.println("");
         System.out.println(parent.getAttribute("name"));

         NodeList childList = parent.getElementsByTagName("speaker");

         for (int j = 0; j < childList.getLength(); j++) {
            Element child = (Element) childList.item(j);
            System.out.println("-" + child.getAttribute("name"));
         }
      }
   }
}
      
   
import org.w3c.dom.NodeList;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.w3c.dom.Attr;
import java.io.File;

/****************************
 * ANNOTATION
 * Creates annotations from
 * the Scripture, Topic, and
 * Content classes.
 ****************************/
public interface Annotation {
   String getDisplayText();
}

/*******************************
 * SCRIPTURE
 * Stores the attributes of each
 * tag into a string.
 *******************************/
class Scripture implements Annotation {
   private String data;

   public Scripture(Element e) {
      NamedNodeMap attributes = e.getAttributes();

      for (int index = 0; index < attributes.getLength(); index++) {
         Attr attr = (Attr) attributes.item(index);

         switch (index) {
            case 0:
               data = ("-" + attr.getNodeValue() + " ");
               break;
            case 1:
               data += attr.getNodeValue();
               break;
            case 2:
               data += (":" + attr.getNodeValue());
               break;
            case 3:
               data += ("-" + attr.getNodeValue());
               break;
         }
      }
   }

   public String getDisplayText() {
      return data;
   }
}

/*********************************
 * TOPIC
 * Stores the name of a topic into
 * an annotation.
 *********************************/
class Topic implements Annotation {
   private String data;
   
   public Topic(Element e) {
      data = "-" + e.getAttribute("name");
   }

   public String getDisplayText() {
      return data;
   }
}

/**********************************
 * Content
 * Stores the the content data
 * into a string.
 **********************************/
class Content implements Annotation {
   private String data;
   
   public Content(Element e) {
      Node node = (Node) e;
      data = node.getTextContent();
   }
   public String getDisplayText() {
      return data;
   }
}


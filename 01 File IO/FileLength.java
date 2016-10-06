import java.io.*;
import java.util.Scanner;

public class FileLength {
   public static void main(String[] args) {
      read(args[0]);
   }

   public static void read(String file) {
      try {
         FileReader reader = new FileReader(file);
         BufferedReader bufferedReader = new BufferedReader(reader);

         String data;
         int count = 0;

         while ((data = bufferedReader.readLine()) != null) {
            count = countWords(data);
            System.out.print(count + ": ");
            System.out.println(data);
         }
 
         reader.close();
      }

      catch (Exception error) {
         error.printStackTrace();
      }
   }

   public static int countWords(String value) {
      String[] words = value.split("\\W+");
      if (words.length == 1 && words[0].length() == 0) {
         return 0;
      }
      return words.length;
   }
      
}

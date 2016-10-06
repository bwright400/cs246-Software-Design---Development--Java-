import java.io.*;
import java.net.*;
import java.util.*;
import java.lang.*;

class ThreadDemo implements Runnable {
   private Thread thread;
   private String threadName;

   public ThreadDemo (String name) {
      threadName = name;
      System.out.println("Creating thread: " + threadName); 
   }
   
   public void run() {
      int type = 0;
      System.out.println("Running thread: " + threadName);
      try {
         if (threadName == "odd") {
            type = 1;
         }
         else if (threadName == "even") {
            type = 0;
         }

         for (int i = type; i < 101; i+=2) {
            System.out.println("Running Thread " + threadName + ", " + i);
            Thread.sleep(100);
         }
            
      }
      catch (Exception error) {
         error.printStackTrace();
      }
   }
   public void start() {
      System.out.println("Starting thread: " + threadName);
      thread = new Thread(this, threadName);
      thread.start();
   }
}


public class ThreadTest {
   public static void main(String args[]) {
      
      ThreadDemo even = new ThreadDemo("even");
      even.start();

      ThreadDemo odd = new ThreadDemo("odd");
      odd.start();

   }
}
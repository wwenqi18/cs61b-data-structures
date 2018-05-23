/* OpenCommercial.java */

import java.net.*;
import java.io.*;

/**  A class that provides a main function to read five lines of a commercial
 *   Web page and print them in reverse order, given the name of a company.
 */

class OpenCommercial {

  /** Prompts the user for the name X of a company (a single string), opens
   *  the Web site corresponding to www.X.com, and prints the first five lines
   *  of the Web page in reverse order.
   *  @param arg is not used.
   *  @exception Exception thrown if there are any problems parsing the 
   *             user's input or opening the connection.
   */
  public static void main(String[] arg) throws Exception {

    BufferedReader keyboard;
    String inputLine;

    keyboard = new BufferedReader(new InputStreamReader(System.in));

    System.out.print("Please enter the name of a company (without spaces): ");
    System.out.flush();        /* Make sure the line is printed immediately. */
    inputLine = keyboard.readLine();

    URL u = new URL("http://www." + inputLine + ".com/");
    InputStream ins = u.openStream();
    BufferedReader webpage = new BufferedReader(new InputStreamReader(ins));

    
    String l1 = new String();
    String l2 = new String();
    String l3 = new String();
    String l4 = new String();
    String l5 = new String();
    l1 = webpage.readLine();
    l2 = webpage.readLine();
    l3 = webpage.readLine();
    l4 = webpage.readLine();
    l5 = webpage.readLine();
    
    System.out.println(l5);
    System.out.println(l4);
    System.out.println(l3);
    System.out.println(l2);
    System.out.println(l1);
    /*
    System.out.println(webpage.readLine());
    System.out.println(webpage.readLine());
    System.out.println(webpage.readLine());
    System.out.println(webpage.readLine());
    System.out.println(webpage.readLine());
    */
  }
}

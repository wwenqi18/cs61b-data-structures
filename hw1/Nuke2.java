/* Nuke2.java */

import java.io.*;

/**  
A class that provides a main function to read a string from the keyboard 
and to print the same string with its second character removed. The input
must be at least 2 characters long. 
*/

class Nuke2 {

    /** 
    Prompts the user for a string and prints the string with its second 
    character removed. 
    */
    public static void main(String[] arg) throws Exception {
    
	BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
	String input = keyboard.readLine();
	String output = input.substring(0,1) + input.substring(2);
	System.out.println(output);
    
    }

}




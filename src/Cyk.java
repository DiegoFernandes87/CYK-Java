import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Implements the CYK Algorithm that uses a context-free grammar file and
 * checks to see whether a given string is accepted by that grammar.
 * CS154 Section 1
 */
public class Cyk
{
   // Constants
   public static final int FILE_ERROR = 1;
   public static final int GRAMMAR_FILE = 0;
   public static final int LAST_ARG = 1;
   public static final int PARSE_ERROR = 2;

   /**
    * Processes the grammar file and builds a DFA
    * @param fin the BufferedReader object
    * @return true if grammar was parsed correctly, false if the grammer
    *         could not be parsed.
    */
   public static boolean processGrammarFile(BufferedReader fin)
   {
      return false;
   }

   /**
    * Tests the string against the given grammar file.
    * @param w the input string to test
    * @return true if string w is accepted by the grammar, false otherwise.
    */
   public static boolean processString(String w)
   {
      return false;
   }

   /**
    * Takes a given grammar file as the input and a given string to test
    * against that grammar
    * @param args the given command-line arguments. They will consist of 
    *             the grammar file and the string to test, strictly in that
    *             order.
    */
   public static void main(String[] args)
   {
      BufferedReader br = null;
      if (args.length != LAST_ARG)
      {
         System.out.println("Usage: java Cyk grammar_file some_string");
         System.exit(FILE_ERROR);
      }
      try
      {
         br = new BufferedReader(new FileReader(args[GRAMMAR_FILE]));
         if (!processGrammarFile(br))
         {
            System.out.println("Grammar file could not be parsed.");
            System.exit(PARSE_ERROR);
         }
         System.out.println(processString(args[LAST_ARG]));
      }
      catch (IOException ex) 
      {
         System.out.println("File IO Error: " + ex);
      }
      finally
      {
         try 
         {
            if (br != null)
               br.close();
         }
         catch (IOException ex)
         {
            System.out.println("Could not close file!");
         }
      }
   }
}

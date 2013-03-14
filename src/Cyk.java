import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * Implements the CYK Algorithm that uses a context-free grammar file and
 * checks to see whether a given string is accepted by that grammar.
 * CS154 Section 1
 */
public class Cyk
{
   // Constants
   public static final int GRAMMAR_FILE = 0;
   public static final int FILE_ERROR = 1;
   public static final int LAST_ARG = 1;
   public static final int PARSE_ERROR = 2;
   public static final int TOTAL_ARGS = 2;

   /* The 2 dimensional table for the CYK algorithm */ 
   private static ArrayList < String > [][] table;

   /* The list of productions given in the grammar file 
    * The only productions stored in the HashSet are productions in the 
    * form A -> BC where A, B, and C are Variables
    */
   private static HashMap < String, String[] > productions;
   private static HashMap < String, String[] > variables;
   private static HashMap < String, Character > terminals;

   /* The start variable */
   private static String startVariable;

   /**
    * Processes the grammar file and builds the list of productions
    * @param fin the BufferedReader object that contains the grammar file
    * @return true if parsing was successful, false otherwise
    */
   public static boolean processGrammarFile(BufferedReader fin)
   {
      try
      {
         String[] line = fin.readLine().split(":");
         startVariable = line[0];
         while (!line[0].isEmpty())
         {
            String variable = line[0];
            String[] buffer = new String[1];
            buffer[0] = line[0];
            if ((line[1].equals("a") || line[1].equals("b")))
            {
               terminals.put(variable, line[1].charAt(0));
               productions.put(variable, buffer);
            }
            else
            {
               String[] rest = line[1].split(",");
               variables.put(variable, rest);
               productions.put(variable, rest);
               return true;
            }
            fin.readLine().split(":");
         }
      }
      catch  (IOException ex)
      {
         return false;
      }
      return false;
   }

   /**
    * Tests the string against the given grammar file using the 
    * CYK Algorithm. 
    * @param w the input string to test
    * @return true if string w is accepted by the grammar, false otherwise.
    */
   public static boolean processString(String w)
   {
      // Initialize the table
      table = new ArrayList [w.length()][];
      for (int i = 0; i < w.length(); ++i)
      {
         table[i] = new ArrayList[w.length()];
         for (int j = 0; j < w.length(); ++j)
         {
            table[j] = new ArrayList(); 
         }
      }
      
      // Start CYK Algorithm
      for (int i = 0; i < w.length(); ++i)
      {
         // Get the list of variables
         Set<String> keys = variables.keySet();
         for (String key: keys)
         {
            if (terminals.get(key).charValue() == w.charAt(i))
               table[i][i].add(key);
         }
      }
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
      if (args.length != TOTAL_ARGS)
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
   }
}

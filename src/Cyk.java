import java.io.File;
import java.util.Scanner;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * Implements the CYK Algorithm that uses a context-free grammar file and checks
 * to see whether a given string is accepted by that grammar. CS154 Section 1
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
   private static ArrayList<String>[][] table;

   private HashMap<String, String[]> variables;
   private HashMap<String, Character> terminals;

   /* The start variable */
   private static String startVariable;

   /**
    * Initializes a Cyk Object
    */
   public Cyk()
   {
      variables = new HashMap<String, String[]>();
      terminals = new HashMap<String, Character>();
   }

   /**
    * Processes the grammar file and builds the list of productions
    * @param file the string representing the path of the grammar file
    */
   public void processGrammarFile(String file)
   {
      File grammarFile = null;
      Scanner scanner = null;
      try
      {
         grammarFile = new File(file);
         scanner = new Scanner(grammarFile);
         String[] line = scanner.nextLine().split(":");
         startVariable = line[0];
         do
         {
            String variable = line[0];
            if ((line[1].equals("a") || line[1].equals("b")))
            {
               terminals.put(variable, line[1].charAt(0));
            }
            else
            {
               String[] rest = line[1].split(",");
               if (rest != null)
               {
                  variables.put(variable, rest);
               }
            }
            if (scanner.hasNextLine())
               line = scanner.nextLine().split(":");
            else
               line = null;
         } while (line != null);
         scanner.close();
      }
      catch (IOException ex)
      {
         ex.printStackTrace();
      }
   }

   /**
    * Tests the string against the given grammar file using the CYK Algorithm.
    * @param w the input string to test
    * @return true if string w is accepted by the grammar, false otherwise.
    */
   @SuppressWarnings("unchecked")
   public boolean processString(String w)
   {
      int length = w.length();
      table = new ArrayList[length][];
      for (int i = 0; i < length; ++i)
      {
         table[i] = new ArrayList[length];
         for (int j = 0; j < length; ++j)
            table[i][j] = new ArrayList < String > ();
      }
      for (int i = 0; i < length; ++i)
      {
         Set<String> keys = terminals.keySet();
         for (String key : keys)
         {
            if (terminals.get(key).charValue() == w.charAt(i))
               table[i][i].add(key);
         }
      }
      for (int l = 2; l <= length; ++l)
      {
         for (int i = 0; i <= length - l; ++i)
         {
            int j = i + l - 1;
            for (int k = i; k <= j - 1; ++k)
            {
               Set<String> keys = variables.keySet();
               for (String key : keys)
               {
                  String[] values = variables.get(key);
                  if (table[i][k].contains((values[0]))
                        && table[k + 1][j].contains(values[1]))
                     table[i][j].add(key);
               }
            }
         }
      }
      if (table[0][length - 1].contains(startVariable)) // we started from 0
         return true;
      return false;
   }

   /**
    * Takes a given grammar file as the input and a given string to test
    * against that grammar.
    * @param args the given command-line arguments. They will consist of the
    *            grammar file and the string to test, strictly in that order.
    */
   public static void main(String[] args)
   {
      if (args.length != TOTAL_ARGS)
      {
         System.out.println("Usage: java Cyk grammar_file some_string");
         System.exit(FILE_ERROR);
      }
      Cyk c = new Cyk();
      c.processGrammarFile(args[GRAMMAR_FILE]);
      if (c.processString(args[LAST_ARG]))
         System.out.println("true");
      else
         System.out.println("false");
   }
}

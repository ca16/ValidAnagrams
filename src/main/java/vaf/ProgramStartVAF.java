package vaf;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * The entry point of the program. Starts communication between user and program. 
 * 
 * Program description: given a list of words, finds the anagrams of those words that appear in a
 * dictionary file. The user may provide their own dictionary file, or use the default one.
 * 
 * Created by Chloe on 10/7/16.
 */
public class ProgramStartVAF {

    private static final String FAILED_MSG = "Apologies. I was unable to find a dictionary file. ";
    private static final String FAILED_MSG2 = "Apologies. I encountered a problem. ";

    public static void main(String[] args){

//        Trie trie = new Trie();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            
            // make an interaction handler
            InteractionHandler communicator = new InteractionHandler(reader);
            
            // figure out walk kind of representation the user wants
            communicator.talkAboutDictRep();
            
            // try to construct the dictionary representation 
            Boolean success = communicator.talkAboutDictionary();

            // dictionary representation construction was a success, 
            // proceed with finding anagrams
            if (success) {
                communicator.talkAboutInputWords();
            }

            // dictionary representation construction was a failure, inform the user and end.
            else {
                System.out.println(FAILED_MSG);
            }
        } catch (IOException e) {
            System.out.println(FAILED_MSG2);
        }
    }

}

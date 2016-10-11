package vaf;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import vaf.anagrammakers.GraphAnagramMaker;
import vaf.anagrammakers.IAnagramMaker;
import vaf.anagrammakers.IterAnagramMaker;

/**
 * Responsible for processing the users input for word for which anagrams will be found.
 * 
 * Created by Chloe on 10/8/16.
 */
public class InputWordListProcessor {

    private List<String> words;
    private IAnagramMaker anagramMaker;
    private Trie trie;

    public InputWordListProcessor(String words, Trie trie, Boolean defaultAM){
        this.words = parseWordList(words);
        this.trie = trie;
        if (defaultAM){
            anagramMaker = new GraphAnagramMaker(trie);
        }
        else{
            anagramMaker = new IterAnagramMaker(trie);
        }
    }

    /**
     * Goes through all the words given as input and prints the anagrams of those words that are
     * in the program's trie.
     */
    void findAnagramsAndCompare(){
        for (String word : words){
            System.out.println("\nFor word: " + word);
            if (word.length() == 0){
                System.out.println("\n");
                continue;
            }
            word = word.toLowerCase().trim();
            Set<String> anagrams = anagramMaker.singleWordAnagrams(word);
            compareAnagrams(anagrams);
            System.out.print("\n");
        }
    }

    /**
     * Provides part of the desired output: goes through the anagrams of one word and prints any
     * anagrams that are also in the trie (so any words that were also in the dictionary).
     * @param anagrams anagrams of a word
     */
    void compareAnagrams(Set<String> anagrams){
        for (String anagram : anagrams){
            if (trie.contains(anagram)){
                System.out.println(anagram);
            }
        }
        System.out.print("\n");
    }


    /**
     * Splits the users input from one long string into words.
     * @param words a string
     * @return a list of words
     */
    List<String> parseWordList(String words){
        // The user didn't provide any words
        if (words.equals(" ") || words == null || words.length() < 1){
            return new ArrayList<>();
        }

        // Words separated by spaces
        Pattern space = Pattern.compile(" ");
        String[] arrayVersion = space.split(words);
        return arrayToList(arrayVersion);
    }

    /**
     * Helper function for turning an array of strings into a list of strings.
     * @param words strings in an array
     * @return the given strings in a list
     */
    List<String> arrayToList(String[] words){
        List<String> ret = new ArrayList<>();
        for (int i = 0; i < words.length; i++){
            ret.add(words[i]);
        }
        return ret;
    }

    /**
     * Processes a word before an anagram finder tries to find anagrams for it. It removes spaces,
     * makes everything lower-case and removes special cahracters.
     * @param word a word
     * @return the word in a form that can be processed by an anagram finder
     */
    public static String preprocessWord(String word){
        StringBuilder builder = new StringBuilder();
        word = word.toLowerCase().trim(); //decide whether to do this here to before
        for (int i = 0; i < word.length(); i++){
            char letter = word.charAt(i);
            if (letter >= 'a' && letter <= 'z'){
                builder.append(word.substring(i, i+1));
            }
        }
        return builder.toString();
    }


}

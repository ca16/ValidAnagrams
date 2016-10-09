package vaf;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import vaf.anagrammakers.IAnagramMaker;

/**
 * Responsible for processing the users input for word for which anagrams will be found.
 * Created by Chloe on 10/8/16.
 */
public class InputWordListProcessor {

    private List<String> words;
    private IAnagramMaker anagramMaker;
    private Trie trie;

    public InputWordListProcessor(String words, Trie trie, Boolean defaultAM){
        this.words = parseWordList(words);
        this.trie = trie;

        // Get the right kind of anagram finder
        if (defaultAM){
            anagramMaker = IAnagramMaker.getGraphAM(trie);
        }
        else {
            anagramMaker = IAnagramMaker.getIterAM(trie);
        }
    }

    /**
     * Goes through all the words given as input and prints the anagrams of those words that are
     * in the programs trie. 
     */
    void findAnagramsAndCompare(){
        for (String word : words){
            System.out.println("\nFor word: " + word);
            if (word.length() == 0){
                System.out.println("\n");
                continue;
            }
            word = word.toLowerCase().trim();
            List<String> anagrams = anagramMaker.singleWordAnagrams(word);
            compareAnagrams(anagrams);
            System.out.print("\n");
        }
    }

    /**
     * Provides part of the desired output: goes through the anagrams of one word and prints any
     * anagrams that are also in the trie (so any words that were also in the dictionary).
     * @param anagrams anagrams of a word
     */
    void compareAnagrams(List<String> anagrams){
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


}

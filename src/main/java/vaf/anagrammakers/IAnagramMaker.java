package vaf.anagrammakers;
import java.util.List;

import vaf.Trie;

/**
 * Responsible for finding anagrams of words.
 * Created by Chloe on 10/7/16.
 */
public interface IAnagramMaker {

    /**
     * Finds anagrams for many words.
     * @param words a list of words
     * @return a list of anagrams of those words.
     */
    List<String> lstOfWordsAnagrams(List<String> words);

    /**
     * Finds anagrams for one word.
     * @param word a word
     * @return a list of anagrams for that word.
     */
    List<String> singleWordAnagrams(String word);

    /**
     * Returns an anagram finder.
     * @param trie the trie the anagram finder will use to construct potential anagrams
     * @param defaultAM true if the user wants to use the default kind of anagram finder, false
     *                  otherwise
     * @return an anagram finder that uses a graph-based search or an anagram finder that uses
     * a more iterative approach
     */
    static IAnagramMaker getAnagramMaker(Trie trie, Boolean defaultAM){
        if (defaultAM){
            return new GraphAnagramMaker(trie);
        }
        else {
            return new IterAnagramMaker(trie);
        }
    }

    /**
     * Processes a word before an anagram finder tries to find anagrams for it. It removes spaces,
     * makes everything lower-case and removes special cahracters.
     * @param word a word
     * @return the word in a form that can be processed by an anagram finder
     */
    static String preprocessWord(String word){
        //fix with buffer
        String ret = "";
        word = word.toLowerCase().trim(); //decide whether to do this here to before
        for (int i = 0; i < word.length(); i++){
            char letter = word.charAt(i);
            if (letter >= 'a' && letter <= 'z'){
                ret = ret + word.substring(i, i+1);
            }
        }
        return ret;
    }
}

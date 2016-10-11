package vaf.anagrammakers;
import java.util.List;
import java.util.Set;

import vaf.Trie;

/**
 * Responsible for finding anagrams of words.
 * 
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
    Set<String> singleWordAnagrams(String word);

}

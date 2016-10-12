package vaf.inputprocessors;

import java.util.List;
import java.util.Set;

import vaf.trieversion.Trie;
import vaf.trieversion.GraphAnagramMaker;
import vaf.trieversion.IAnagramMaker;
import vaf.trieversion.IterAnagramMaker;

/**
 * Responsible for processing the users input for word for which anagrams will be found.
 * 
 * Created by Chloe on 10/8/16.
 */
public class TrieWordsToOutput extends AWordsToOutputHandler {

    private List<String> words;
    private IAnagramMaker anagramMaker;
    private Trie trie;

    public TrieWordsToOutput(String words, Trie trie, Boolean defaultAM){
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
    public void findAnagramsAndCompare(){
        for (String word : words){
            System.out.println("\nFor word: " + word);
            if (word.length() == 0){
                System.out.println("\n");
                continue;
            }
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
    private void compareAnagrams(Set<String> anagrams){
        for (String anagram : anagrams){
            if (trie.contains(anagram)){
                System.out.println(anagram);
            }
        }
        System.out.print("\n");
    }


}

package vaf.inputprocessors;

import java.util.List;
import java.util.Map;
import java.util.Set;

import vaf.mapversion.Sorter;
/**
 * Responsible for processing the users input for word for which anagrams will be found.
 * 
 * Created by Chloe on 10/8/16.
 */
public class MapWordsToOutput extends AWordsToOutputHandler {

    private List<String> words;
    private Map<String, Set<String>> map;

    public MapWordsToOutput(String words, Map<String, Set<String>> map){
        this.words = parseWordList(words);
        this.map = map;
    }

    /**
     * Goes through all the words given as input and prints the anagrams of those words that are
     * in the dictionary file.
     */
    public void findAnagramsAndCompare(){
        for (String word : words){
            System.out.println("\nFor word: " + word);
            if (word.length() == 0){
                System.out.println("\n");
                continue;
            }
            String sortedWord = Sorter.sortWord(word);
            if (map.containsKey(sortedWord)){
                for (String containedWord : map.get(sortedWord)){
                    System.out.println(containedWord);
                }
            }
            System.out.print("\n");
        }
    }
    
}

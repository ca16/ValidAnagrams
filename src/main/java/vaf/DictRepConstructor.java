package vaf;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import vaf.inputprocessors.AWordsToOutputHandler;
import vaf.trieversion.Trie;
import vaf.mapversion.Sorter;

/**
 * Responsible for processing a dictionary file.
 * 
 * Created by Chloe on 10/7/16.
 */
public class DictRepConstructor {

    /**
     * Puts the words in the file at the given file path into a list.
     *
     * For the program to function as expected, it is expected that the file given to this function
     * is a file containing one word per line.
     *
     * If it throws an exception, it is expected that the exception will be caught
     * by ProgramStartVAF.
     *
     * @param filePath the file path to the dictionary file
     * @return a list containing the words in the given file
     * @throws IOException if there is a problem reading the file or with the reader
     */
    public static Trie constructTrie(String filePath) throws IOException{

        List<String> wrdLst = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String word = null;
            while ((word = reader.readLine()) != null) {
                word = word.toLowerCase();
                word = word.trim();
                wrdLst.add(word);
            }
        }

        Trie trie = new Trie();
        trie.addWordList(wrdLst);
        return trie;
    }

    /**
     * Puts the words in the file at the given file path into a list.
     *
     * For the program to function as expected, it is expected that the file given to this function
     * is a file containing one word per line.
     *
     * If it throws an exception, it is expected that the exception will be caught
     * by ProgramStartVAF.
     *
     * @return a list containing the words in the given file
     * @throws IOException if there is a problem reading the file or with the reader
     */
    public static Map<String, Set<String>> constructMap(String file){

        Map<String, Set<String>> map = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String word = null;
            while ((word = reader.readLine()) != null) {
                String processedWord = AWordsToOutputHandler.preprocessWord(word);
                String sortedWord = Sorter.sortWord(word);
                addValueToSet(processedWord, sortedWord, map);
            }
        }catch (IOException e){
            // do something about it
        }
        return map;
    }

    private static void addValueToSet(String word, String sortedWord, Map<String, Set<String>> map){
        if (map.containsKey(sortedWord) && map.get(sortedWord).contains(word)){
            return;
        }
        if (map.containsKey(sortedWord)){
            map.get(sortedWord).add(word);
        }
        else {
            Set<String> values = new HashSet<>();
            values.add(word);
            map.put(sortedWord, values);
        }
    }
}

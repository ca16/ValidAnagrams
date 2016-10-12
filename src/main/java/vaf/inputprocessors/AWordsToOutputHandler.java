package vaf.inputprocessors;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by Chloe on 10/11/16.
 */
public abstract class AWordsToOutputHandler implements IWordsToOutputHandler {

    /**
     * Splits the users input from one long string into words.
     * @param words a string
     * @return a list of words
     */
    public List<String> parseWordList(String words){
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
    public List<String> arrayToList(String[] words){
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
        word = word.toLowerCase().trim();
        for (int i = 0; i < word.length(); i++){
            char letter = word.charAt(i);
            if (letter >= 'a' && letter <= 'z'){
                builder.append(word.substring(i, i+1));
            }
        }
        return builder.toString();
    }
}

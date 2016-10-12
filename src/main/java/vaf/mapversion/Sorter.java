package vaf.mapversion;

import vaf.inputprocessors.AWordsToOutputHandler;

/**
 * Created by Chloe on 10/11/16.
 */
public class Sorter {

    /**
     * Sorts the characters of the given word
     * @param word the word who characters we want to sort
     * @return the sorted version of the word
     */
    public static String sortWord(String word){
        String processedWord = AWordsToOutputHandler.preprocessWord(word);
        int len = processedWord.length();
        int[] buckets = new int[26];
        for (int i = 0; i  < 26; i++){
            buckets[i] = 0;
        }
        for (int i = 0; i < len; i ++){
            char letter = processedWord.charAt(i);
            int loc = letter - 'a';
            buckets[loc]++;
        }
        for (int i = 1; i < 26; i++){
            buckets[i] = buckets[i] + buckets[i-1];
        }
        
        char[] sortedChars = new char[len];
        for (int i = 0; i < len; i++){
            sortedChars[buckets[processedWord.charAt(i)-'a']-1] = processedWord.charAt(i);
            buckets[processedWord.charAt(i)-'a']--;
            
        }

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < len; i++){
            builder.append(Character.toString(sortedChars[i]));
        }
        
        return builder.toString();
    }
    
    // add constants for 0 and 26 to other
}

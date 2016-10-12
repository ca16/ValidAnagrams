package vaf.mapversion;

import vaf.inputprocessors.AWordsToOutputHandler;

/**
 * Created by Chloe on 10/11/16.
 */
public class Sorter {
    
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
    
    public static void main(String[] args){

        // For default file path
        String fileSep = System.getProperty("file.separator");
        String defDict = "wordsEn.txt";
        String currDir = ".";
        String parentDir = "..";

        // Important sub-folders
        String srcDir = "src";
        String mnDir = "main";
        String recDir = "resources";
        String jvaDir = "java";

        String getToMain = "";
        String workingDir = System.getProperty("user.dir");

        // For command line
        if (workingDir.endsWith(fileSep + jvaDir)){
            getToMain = parentDir + fileSep;
        }

        // For Intellij
        else{
            getToMain = currDir + fileSep + srcDir + fileSep + mnDir + fileSep;
        }

//        String defaultPath = getToMain + recDir + fileSep + defDict;
//        MapDictProcessor proc = new MapDictProcessor(defaultPath);
//        MapWordsToOutput otherProc = new MapWordsToOutput("hi there find anagrams stab", proc.dictToList());
//        otherProc.findAnagramsAndCompare();

        
    }
    
    // add constants for 0 and 26 to other
}

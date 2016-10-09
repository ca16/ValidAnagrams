package vaf;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Responsible for processing a dictionary file.
 * Created by Chloe on 10/7/16.
 */
public class DictProcessor {

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
     * @throws FileNotFoundException if the file cannot be found
     */
    public static List<String> wordsToList(String filePath) throws IOException{

        List<String> ret = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String word = null;
            while ((word = reader.readLine()) != null) {
                word = word.toLowerCase();
                word = word.trim();
                ret.add(word);
            }
        }
        return ret;
    }
}

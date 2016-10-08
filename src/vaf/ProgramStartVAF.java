package vaf;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by Chloe on 10/7/16.
 */
public class ProgramStartVAF {

    public static void main(String[] args){

        String defaultPathV1 = "./wordsEn.txt";
        String defaultPathV2 = "../../../wordsEn.txt";
        String defaultPathV3 = "../wordsEn.txt";
        String filePath = defaultPathV1;
        Boolean needFile = false;

        WordTrie trie = new WordTrie();

        try {
            List<String> dictWords = DictParser.wordsToList(filePath);
            trie.addWordList(dictWords);
        }
        catch(FileNotFoundException fnfe){
            filePath = defaultPathV2;
        }
        try {
            List<String> dictWords = DictParser.wordsToList(filePath);
            trie.addWordList(dictWords);
        }
        catch(FileNotFoundException fnfe){
            filePath = defaultPathV3;
        }
        try {
            List<String> dictWords = DictParser.wordsToList(filePath);
            trie.addWordList(dictWords);
        }
        catch(FileNotFoundException fnfe){
            needFile = true;
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            InteractionHandler communicator = new InteractionHandler(trie, reader);
            communicator.talkAboutDictionary();
            communicator.talkAboutInputWords();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

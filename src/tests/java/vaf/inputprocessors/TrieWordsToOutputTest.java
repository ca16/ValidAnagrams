package vaf.inputprocessors;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import vaf.DictRepConstructor;
import vaf.PathsAndNames;
import vaf.trieversion.GraphAnagramMaker;
import vaf.trieversion.IAnagramMaker;
import vaf.trieversion.IterAnagramMaker;
import vaf.trieversion.Trie;
import vaf.inputprocessors.TrieWordsToOutput;

import static vaf.PathsAndNames.*;


/**
 * Created by Chloe on 10/8/16.
 */
public class TrieWordsToOutputTest {

    private String spaceInput;

    private String oneWordInput;
    private String fewWordInput;
    private String manyWordInput;

    String word1;
    String word2;
    String word3;
    String word4;
    String word5;
    String word6;
    String word7;
    String word8;
    
    private Trie bigTrie;
    private Trie littleTrie;


    @Before
    public void setUp() throws Exception {

        PathsAndNames.populatePaths();

        spaceInput = " ";

        word1 = "snag";
        word2 = "three";
        word3 = "pods";
        word4 = "stab";
        word5 = "helicopter";
        word6 = "kettle";
        word7 = "silly";
        word8 = "misfortune";

        oneWordInput = word1;
        fewWordInput = word1 + spaceInput + word2 + spaceInput + word3;
        manyWordInput = word1 + spaceInput + word2 + spaceInput + word3 + spaceInput + word4 +
                spaceInput + word5 + spaceInput + word6 + spaceInput + word7 + spaceInput + word8;
        
        
        bigTrie = DictRepConstructor.constructTrie(PATH_TO_B_DICT);
        littleTrie = DictRepConstructor.constructTrie(PATH_TO_S_DICT);


    }

    @Test
    public void findAnagramsAndCompare() throws Exception {
        
        // File names
        String manyWordsBig = "ManyWordsOutputTrie.txt";
        String threeChangeSmall = "SmallDictThreeWordsChangeOutput.txt";

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        PrintStream oldOut = System.out;
        System.setOut(new PrintStream(os));

        // One word, the big dictionary, switched anagram finding method
        TrieWordsToOutput bigIterOneWord = new TrieWordsToOutput(oneWordInput, bigTrie, false);
        os.reset();
        String expectedOutput = "\n\nFor word: " + word1 + "\nnags\nsang\nsnag\n\n\n";
        System.out.println(os);
        bigIterOneWord.findAnagramsAndCompare();
        Assert.assertEquals(expectedOutput, os.toString());
        os.reset();

        // One word, the little dictionary, default anagram finding method
        TrieWordsToOutput littleGraphOneWord = new TrieWordsToOutput(oneWordInput, littleTrie, true);
        expectedOutput = "\nFor word: " + word1 + "\n\n\n";
        littleGraphOneWord.findAnagramsAndCompare();
        Assert.assertEquals(expectedOutput, os.toString());
        os.reset();

        // Many words, the big dictionary, default anagram finding method
        TrieWordsToOutput bigGraphManyWords = new TrieWordsToOutput(manyWordInput, bigTrie, true);
        String progOutputFileCase3 = PATH_TO_ANA_ANS + FILE_SEP + manyWordsBig;
        BufferedReader expRespReader = new BufferedReader(new FileReader(progOutputFileCase3));
        StringBuilder expOutput = new StringBuilder();
        String line = null;
        while ((line = expRespReader.readLine()) != null){
            expOutput = expOutput.append(line + "\n");
        }
        bigGraphManyWords.findAnagramsAndCompare();
        Assert.assertEquals(expOutput.toString(), os.toString());
        os.reset();

        // A few words, the little dictionary, switched anagram finding method
        TrieWordsToOutput littleIterFewWords = new TrieWordsToOutput(fewWordInput, littleTrie, false);
        String progOutputFileCase4 = PATH_TO_ANA_ANS + FILE_SEP + threeChangeSmall;
        expRespReader = new BufferedReader(new FileReader(progOutputFileCase4));
        expOutput = new StringBuilder(); 
        line = null;
        while ((line = expRespReader.readLine()) != null){
            expOutput = expOutput.append(line + "\n");
        }
        littleIterFewWords.findAnagramsAndCompare();
        Assert.assertEquals(expOutput.toString(), os.toString());
        os.reset();
        
        Assert.assertFalse(bigTrie.contains(null));
        Assert.assertFalse(bigTrie.contains(""));
        Assert.assertFalse(bigTrie.isPrefix(null));

        os.close();
        expRespReader.close();
        System.setOut(oldOut);

    }

}
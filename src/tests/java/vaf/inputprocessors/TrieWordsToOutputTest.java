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

    private String emptyInput;
    private String spaceInput;
    private String lotsOfSpaceInput;

    private String oneWordInput;
    private String fewWordInput;
    private String manyWordInput;

    private String[] oneWord;
    private String[] fewWords;
    private String[] manyWords;

    private List<String> oneWordLst;
    private List<String> fewWordLst;
    private List<String> manyWordLst;

    String word1;
    String word2;
    String word3;
    String word4;
    String word5;
    String word6;
    String word7;
    String word8;


    private List<String> emptyLst;

    private Trie bigTrie;
    private Trie littleTrie;

    private IAnagramMaker gmBig;
    private IAnagramMaker imBig;

    private IAnagramMaker gmSmall;
    private IAnagramMaker imSmall;

    private TrieWordsToOutput procBig;
    private TrieWordsToOutput procSmall;


    @Before
    public void setUp() throws Exception {

        PathsAndNames.populatePaths();

        emptyInput = "";
        spaceInput = " ";
        lotsOfSpaceInput = "    ";

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

        oneWord = new String[1];
        fewWords = new String[3];
        manyWords = new String[8];

        oneWord[0] = word1;
        fewWords[0] = word1;
        fewWords[1] = word2;
        fewWords[2] = word3;
        manyWords[0] = word1;
        manyWords[1] = word2;
        manyWords[2] = word3;
        manyWords[3] = word4;
        manyWords[4] = word5;
        manyWords[5] = word6;
        manyWords[6] = word7;
        manyWords[7] = word8;

        oneWordLst = new ArrayList<>();
        fewWordLst = new ArrayList<>();
        manyWordLst = new ArrayList<>();

        oneWordLst.add(word1);
        fewWordLst.add(word1);
        fewWordLst.add(word2);
        fewWordLst.add(word3);
        manyWordLst.add(word1);
        manyWordLst.add(word2);
        manyWordLst.add(word3);
        manyWordLst.add(word4);
        manyWordLst.add(word5);
        manyWordLst.add(word6);
        manyWordLst.add(word7);
        manyWordLst.add(word8);

        emptyLst = new ArrayList<>();
        
        bigTrie = DictRepConstructor.constructTrie(PATH_TO_B_DICT);
        littleTrie = DictRepConstructor.constructTrie(PATH_TO_S_DICT);
        
        gmBig = new GraphAnagramMaker(bigTrie);
        gmSmall = new GraphAnagramMaker(littleTrie);
        imBig = new IterAnagramMaker(bigTrie);
        imSmall = new IterAnagramMaker(bigTrie);

        procBig = new TrieWordsToOutput(emptyInput, bigTrie, true);
        procSmall = new TrieWordsToOutput(emptyInput, littleTrie, true);


    }

    @Test
    public void findAnagramsAndCompare() throws Exception {
        
        // File names
        String manyWordsBig = "ManyWordsOutput.txt";
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

        os.close();
        expRespReader.close();
        System.setOut(oldOut);

    }

    @Test
    public void parseWordList() throws Exception {

        Assert.assertEquals(emptyLst, procBig.parseWordList(emptyInput));
        Assert.assertEquals(emptyLst, procBig.parseWordList(spaceInput));
        Assert.assertEquals(emptyLst, procBig.parseWordList(lotsOfSpaceInput));

        Assert.assertEquals(oneWordLst, procBig.parseWordList(oneWordInput));
        Assert.assertEquals(fewWordLst, procBig.parseWordList(fewWordInput));
        Assert.assertEquals(manyWordLst, procBig.parseWordList(manyWordInput));


    }

    @Test
    public void arrayToList() throws Exception {

        Assert.assertEquals(oneWordLst, procBig.arrayToList(oneWord));
        Assert.assertEquals(fewWordLst, procBig.arrayToList(fewWords));
        Assert.assertEquals(manyWordLst, procBig.arrayToList(manyWords));

    }

    @Test
    public void preprocessWord() throws Exception {

        Assert.assertEquals("quit", TrieWordsToOutput.preprocessWord("Qui!t"));
        Assert.assertEquals("quit", TrieWordsToOutput.preprocessWord("quit"));
        Assert.assertEquals("findme", TrieWordsToOutput.preprocessWord("-298findme029!*"));
        Assert.assertEquals("ohio", TrieWordsToOutput.preprocessWord("Ohio"));
    }

}
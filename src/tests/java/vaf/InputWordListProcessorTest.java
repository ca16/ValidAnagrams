package vaf;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import vaf.anagrammakers.GraphAnagramMaker;
import vaf.anagrammakers.IAnagramMaker;
import vaf.anagrammakers.IterAnagramMaker;
import static vaf.PathsAndNames.*;


/**
 * Created by Chloe on 10/8/16.
 */
public class InputWordListProcessorTest {

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

    private InputWordListProcessor procBig;
    private InputWordListProcessor procSmall;


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

        bigTrie = new Trie(PATH_TO_B_DICT);
        littleTrie = new Trie(PATH_TO_S_DICT);

        gmBig = new GraphAnagramMaker(bigTrie);
        gmSmall = new GraphAnagramMaker(littleTrie);
        imBig = new IterAnagramMaker(bigTrie);
        imSmall = new IterAnagramMaker(bigTrie);

        procBig = new InputWordListProcessor(emptyInput, bigTrie, true);
        procSmall = new InputWordListProcessor(emptyInput, littleTrie, true);


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
        InputWordListProcessor bigIterOneWord = new InputWordListProcessor(oneWordInput, bigTrie, false);
        os.reset();
        String expectedOutput = "\nFor word: " + word1 + "\nnags\nsang\nsnag\n\n\n";
        bigIterOneWord.findAnagramsAndCompare();
        Assert.assertEquals(expectedOutput, os.toString());
        os.reset();

        // One word, the little dictionary, default anagram finding method
        InputWordListProcessor littleGraphOneWord = new InputWordListProcessor(oneWordInput, littleTrie, true);
        expectedOutput = "\nFor word: " + word1 + "\n\n\n";
        littleGraphOneWord.findAnagramsAndCompare();
        Assert.assertEquals(expectedOutput, os.toString());
        os.reset();

        // Many words, the big dictionary, default anagram finding method
        InputWordListProcessor bigGraphManyWords = new InputWordListProcessor(manyWordInput, bigTrie, true);
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
        InputWordListProcessor littleIterFewWords = new InputWordListProcessor(fewWordInput, littleTrie, false);
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
    public void compareAnagrams() throws Exception {

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        PrintStream oldOut = System.out;
        System.setOut(new PrintStream(os));

        Set<String> allInDict = new HashSet<>();
        Set<String> noneInDict = new HashSet<>();
        Set<String> someInDict = new HashSet<>();

        allInDict.add("stab");
        allInDict.add("harmony");
        allInDict.add("symmetry");
        allInDict.add("misty");

        noneInDict.add("osdvoi");
        noneInDict.add("gggg");
        noneInDict.add("zbzbzbz");

        someInDict.add("fog");
        someInDict.add("dddd");
        someInDict.add("carmine");
        someInDict.add("slddng");
        someInDict.add("castle");

        procBig.compareAnagrams(allInDict);
        String expectedOutput = "harmony\nstab\nsymmetry\nmisty";
        Assert.assertEquals(expectedOutput, os.toString().trim());

        os.reset();

        //fewer words should be printed because this processor's dictionary is smaller
        procSmall.compareAnagrams(allInDict);
        expectedOutput = "stab\nmisty";
        Assert.assertEquals(expectedOutput, os.toString().trim());

        os.reset();

        procBig.compareAnagrams(noneInDict);
        expectedOutput = "";
        Assert.assertEquals(expectedOutput, os.toString().trim());

        os.reset();

        // should have the same behaviour as procBig, no words are in the small dictionary
        // that aren't in the big one
        procSmall.compareAnagrams(noneInDict);
        expectedOutput = "";
        Assert.assertEquals(expectedOutput, os.toString().trim());

        os.reset();

        procBig.compareAnagrams(someInDict);
        expectedOutput = "castle\nfog\ncarmine";
        Assert.assertEquals(expectedOutput, os.toString().trim());

        os.reset();

        // though some fo these words were in the bigger dictionary, none of them are in the
        // small one
        procSmall.compareAnagrams(someInDict);
        expectedOutput = "";
        Assert.assertEquals(expectedOutput, os.toString().trim());

        os.close();
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

        Assert.assertEquals("quit", InputWordListProcessor.preprocessWord("Qui!t"));
        Assert.assertEquals("quit", InputWordListProcessor.preprocessWord("quit"));
        Assert.assertEquals("findme", InputWordListProcessor.preprocessWord("-298findme029!*"));
        Assert.assertEquals("ohio", InputWordListProcessor.preprocessWord("Ohio"));
    }

}
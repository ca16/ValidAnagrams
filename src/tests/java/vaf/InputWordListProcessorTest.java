package vaf;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import vaf.anagrammakers.IAnagramMaker;

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

        String littleFile = "./TestSmallDict.txt";
        String bigFile = "./wordsEn.txt";

        bigTrie = new Trie(bigFile);
        littleTrie = new Trie(littleFile);

        gmBig = IAnagramMaker.getAnagramMaker(bigTrie, true);
        gmSmall = IAnagramMaker.getAnagramMaker(littleTrie, true);
        imBig = IAnagramMaker.getAnagramMaker(bigTrie, false);
        imSmall = IAnagramMaker.getAnagramMaker(bigTrie, false);

        procBig = new InputWordListProcessor(emptyInput, bigTrie, true);
        procSmall = new InputWordListProcessor(emptyInput, littleTrie, true);


    }

    @Test
    public void findAnagramsAndCompare() throws Exception {

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        System.setOut(new PrintStream(os));

        InputWordListProcessor bigGraphOneWord = new InputWordListProcessor(oneWordInput, bigTrie, true);
        bigGraphOneWord.compareAnagrams(gmBig.singleWordAnagrams(oneWordInput));
        String intermediateOutput = os.toString();
        os.reset();
        String expectedOutput = "\nFor word: " + word1 + "\n" + intermediateOutput + "\n";
        bigGraphOneWord.findAnagramsAndCompare();
        Assert.assertEquals(expectedOutput, os.toString());
        os.reset();

        InputWordListProcessor bigIterOneWord = new InputWordListProcessor(oneWordInput, bigTrie, false);
        bigIterOneWord.compareAnagrams(imBig.singleWordAnagrams(oneWordInput));
        intermediateOutput = os.toString();
        os.reset();
        expectedOutput = "\nFor word: " + word1 + "\n" + intermediateOutput + "\n";
        bigIterOneWord.findAnagramsAndCompare();
        Assert.assertEquals(expectedOutput, os.toString());
        os.reset();


        InputWordListProcessor littleGraphOneWord = new InputWordListProcessor(oneWordInput, littleTrie, true);
        expectedOutput = "\nFor word: " + word1 + "\n\n\n";
        littleGraphOneWord.findAnagramsAndCompare();
        Assert.assertEquals(expectedOutput, os.toString());
        os.reset();

        InputWordListProcessor littleIterOneWord = new InputWordListProcessor(oneWordInput, littleTrie, true);
        expectedOutput = "\nFor word: " + word1 + "\n\n\n";
        littleIterOneWord.findAnagramsAndCompare();
        Assert.assertEquals(expectedOutput, os.toString());
        os.reset();


//        InputWordListProcessor bigGraphFewWords = new InputWordListProcessor(fewWordInput, bigTrie, true);
//        InputWordListProcessor bigIterFewWords = new InputWordListProcessor(fewWordInput, bigTrie, false);
//        InputWordListProcessor bigGraphManyWords = new InputWordListProcessor(manyWordInput, bigTrie, true);
//        InputWordListProcessor bigIterManyWords = new InputWordListProcessor(manyWordInput, bigTrie, false);
//

//        InputWordListProcessor littleGraphManyWords = new InputWordListProcessor(manyWordInput, littleTrie, true);
//        InputWordListProcessor littleIterManyWords = new InputWordListProcessor(manyWordInput, littleTrie, false);


        os.close();
        System.setOut(null);

    }

    @Test
    public void compareAnagrams() throws Exception {

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        System.setOut(new PrintStream(os));

        List<String> allInDict = new ArrayList<>();
        List<String> noneInDict = new ArrayList<>();
        List<String> someInDict = new ArrayList<>();

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
        String expectedOutput = "stab\nharmony\nsymmetry\nmisty";
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
        expectedOutput = "fog\ncarmine\ncastle";
        Assert.assertEquals(expectedOutput, os.toString().trim());

        os.reset();

        // though some fo these words were in the bigger dictionary, none of them are in the
        // small one
        procSmall.compareAnagrams(someInDict);
        expectedOutput = "";
        Assert.assertEquals(expectedOutput, os.toString().trim());

        os.close();
        System.setOut(null);
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

}
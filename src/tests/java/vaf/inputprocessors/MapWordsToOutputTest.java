package vaf.inputprocessors;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.Map;
import java.util.Set;

import vaf.DictRepConstructor;
import vaf.PathsAndNames;
import vaf.trieversion.Trie;

import static org.junit.Assert.*;
import static vaf.PathsAndNames.FILE_SEP;
import static vaf.PathsAndNames.PATH_TO_ANA_ANS;
import static vaf.PathsAndNames.PATH_TO_B_DICT;
import static vaf.PathsAndNames.PATH_TO_S_DICT;

/**
 * Created by Chloe on 10/13/16.
 */
public class MapWordsToOutputTest {

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

    private Map<String, Set<String>> bigMap;
    private Map<String, Set<String>> littleMap;

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


        bigMap = DictRepConstructor.constructMap(PATH_TO_B_DICT);
        littleMap = DictRepConstructor.constructMap(PATH_TO_S_DICT);

    }

    @Test
    public void findAnagramsAndCompare() throws Exception {

        // File names
        String manyWordsBig = "ManyWordsOutputMap.txt";
        String threeChangeSmall = "SmallDictThreeWordsChangeOutput.txt";

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        PrintStream oldOut = System.out;
        System.setOut(new PrintStream(os));

        // One word, the big dictionary
        MapWordsToOutput bigOneWord = new MapWordsToOutput(oneWordInput, bigMap);
        os.reset();
        String expectedOutput = "\n\nFor word: " + word1 + "\nsang\nsnag\nnags\n\n\n";
        System.out.println(os);
        bigOneWord.findAnagramsAndCompare();
        Assert.assertEquals(expectedOutput, os.toString());
        os.reset();

        // One word, the little dictionary
        MapWordsToOutput littleOneWord = new MapWordsToOutput(oneWordInput, littleMap);
        expectedOutput = "\nFor word: " + word1 + "\n\n\n";
        littleOneWord.findAnagramsAndCompare();
        Assert.assertEquals(expectedOutput, os.toString());
        os.reset();

        // Many words, the big dictionary
        MapWordsToOutput bigManyWords = new MapWordsToOutput(manyWordInput, bigMap);
        String progOutputFileCase3 = PATH_TO_ANA_ANS + FILE_SEP + manyWordsBig;
        BufferedReader expRespReader = new BufferedReader(new FileReader(progOutputFileCase3));
        StringBuilder expOutput = new StringBuilder();
        String line = null;
        while ((line = expRespReader.readLine()) != null){
            expOutput = expOutput.append(line + "\n");
        }
        bigManyWords.findAnagramsAndCompare();
        Assert.assertEquals(expOutput.toString(), os.toString());
        os.reset();

        // A few words, the little dictionary
        MapWordsToOutput littleFewWords = new MapWordsToOutput(fewWordInput, littleMap);
        String progOutputFileCase4 = PATH_TO_ANA_ANS + FILE_SEP + threeChangeSmall;
        expRespReader = new BufferedReader(new FileReader(progOutputFileCase4));
        expOutput = new StringBuilder();
        line = null;
        while ((line = expRespReader.readLine()) != null){
            expOutput = expOutput.append(line + "\n");
        }
        littleFewWords.findAnagramsAndCompare();
        Assert.assertEquals(expOutput.toString(), os.toString());
        os.reset();

        os.close();
        expRespReader.close();
        System.setOut(oldOut);

    }

}
package vaf;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.PrintStream;

/**
 * Created by Chloe on 10/8/16.
 */
public class InteractionHandlerTest {

    private final String CURR_DIR = ".";
    private final String FILE_SEP = System.getProperty("file.separator");

    private final String SRC_DIR = "src";
    private final String MN_DIR = "main";
    private final String TESTS_DIR = "tests";
    private final String REC_DIR = "resources";
    private final String RESPONSES_DIR = "UserResponsesForTests";


    private final String PATH_TO_MN_REC = CURR_DIR + FILE_SEP + SRC_DIR + FILE_SEP + MN_DIR + FILE_SEP + REC_DIR;
    private final String PATH_TO_TEST_REC = CURR_DIR + FILE_SEP + SRC_DIR + FILE_SEP + TESTS_DIR + FILE_SEP + REC_DIR;
    private final String PATH_TO_RESPS = PATH_TO_TEST_REC + FILE_SEP + RESPONSES_DIR;


    private final String S_DICT_NAME = "TestSmallDict.txt";
    private final String B_DICT_NAME = "wordsEn.txt";

    private final String defDict = "IHTADDefault.txt";
    private final String ownDictUsable = "IHTADSmall.txt";
    private final String otherDictNotUsable = "IHTADOther.txt";
    private final String otherDictFailToSucc = "IHTADEventualSuccess.txt";

    private Trie bigTrie;
    private Trie littleTrie;

    // Questions
    private final String OWN_DICT_QU = "Would you like to use your own dictionary file? ";
    private final String CHANGE_QU = "Would you like to use a different anagram finding method? " +
            "Default: graph-based. Other option: iterative. ";

    // Problems
    private final String MISUNDERSTOOD = "Apologies. I didn't understand your response. ";
    private final String OPENING_ISSUE = "I'm have difficulty opening my default dictionary file. ";
    private final String FNFE_ISSUE = "Apologies. The file was not found. Would  you like to try again? ";


    // Instructions
    private final String RESPONSE_INSTR = "Please respond with 'y' or 'n'. ";
    private final String CHANGE_INSTR = "Please response with 'yes' to change or 'no' otherwise. ";
    private final String INPUT_INSTR = "Please enter the word or words you'd like to find anagrams of. ";
    private final String DICT_PATH_INSTR = "Please enter the path to a dictionary file. ";
    private final String QUIT_INSTR = "\nOtherwise, to quit please enter 'Quit!' ";

    @Before
    public void setUp() throws Exception {

        bigTrie = new Trie(PATH_TO_MN_REC +FILE_SEP + B_DICT_NAME);
        littleTrie = new Trie(PATH_TO_TEST_REC + FILE_SEP + S_DICT_NAME);
    }

    @Test
    public void talkAboutDictionary() throws Exception {

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        System.setOut(new PrintStream(os));

        // User does not want to provide their own file
        BufferedReader reader = new BufferedReader(new FileReader(PATH_TO_RESPS + FILE_SEP + defDict));
        InteractionHandler ih = new InteractionHandler(bigTrie, reader);
        ih.talkAboutDictionary();
        String expectedOutput = OWN_DICT_QU + RESPONSE_INSTR + "\n";
        Assert.assertEquals(expectedOutput, os.toString());

        os.reset();

        // User successfully provides their own file
        reader = new BufferedReader(new FileReader(PATH_TO_RESPS + FILE_SEP + ownDictUsable));
        ih = new InteractionHandler(bigTrie, reader);
        ih.talkAboutDictionary();
        expectedOutput = OWN_DICT_QU + RESPONSE_INSTR + "\n" + DICT_PATH_INSTR + "\n";
        Assert.assertEquals(expectedOutput, os.toString());

        os.reset();

        // User tries to provide their own file but fails
        reader = new BufferedReader(new FileReader(PATH_TO_RESPS + FILE_SEP + otherDictNotUsable));
        ih = new InteractionHandler(bigTrie, reader);
        ih.talkAboutDictionary();
        expectedOutput = OWN_DICT_QU + RESPONSE_INSTR + "\n" + DICT_PATH_INSTR + "\n" +
                FNFE_ISSUE + RESPONSE_INSTR + "\n" + DICT_PATH_INSTR + "\n" + FNFE_ISSUE +
                RESPONSE_INSTR + "\n";
        Assert.assertEquals(expectedOutput, os.toString());

        os.reset();

        // User tries to provide their own file, fails at first, then succeeds
        reader = new BufferedReader(new FileReader(PATH_TO_RESPS + FILE_SEP + otherDictFailToSucc));
        ih = new InteractionHandler(bigTrie, reader);
        ih.talkAboutDictionary();
        expectedOutput = OWN_DICT_QU + RESPONSE_INSTR + "\n" + DICT_PATH_INSTR + "\n" +
                FNFE_ISSUE + RESPONSE_INSTR + "\n" + DICT_PATH_INSTR + "\n" + FNFE_ISSUE +
                RESPONSE_INSTR + "\n" + DICT_PATH_INSTR + "\n" + FNFE_ISSUE + RESPONSE_INSTR +
                "\n" + DICT_PATH_INSTR + "\n";
        Assert.assertEquals(expectedOutput, os.toString());

        os.reset();

        os.close();
        System.setOut(null);

    }

    @Test
    public void talkAboutInputWords() throws Exception {

    }

    @Test
    public void talkAboutAnagramFindingStrategy() throws Exception {

    }

}
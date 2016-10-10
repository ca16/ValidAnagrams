package vaf;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.PrintStream;

import static vaf.PathsAndNames.*;

/**
 * Created by Chloe on 10/8/16.
 */
public class InteractionHandlerTest {

    // What the program might say at different points...
    private final String MISUNDERSTOOD = "Apologies. I didn't understand your response. ";
    private final String RESPONSE_INSTR = "Please respond with 'y' or 'n'. ";

    // A phrase the program will use quite often for some tests.
    private String notUnderstood;

    private Trie bigTrie;
    private Trie littleTrie;


    @Before
    public void setUp() throws Exception {

        bigTrie = new Trie(PATH_TO_B_DICT);
        littleTrie = new Trie(PATH_TO_S_DICT);

        notUnderstood = MISUNDERSTOOD + RESPONSE_INSTR + "\n";

    }

    @Test
    public void talkAboutDictionary() throws Exception {

        // What the program should say about dictionaries
        String ownDictQu = "Would you like to use your own dictionary file? ";
        String dictPathInstr = "Please enter the path to a dictionary file. ";
        String fnfeIssue = "Apologies. The file was not found. Would  you like to try again? ";

        // Response file names
        String defDict = "NoFile.txt";
        String ownDictUsable = "IHTADSmall.txt";
        String otherDictNotUsable = "IHTADOther.txt";
        String otherDictFailToSucc = "IHTADEventualSuccess.txt";

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        System.setOut(new PrintStream(os));

        // User does not want to provide their own file
        BufferedReader reader = new BufferedReader(new FileReader(PATH_TO_USER_RESPS + FILE_SEP + defDict));
        InteractionHandler ih = new InteractionHandler(bigTrie, reader);
        ih.talkAboutDictionary();
        String expectedOutput = ownDictQu + RESPONSE_INSTR + "\n";
        Assert.assertEquals(expectedOutput, os.toString());

        os.reset();

        // User successfully provides their own file
        reader = new BufferedReader(new FileReader(PATH_TO_USER_RESPS + FILE_SEP + ownDictUsable));
        ih = new InteractionHandler(bigTrie, reader);
        ih.talkAboutDictionary();
        expectedOutput = ownDictQu + RESPONSE_INSTR + "\n" + dictPathInstr + "\n";
        Assert.assertEquals(expectedOutput, os.toString());

        os.reset();

        // User tries to provide their own file but fails
        reader = new BufferedReader(new FileReader(PATH_TO_USER_RESPS + FILE_SEP + otherDictNotUsable));
        ih = new InteractionHandler(bigTrie, reader);
        ih.talkAboutDictionary();
        expectedOutput = ownDictQu + RESPONSE_INSTR + "\n" + dictPathInstr + "\n" +
                fnfeIssue + RESPONSE_INSTR + "\n" + dictPathInstr + "\n" + fnfeIssue +
                RESPONSE_INSTR + "\n" + notUnderstood;
        Assert.assertEquals(expectedOutput, os.toString());

        os.reset();

        // User tries to provide their own file, fails at first, then succeeds
        reader = new BufferedReader(new FileReader(PATH_TO_USER_RESPS + FILE_SEP + otherDictFailToSucc));
        ih = new InteractionHandler(bigTrie, reader);
        ih.talkAboutDictionary();
        expectedOutput = ownDictQu + RESPONSE_INSTR + "\n" + dictPathInstr + "\n" +
                fnfeIssue + RESPONSE_INSTR + "\n" + notUnderstood + notUnderstood + dictPathInstr
                + "\n" + fnfeIssue + RESPONSE_INSTR + "\n" + dictPathInstr + "\n" + fnfeIssue +
                RESPONSE_INSTR + "\n" + dictPathInstr + "\n";
        Assert.assertEquals(expectedOutput, os.toString());

        os.reset();

        os.close();
        System.setOut(null);

    }

    @Test
    public void talkAboutInputWords() throws Exception {

        String inputInstr = "Please enter the word or words you'd like to find anagrams of. ";
        String quitInstr = "\nOtherwise, to quit please enter 'Quit!' ";
        String changeQu = "Would you like to use a different anagram finding method? " +
                "Default: graph-based. Other option: iterative. For words of 9 characters and " +
                "above it is recommended that you stick to the default. ";
        String changeInstr = "Please respond with 'yes' to change or 'no' otherwise. ";

        // Response file names
        // Input
        String oneWord = "OneWordInput.txt";
        String manyRound = "ManyRoundInput.txt";
        String manyWords = "ManyWordsInput.txt";
        String oneWordChange = "OneWordWithChange.txt";
        
        //Response
        String oneWordResp = "OneWordResponse.txt";
        String manyRoundResp = "ThreeRoundResponse.txt";
        String manyWordsResp = "ManyWordsResponse.txt";
        String oneWordChangeResp = "OneWordChangeResponse.txt";

        // Path to long program response files and program response dir name
        String progRespsDir = "LongExpectedResponses";

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        System.setOut(new PrintStream(os));

        // User enters one word and keeps default strategy
        BufferedReader reader = new BufferedReader(new FileReader(PATH_TO_USER_RESPS + FILE_SEP + oneWord));
        InteractionHandler ih = new InteractionHandler(bigTrie, reader);
        BufferedReader expRespReader = new BufferedReader(new FileReader(PATH_TO_PROG_RESPS +
                FILE_SEP  + oneWordResp));
        String expectedOutput = ""; // user buffer thing
        String line = null;
        while ((line = expRespReader.readLine()) != null){
            expectedOutput = expectedOutput + line + "\n";
        }
        ih.talkAboutInputWords();
        Assert.assertEquals(expectedOutput, os.toString());

        os.reset();

        // User enters one words and changes strategy
        reader = new BufferedReader(new FileReader(PATH_TO_USER_RESPS + FILE_SEP + oneWordChange));
        ih = new InteractionHandler(bigTrie, reader);
        expRespReader = new BufferedReader(new FileReader(PATH_TO_PROG_RESPS + FILE_SEP  + oneWordChangeResp));
        expectedOutput = ""; // user buffer thing
        line = null;
        while ((line = expRespReader.readLine()) != null){
            expectedOutput = expectedOutput + line + "\n";
        }
        ih.talkAboutInputWords();
        Assert.assertEquals(expectedOutput, os.toString());

        os.reset();

        // User enters eight words and keeps the default strategy
        reader = new BufferedReader(new FileReader(PATH_TO_USER_RESPS + FILE_SEP + manyWords));
        ih = new InteractionHandler(bigTrie, reader);
        expRespReader = new BufferedReader(new FileReader(PATH_TO_PROG_RESPS + FILE_SEP  + manyWordsResp));
        expectedOutput = ""; // user buffer thing
        line = null;
        while ((line = expRespReader.readLine()) != null){
            expectedOutput = expectedOutput + line + "\n";
        }
        ih.talkAboutInputWords();
        Assert.assertEquals(expectedOutput, os.toString());

        os.reset();

        // User enters three words, then one word, then four words.
        // Changes strategy for the first two, goes to default for the last. 
        reader = new BufferedReader(new FileReader(PATH_TO_USER_RESPS + FILE_SEP + manyRound));
        ih = new InteractionHandler(bigTrie, reader);
        expRespReader = new BufferedReader(new FileReader(PATH_TO_PROG_RESPS + FILE_SEP  + manyRoundResp));
        expectedOutput = ""; // user buffer thing
        line = null;
        while ((line = expRespReader.readLine()) != null){
            expectedOutput = expectedOutput + line + "\n";
        }
        ih.talkAboutInputWords();
        Assert.assertEquals(expectedOutput, os.toString());

        os.close();
        System.setOut(null);


    }

    @Test
    public void talkAboutAnagramFindingStrategy() throws Exception {

        // What the program should say about changing anagram finding strategy.
        String changeQu = "Would you like to use a different anagram finding method? " +
                "Default: graph-based. Other option: iterative. For words of 9 characters and " +
                "above it is recommended that you stick to the default. ";
        String changeInstr = "Please respond with 'yes' to change or 'no' otherwise. ";

        // Response file names
        String noFile = "NoFile.txt";
        String yesFile = "YesFile.txt";
        String incomprehensible = "Incomprehensible.txt";

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        System.setOut(new PrintStream(os));

        // User wants to change anagram finding strategy
        BufferedReader reader = new BufferedReader(new FileReader(PATH_TO_USER_RESPS + FILE_SEP + yesFile));
        InteractionHandler ih = new InteractionHandler(bigTrie, reader);
        ih.talkAboutAnagramFindingStrategy();
        String expectedOutput = changeQu + changeInstr + "\n";
        Assert.assertEquals(expectedOutput, os.toString());

        os.reset();

        // User does not want to change anagram finding strategy
        reader = new BufferedReader(new FileReader(PATH_TO_USER_RESPS + FILE_SEP + noFile));
        ih = new InteractionHandler(bigTrie, reader);
        ih.talkAboutAnagramFindingStrategy();
        expectedOutput = changeQu + changeInstr + "\n";
        Assert.assertEquals(expectedOutput, os.toString());

        os.reset();


        // User wants to change anagram strategy but isn't very good at typing
        reader = new BufferedReader(new FileReader(PATH_TO_USER_RESPS + FILE_SEP + incomprehensible));
        ih = new InteractionHandler(bigTrie, reader);
        ih.talkAboutAnagramFindingStrategy();
        expectedOutput = changeQu + changeInstr + "\n" + notUnderstood + notUnderstood +
                notUnderstood + notUnderstood + notUnderstood + notUnderstood + notUnderstood +
                notUnderstood + notUnderstood;
        Assert.assertEquals(expectedOutput, os.toString());

        os.close();
        System.setOut(null);


    }

}
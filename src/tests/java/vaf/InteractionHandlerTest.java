package vaf;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.Map;
import java.util.Set;

import vaf.trieversion.Trie;

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
    private Map<String, Set<String>> bigMap;


    @Before
    public void setUp() throws Exception {

        PathsAndNames.populatePaths();

        bigTrie = DictRepConstructor.constructTrie(PATH_TO_B_DICT);
        bigMap = DictRepConstructor.constructMap(PATH_TO_B_DICT);

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
        PrintStream oldOut = System.out;
        System.setOut(new PrintStream(os));

        // Case 1: User does not want to provide their own file
        String pathToFileCase1 = PATH_TO_USER_RESPS + FILE_SEP + defDict;
        String expOutputCase1 = ownDictQu + RESPONSE_INSTR + "\n";
        talkAboutDictionaryTestHelper(pathToFileCase1, expOutputCase1, os);

        // Case 2: User successfully provides their own file
        String pathToFileCase2 = PATH_TO_USER_RESPS + FILE_SEP + ownDictUsable;
        String expOutputCase2 = ownDictQu + RESPONSE_INSTR + "\n" + dictPathInstr + "\n";
        talkAboutDictionaryTestHelper(pathToFileCase2, expOutputCase2, os);

        // Case 3: User tries to provide their own file but fails
        String pathToFileCase3 = PATH_TO_USER_RESPS + FILE_SEP + otherDictNotUsable;
        String expOutputCase3 = ownDictQu + RESPONSE_INSTR + "\n" + dictPathInstr + "\n" +
                fnfeIssue + RESPONSE_INSTR + "\n" + dictPathInstr + "\n" + fnfeIssue +
                RESPONSE_INSTR + "\n" + notUnderstood;
        talkAboutDictionaryTestHelper(pathToFileCase3, expOutputCase3, os);

        // Case 4: User tries to provide their own file, fails at first, then succeeds
        String pathToFileCase4 = PATH_TO_USER_RESPS + FILE_SEP + otherDictFailToSucc;
        String expOutputCase4 = ownDictQu + RESPONSE_INSTR + "\n" + dictPathInstr + "\n" +
                fnfeIssue + RESPONSE_INSTR + "\n" + notUnderstood + notUnderstood + dictPathInstr
                + "\n" + fnfeIssue + RESPONSE_INSTR + "\n" + dictPathInstr + "\n" + fnfeIssue +
                RESPONSE_INSTR + "\n" + dictPathInstr + "\n";
        talkAboutDictionaryTestHelper(pathToFileCase4, expOutputCase4, os);

        os.close();
        System.setOut(oldOut);

    }
    
    private void talkAboutDictionaryTestHelper(String userInputFile, String expectedOutput, ByteArrayOutputStream os) throws Exception{

        // User tries to provide their own file, fails at first, then succeeds
        BufferedReader reader = new BufferedReader(new FileReader(userInputFile));
        InteractionHandler ih = new InteractionHandler(reader);
        ih.talkAboutDictionary();
        Assert.assertEquals(expectedOutput, os.toString());

        os.reset();
        reader.close();
    }

    @Test
    public void talkAboutInputWords() throws Exception {

        // Response file names
        // Trie rep
        // Input 
        String oneWord = "OneWordInput.txt";
        String manyRound = "ManyRoundInput.txt";
        String manyWords = "ManyWordsInput.txt";
        String oneWordChange = "OneWordWithChange.txt";

        //Response
        String oneWordResp = "OneWordResponseTrie.txt";
        String manyRoundResp = "ThreeRoundResponseTrie.txt";
        String manyWordsResp = "ManyWordsResponseTrie.txt";
        String oneWordChangeResp = "OneWordChangeResponseTrie.txt";

        // Map rep
        // Input 
        String oneWordMap = "OneWordInputMap.txt";
        String manyRoundMap = "ManyRoundInputMap.txt";
        String manyWordsMap = "ManyWordsInputMap.txt";
        
        //Response
        String oneWordRespMap = "OneWordResponseMap.txt";
        String manyRoundRespMap = "ThreeRoundResponseMap.txt";
        String manyWordsRespMap = "ManyWordsResponseMap.txt";

        // Path to long program response files and program response dir name
        String progRespsDir = "LongExpectedResponses";

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        PrintStream oldOut = System.out;
        System.setOut(new PrintStream(os));
        
        // Trie cases
        // Case 1: User enters one word and keeps default strategy
        // Here we're using the word 'quit' to ensure that the user can still search
        // for anagrams of this word, even though the specific string 'Quit!' will end the program.
        String userInputCase1 = PATH_TO_USER_RESPS + FILE_SEP + oneWord;
        String progOutputCase1 = PATH_TO_PROG_RESPS + FILE_SEP  + oneWordResp;
        talkAboutInputWordsTrieTestHelper(userInputCase1, progOutputCase1, os);

        // Case 2: User enters one word and changes strategy
        String userInputCase2 = PATH_TO_USER_RESPS + FILE_SEP + oneWordChange;
        String progOutputCase2 = PATH_TO_PROG_RESPS + FILE_SEP  + oneWordChangeResp;
        talkAboutInputWordsTrieTestHelper(userInputCase2, progOutputCase2, os);

        // Case 3: User enters eight words and keeps the default strategy
        String userInputCase3 = PATH_TO_USER_RESPS + FILE_SEP + manyWords;
        String progOutputCase3 = PATH_TO_PROG_RESPS + FILE_SEP  + manyWordsResp;
        talkAboutInputWordsTrieTestHelper(userInputCase3, progOutputCase3, os);

        // Case 4: User enters three words, then one word, then four words.
        // Changes strategy for the first two, goes to default for the last.  
        String userInputCase4 = PATH_TO_USER_RESPS + FILE_SEP + manyRound;
        String progOutputCase4 = PATH_TO_PROG_RESPS + FILE_SEP  + manyRoundResp;
        talkAboutInputWordsTrieTestHelper(userInputCase4, progOutputCase4, os);

        // Map cases
        // Case 5: User enters one word
        // Here we're using the word 'quit' to ensure that the user can still search
        // for anagrams of this word, even though the specific string 'Quit!' will end the program.
        String userInputCase5 = PATH_TO_USER_RESPS + FILE_SEP + oneWordMap;
        String progOutputCase5 = PATH_TO_PROG_RESPS + FILE_SEP  + oneWordRespMap;
        talkAboutInputWordsMapTestHelper(userInputCase5, progOutputCase5, os);

        // Case 6: User enters one word
        String userInputCase6 = PATH_TO_USER_RESPS + FILE_SEP + manyWordsMap;
        String progOutputCase6 = PATH_TO_PROG_RESPS + FILE_SEP  + manyWordsRespMap;
        talkAboutInputWordsMapTestHelper(userInputCase6, progOutputCase6, os);

        // Case 7: User enters eight words
        String userInputCase7 = PATH_TO_USER_RESPS + FILE_SEP + manyRoundMap;
        String progOutputCase7 = PATH_TO_PROG_RESPS + FILE_SEP  + manyRoundRespMap;
        talkAboutInputWordsMapTestHelper(userInputCase7, progOutputCase7, os);
        
        os.close();
        System.setOut(oldOut);
        
    }
    
    
    private void talkAboutInputWordsTrieTestHelper(String userInputFile, String progOutputFile,
                                                   ByteArrayOutputStream os) throws Exception{

        BufferedReader reader = new BufferedReader(new FileReader(userInputFile));
        InteractionHandler ih = new InteractionHandler(reader, bigTrie);
        BufferedReader expRespReader = new BufferedReader(new FileReader(progOutputFile));
        StringBuilder expectedOutput = new StringBuilder();
        String line = null;
        while ((line = expRespReader.readLine()) != null){
            expectedOutput.append(line + "\n");
        }
        ih.talkAboutInputWords();
        Assert.assertEquals(expectedOutput.toString(), os.toString());

        os.reset();
        reader.close();
        expRespReader.close();
        
    }

    private void talkAboutInputWordsMapTestHelper(String userInputFile, String progOutputFile,
                                                   ByteArrayOutputStream os) throws Exception{

        BufferedReader reader = new BufferedReader(new FileReader(userInputFile));
        InteractionHandler ih = new InteractionHandler(reader, bigMap);
        BufferedReader expRespReader = new BufferedReader(new FileReader(progOutputFile));
        StringBuilder expectedOutput = new StringBuilder();
        String line = null;
        while ((line = expRespReader.readLine()) != null){
            expectedOutput.append(line + "\n");
        }
        ih.talkAboutInputWords();
        Assert.assertEquals(expectedOutput.toString(), os.toString());

        os.reset();
        reader.close();
        expRespReader.close();

    }
    
    @Test
    public void talkAboutDictRep() throws Exception {

        // What the program should say about changing anagram finding strategy.
        String changeQu = "Would you like to use a different dictionary representation method? " +
                "Default: trie representation. Other option: map representation. ";
        String changeInstr = "Please respond with 'yes' to change or 'no' otherwise. ";

        // Response file names
        String noFile = "NoFile.txt";
        String yesFile = "YesFile.txt";
        String incomprehensible = "Incomprehensible.txt";

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        PrintStream oldOut = System.out;
        System.setOut(new PrintStream(os));

        // Case 1: User wants to change dictionary represention
        String userInputCase1 = PATH_TO_USER_RESPS + FILE_SEP + yesFile;
        String progOutputCase1 = changeQu + changeInstr + "\n";
        talkAboutDictRepHelper(userInputCase1, progOutputCase1, os);

        // Case 2: User does not want to change dictionary represention
        String userInputCase2 = PATH_TO_USER_RESPS + FILE_SEP + noFile;
        String progOutputCase2 = changeQu + changeInstr + "\n";
        talkAboutDictRepHelper(userInputCase2, progOutputCase2, os);

        // Case 3: User wants to change dictionary represention but isn't very good at typing
        String userInputCase3 = PATH_TO_USER_RESPS + FILE_SEP + incomprehensible;
        String progOutputCase3 = changeQu + changeInstr + "\n" + notUnderstood + notUnderstood +
                notUnderstood + notUnderstood + notUnderstood + notUnderstood + notUnderstood +
                notUnderstood + notUnderstood;
        talkAboutDictRepHelper(userInputCase3, progOutputCase3, os);

        os.close();
        System.setOut(oldOut);
        
    }

    private void talkAboutDictRepHelper(String inputFilePath, String expOutput, ByteArrayOutputStream os) throws Exception{

        BufferedReader reader = new BufferedReader(new FileReader(inputFilePath));
        InteractionHandler ih = new InteractionHandler(reader);
        ih.talkAboutDictRep();
        Assert.assertEquals(expOutput, os.toString());

        os.reset();
        reader.close();

    }

}
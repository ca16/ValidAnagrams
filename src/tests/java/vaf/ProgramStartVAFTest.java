package vaf;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.PrintStream;

import static vaf.PathsAndNames.FILE_SEP;
import static vaf.PathsAndNames.PATH_TO_PROG_RESPS;
import static vaf.PathsAndNames.PATH_TO_USER_RESPS;

/**
 * Created by Chloe on 10/7/16.
 */
public class ProgramStartVAFTest {

    @Before
    public void setUp() throws Exception {

        PathsAndNames.populatePaths();
    }

    @Test
    public void main() throws Exception {
        
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        PrintStream oldOut = System.out;
        System.setOut(new PrintStream(os));

        // Case 1: user uses default dictionary, uses both anagram finding algorithms,
        // messes up replying 'yes' or 'no' occasionally
        
        // Case 2: user uses their own dictionary, uses both anagram finding algorithms,
        // messes up replying 'yes' or 'no' occasionally. Note: many anagrams are missing
        // because the dictionary used is tiny. 

        // Case 3: user does not want to use their own file and quits immediately

        // Case 4: user wants to use their own file but fails to provide a good path
        // eventually the default file is used for a few words
        
        // The tests all have a similar format (the main differences are contained in the files
        // passed to them). Run all the cases.
        for (Integer i = 1; i < 5; i++){
            mainTestHelper(i.toString(), os);
        }
        
        os.close();
        System.setOut(oldOut);


    }
    
    private void mainTestHelper(String testCase, ByteArrayOutputStream os) throws Exception{


        String prefix = "FullRun";
        String userInputSuffix = ".txt";
        String progOutputSuffix = "Responses.txt";
        
        String userInputFile = prefix + testCase + userInputSuffix;
        String progOutputFile = prefix + testCase + progOutputSuffix;

        InputStream oldIn = System.in;
        System.setIn(new FileInputStream(PATH_TO_USER_RESPS + FILE_SEP + userInputFile));
        String[] args = new String[0];
        ProgramStartVAF.main(args);
        BufferedReader expRespReader = new BufferedReader(new FileReader(PATH_TO_PROG_RESPS +
                FILE_SEP  + progOutputFile));
        StringBuilder expectedOutput = new StringBuilder();
        String line = null;
        while ((line = expRespReader.readLine()) != null){
            expectedOutput.append(line + "\n");
        }
        Assert.assertEquals(expectedOutput.toString(), os.toString());
        
        os.reset();
        System.setIn(oldIn);
        expRespReader.close();
    }

}
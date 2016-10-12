package vaf;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

import vaf.trieversion.Trie;
import vaf.inputprocessors.IWordsToOutputHandler;
import vaf.inputprocessors.TrieWordsToOutput;
import vaf.inputprocessors.MapWordsToOutput;

/**
 * Responsible for handling the interactions between program and user.
 * 
 * Created by Chloe on 10/8/16.
 */
public class InteractionHandler {

    // Some possible user responses
    private static final String POS_LONG = "yes";
    private static final String POS_SHORT = "y";
    private static final String NEG_LONG = "no";
    private static final String NEG_SHORT = "n";

    // Questions
    private static final String OWN_DICT_QU = "Would you like to use your own dictionary file? ";
    private static final String ANA_STRAT_CHANGE_QU = "Would you like to use a different anagram finding method? " +
            "Default: graph-based. Other option: iterative. For words of 9 characters and above " +
            "it is recommended that you stick to the default. ";
    private static final String DICT_REP_CHANGE_QU = "Would you like to use a different dictionary representation method? " +
            "Default: trie representation. Other option: map representation. ";

    // Problems
    private static final String MISUNDERSTOOD = "Apologies. I didn't understand your response. ";
    private static final String OPENING_ISSUE = "I'm have difficulty opening my default dictionary file. ";
    private static final String FNFE_ISSUE = "Apologies. The file was not found. Would  you like to try again? ";


    // Instructions
    private static final String RESPONSE_INSTR = "Please respond with 'y' or 'n'. ";
    private static final String CHANGE_INSTR = "Please respond with 'yes' to change or 'no' otherwise. ";
    private static final String INPUT_INSTR = "Please enter the word or words you'd like to find anagrams of. ";
    private static final String DICT_PATH_INSTR = "Please enter the path to a dictionary file. ";
    private static final String QUIT_INSTR = "\nOtherwise, to quit please enter 'Quit!' ";


    private Trie trie;
    private BufferedReader reader;
    private Map<String, Set<String>> map;
    private Boolean defDictRep;

    public InteractionHandler(BufferedReader reader){

        this.reader = reader;
        this.defDictRep = true;
    }
    
    public InteractionHandler(BufferedReader reader, Trie trie){
        this.reader = reader;
        this.defDictRep = true;
        this.trie = trie;
    }
    
    public InteractionHandler(BufferedReader reader, Map<String, Set<String>> map){
        this.reader = reader;
        this.defDictRep = true;
        this.map = map;
    }

    /**
     * Gives the user the option of using their own dictionary file. Populates a trie based on that
     * file or the default dictionary file.
     * @return true if a dictionary file was successfuly processed into a trie, false otherwise
     * @throws IOException if there is a problem with reading the file or with the reader
     */
    public Boolean talkAboutDictionary() throws IOException {
        System.out.println(OWN_DICT_QU + RESPONSE_INSTR);
        Boolean success = askForFile();

        // User has provided a dictionary file
        if (success) {
            return success;
        }

        // User wants to user the default dictionary file or their own dictionary file
        // could not be obtained. Use the default file.
        else {
            return useDefaultOrPrompt();
        }
    }

    /**
     * Populates the trie based on the default dictionary file.
     * @return true if the trie is successfilly populated, false otherwise.
     * @throws IOException if there is a problem with reading the file or with the reader
     */
    private Boolean useDefaultFile() throws IOException{

        // For default file path
        String fileSep = System.getProperty("file.separator");
        String defDict = "wordsEn.txt";
        String currDir = ".";
        String parentDir = "..";

        // Important sub-folders
        String srcDir = "src";
        String mnDir = "main";
        String recDir = "resources";
        String jvaDir = "java";

        String getToMain = "";
        String workingDir = System.getProperty("user.dir");

        // For command line
        if (workingDir.endsWith(fileSep + jvaDir)){
            getToMain = parentDir + fileSep;
        }

        // For Intellij
        else{
            getToMain = currDir + fileSep + srcDir + fileSep + mnDir + fileSep;
        }

        String defaultPath = getToMain + recDir + fileSep + defDict;

        try {
            makeStructure(defaultPath);
        }

        // default dictionary could not be found
        catch(FileNotFoundException fnfe){
            return false;
        }
        // The trie was successfully updated.
        return true;
    }

    /**
     * Attempts to populate the trie based on the default dictionary file. If it fails, it asks
     * the user for a dictionary file.
     * @return true if the trie was successfully populated, false otherwise
     * @throws IOException if there is a problem with reading the file or with the reader
     */
    private Boolean useDefaultOrPrompt() throws IOException{
        Boolean ret = useDefaultFile();
        // default file used successfully
        if (ret){
            return ret;
        }

        // problem with default file, ask user for a file
        else {
            System.out.println(OPENING_ISSUE + OWN_DICT_QU);
            ret = askForFile();
            return ret;
        }
    }

    /**
     * Asks the user to provide a dictionary file and populates the trie based on it.
     * @return true if a dictionary file was successfully processed, false otherwise
     * @throws IOException if there is a problem with reading the file or with the reader
     */
    private Boolean askForFile() throws IOException{
        while(true) {
            String instructions = reader.readLine();

            // The user wants to provide a file
            if (positiveResponse(instructions)) {
                System.out.println(DICT_PATH_INSTR);
                String newFilePath = reader.readLine();
                try {
                    makeStructure(newFilePath);
                }

                // Problems getting the file
                catch(FileNotFoundException fnfe){
                    System.out.println(FNFE_ISSUE + RESPONSE_INSTR);
                    continue;
                }

                // File obtained successfully
                return true;
            }

            // User's response is incomprehensible
            else if (!negativeResponse(instructions)) {
                System.out.println(MISUNDERSTOOD + RESPONSE_INSTR);
            }

            // User does not want to provide a file
            else {
                break;
            }
        }

        // A dictionary file was not successfully obtained
        return false;
    }



    // Non-dictionary related //

    /**
     * Asks the user to input the words for which anagrams will be found. Gathers the data needed
     * to set the anagram-finding in motion (words, trie and which anagram finding method will
     * be used), and sets it in motion.
     * @throws IOException if there is a problem with the reader
     */
    public void talkAboutInputWords() throws IOException{
        while (true){
            System.out.println(INPUT_INSTR + QUIT_INSTR);
            String instructions = reader.readLine();

            // User wants to quit.
            if (quitReponse(instructions)){
                break;
            }

            // User does not want to quit
            IWordsToOutputHandler proc;
            if (defDictRep) {
                Boolean defaultPermMethod = makeDecision(ANA_STRAT_CHANGE_QU);
                proc = new TrieWordsToOutput(instructions, trie, defaultPermMethod);
            }
            else{
                proc = new MapWordsToOutput(instructions, map);
            }
            proc.findAnagramsAndCompare();
        }
    }

    /**
     * In charge of handling some yes or no questions.
     * @param question The question to be asked.
     * @return true if the user responded no, and false otherwise.
     * @throws IOException if there is a problem with the reader
     */
    public Boolean makeDecision(String question) throws IOException{
        Boolean ret = true;
        System.out.println(question + CHANGE_INSTR);
        while (true) {
            String response = reader.readLine();
            if (positiveResponse(response)) {
                ret = false;
                break;
            } else if (!negativeResponse(response)) {
                System.out.println(MISUNDERSTOOD + RESPONSE_INSTR);
            }else{
                break;
            }
        }
        return ret;
    }
    
    public void talkAboutDictRep() throws IOException{
        defDictRep = makeDecision(DICT_REP_CHANGE_QU);
    }

    /**
     * Construct either a trie representation or a map representation of the dictionary file.
     * @param filePath the path to the dictionary file
     * @throws IOException if there is a problem with reading the file
     */
    private void makeStructure(String filePath) throws IOException{
        if (defDictRep){
            this.trie = DictRepConstructor.constructTrie(filePath);
        }
        else{
            this.map = DictRepConstructor.constructMap(filePath);
        }
    }


    // Response parsers//

    /**
     * Figures out if the user replied with some variation of 'yes'.
     * @param response the user's response
     * @return true if the user replied with some variation of 'yes', false otherwise
     */
    private Boolean positiveResponse(String response){
        response = response.toLowerCase().trim();
        return (response.equals(POS_SHORT) || response.equals(POS_LONG));
    }

    /**
     * Figures out if the user replied with some variation of 'no'.
     * @param response the user's response
     * @return true if the user replied with some variation of 'no', false otherwise
     */
    private Boolean negativeResponse(String response){
        response = response.toLowerCase().trim();
        return (response.equals(NEG_SHORT) || response.equals(NEG_LONG));
    }

    /**
     * Figures out if the user wants to quit.
     * @param response the user's response
     * @return true if the user replied with 'Quit!', false otherwise
     */
    private Boolean quitReponse(String response){
        return (response.equals("Quit!"));
    }
}

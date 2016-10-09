package vaf;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * Created by Chloe on 10/8/16.
 */
public class InteractionHandler {

    // For default file path
    private final String FILE_SEPARATOR = System.getProperty("file.separator");
    private final String DEFAULT_DICT_NAME = "wordsEn.txt";
    private final String CURR_DIRECTORY = ".";
    private final String PARENT_DIRECTORY = "..";

    // Some possible user responses
    private final String POS_LONG = "yes";
    private final String POS_SHORT = "y";
    private final String NEG_LONG = "no";
    private final String NEG_SHORT = "n";

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


    private Trie trie;
    private BufferedReader reader;

    public InteractionHandler(Trie trie, BufferedReader reader){

        this.trie = trie;
        this.reader = reader;
    }

    /**
     * Gives the user the option of using their own dictionary file. Populates a trie based on that
     * file or the default dictionary file.
     * @return true if a dictionary file was successfuly processed into a trie, false otherwise
     * @throws IOException if a file is not found
     */
    public Boolean talkAboutDictionary() throws IOException {
        System.out.println(OWN_DICT_QU + RESPONSE_INSTR);
        while(true) {
            String instructions = reader.readLine();

            // The user wants to provide their own dictionary file.
            if (positiveResponse(instructions)) {
                System.out.println(DICT_PATH_INSTR);
                String newFilePath = reader.readLine();
                try {
                    List<String> dictWords = DictProcessor.wordsToList(newFilePath);
                    trie.addWordList(dictWords);
                }
                // Problem getting the user's dictionary file
                catch(FileNotFoundException fnfe) {
                    System.out.println(FNFE_ISSUE + RESPONSE_INSTR);
                    Boolean success = askForFile(reader);

                    // A dictionary file has been successfully obtained
                    if (success) {
                        return success;
                    }

                    // Try to use the default dictionary file.
                    else {
                        return useDefaultOrPrompt(reader);
                    }
                }
                // User's dictionary file obtained successfully
                return true;
            }

            // The user's response is incomprehensible.
            else if (!negativeResponse(instructions)) {
                System.out.println(MISUNDERSTOOD + RESPONSE_INSTR);
            }

            // The user does not want to use their own file.
            else {
                return useDefaultOrPrompt(reader);
            }
        }
    }

    /**
     * Populates the trie based on the default dictionary file.
     * @return true if the trie is successfilly populated, false otherwise.
     */
    private Boolean useDefaultFile(){

        // For Intellij
        String defaultPathV1 = CURR_DIRECTORY + FILE_SEPARATOR + DEFAULT_DICT_NAME;
        // For command line
        String defaultPathV2 = PARENT_DIRECTORY + FILE_SEPARATOR + DEFAULT_DICT_NAME;

        String filePath = defaultPathV1;

        try {
            List<String> dictWords = DictProcessor.wordsToList(filePath);
            trie.addWordList(dictWords);
        }
        catch(IOException ioe){
            filePath = defaultPathV2;
        }
        try {
            List<String> dictWords = DictProcessor.wordsToList(filePath);
            trie.addWordList(dictWords);
        }

        // Neither file could be successfully used
        catch(IOException ioe){
            return false;
        }
        // The trie was successfully updated.
        return true;
    }

    /**
     * Attempts to populate the trie based on the default dictionary file. If it fails, it asks
     * the user for a dictionary file.
     * @param reader a reader for the user's input
     * @return true if the trie was successfully populated, false otherwise
     * @throws IOException if a file is not found
     */
    private Boolean useDefaultOrPrompt(BufferedReader reader) throws IOException{
        Boolean ret = useDefaultFile();
        // default file used successfully
        if (ret){
            return ret;
        }

        // problem with default file, ask user for a file
        else {
            System.out.println(OPENING_ISSUE + OWN_DICT_QU);
            ret = askForFile(reader);
            return ret;
        }
    }

    /**
     * Asks the user to provide a dictionary file and populates the trie based on it.
     * @param reader a reader for the user's input
     * @return true if a dictionary file was successfully processed, false otherwise
     * @throws IOException if a file is not found
     */
    public Boolean askForFile(BufferedReader reader) throws IOException{
        while(true) {
            String instructions = reader.readLine();

            // The user wants to provide a file
            if (positiveResponse(instructions)) {
                System.out.println(DICT_PATH_INSTR);
                String newFilePath = reader.readLine();
                try {
                    List<String> dictWords = DictProcessor.wordsToList(newFilePath);
                    trie.addWordList(dictWords);
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
     * @throws IOException
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
            Boolean defaultPermMethod = talkAboutAnagramFindingStrategy();
            InputWordListProcessor proc = new InputWordListProcessor(instructions, trie, defaultPermMethod);
            proc.findAnagramsAndCompare();
        }
    }

    /**
     * Gives the user the option of changing the anagram finding method. Default option: graph
     * based search. Other option: a more iterative approach. It is recommended to stick to
     * the default option when handling words of about 9+ characters.
     * @return true if the default strategy will be used, false otherwise
     * @throws IOException
     */
    public Boolean talkAboutAnagramFindingStrategy() throws IOException{
        Boolean ret = true;
        System.out.println(CHANGE_QU + CHANGE_INSTR);
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

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by Chloe on 10/7/16.
 */
public class Start {

    private final String DICT_OPT = "Would you like to use your own dictionary file? Please respond with 'yes' or 'no'.";

    public static void main(String[] args){

        String defaultPathV1 = "./wordsEn.txt";
        String defaultPathV2 = "../../../wordsEn.txt";
        String defaultPathV3 = "../wordsEn.txt";
        String filePath = defaultPathV1;
        Boolean needFile = false;

        WordTrie trie = new WordTrie();

        try {
            List<String> dictWords = DictParser.wordsToList(filePath);
            trie.addWordList(dictWords);
        }
        catch(FileNotFoundException fnfe){
            filePath = defaultPathV2;
        }
        try {
            List<String> dictWords = DictParser.wordsToList(filePath);
            trie.addWordList(dictWords);
        }
        catch(FileNotFoundException fnfe){
            filePath = defaultPathV3;
        }
        try {
            List<String> dictWords = DictParser.wordsToList(filePath);
            trie.addWordList(dictWords);
        }
        catch(FileNotFoundException fnfe){
            needFile = true;
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))){
                System.out.println("Would you like to use your own dictionary file? Please respond with 'yes' or 'no'.");
            while(true) {
                String instructions = reader.readLine();
                String fileResponse = instructions.toLowerCase().trim();
                if (fileResponse.equals("yes") || fileResponse.equals("y")) {
                    System.out.println("Please enter the path to your dictionary file.");
                    String newFilePath = reader.readLine();
                    WordTrie newTrie = new WordTrie(newFilePath);
                    trie = newTrie;
                    filePath = newFilePath;
                    break;
                } else if (!fileResponse.equals("no") && !fileResponse.equals("n")) {
                    System.out.println("I'm sorry. I didn't understand your response. Please respond with 'yes' or 'no'.");
                }else {
                    break;
                }
            }
            while (true){
                System.out.println("Please enter the word or words you'd like me to find permutations of." +
                        "\nTo quit please enter 'Quit!'");
                String instructions = reader.readLine();
                if (instructions.equals("Quit!")){
                    break;
                }
                Boolean defaultPermMethod = true;
                System.out.println("Would you like to use a different permutation method? Default: iterative. " +
                        "Other option: graph. Please response with 'yes' to change or 'no' otherwise.");
                while (true) {
                    String response = reader.readLine();
                    response = response.toLowerCase().trim();
                    if (response.equals("yes") || response.equals("y")) {
                        defaultPermMethod = false;
                        break;
                    } else if (!response.equals("no") && !response.equals("n")) {
                        System.out.println("I'm sorry. I didn't understand your response. Please respond with 'yes' or 'no'.");
                    }else{
                        break;
                    }
                }
                WordListProcessor proc = new WordListProcessor(instructions, trie, defaultPermMethod);
                proc.findPermsandCompare();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

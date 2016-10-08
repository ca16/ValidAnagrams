import java.io.BufferedReader;
import java.io.IOException;

import javafx.beans.binding.BooleanExpression;

/**
 * Created by Chloe on 10/8/16.
 */
public class InteractionHandler {

    private WordTrie trie;
    private BufferedReader reader;

    public InteractionHandler(WordTrie trie, BufferedReader reader){
        this.trie = trie;
        this.reader = reader;
    }

    public void talkAboutDictionary() throws IOException {
        System.out.println("Would you like to use your own dictionary file? Please respond with 'yes' or 'no'.");
        while(true) {
            String instructions = reader.readLine();
            String fileResponse = instructions.toLowerCase().trim();
            if (fileResponse.equals("yes") || fileResponse.equals("y")) {
                System.out.println("Please enter the path to your dictionary file.");
                String newFilePath = reader.readLine();
                WordTrie newTrie = new WordTrie(newFilePath);
                trie = newTrie;
                break;
            } else if (!fileResponse.equals("no") && !fileResponse.equals("n")) {
                System.out.println("I'm sorry. I didn't understand your response. Please respond with 'yes' or 'no'.");
            }else {
                break;
            }
        }
    }

    public void talkAboutInputWords() throws IOException{
        while (true){
            System.out.println("Please enter the word or words you'd like to find anagrams of." +
                    "\nOtherwise, to quit please enter 'Quit!'");
            String instructions = reader.readLine();
            if (instructions.equals("Quit!")){
                break;
            }
            Boolean defaultPermMethod = true;
            defaultPermMethod = talkAboutAnagramFindingStrategy(defaultPermMethod);
            WordListProcessor proc = new WordListProcessor(instructions, trie, defaultPermMethod);
            proc.findAnagramsAndCompare();
        }
    }

    public Boolean talkAboutAnagramFindingStrategy(Boolean defaultPermMethod) throws IOException{
        System.out.println("Would you like to use a different anagram making method? Default: iterative. " +
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
        return defaultPermMethod;
    }
}

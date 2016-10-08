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
                } else if (!fileResponse.toLowerCase().equals("no") && !fileResponse.equals("n")) {
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
                String[] words = parseWordList(instructions);
                findPermsandCompare(words, trie);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

//        System.out.println("Please give me one or more words. No special characters!");
//        if (needFile){
//            System.out.println("I've been unable to open my own dictionary file. To proceed you'll have");
//        }


        // do better input checking

//        int len = args.length;
//
//        if (len == 0){
//            throw new RuntimeException("Gimme some arguments please. Like a word to permute. Maybe a file...");
//        }

//        String defaultPathV1 = "./wordsEn.txt";
//        String defaultPathV2 = "../../../wordsEn.txt";
//        String defaultPathV3 = "../wordsEn.txt";

//        List<String> words = new ArrayList<>();
//        int index = 0;
////        String filePath = null;
//
//        if (args[0].equals("--file")){
//            filePath = args[1];
//            index = 2;
//        }
//
//        while(index < len){
//            words.add(args[index]);
//            index++;
//        }
//
//        if (filePath == null){
//            filePath = defaultPathV1;
//        }


//        try {
//            List<String> dictWords = DictParser.wordsToList(filePath);
//            trie.addWordList(dictWords);
//        }
//        catch(FileNotFoundException fnfe){
//            filePath = defaultPathV2;
//        }
//        try {
//            List<String> dictWords = DictParser.wordsToList(filePath);
//            trie.addWordList(dictWords);
//        }
//        catch(FileNotFoundException fnfe){
//            throw new RuntimeException("tough luck");
//        }

//        IPermuter permuter = IPermuter.getIterPermuter(trie);
//        List<String> permutedWords = permuter.permuteListOfWords(words);
////        IPermuter permuter = IPermuter.getGraphPermuter(trie);
////        List<String> permutedWords = permuter.permuteListOfWords(words);
//
//        for (String word: permutedWords){
//            if (trie.contains(word)){
//                System.out.println(word);
//            }
//        }

    }

    static String[] parseWordList(String words){
        Pattern space = Pattern.compile(" ");
        return space.split(words);
    }

    static void findPermsandCompare(String[] words, WordTrie trie){
        List<String> wordList = new ArrayList<>();
        for (int i = 0; i < words.length; i++){
            wordList.add(words[i]);
        }
        IPermuter permuter = IPermuter.getIterPermuter(trie);
        List<String> permutedWords = permuter.permuteListOfWords(wordList);
//        IPermuter permuter = IPermuter.getGraphPermuter(trie);
//        List<String> permutedWords = permuter.permuteListOfWords(words);

        for (String word: permutedWords){
            if (trie.contains(word)){
                System.out.println(word);
            }
        }
    }
}

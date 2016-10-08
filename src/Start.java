import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chloe on 10/7/16.
 */
public class Start {

    public static void main(String[] args){
        // do better input checking

        int len = args.length;

        if (len == 0){
            throw new RuntimeException("Gimme some arguments please. Like a word to permute. Maybe a file...");
        }

        String defaultPathV1 = "./wordsEn.txt";
        String defaultPathV2 = "../../../wordsEn.txt";

        List<String> words = new ArrayList<>();
        int index = 0;
        String filePath = null;

        if (args[0].equals("--file")){
            filePath = args[1];
            index = 2;
        }

        while(index < len){
            words.add(args[index]);
            index++;
        }

        if (filePath == null){
            filePath = defaultPathV1;
        }

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
            throw new RuntimeException("tough luck");
        }

        IPermuter permuter = IPermuter.getIterPermuter(trie);
        List<String> permutedWords = permuter.permuteListOfWords(words);
//        IPermuter permuter = IPermuter.getGraphPermuter(trie);
//        List<String> permutedWords = permuter.permuteListOfWords(words);

        for (String word: permutedWords){
            if (trie.contains(word)){
                System.out.println(word);
            }
        }

    }
}

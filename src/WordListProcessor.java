import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by Chloe on 10/8/16.
 */
public class WordListProcessor {

    private List<String> words;
    private IPermuter permuter;
    private WordTrie trie;

    public WordListProcessor(String words, WordTrie trie, Boolean defaultPermuter){
        this.words = parseWordList(words);
        this.trie = trie;
        if (defaultPermuter){
            permuter = IPermuter.getIterPermuter(trie);
        }
        else {
            permuter = IPermuter.getGraphPermuter(trie);
        }
    }

    void findPermsandCompare(){
        List<String> permutedWords = permuter.permuteListOfWords(words);
        for (String word: permutedWords){
            if (trie.contains(word)){
                System.out.println(word);
            }
        }
    }

    List<String> parseWordList(String words){
        Pattern space = Pattern.compile(" ");
        String[] arrayVersion = space.split(words);
        return arrayToList(arrayVersion);
    }

    List<String> arrayToList(String[] words){
        List<String> ret = new ArrayList<>();
        for (int i = 0; i < words.length; i++){
            ret.add(words[i]);
        }
        return ret;
    }


}

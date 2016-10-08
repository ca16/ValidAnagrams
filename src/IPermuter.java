import java.util.List;

/**
 * Created by Chloe on 10/7/16.
 */
public interface IPermuter {

    List<String> permuteListOfWords(List<String> words);

    List<String> permuteSingleWord(String word);

    static GraphPermuter getGraphPermuter(WordTrie trie){
        return new GraphPermuter(trie);
    }

    static IterPermuter getIterPermuter(WordTrie trie){
        return new IterPermuter(trie);
    }
}

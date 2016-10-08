import java.util.List;

/**
 * Created by Chloe on 10/7/16.
 */
public interface IAnagramMaker {

    List<String> lstOfWordsAM(List<String> words);

    List<String> singleWordAM(String word);

    static GraphAnagramMaker getGraphAM(WordTrie trie){
        return new GraphAnagramMaker(trie);
    }

    static IterAnagramMaker getIterAM(WordTrie trie){
        return new IterAnagramMaker(trie);
    }
}

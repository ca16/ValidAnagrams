import java.util.List;

/**
 * Created by Chloe on 10/7/16.
 */
public interface IPermuter {

    public List<String> permuteListOfWords(List<String> words);

    public static GraphPermuter getGraphPermuter(WordTrie trie){
        return new GraphPermuter(trie);
    }

    public static IterPermuter getIterPermuter(){
        return new IterPermuter();
    }
}

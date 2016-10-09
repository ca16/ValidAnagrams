package vaf.anagrammakers;
import java.util.List;

import vaf.Trie;

/**
 * Created by Chloe on 10/7/16.
 */
public interface IAnagramMaker {

    List<String> lstOfWordsAnagrams(List<String> words);

    List<String> singleWordAnagrams(String word);

    static GraphAnagramMaker getGraphAM(Trie trie){
        return new GraphAnagramMaker(trie);
    }

    static IterAnagramMaker getIterAM(Trie trie){
        return new IterAnagramMaker(trie);
    }

    static String preprocessWord(String word){
        //fix with buffer
        String ret = "";
        word = word.toLowerCase().trim(); //decide whether to do this here to before
        for (int i = 0; i < word.length(); i++){
            char letter = word.charAt(i);
            if (letter >= 'a' && letter <= 'z'){
                ret = ret + word.substring(i, i+1);
            }
        }
        return ret;
    }
}

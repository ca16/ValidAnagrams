import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by Chloe on 10/8/16.
 */
public class WordListProcessor {

    private List<String> words;
    private IAnagramMaker anagramMaker;
    private WordTrie trie;

    public WordListProcessor(String words, WordTrie trie, Boolean defaultAM){
        this.words = parseWordList(words);
        this.trie = trie;
        if (defaultAM){
            anagramMaker = IAnagramMaker.getIterAM(trie);
        }
        else {
            anagramMaker = IAnagramMaker.getGraphAM(trie);
        }
    }

    void findAnagramsAndCompare(){
        for (String word : words){
            System.out.println("\nFor word: " + word);
            List<String> anagrams = anagramMaker.singleWordAM(word);
            for (String anagram : anagrams){
                if (trie.contains(anagram)){
                    System.out.println(anagram);
                }
            }
            System.out.print("\n");
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.CheckedInputStream;

/**
 * Created by Chloe on 10/4/16.
 */
public class IterPermuter implements IPermuter {

    private WordTrie trie;
    private List<Character> vowels;

    public IterPermuter(WordTrie trie){
        this.trie = trie;
        this.vowels = new ArrayList<>();
        vowels.add('a');
        vowels.add('e');
        vowels.add('i');
        vowels.add('o');
        vowels.add('u');
    }

    public List<String> permuteListOfWords(List<String> words){
        List<String> ret = new ArrayList<>();
        for (String word: words){
            ret.addAll(permuteSingleWord(reorder(word)));
//            ret.addAll(singleWordPermutations(word));
        }
        return ret;
    }


    public List<String> permuteSingleWord(String word){
        Integer counter = 0;

        List<String> ret = new ArrayList<>();
        Integer len = word.length();

        if (len == 0){
            return ret;
        }

        String empty = "";
        ret.add(empty);

        for (int i = 0; i < len; i++){
            String addMe = word.substring(i, i+1);
            List<String> addTo = new ArrayList<>();
            for (String w: ret){
                Integer wLen = w.length();
                for (int j = 0; j <= wLen; j++){

                    // If we're inserting the final character, we don't need to construct
                    // permutations where the substring before the insertion is not a
                    // prefix to a word.
                    // E.g. suppose word = "stab" addMe = "a", w = "bts", j = 2
                    // and no words in the dictionary file start with "bt"
                    // No need to add "btas" or "btsa" because no words start with "bt"
                    if ((i == len-1) && (j < wLen) && !trie.isPrefix(w.substring(0, j))){
                        break;
                    }

                    // If there is more than one of a letter, don't add the same word more than
                    // once to account for different arrangements within those letters that are
                    // the same. E.g. given "doom". "mood" should not be added twice even though
                    // one arrangement has the first 'o' first, and the second has it second.
                    if (j < wLen && addMe.equals(w.substring(j, j+1))) {
                        continue;
                    }

                    // No special case if we get to this point, add this permutation of the first
                    // i letters to the list.
                    String toAdd = w.substring(0, j) + addMe + w.substring(j, wLen);
                    counter++;
                    addTo.add(toAdd);
                }
            }
            ret = addTo;
        }

//        System.out.println("counter: " + counter);
//        System.out.println(ret);
        return ret;

    }



    private String reorder(String word){
        char[] arrayVersion = word.toCharArray();
        int len = arrayVersion.length;
        int last = len-1;
        int first = 0;
        while (first < len){
            if (vowels.contains(arrayVersion[first])){
                break;
            }
            first++;
        }
        while (last >= 0){
            if (!vowels.contains(arrayVersion[last])){
                break;
            }
            last--;
        }
        while (first < last){
            char temp = arrayVersion[last];
            arrayVersion[last] = arrayVersion[first];
            arrayVersion[first] = temp;
            while (!vowels.contains(arrayVersion[first])){
                first++;
            }
            while (vowels.contains(arrayVersion[last])){
                last--;
            }
        }

        String reordered = new String(arrayVersion);
        return reordered;

    }




}

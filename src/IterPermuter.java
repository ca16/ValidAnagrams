import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Chloe on 10/4/16.
 */
public class IterPermuter implements IPermuter {


    public static List<String> singleWordPermutations(String word){


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
                    String comp = "";
                    if (j < wLen){
                        comp = w.substring(j, j+1);
                        if (!addMe.equals(comp)){
//                            IterPermuter.insertLetter(addMe, w, j, addTo, counter, trie);
                            String toAdd = w.substring(0, j) + addMe + w.substring(j, wLen);
                            counter++;
                            addTo.add(toAdd);

                        }
                    }
                    else {
//                        IterPermuter.insertLetter(addMe, w, j, addTo, counter, trie);
                        String toAdd = w.substring(0, j) + addMe + w.substring(j, wLen);
                        counter++;
                        addTo.add(toAdd);
                    }
                }
            }
            ret = addTo;
        }
        System.out.println("Counter: " + counter);
        return ret;

    }

    private static void insertLetter(String letter, String word, int index, List<String> addTo, Integer counter, WordTrie trie){
        String toAdd = word.substring(0, index) + letter + word.substring(index, word.length());
        counter++;
        if (trie.isPrefix(toAdd)) {
            addTo.add(toAdd);
        }
    }// doesn't work, next round someone else could shove something in there
    // would only work for final letter. worth it?

    public List<String> permuteListOfWords(List<String> words){
        List<String> ret = new ArrayList<>();
        for (String word: words){
            ret.addAll(singleWordPermutations(word));
        }
        return ret;
    }


}

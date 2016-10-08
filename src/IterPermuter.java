import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Chloe on 10/4/16.
 */
public class IterPermuter implements IPermuter {

    private WordTrie trie;

    public IterPermuter(WordTrie trie){
        this.trie = trie;
    }


    private List<String> singleWordPermutations(String word){


        Integer counter = 0;

        List<String> ret = new ArrayList<>();
        Integer len = word.length();

        if (len == 0){
            return ret;
        }

        String empty = "";
        ret.add(empty);

        for (int i = 0; i < len-1; i++){
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

        String addMe = word.substring(len-1, len);
        List<String> addTo = new ArrayList<>();
        for (String w: ret){
            Integer wLen = w.length();
            for (int j = 0; j <= wLen; j++){
                if ((j < wLen) && !trie.isPrefix(w.substring(0, j))){
                    break;
                }
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


//        System.out.println("Counter: " + counter);
//        System.out.println(ret);
        return ret;

    }

    private void insertLetter(String letter, String word, int index, List<String> addTo, Integer counter, WordTrie trie){
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
//            ret.addAll(singleWordPermutations(reorder(word)));
            ret.addAll(singleWordPermutations(word));
        }
        return ret;
    }

//    private List<String> reorderAll(List<String> words){
//        List<String> ret = new ArrayList<>();
//        for (String word: words){
//            ret.add(reorder(word));
//        }
//        return ret;
//    }

    private String reorder(String word){
        List<Character> vowels = new ArrayList<>();
        vowels.add('a');
        vowels.add('e');
        vowels.add('i');
        vowels.add('o');
        vowels.add('u');
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
        System.out.println("first is" + first);
        while (last >= 0){
            if (!vowels.contains(arrayVersion[last])){
                break;
            }
            last--;
        }
        System.out.println("last is" + first);
        while (first < last){
            char temp = arrayVersion[last];
            arrayVersion[last] = arrayVersion[first];
            arrayVersion[first] = temp;
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
        }
//        System.out.println(arrayVersion);
//        Character.
        String reordered = new String(arrayVersion);
//        System.out.println("reordered" + reordered);
        return reordered;

    }




}

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chloe on 10/4/16.
 */
public class Permuter {


    public static List<String> singleWordPermutations(String word){

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
                            String toAdd = w.substring(0, j) + addMe + w.substring(j, wLen);
                            addTo.add(toAdd);
                        }
                    }
                    else {
                        String toAdd = w.substring(0, j) + addMe + w.substring(j, wLen);
                        addTo.add(toAdd);
                    }
                }
            }
            ret = addTo;
        }

        return ret;

    }

    public static List<String> permutationsWordList(List<String> words){
        List<String> ret = new ArrayList<>();
        for (String word: words){
            ret.addAll(singleWordPermutations(word));
        }
        return ret;
    }
}

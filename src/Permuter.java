import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Chloe on 10/4/16.
 */
public class Permuter {


    public static List<String> singleWordPermutations(String word, WordTrie trie){


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
//                            Permuter.insertLetter(addMe, w, j, addTo, counter, trie);
                            String toAdd = w.substring(0, j) + addMe + w.substring(j, wLen);
                            counter++;
                            addTo.add(toAdd);

                        }
                    }
                    else {
//                        Permuter.insertLetter(addMe, w, j, addTo, counter, trie);
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

    public static List<String> permutationsWordList(List<String> words, WordTrie trie){
        List<String> ret = new ArrayList<>();
        for (String word: words){
            ret.addAll(singleWordPermutations(word, trie));
        }
        return ret;
    }

    public static List<Integer>[] makeGraph(String word){
        Integer len = word.length();
        List<Integer>[] ret = new List[len];
        int counter = 0;
        while (counter < len) {
            List<Integer> destinations = new ArrayList<>();
            for (int i = 0; i < len; i++) {
                if (counter != i){
                    destinations.add(i);
                }
                ret[counter] = destinations;
            }
            counter++;
        }
        return ret;
    }

    public static List<String> pathsToWords(List<List<Integer>> paths, String word){
        List<String> words = new ArrayList<>();
        for (List<Integer> path : paths){
            // fix buffer thing
            String toAdd = "";
            for (Integer node : path){
                String letter = word.substring(node, node+1);
                toAdd = toAdd + letter;
            }
            if (!words.contains(toAdd)) {
                words.add(toAdd);
            }
        }
        return words;

    }

    public static List<List<Integer>> dfsAll(List<Integer>[] graph, String word, WordTrie trie){
        List<List<Integer>> ret = new ArrayList<>();
        for (int i = 0; i < graph.length; i++){
            ret.addAll(dfs(graph, i, word, trie));
        }
        return ret;
    }

    public static List<List<Integer>> dfs(List<Integer>[] graph, Integer node, String word, WordTrie trie){
        List<List<Integer>> pathList = new ArrayList<>();
        List<Integer> path = new ArrayList<>();
        path.add(node);
        for (Integer succ : graph[node]){
            List<Integer> pathCopy = new ArrayList<>();
            pathCopy.addAll(path);
            dfsHelper(graph, succ, pathCopy, pathList, word, trie);
        }

        return pathList;

    }


    public static void dfsHelper(List<Integer>[] graph, Integer node, List<Integer> path, List<List<Integer>> pathList, String word, WordTrie trie){
        path.add(node);
        if (path.size() == graph.length){
            pathList.add(path);
            return;
        }
        if (!trie.isPrefix(pathToWord(path, word))){
            return;
        }
        for (Integer succ : graph[node]){
            if (!path.contains(succ)){
                List<Integer> pathCopy = new ArrayList<>();
                pathCopy.addAll(path);
                dfsHelper(graph, succ, pathCopy, pathList, word, trie);
            }
        }

    }

    public static List<String> dfsPermutations(String word, WordTrie trie){
        System.out.println("0");
        List<Integer>[] graph = makeGraph(word);
        System.out.println("1");
        List<List<Integer>> paths = dfsAll(graph, word, trie);
        System.out.println("2");
        List<String> perms = pathsToWords(paths, word);
        System.out.println("3");
        System.out.println(perms);
        return perms;
    }

    public static List<String> dfsPermsList(List<String> words, WordTrie trie){
        List<String> ret = new ArrayList<>();
        for (String word : words){
            List<String> perms = dfsPermutations(word, trie);
            for (String perm : perms){
                if (!ret.contains(perm)){
                    ret.add(perm);
                }
            }
        }
        return ret;
    }

    private static String pathToWord(List<Integer> path, String word){
        String ret = ""; //fix
        for (Integer node : path){
            ret = ret + word.substring(node, node+1);
        }
        return ret;
    }
}

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chloe on 10/7/16.
 */
public class GraphPermuter implements IPermuter {

    private WordTrie trie;

    private List<Integer>[] currGraph;
    private String currWord;

    public GraphPermuter(WordTrie trie){
        this.trie = trie;
    }

    public List<String> permuteListOfWords(List<String> words){
        List<String> ret = new ArrayList<>();
        for (String word : words){
            List<String> perms = permuteSingleWord(word);
            for (String perm : perms){
                if (!ret.contains(perm)){
                    ret.add(perm);
                }
            }
        }
//        System.out.println(ret);
        return ret;
    }

    public List<String> permuteSingleWord(String word){
        this.currWord = word;
        makeGraph(currWord);
        List<List<Integer>> paths = dfsAll();
        List<String> perms = pathsToWords(paths);
        return perms;
    }

    private List<Integer>[] makeGraph(String word){
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
        this.currGraph = ret;
        return ret;
    }



    private List<List<Integer>> dfsAll(){
        List<List<Integer>> ret = new ArrayList<>();
        for (int i = 0; i < currGraph.length; i++){
            ret.addAll(dfs(i));
        }
        return ret;
    }

    private List<List<Integer>> dfs(Integer node){
        List<List<Integer>> pathList = new ArrayList<>();
        List<Integer> path = new ArrayList<>();
        path.add(node);
        for (Integer succ : currGraph[node]){
            List<Integer> pathCopy = new ArrayList<>();
            pathCopy.addAll(path);
            dfsHelper(succ, pathCopy, pathList);
        }

        return pathList;

    }


    private void dfsHelper(Integer node, List<Integer> path, List<List<Integer>> pathList){
        path.add(node);
        if (path.size() == currGraph.length){
            pathList.add(path);
            return;
        }
        if (!trie.isPrefix(pathToWord(path))){
            return;
        }
        for (Integer succ : currGraph[node]){
            if (!path.contains(succ)){
                List<Integer> pathCopy = new ArrayList<>();
                pathCopy.addAll(path);
                dfsHelper(succ, pathCopy, pathList);
            }
        }

    }


    private List<String> pathsToWords(List<List<Integer>> paths){
        List<String> words = new ArrayList<>();
        for (List<Integer> path : paths){
            // fix buffer thing
            String toAdd = "";
            for (Integer node : path){
                String letter = currWord.substring(node, node+1);
                toAdd = toAdd + letter;
            }
            if (!words.contains(toAdd)) {
                words.add(toAdd);
            }
        }
        return words;

    }


    private String pathToWord(List<Integer> path){
        String ret = ""; //fix
        for (Integer node : path){
            ret = ret + currWord.substring(node, node+1);
        }
        return ret;
    }
}

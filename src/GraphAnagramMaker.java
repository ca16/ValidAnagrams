import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chloe on 10/7/16.
 */
public class GraphAnagramMaker implements IAnagramMaker {

    private WordTrie trie;

    private List<List<Integer>> currGraph;
    private String currWord;

    public GraphAnagramMaker(WordTrie trie){
        this.trie = trie;
    }

    public List<String> lstOfWordsAM(List<String> words){
        List<String> ret = new ArrayList<>();
        for (String word : words){
            List<String> anagrams = singleWordAM(word);
            for (String anagram : anagrams){
                if (!ret.contains(anagram)){
                    ret.add(anagram);
                }
            }
        }
        return ret;
    }

    public List<String> singleWordAM(String word){
        word = IAnagramMaker.preprocessWord(word);
        this.currWord = word;
        makeGraph();
        List<List<Integer>> paths = dfsAll();
        List<String> anagrams = pathsToWords(paths);
        return anagrams;
    }

    private List<List<Integer>> makeGraph(){
        Integer len = currWord.length();
        List<List<Integer>> ret = new ArrayList<>();
        int counter = 0;
        while (counter < len) {
            List<Integer> destinations = new ArrayList<>();
            for (int i = 0; i < len; i++) {
                if (counter != i){
                    destinations.add(i);
                }
            }
            ret.add(counter, destinations);
            counter++;
        }
        this.currGraph = ret;
        return ret;
    }



    private List<List<Integer>> dfsAll(){
        List<List<Integer>> ret = new ArrayList<>();
        int len = currGraph.size();

        // No search needed if we only have one letter.
        if (len == 1){
            List<Integer> single = new ArrayList<>();
            single.add(0);
            ret.add(single);
            return ret;
        }

        // More than one letter in the word - search.
        for (int i = 0; i < len; i++){
            ret.addAll(dfs(i));
        }
        return ret;
    }

    private List<List<Integer>> dfs(Integer node){
        List<List<Integer>> pathList = new ArrayList<>();
        List<Integer> path = new ArrayList<>();
        path.add(node);
        for (Integer succ : currGraph.get(node)){
            List<Integer> pathCopy = new ArrayList<>();
            pathCopy.addAll(path);
            dfsHelper(succ, pathCopy, pathList);
        }

        return pathList;

    }


    private void dfsHelper(Integer node, List<Integer> path, List<List<Integer>> pathList){
        path.add(node);
        if (path.size() == currGraph.size()){
            pathList.add(path);
            return;
        }
        if (!trie.isPrefix(pathToWord(path))){
            return;
        }
        for (Integer succ : currGraph.get(node)){
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
            String word = pathToWord(path);
            if (!words.contains(word)) {
                words.add(word);
            }
        }
        return words;

    }


    private String pathToWord(List<Integer> path){
        String ret = ""; //fix
        // fix buffer thing
        for (Integer node : path){
            ret = ret + currWord.substring(node, node+1);
        }
        return ret;
    }
}

package vaf.anagrammakers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import vaf.InputWordListProcessor;
import vaf.Trie;

/**
 * A kind of anagram finder that finds anagrams using a graph-based search.
 * 
 * Created by Chloe on 10/7/16.
 */
public class GraphAnagramMaker implements IAnagramMaker {

    private Trie trie;
    private List<List<Integer>> currGraph;
    private String currWord;
    private Set<String> pathsSeen;

    /**
     * Initialize the anagram maker.
     * @param trie the trie the anagram make will use to find valid anagrams.
     */
    public GraphAnagramMaker(Trie trie){
        this.trie = trie;
    }

    /**
     * Finds anagrams for many words.
     * @param words a list of words
     * @return a list of anagrams of those words.
     */
    public List<String> lstOfWordsAnagrams(List<String> words){
        List<String> ret = new ArrayList<>();
        for (String word : words){
            Set<String> anagrams = singleWordAnagrams(word);
            ret.addAll(anagrams);
        }
        return ret;
    }

    /**
     * Finds anagrams for one word.
     * @param word a word
     * @return a list of anagrams for that word.
     */
    public Set<String> singleWordAnagrams(String word){
        word = InputWordListProcessor.preprocessWord(word);
        this.currWord = word;
        makeGraph();
        pathsSeen = new HashSet<>();
        List<List<Integer>> paths = anagramDfsAll();
        Set<String> anagrams = pathsToWords(paths);
        return anagrams;
    }

    /**
     * Constructs a graph from the current word. Letters are nodes. Edges exist between all
     * pairs of letters. Return the graph.
     * Note: each letter is represented as a node based on its position in the current word. E.g.
     * if "fog" is the current word, 'f' is represented by 0, 'o' as 1, 'g' as 2.
     */
    private void makeGraph(){
        Integer len = currWord.length();
        List<List<Integer>> graph = new ArrayList<>();
        int counter = 0;

        // Go through letters
        while (counter < len) {
            List<Integer> destinations = new ArrayList<>();

            // for each other letter, add an edge between it and the letter we're looking at
            for (int i = 0; i < len; i++) {
                if (counter != i){
                    destinations.add(i);
                }
            }

            // add all a letter's edges to the graph
            graph.add(counter, destinations);
            counter++;
        }
        this.currGraph = graph;
    }

    /**
     * Runs an altered version of depth first search starting from each node to find possible
     * anagrams of the current word.
     * @return A list of paths that correspond to possible anagrams. Note: as letters are
     * represented in the graph by their position in the word, paths are a list of integers.
     */
    private List<List<Integer>> anagramDfsAll(){
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
            ret.addAll(anagramDfs(i));
        }
        return ret;
    }

    /**
     * Runs a version of depth first search from the given node to find the possible anagrams
     * of the current word that start at the letter corresponding to this node.
     * @param node the node we're starting from
     * @return the possible anagrams that start from the letter corresponding to this node
     */
    private List<List<Integer>> anagramDfs(Integer node){

        // List a paths
        List<List<Integer>> pathList = new ArrayList<>();

        // New path starting at this node
        List<Integer> path = new ArrayList<>();
        path.add(node);
        pathsSeen.add(pathToWord(path));

        // Continue the search at each of the node's neighbors using a copy of the path so far
        // (containing only the starting node) and anagramDfsHelper.
        for (Integer succ : currGraph.get(node)){
            List<Integer> pathCopy = new ArrayList<>();
            pathCopy.addAll(path);
            Trie.TrieNode self = trie.hasChildWithLetter(nodeToLetter(node), trie.getRoot());
            if (null != self) {
                anagramDfsHelper(succ, pathCopy, pathList, self);
            }
        }
        
        return pathList;
    }


    /**
     * Does most of the work for finding anagrams. Every time it is called, it takes the search
     * one step further (i.e. possible substrings of 3 characters to substrings of 4). It adds
     * the anagrams it finds of the desired length to the list of paths (which correspond to words)
     * its given.
     * @param node the node we're about to explore
     * @param path the path to that node
     * @param pathList the list of all paths of required length found so far
     */
    private void anagramDfsHelper(Integer node, List<Integer> path, List<List<Integer>> pathList, Trie.TrieNode parent){

        // add the node we're exploring to the path
        path.add(node);
        if (pathsSeen.contains(pathToWord(path))){
            return;
        }
        else {
            pathsSeen.add(pathToWord(path));
        }

        // the path is the required size, add it to the list of paths
        // we're done with this node/path pair
        if (path.size() == currGraph.size()){
            pathList.add(path);
            return;
        }

        // the substring corresponding to the path so far is not the prefix of a word in the trie
        // there is no point in continuing to search for paths that start with the current path
        // as they will not result in words in the dictionary. Return.
        Trie.TrieNode self = trie.hasChildWithLetter(nodeToLetter(node), parent);
        if (null == self){
            return;
        }

        // pass the search on to the node's neighbors, but only if those aren't already contained
        // in the path used to get this far (otherwise we'd be using letters more often than they
        // appear in the original word). Make sure each neighbor gets its own copy of the path.
        // E.g. if our path so far is 'ch' and we want to explore neighbors 'e' and 'a', we want to
        // get two new paths 'che' and 'cha', rather than something like 'chea', or 'chae'.
        for (Integer succ : currGraph.get(node)){
            if (!path.contains(succ)){
                List<Integer> pathCopy = new ArrayList<>();
                pathCopy.addAll(path);
                anagramDfsHelper(succ, pathCopy, pathList, self);
            }
        }

    }

    /**
     * Converts paths (list of integers) to the corresponding words, using the current word as
     * a map between the two.
     * @param paths the paths we want to convert to words
     * @return a list of words corresponding to the paths given. No duplicates.
     */
    private Set<String> pathsToWords(List<List<Integer>> paths){
        Set<String> words = new HashSet<>();
        for (List<Integer> path : paths){
            String word = pathToWord(path);
            words.add(word);
        }
        return words;

    }

    /**
     * Converts a single path to a word using the current word as a map.
     * @param path the path we want to convert to a word
     * @return the word corresponding to the path given
     */
    private String pathToWord(List<Integer> path){
        StringBuilder builder = new StringBuilder();
        for (Integer node : path){
            builder.append(currWord.substring(node, node+1));
        }
        return builder.toString();
    }

    private Character nodeToLetter(Integer node){
        return currWord.charAt(node);
    }
}

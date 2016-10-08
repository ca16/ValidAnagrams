package Trie;

import java.io.FileNotFoundException;
import java.util.List;

import Trie.ITrieNode;

/**
 * Created by Chloe on 10/8/16.
 */
public class TrieRoot  extends TrieNode implements ITrieNode {

    public TrieRoot() {
        super(' ');
        // no special characters
    }

    public TrieRoot(String filePath){
        super(' ');
        try {
            List<String> dictWords = DictParser.wordsToList(filePath);
            this.addWordList(dictWords);
        }catch (FileNotFoundException fnfe){
            System.out.println("Words could not be added to the trie because the dictionary file " +
                    "could not be found");
        }
    }

    public void addToTrie(String word){
        if (null == word || word == "" || word.length() < 1){
            return; // throw exception here
        }
        word = word.toLowerCase();
        super.addWord(word);

    }

    public Boolean contains(String word){
        if (null == word || word == "" || word.length() < 1){
            return false;
        }
        word = word.toLowerCase();
        return super.containsWord(word);

    }

    public Boolean isPrefix(String word){
        if (null == word){
            return false;
        }
        if (word == "" || word.length() < 1){
            return true;
        }
        word = word.toLowerCase();
        return super.isPrefix(word);

    }

    public void addWordList(List<String> words){
        for (String word: words){
            this.addToTrie(word);
        }
    }

//    @Override
//    public String toString() {
//        return "WordTrie{" +
//                "root=" + root +
//                '}';
//    }
//
//    private class TrieNode {
//
//        private char letter;
//        private Boolean endOfWord;
//        private Boolean prefix;
//        private WordTrie.TrieNode[] children;
//
//        private TrieNode(char letter) {
//            this.letter = letter;
//            endOfWord = false;
//            prefix = false;
//            children = new WordTrie.TrieNode[26];
//
//
//        }
}

import java.io.FileNotFoundException;
import java.util.List;

/**
 * Created by Chloe on 10/7/16.
 */
public class WordTrie {

    private TrieNode root;
    private String filePath;

    public WordTrie() {
        trieSetup(); // can I just start with the root? figure out later
        // no special characters
    }
    public WordTrie(String filePath){
        trieSetup();
        this.filePath = filePath;
        try {
            List<String> dictWords = DictParser.wordsToList(filePath);
            this.addWordList(dictWords);
        }catch (FileNotFoundException fnfe){
            System.out.println("Words could not be added to the trie because the dictionary file " +
                    "could not be found");
        }
    }

    private void trieSetup() {
        this.root = new TrieNode(' ');
        char toAdd = 'a';
        for (int i = 0; i < 26; i++) {
            TrieNode child = new TrieNode(toAdd);
            this.root.addToChildren(child, i);
            // Assuming we're working with English.
            if (i == 0 || i == 8) {
                child.setEndOfWord(true);
            }
            // words start from all letters in English.
            child.setPrefix(true);
            child.setParent(root);
            toAdd++;
        }
    }

    public void addToTrie(String word){
        if (null == word || word == "" || word.length() < 1){
            return; // throw exception here
        }
//        if (word.length() < 1){
//            return;
//        }
        word = word.toLowerCase();
        char first = word.charAt(0);
        if (first < 'a' || first > 'z'){
            return; // throw exception here
        }
        String rest = word.substring(1, word.length());
        this.root.getChildWithLetter(first).addWord(rest);

    }

    public Boolean contains(String word){
        if (null == word || word == "" || word.length() < 1){
            return false;
        }
        word = word.toLowerCase();
        char first = word.charAt(0);
        if (first < 'a' || first > 'z'){
            return false; // throw exception here
        }
        String rest = word.substring(1, word.length());
        return this.root.getChildWithLetter(first).containsWord(rest);

    }

    public Boolean isPrefix(String word){
        if (null == word){
            return false;
        }
        if (word == "" || word.length() < 1){
            return true;
        }
        word = word.toLowerCase();
        char first = word.charAt(0);
        if (first < 'a' || first > 'z'){
            return false; // throw exception here
        }
        String rest = word.substring(1, word.length());
        return this.root.getChildWithLetter(first).isPrefix(rest);

    }

    public void addWordList(List<String> words){
        for (String word: words){
            this.addToTrie(word);
        }
    }

    @Override
    public String toString() {
        return "WordTrie{" +
                "root=" + root +
                '}';
    }
}

package vaf;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * Responsible for keeping track of a set of words.
 * In this case, it keeps track of the words in a dictionary file. Stores words in lower-case, only
 * uses alphabetic characters (a-z).
 * Created by Chloe on 10/7/16.
 */
public class Trie {

    private TrieNode root;

    /**
     * Constructs the root node.
     */
    public Trie() {
        this.root = new TrieNode(' ');
    }

    /**
     * Constructs the root node and populates the trie with words from the given file if possible.
     * @param filePath the path to a file containing the words to be added
     *                 it is expected that there will be one words per line
     */
    public Trie(String filePath){
        this.root = new TrieNode(' ');
        try {
            List<String> dictWords = DictProcessor.wordsToList(filePath);
            this.addWordList(dictWords);
        }catch (IOException ioe){
            System.out.println("Words could not be added to the trie because the dictionary file " +
                    "could not be found");
        }
    }

    /**
     * Adds the given word to the trie if possible (doesn't accept words containing characters other
     * than A-Z, a-z)
     * @param word the word to be added to the trie.
     */
    public void addToTrie(String word){

        // we don't add the empty string or null
        if (null == word || word.equals("")){
            return;
        }
        word = word.toLowerCase();
        this.root.addWord(word);

    }

    /**
     * Checks if the trie contains the given word.
     * @param word the word we're looking for
     * @return true if the trie contains the word, false otherwise
     */
    public Boolean contains(String word){

        // the trie doesn't contain the empty string or null
        // and doesn't want to look for them.
        if (null == word || word.equals("")){
            return false;
        }
        word = word.toLowerCase();
        return this.root.containsWord(word);

    }

    /**
     * Checks if the given string is the prefix of a word in the trie.
     * @param str the string we're interested in
     * @return true if the string is the prefix of a word in the trie, false otherwise.
     */
    public Boolean isPrefix(String str){

        // null is not the prefix of any word
        if (null == str){
            return false;
        }

        // the empty string is the prefix of all words
        if (str.equals("")){
            return true;
        }
        str = str.toLowerCase();
        return this.root.isPrefix(str);

    }

    /**
     * Adds all the words in a list to the trie
     * @param words the words to be added to the trie
     */
    public void addWordList(List<String> words){
        for (String word: words){
            this.addToTrie(word);
        }
    }

    @Override
    public String toString() {
        return "vaf.Trie{" +
                "root=" + root +
                '}';
    }

    /**
     * Represents a node in a trie.
     */
    private class TrieNode {

        // The letter the node corresponds to
        private char letter;

        // True if the string ending at this node is a word, false otherwise
        private Boolean endOfWord;

        // True if the string ending at this node is the prefix of another word in the trie,
        // false otherwise
        private Boolean prefix;

        // The letters that may follow the string ending at the current node to form words.
        // A position in the array holds null if the letter at that position cannot be added
        // the string ending at the current node to form a word.
        private TrieNode[] children;

        /**
         * Construct a trie node.
         * @param letter the letter the node corrresponds to.
         */
        private TrieNode(char letter) {
            this.letter = letter;
            endOfWord = false;
            prefix = false;
            children = new TrieNode[26];
        }

        /**
         * Returns the child of the current node with the letter given.
         * @param let the letter associated with the child we're looking for
         * @return the child associated with the given letter if it exists, null otherwise
         */
        private TrieNode getChildWithLetter(char let) {
            return children[let - 'a'];
        }

        /**
         * Returns true if the sub-trie rooted at the current node contains the given word
         * @param word the word we're looking for
         * @return true if the current usb-trie contains the word, false otherwise
         */
        private Boolean containsWord(String word) {
            // The current node's letter is the last letter. If the current node also marks the
            // end of a word, the word is contained in the trie.
            if (word.length() == 0 && this.endOfWord) {
                return true;
            }
            // This is the last letter in the word and the node does not mark the end of the word
            // so the word is not contained in the trie.
            if (word.length() == 0) {
                return false;
            }

            // There are still letters left in the other word, but the collection of letters so far
            // is not a prefix to any word, so the given word is not contained.
            if (!this.prefix) {
                return false;
            }

            // The word may yet be in the trie, keep looking for it
            char first = word.charAt(0);

            // we only have lower case alphabetic characters in the trie
            if (first < 'a' || first > 'z') {
                return false;
            }
            String rest = word.substring(1, word.length());
            TrieNode next = getChildWithLetter(first);
            if (next != null) {
                return next.containsWord(rest);
            }
            // The next letter in the word is not a child of the current node, so the given word
            // is not in the trie
            else {
                return false;
            }

        }

        /**
         * Figures out if the given string is the prefix of a word in the sub-trie rooted at
         * this node.
         * @param str the string we're interested in
         * @return true if the string is the prefix of a word in the trie, false otherwise.
         */
        private Boolean isPrefix(String str) {
            // The letter in this nofr is the last letter in the potential prefix.
            if (str.length() == 0) {
                return this.prefix;
            }

            // If the first part of the potential prefix is not the prefix of a valid word,
            // the whole string is not the prefix of a valid word.
            if (!this.prefix) {
                return false;
            }
            char first = str.charAt(0);
            // we only have lower case alphabetic characters in the trie
            if (first < 'a' || first > 'z') {
                return false;
            }
            String rest = str.substring(1, str.length());
            TrieNode next = getChildWithLetter(first);
            if (next != null) {
                return next.isPrefix(rest);
            }
            // The next letter in the word is not a child of the
            // current node, so the given word is not in the trie at all
            // so it cannot be a prefix
            else {
                return false;
            }

        }

        /**
         * Adds the given word to the sub-trie rooted at the current node
         * @param word the word we want to add
         */
        private void addWord(String word) {
            // We've added all the letters, mark this node (holding the last letter)
            // as the end of a word
            if (word.length() == 0) {
                this.endOfWord = true;
                return;
            }

            // There are more letters to add
            char first = word.charAt(0);
            // can't add a word with special characters in it
            if (first < 'a' || first > 'z') {
                throw new RuntimeException("Attempted to add a word with " + first + " in it. " +
                        "No non-alphabetic characters, please.");
            }
            String rest = word.substring(1, word.length());
            TrieNode next = getChildWithLetter(first);
            this.prefix = true;

            // The next letter is not already a child, so add it to the current node's children
            if (null == next) {
                next = new TrieNode(first);
                this.children[first - 'a'] = next;
            }

            // Add the rest of the word to the trie
            next.addWord(rest);

        }


        @Override
        public String toString() {
            return "Trie.TrieNode{" +
                    "letter=" + letter +
                    ", endOfWord=" + endOfWord +
                    ", prefix=" + prefix +
                    ", children=\n" + this.pprintChildren() +
                    '}';
        }

        private String pprintChildren() {
            // fix this
            String ret = "";
            for (int i = 0; i < 26; i++) {
                TrieNode child = this.children[i];
                if (child != null) {
                    ret = ret + "\n    " + this.children[i].toString();
                }
            }
            return ret;
        }
    }
}

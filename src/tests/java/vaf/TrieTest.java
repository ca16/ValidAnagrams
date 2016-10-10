package vaf;

import org.junit.Assert;
import org.junit.Test;

import static vaf.PathsAndNames.*;
/**
 * Created by Chloe on 10/7/16.
 */
public class TrieTest {

    private Trie babyTree;
    private Trie smallTree;
    private Trie biggerTree;
    
    @org.junit.Before
    public void setUp() throws Exception {

        babyTree = new Trie();
        smallTree = new Trie();
        biggerTree = new Trie();

        smallTree.addToTrie("goblet");
        smallTree.addToTrie("of");
        smallTree.addToTrie("fire");
        
        biggerTree = new Trie(PATH_TO_B_DICT);
        
    }

    @org.junit.Test
    public void addToTrie() throws Exception {

        Assert.assertFalse(smallTree.contains("z"));
        Assert.assertFalse(smallTree.contains("canary"));
        Assert.assertFalse(smallTree.contains("at"));
        Assert.assertFalse(smallTree.contains("barn"));
        Assert.assertFalse(smallTree.contains("symbol"));

        smallTree.addToTrie("at");
        smallTree.addToTrie("barn");
        smallTree.addToTrie("symbol");

        Assert.assertFalse(smallTree.contains("z"));
        Assert.assertFalse(smallTree.contains("canary"));
        Assert.assertTrue(smallTree.contains("at"));
        Assert.assertTrue(smallTree.contains("barn"));
        Assert.assertTrue(smallTree.contains("symbol"));
        
        // All letters are turned to lower case letters when a word is added to the trie
        // but the trie will still recognize a word given to it with capital letters
        Assert.assertFalse(smallTree.contains("RAIN"));
        Assert.assertFalse(smallTree.contains("rain"));
        smallTree.addToTrie("rain");
        Assert.assertTrue(smallTree.contains("RAIN"));
        Assert.assertTrue(smallTree.contains("rain"));
        
        // words with non-alphabetic characters will not be added
        Assert.assertFalse(biggerTree.contains("qu-aint"));
        biggerTree.addToTrie("qu-aint");
        Assert.assertFalse(biggerTree.contains("qu-aint"));

        Assert.assertFalse(biggerTree.contains("look!"));
        biggerTree.addToTrie("look!");
        Assert.assertFalse(biggerTree.contains("look!"));

    }

    @org.junit.Test
    public void contains() throws Exception {

        Assert.assertFalse(babyTree.contains("z"));
        Assert.assertFalse(babyTree.contains("sea"));
        Assert.assertFalse(babyTree.contains("caricature"));

        Assert.assertFalse(smallTree.contains("z"));
        Assert.assertFalse(smallTree.contains("sea"));
        Assert.assertFalse(smallTree.contains("caricature"));

        Assert.assertTrue(smallTree.contains("goblet"));
        Assert.assertTrue(smallTree.contains("of"));
        Assert.assertTrue(smallTree.contains("fire"));
        
        Assert.assertTrue(biggerTree.contains("goblet"));
        Assert.assertTrue(biggerTree.contains("caricature"));
        Assert.assertTrue(biggerTree.contains("sea"));

        Assert.assertFalse(biggerTree.contains("dddd"));
        Assert.assertFalse(biggerTree.contains("gzgzgz"));
        Assert.assertFalse(biggerTree.contains("owihowrg"));
        Assert.assertFalse(biggerTree.contains("slddng"));

    }

    @org.junit.Test
    public void isPrefix() throws Exception {

        Assert.assertFalse(babyTree.isPrefix("z"));
        babyTree.addToTrie("zoo");
        Assert.assertTrue(babyTree.isPrefix("z"));
        Assert.assertTrue(babyTree.isPrefix("zo"));

        Assert.assertTrue(smallTree.isPrefix("go"));
        Assert.assertFalse(smallTree.isPrefix("bo"));
        Assert.assertFalse(smallTree.isPrefix("fire"));

        Assert.assertTrue(biggerTree.isPrefix("fast"));
        Assert.assertTrue(biggerTree.isPrefix("sur"));
        Assert.assertTrue(biggerTree.isPrefix("ded"));

        Assert.assertFalse(biggerTree.isPrefix("brk"));
        Assert.assertFalse(biggerTree.isPrefix("nf"));
        Assert.assertFalse(biggerTree.isPrefix("lc"));


    }


}
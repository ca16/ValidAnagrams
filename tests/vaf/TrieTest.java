package vaf;

import org.junit.Assert;

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


    }


}
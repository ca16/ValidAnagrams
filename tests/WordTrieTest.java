import org.junit.Assert;

import static org.junit.Assert.*;

/**
 * Created by Chloe on 10/7/16.
 */
public class WordTrieTest {

    private WordTrie babyTree;
    private WordTrie smallTree;
    private WordTrie biggerTree;

    @org.junit.Before
    public void setUp() throws Exception {

        babyTree = new WordTrie();
        smallTree = new WordTrie();
        biggerTree = new WordTrie();

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

}
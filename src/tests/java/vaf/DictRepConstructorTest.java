package vaf;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import vaf.mapversion.Sorter;
import vaf.trieversion.Trie;

import static vaf.PathsAndNames.PATH_TO_B_DICT;
import static vaf.PathsAndNames.PATH_TO_S_DICT;

/**
 * Created by Chloe on 10/8/16.
 */
public class DictRepConstructorTest {

    List<String> smallDictContents;
    List<String> otherWords;



    @Before
    public void setUp() throws Exception {

        PathsAndNames.populatePaths();

        smallDictContents = new ArrayList<>();
        smallDictContents.add("pumpkin");
        smallDictContents.add("beverage");
        smallDictContents.add("light");
        smallDictContents.add("tabs");
        smallDictContents.add("cringe");
        smallDictContents.add("tickle");
        smallDictContents.add("misty");
        smallDictContents.add("deed");
        smallDictContents.add("pickle");
        smallDictContents.add("bats");
        smallDictContents.add("stab");
        smallDictContents.add("canopy");
        smallDictContents.add("cereal");
        smallDictContents.add("globe");

        otherWords = new ArrayList<>();
        otherWords.add("yellow");
        otherWords.add("fog");
        otherWords.add("sea");
        otherWords.add("something");
        otherWords.add("beyond");
        otherWords.add("consciousness");
        otherWords.add("characteristic");
        otherWords.add("playwright");
        otherWords.add("pessimistic");
        otherWords.add("stripes");
        otherWords.add("ray");
        otherWords.add("beetle");
        otherWords.add("caring");
        otherWords.add("acted");
    }

    @Test
    public void constructTrie() throws Exception {
        
        
        Trie smallDictComp = DictRepConstructor.constructTrie(PATH_TO_S_DICT);
        
        for (String word : smallDictContents){
            Assert.assertTrue(smallDictComp.contains(word));
        }

        Trie bigDictComp = DictRepConstructor.constructTrie(PATH_TO_B_DICT);

        for (String word : smallDictContents){
            Assert.assertTrue(bigDictComp.contains(word));
        }        



        for (String word : otherWords){
            Assert.assertTrue(bigDictComp.contains(word));
        }

        for (String word : otherWords){
            Assert.assertFalse(smallDictComp.contains(word));
        }

        Assert.assertFalse(bigDictComp.contains("gzgzgzgz"));
        Assert.assertFalse(bigDictComp.contains("ddddd"));
        Assert.assertFalse(bigDictComp.contains("owiwewd"));
        Assert.assertFalse(bigDictComp.contains("slddng"));


    }

    @Test
    public void constructMap() throws Exception {

        Map smallDictComp = DictRepConstructor.constructMap(PATH_TO_S_DICT);

        for (String word : smallDictContents){
            Assert.assertTrue(smallDictComp.containsKey(Sorter.sortWord(word)));
        }

        Map bigDictComp = DictRepConstructor.constructMap(PATH_TO_B_DICT);

        for (String word : smallDictContents){
            Assert.assertTrue(bigDictComp.containsKey(Sorter.sortWord(word)));
        }


        for (String word : otherWords){
            Assert.assertTrue(bigDictComp.containsKey(Sorter.sortWord(word)));
        }

        for (String word : otherWords){
            Assert.assertFalse(smallDictComp.containsKey(Sorter.sortWord(word)));
        }

        Assert.assertFalse(bigDictComp.containsKey(Sorter.sortWord("gzgzgzgz")));
        Assert.assertFalse(bigDictComp.containsKey(Sorter.sortWord("ddddd")));
        Assert.assertFalse(bigDictComp.containsKey(Sorter.sortWord("owiwewd")));
        Assert.assertFalse(bigDictComp.containsKey(Sorter.sortWord("slddng")));


    }
}
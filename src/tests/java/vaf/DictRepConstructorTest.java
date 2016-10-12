package vaf;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import vaf.trieversion.Trie;

import static vaf.PathsAndNames.PATH_TO_B_DICT;
import static vaf.PathsAndNames.PATH_TO_S_DICT;

/**
 * Created by Chloe on 10/8/16.
 */
public class DictRepConstructorTest {
    
    @Before
    public void setUp() throws Exception {

        PathsAndNames.populatePaths();
    }

    @Test
    public void constructTrie() throws Exception {
        
        
        List<String> smallDictContents = new ArrayList<>();
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
        
        Trie smallDictComp = DictRepConstructor.constructTrie(PATH_TO_S_DICT);
        
        for (String word : smallDictContents){
            Assert.assertTrue(smallDictComp.contains(word));
        }

        Trie bigDictComp = DictRepConstructor.constructTrie(PATH_TO_B_DICT);

        for (String word : smallDictContents){
            Assert.assertTrue(bigDictComp.contains(word));
        }        
        List<String> otherWords = new ArrayList<>();
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
}
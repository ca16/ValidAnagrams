package vaf;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static vaf.PathsAndNames.*;

/**
 * Created by Chloe on 10/8/16.
 */
public class DictProcessorTest {
    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void wordsToList() throws Exception {
        
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
        
        List<String> smallDictComp = DictProcessor.wordsToList(PATH_TO_S_DICT);

        Assert.assertTrue(smallDictContents.containsAll(smallDictComp) && 
                smallDictComp.containsAll(smallDictContents));

        List<String> bigDictComp = DictProcessor.wordsToList(PATH_TO_B_DICT);
        
        Assert.assertTrue(bigDictComp.containsAll(smallDictContents));
        
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


        Assert.assertTrue(bigDictComp.containsAll(otherWords));
        Assert.assertFalse(smallDictComp.containsAll(otherWords));

        Assert.assertFalse(bigDictComp.contains("gzgzgzgz"));
        Assert.assertFalse(bigDictComp.contains("ddddd"));
        Assert.assertFalse(bigDictComp.contains("owiwewd"));
        Assert.assertFalse(bigDictComp.contains("slddng"));


    }
}
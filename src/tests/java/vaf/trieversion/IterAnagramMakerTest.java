package vaf.trieversion;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import vaf.PathsAndNames;
import vaf.DictRepConstructor;

import static vaf.PathsAndNames.*;


/**
 * Created by Chloe on 10/8/16.
 */
public class IterAnagramMakerTest {

    private IterAnagramMaker bigMaker;
    private IterAnagramMaker littleMaker;

    String inputEmpty;
    String input1;
    String input2;
    String input3;
    String input4;

    private List<String> emptyList;
    private Set<String> emptySet;

    @Before
    public void setUp() throws Exception {

        PathsAndNames.populatePaths();
        
        Trie bigTrie = DictRepConstructor.constructTrie(PATH_TO_B_DICT);
        Trie smallTrie = DictRepConstructor.constructTrie(PATH_TO_S_DICT);
        
        
        bigMaker = new IterAnagramMaker(bigTrie);
        littleMaker = new IterAnagramMaker(smallTrie);

        inputEmpty = "";
        input1 = "a";
        input2 = "to";
        input3 = "how";
        input4 = "deed";

        emptyList = new ArrayList<>();
        emptySet = new HashSet<>();

    }

    @Test
    public void lstOfWordsAnagrams() throws Exception {

        List<String> singleWordLst = new ArrayList<>();
        singleWordLst.add(input1);
        List<String> twoWordLst = new ArrayList<>();
        twoWordLst.addAll(singleWordLst);
        twoWordLst.add(input2);
        List<String> fourWordLst = new ArrayList<>();
        fourWordLst.addAll(twoWordLst);
        fourWordLst.add(input3);
        fourWordLst.add(input4);

        // See comments on singleWordAnagrams() test below for reasoning behind which
        // anagrams are expected for each input list.
        List<String> singleWordExpectedBig = new ArrayList<>();
        singleWordExpectedBig.add("a");

        List<String> twoWordExpectedBig = new ArrayList<>();
        twoWordExpectedBig.addAll(singleWordExpectedBig);
        twoWordExpectedBig.add("to");
        twoWordExpectedBig.add("ot");

        List<String> fourWordExpectedBig = new ArrayList<>();
        fourWordExpectedBig.addAll(twoWordExpectedBig);
        fourWordExpectedBig.add("how");
        fourWordExpectedBig.add("hwo");
        fourWordExpectedBig.add("ohw");
        fourWordExpectedBig.add("owh");
        fourWordExpectedBig.add("who");
        fourWordExpectedBig.add("woh");
        fourWordExpectedBig.add("deed");
        fourWordExpectedBig.add("dede");
        fourWordExpectedBig.add("edde");
        fourWordExpectedBig.add("eded");
        fourWordExpectedBig.add("eedd");


        List<String> fourWordExpectedLittle = new ArrayList<>();
        fourWordExpectedLittle.addAll(twoWordExpectedBig);
        fourWordExpectedLittle.add("owh");
        fourWordExpectedLittle.add("ohw");
        fourWordExpectedLittle.add("deed");
        fourWordExpectedLittle.add("deed");
        fourWordExpectedLittle.add("edde");
        fourWordExpectedLittle.add("eded");
        fourWordExpectedLittle.add("dede");

        Assert.assertTrue(compareLists(singleWordExpectedBig, bigMaker.lstOfWordsAnagrams(singleWordLst)));
        Assert.assertTrue(compareLists(twoWordExpectedBig, bigMaker.lstOfWordsAnagrams(twoWordLst)));
        Assert.assertTrue(compareLists(fourWordExpectedBig, bigMaker.lstOfWordsAnagrams(fourWordLst)));
        Assert.assertTrue(compareLists(emptyList, bigMaker.lstOfWordsAnagrams(emptyList)));
        Assert.assertTrue(compareLists(singleWordExpectedBig, littleMaker.lstOfWordsAnagrams(singleWordLst)));
        Assert.assertTrue(compareLists(twoWordExpectedBig, littleMaker.lstOfWordsAnagrams(twoWordLst)));
        Assert.assertTrue(compareLists(fourWordExpectedLittle, littleMaker.lstOfWordsAnagrams(fourWordLst)));
        Assert.assertTrue(compareLists(emptyList, littleMaker.lstOfWordsAnagrams(emptyList)));


    }

    @Test
    public void singleWordAnagrams() throws Exception {

        // Using the large dictionary file (roughly all English words)

        // 1 possible anagram
        Set<String> input1ExpectedAnagramsBig = new HashSet<>();
        input1ExpectedAnagramsBig.add("a");

        // 2 x 1 = 2 possible anagrams
        Set<String> input2ExpectedAnagramsBig = new HashSet<>();
        input2ExpectedAnagramsBig.add("to");
        input2ExpectedAnagramsBig.add("ot");

        // 3 x 2 x 1 = 6 possible anagrams
        Set<String> input3ExpectedAnagramsBig = new HashSet<>();
        input3ExpectedAnagramsBig.add("how");
        input3ExpectedAnagramsBig.add("hwo");
        input3ExpectedAnagramsBig.add("ohw");
        input3ExpectedAnagramsBig.add("owh");
        input3ExpectedAnagramsBig.add("who");
        input3ExpectedAnagramsBig.add("woh");

        // 4 x 3 x 2 x 1 = 24 possible anagrams
        // 24 /2 = 12 (account for the two 'd's switching positions)
        // 12 -2 = 10 (no words start with 'dd' in the dictionary)
        // 10 - 2 = 8 (double 'e's)
        // 8 - 3 = 5 (remove duplicates)
        Set<String> input4ExpectedAnagramsBig = new HashSet<>();
        input4ExpectedAnagramsBig.add("deed");
        input4ExpectedAnagramsBig.add("dede");
        input4ExpectedAnagramsBig.add("edde");
        input4ExpectedAnagramsBig.add("eded");
        input4ExpectedAnagramsBig.add("eedd");

        Assert.assertEquals(input1ExpectedAnagramsBig, bigMaker.singleWordAnagrams(input1));
        Assert.assertEquals(input2ExpectedAnagramsBig, bigMaker.singleWordAnagrams(input2));
        Assert.assertEquals(input3ExpectedAnagramsBig, bigMaker.singleWordAnagrams(input3));
        Assert.assertEquals(input4ExpectedAnagramsBig, bigMaker.singleWordAnagrams(input4));
        Assert.assertEquals(emptySet, bigMaker.singleWordAnagrams(inputEmpty));


        // Using the smaller dictionary file (12 words)

        // Same expected output for input 1 with big dictionary

        // Same expected output for input 2 with big dictionary

        // 3 x 2 x 1 = 6 possible anagrams
        // 6 - 2 = 4 no words in small dictionary starting with 'w'
        // 4 - 2 = 2 no words in small dictionary starting with 'h'
        Set<String> input3ExpectedAnagramsLittle = new HashSet<>();
        input3ExpectedAnagramsLittle.add("owh");
        input3ExpectedAnagramsLittle.add("ohw");


        // 4 x 3 x 2 x 1 = 24 possible anagrams
        // 24 / 2 = 12 (account for the two 'd's switching positions
        // 12 - 2 = 10 (no words start with 'dd' in the little dictionary)
        // 10 - 2 = 8 (double 'e's)
        // 8 - 1 = 7 (no words start with 'ded' in the little dictionary)
        // 7 - 3 = 4 (no words start with 'e' in the little dictionary)
        Set<String> input4ExpectedAnagramsLittle = new HashSet<>();
        input4ExpectedAnagramsLittle.add("deed");
        input4ExpectedAnagramsLittle.add("edde");
        input4ExpectedAnagramsLittle.add("eded");
        input4ExpectedAnagramsLittle.add("dede");
        

        Assert.assertEquals(input1ExpectedAnagramsBig, littleMaker.singleWordAnagrams(input1));
        Assert.assertEquals(input2ExpectedAnagramsBig, littleMaker.singleWordAnagrams(input2));
        Assert.assertEquals(input3ExpectedAnagramsLittle, littleMaker.singleWordAnagrams(input3));
        Assert.assertEquals(input4ExpectedAnagramsLittle, littleMaker.singleWordAnagrams(input4));
        Assert.assertEquals(emptySet, littleMaker.singleWordAnagrams(inputEmpty));

    }

    private Boolean compareLists(List<String> lst1, List<String> lst2){
        return (lst1.containsAll(lst2) && lst2.containsAll(lst1));
    }

}
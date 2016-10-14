package vaf.mapversion;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Chloe on 10/13/16.
 */
public class SorterTest {
    
    private String sorted1;
    private String sorted2;
    private String sorted3;
    private String sorted4;
    
    @Before
    public void setUp() throws Exception {
        
        sorted1 = "agns";
        sorted2 = "abst";
        sorted3 = "eehrt";
        sorted4 = "ceehiloprt";
        

    }

    @Test
    public void sortWord() throws Exception {
        
        Assert.assertEquals(Sorter.sortWord("snag"), sorted1);
        Assert.assertEquals(Sorter.sortWord("nags"), sorted1);
        Assert.assertEquals(Sorter.sortWord("sang"), sorted1);
        Assert.assertEquals(Sorter.sortWord("angs"), sorted1);
        Assert.assertEquals(Sorter.sortWord("stab"), sorted2);
        Assert.assertEquals(Sorter.sortWord("bats"), sorted2);
        Assert.assertEquals(Sorter.sortWord("tabs"), sorted2);
        Assert.assertEquals(Sorter.sortWord("bast"), sorted2);
        Assert.assertEquals(Sorter.sortWord("three"), sorted3);
        Assert.assertEquals(Sorter.sortWord("there"), sorted3);
        Assert.assertEquals(Sorter.sortWord("heert"), sorted3);
        Assert.assertEquals(Sorter.sortWord("etreh"), sorted3);
        Assert.assertEquals(Sorter.sortWord("eehtr"), sorted3);
        Assert.assertEquals(Sorter.sortWord("ceehiloptr"), sorted4);
        Assert.assertEquals(Sorter.sortWord("helicopter"), sorted4);





    }

}
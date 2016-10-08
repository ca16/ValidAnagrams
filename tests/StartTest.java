import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Chloe on 10/7/16.
 */
public class StartTest {
    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void main() throws Exception {

//        String[] argsWithFile = new String[6];
//        argsWithFile[0] = "--file";
//        argsWithFile[1] = "./TestSmallDict.txt";
//        argsWithFile[2] = "stab";
//        argsWithFile[3] = "tickle";
//        argsWithFile[4] = "globe";
//        argsWithFile[5] = "bat";
//
//        Start.main(argsWithFile);
//
//        System.out.println("\n\n\n");
//
//
//        String[] argsNoFile = new String[4];
//        argsNoFile[0] = "stab";
//        argsNoFile[1] = "tickle";
//        argsNoFile[2] = "globe";
//        argsNoFile[3] = "bat";
//
//        Start.main(argsNoFile);
//
//        System.out.println("\n\n\n");
//
//        String[] argsWithBigFile = new String[6];
//        argsWithBigFile[0] = "--file";
//        argsWithBigFile[1] = "./wordsEn.txt";
//        argsWithBigFile[2] = "stab";
//        argsWithBigFile[3] = "tickle";
//        argsWithBigFile[4] = "globe";
//        argsWithBigFile[5] = "bat";
//
//        Start.main(argsWithBigFile);
//
//        List<String> one = new ArrayList<>();
//        one.add("hi");
//        one.add("there");
//        List<String> two = new ArrayList<>();
//        two.add("there");
//        two.add("hi");
//        System.out.println(one.equals(two));

        String[] exampleGiven = new String[1];
        exampleGiven[0] = "stab";
        Start.main(exampleGiven);

        WordTrie trie = new WordTrie("./wordsEn.txt");
//        DictParser parser = new DictParser("./wordsEn.txt");
//        List<String> dictWords = parser.wordsToList();
//        trie.addWordList(dictWords);
        IPermuter permuter = new IterPermuter(trie);
        List<String> lst = new ArrayList<>();
        lst.add("hhoh");
        permuter.permuteListOfWords(lst);
//
//        List<String> list1 = new ArrayList<>();
//        list1.add("1");
//        list1.add("2");
//        list1.add("3");
//        list1.add("4");
//        System.out.println(list1);
//
//        List<String> list2 = new ArrayList<>();
//        list2.addAll(list1);
////        System.out.println(list2);
//
//        char[] arr = new char[4];
//        arr[0] = 'a';
//        arr[1] = 'c';
//        arr[2] = 'v';
//        arr[3] = 'e';
//        System.out.println("alone" + arr);
//        System.out.println("to string" + new String(arr));
    }

}
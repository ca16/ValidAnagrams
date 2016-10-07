import org.junit.Before;
import org.junit.Test;

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

        String[] argsWithFile = new String[6];
        argsWithFile[0] = "--file";
        argsWithFile[1] = "./TestSmallDict.txt";
        argsWithFile[2] = "stab";
        argsWithFile[3] = "tickle";
        argsWithFile[4] = "globe";
        argsWithFile[5] = "bat";

        Start.main(argsWithFile);

        System.out.println("\n\n\n");


        String[] argsNoFile = new String[4];
        argsNoFile[0] = "stab";
        argsNoFile[1] = "tickle";
        argsNoFile[2] = "globe";
        argsNoFile[3] = "bat";

        Start.main(argsNoFile);

        System.out.println("\n\n\n");

        String[] argsWithBigFile = new String[6];
        argsWithBigFile[0] = "--file";
        argsWithBigFile[1] = "./wordsEn.txt";
        argsWithBigFile[2] = "stab";
        argsWithBigFile[3] = "tickle";
        argsWithBigFile[4] = "globe";
        argsWithBigFile[5] = "bat";

        Start.main(argsWithBigFile);

    }

}
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

        String[] argsNoFile = new String[4];
        argsNoFile[0] = "stab";
        argsNoFile[1] = "tickle";
        argsNoFile[2] = "globe";
        argsNoFile[3] = "bat";

        Start.main(argsNoFile);


    }

}
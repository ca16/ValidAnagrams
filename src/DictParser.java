import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chloe on 10/7/16.
 */
public class DictParser {

    public static List<String> wordsToList(String filePath) throws FileNotFoundException{

        List<String> ret = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))){
            String word = null;
            while ((word = reader.readLine()) != null){
                word = word.toLowerCase();
                word = word.trim();
                ret.add(word);

//                System.out.println(word);
            }
        }
        catch(FileNotFoundException fnfe){
            throw new FileNotFoundException("catch me if you can!");
        }
        catch (IOException ioe){
            throw new RuntimeException("dude seriously");
        }

        return ret;
    }
}

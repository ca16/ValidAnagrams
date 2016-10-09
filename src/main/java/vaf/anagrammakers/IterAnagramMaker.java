package vaf.anagrammakers;

import java.util.ArrayList;
import java.util.List;

import vaf.InputWordListProcessor;
import vaf.Trie;

/**
 * A kind of anagram finder that looks for anagrams using a more iterative approach.
 * Created by Chloe on 10/4/16.
 */
public class IterAnagramMaker implements IAnagramMaker {

    private Trie trie;
    private List<Character> vowels;

    /**
     * Initializes the anagram finder.
     * Creates a list of vowels that will be used by the reorder() method.
     * @param trie the trie we're using to find valid anagrams
     */
    public IterAnagramMaker(Trie trie){
        this.trie = trie;
        this.vowels = new ArrayList<>();
        vowels.add('a');
        vowels.add('e');
        vowels.add('i');
        vowels.add('o');
        vowels.add('u');
    }

    /**
     * @inheritdoc
     */
    public List<String> lstOfWordsAnagrams(List<String> words){
        List<String> ret = new ArrayList<>();
        for (String word: words){
            ret.addAll(singleWordAnagrams(word));
        }
        return ret;
    }

    /**
     * @inheritdoc
     */
    public List<String> singleWordAnagrams(String word){
        Integer counter = 0;

        List<String> ret = new ArrayList<>();
        Integer len = word.length();

        if (len == 0){
            return ret;
        }
        String empty = "";
        ret.add(empty);

        // pre-process the word a little
        word = InputWordListProcessor.preprocessWord(word);
        word = reorder(word);
        len = word.length();

        // go through each letter
        for (int i = 0; i < len; i++){
            String toInsert = word.substring(i, i+1);

            // holds the substrings created by inserting the current letter at various positions
            // in the substrings contained in ret (substrings that are shorter by one letter)
            List<String> addTo = new ArrayList<>();
            for (String w: ret){
                Integer wLen = w.length();

                //the positions toInsert can be inserting at
                for (int j = 0; j <= wLen; j++){

                    // If we're inserting the final character, we don't need to construct
                    // anagrams where the substring before the insertion is not a prefix to a word.
                    // E.g. suppose word = "stab". toInsert = "a", w = "bts", j = 2
                    // and no words in the dictionary file start with "bt"
                    // No need to add "btas" or "btsa" because no words start with "bt"
                    if ((i == len-1) && (j < wLen) && !trie.isPrefix(w.substring(0, j))){
                        break;
                    }

                    // If there is more than one of a letter, don't add the same word more than
                    // once to account for different arrangements within those letters that are
                    // the same. E.g. given "doom", "mood" should not be added twice. Note: only
                    // handles cases where the duplicate letters are next to each other. E.g.
                    // substrings like "lil" (two 'l's) would still appear twice.
                    if (j < wLen && toInsert.equals(w.substring(j, j+1))) {
                        continue;
                    }

                    // No special case if we get to this point, add this permutation of the first
                    // i letters to the list.
                    String toAdd = w.substring(0, j) + toInsert + w.substring(j, wLen);
                    counter++;

                    // Covering cases where two anagrams that are the same can be formed from
                    // the same word that are also not covered by the above check. E.g. "dead"
                    // and "dead."
                    if (!addTo.contains(toAdd)) {
                        addTo.add(toAdd);
                    }
                }
            }
            ret = addTo;
        }
        return ret;

    }

    /**
     * Rearranges the letter of the given word to bring the consonants to the front
     * and the vowels to the back.
     * @param word the word whose letters will be rearranged
     * @return the string containing the word's consonants towards the front and its vowels towards
     * the back
     */
    private String reorder(String word){
        char[] arrayVersion = word.toCharArray();
        int len = arrayVersion.length;
        int last = len-1;
        int first = 0;

        // first stops when it finds the first vowel from the front
        while (first < len){
            if (vowels.contains(arrayVersion[first])){
                break;
            }
            first++;
        }

        // last stops when it finds the first consonant from the back
        while (last >= 0){
            if (!vowels.contains(arrayVersion[last])){
                break;
            }
            last--;
        }

        // keep swapping vowels from the front with consonants from the back until all the
        // consonants are in the front and all the vowels are in the back
        while (first < last){
            char temp = arrayVersion[last];
            arrayVersion[last] = arrayVersion[first];
            arrayVersion[first] = temp;
            while (!vowels.contains(arrayVersion[first])){
                first++;
            }
            while (vowels.contains(arrayVersion[last])){
                last--;
            }
        }

        String reordered = new String(arrayVersion);
        return reordered;

    }




}

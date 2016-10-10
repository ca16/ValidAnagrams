package vaf;

/**
 * Created by Chloe on 10/9/16.
 */
public class PathsAndNames {

    // General
    public static final String CURR_DIR = ".";
    public static final String FILE_SEP = System.getProperty("file.separator");

    // Important sub-folders
    public static final String SRC_DIR = "src";
    public static final String MN_DIR = "main";
    public static final String TESTS_DIR = "tests";
    public static final String REC_DIR = "resources";
    public static final String USER_RESP_DIR = "UserResponsesForTests";
    public static final String LONG_RESP_DIR = "LongExpectedResponses";
    public static final String ANAGRAM_ANS = "AnagramAnswers";

    // Dictionary file names
    public static final String S_DICT_NAME = "TestSmallDict.txt";
    public static final String B_DICT_NAME = "wordsEn.txt";

    public static final String PATH_TO_MN_REC = CURR_DIR + FILE_SEP + SRC_DIR + FILE_SEP + MN_DIR + FILE_SEP + REC_DIR;
    public static final String PATH_TO_TEST_REC = CURR_DIR + FILE_SEP + SRC_DIR + FILE_SEP + TESTS_DIR + FILE_SEP + REC_DIR;
    public static final String PATH_TO_USER_RESPS = PATH_TO_TEST_REC + FILE_SEP + USER_RESP_DIR;
    public static final String PATH_TO_PROG_RESPS = PATH_TO_TEST_REC + FILE_SEP + LONG_RESP_DIR;
    public static final String PATH_TO_ANA_ANS = PATH_TO_TEST_REC + FILE_SEP + ANAGRAM_ANS;
    public static final String PATH_TO_B_DICT = PATH_TO_MN_REC + FILE_SEP + B_DICT_NAME;
    public static final String PATH_TO_S_DICT = PATH_TO_TEST_REC + FILE_SEP + S_DICT_NAME;

}




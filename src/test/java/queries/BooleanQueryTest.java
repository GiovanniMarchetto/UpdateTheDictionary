package queries;

import dataStructure.Dictionary;
import miscellaneous.Miscellaneous;
import operations.StopWord;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BooleanQueryTest {

    public static final String resourcePath = System.getProperty("user.dir") + "\\src\\test\\resources\\";

    public static Dictionary dictionaryWithTestTextAB() {
        Dictionary dictionary = new Dictionary();
        dictionary.setStopWordListSize(StopWord.StopWordSize.NONE);

        String docPathA = resourcePath + "A.txt";
        dictionary.addDocumentAtDictionary(docPathA);

        String docPathB = resourcePath + "B.txt";
        dictionary.addDocumentAtDictionary(docPathB);

//        dictionary.printDictionary();
//        dictionary.printDocumentList();

        return dictionary;
    }

    public static Dictionary dictionaryWithTestTextABC() {
        Dictionary dictionary = new Dictionary();
        dictionary.setStopWordListSize(StopWord.StopWordSize.NONE);

        dictionary.addDocumentsFromListAtDictionary(resourcePath + "listOfDocs.txt");
        return dictionary;
    }

    public static ArrayList<String> baseSelectionOfWords() {
        ArrayList<String> words = new ArrayList<>();
        words.add("cat");
        words.add("table");
        return words;
    }

    @Test
    void baseAND() {
        Miscellaneous.testTitleFormatting("TEST-BASE-AND");

        Dictionary dictionary = dictionaryWithTestTextAB();

        ArrayList<String> words = baseSelectionOfWords();

        assertEquals(1, BooleanQueries.queryAND(dictionary, words).size());
    }

    @Test
    void baseOR() {
        Miscellaneous.testTitleFormatting("TEST-BASE-OR");

        Dictionary dictionary = dictionaryWithTestTextAB();

        ArrayList<String> words = baseSelectionOfWords();

        assertEquals(2, BooleanQueries.queryOR(dictionary, words).size());

    }

    @Test
    void baseNOT() {
        Miscellaneous.testTitleFormatting("TEST-BASE-NOT");

        Dictionary dictionary = dictionaryWithTestTextAB();

        ArrayList<String> words = baseSelectionOfWords();

        assertEquals(0, BooleanQueries.queryNOT(dictionary, words).size());

    }

    @Test
    void advAND() {
        Miscellaneous.testTitleFormatting("TEST-ADV-AND");

        Dictionary dictionary = dictionaryWithTestTextAB();

        ArrayList<String> words = advSelectionOfWords();

        assertEquals(0, BooleanQueries.queryAND(dictionary, words).size());

    }

    private ArrayList<String> advSelectionOfWords() {
        ArrayList<String> words = baseSelectionOfWords();
        words.add("cheese");
        return words;
    }

}

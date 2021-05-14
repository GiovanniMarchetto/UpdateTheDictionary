import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class booleanQueryTest {

    @Test
    void baseAND() {
        System.out.println("\n++++++++++++++++++++++++++   TEST-BASE-AND   +++++++++++++++++++++++++");

        Dictionary dictionary = dictionaryWithTestTextAB();

        ArrayList<String> words = baseSelectionOfWords();

        assertEquals(1, BooleanQueries.queryAND(dictionary, words).size());

        System.out.println("++++++++++++++++++++++++   END TEST-BASE-AND   +++++++++++++++++++++++");
    }

    @Test
    void baseOR() {
        System.out.println("\n++++++++++++++++++++++++++   TEST-BASE-OR   ++++++++++++++++++++++++++");

        Dictionary dictionary = dictionaryWithTestTextAB();

        ArrayList<String> words = baseSelectionOfWords();

        assertEquals(2, BooleanQueries.queryOR(dictionary, words).size());

        System.out.println("++++++++++++++++++++++++   END TEST-BASE-OR   ++++++++++++++++++++++++");
    }

    @Test
    void baseNOT() {
        System.out.println("\n++++++++++++++++++++++++++   TEST-BASE-NOT   +++++++++++++++++++++++++");

        Dictionary dictionary = dictionaryWithTestTextAB();

        ArrayList<String> words = baseSelectionOfWords();

        assertEquals(0, BooleanQueries.queryNOT(dictionary, words).size());

        System.out.println("++++++++++++++++++++++++   END TEST-BASE-NOT   +++++++++++++++++++++++");
    }

    @Test
    void advAND() {
        System.out.println("\n++++++++++++++++++++++++++   TEST-ADV-AND   ++++++++++++++++++++++++++");

        Dictionary dictionary = dictionaryWithTestTextAB();

        ArrayList<String> words = advSelectionOfWords();

        assertEquals(0, BooleanQueries.queryAND(dictionary, words).size());

        System.out.println("++++++++++++++++++++++++   END TEST-ADV-AND   ++++++++++++++++++++++++");
    }

    private Dictionary dictionaryWithTestTextAB() {
        Dictionary dictionary = new Dictionary();

        String docPathA = System.getProperty("user.dir") + "\\src\\test\\resources\\" + "A.txt";
        dictionary.addDocumentAtDictionary(docPathA);

        String docPathB = System.getProperty("user.dir") + "\\src\\test\\resources\\" + "B.txt";
        dictionary.addDocumentAtDictionary(docPathB);

//        dictionary.printDictionary();
//        dictionary.printDocumentList();

        return dictionary;
    }


    private ArrayList<String> baseSelectionOfWords() {
        ArrayList<String> words = new ArrayList<>();
        words.add("the");
        words.add("table");
        return words;
    }

    private ArrayList<String> advSelectionOfWords() {
        ArrayList<String> words = baseSelectionOfWords();
        words.add("cheese");
        return words;
    }

}

package dataStructure;

import miscellaneous.Miscellaneous;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BasicDictionaryTest {

    @Test
    void add_doc_A() {
        Miscellaneous.testTitleFormatting("TEST-A");

        Dictionary dictionary = new Dictionary();
        String docPath = System.getProperty("user.dir") + "\\src\\test\\resources\\" + "A.txt";
        dictionary.addDocumentAtDictionary(docPath);
        assertEquals(1, dictionary.getDocumentList().size());
//        dictionary.printDictionary();
    }

    @Test
    void add_doc_Bending() {
        Miscellaneous.testTitleFormatting("TEST-BENDING");

        Dictionary dictionary = new Dictionary();
        String docPath = System.getProperty("user.dir") + "\\src\\test\\resources\\" + "Bending Spoons.pdf";
        dictionary.addDocumentAtDictionary(docPath);
        assertEquals(1, dictionary.getDocumentList().size());
//        dictionary.printDictionary();

    }

    @Test
    void add_doc_AB_and_remove_A() {
        Miscellaneous.testTitleFormatting("TEST-REMOVE");

        Dictionary dictionary = new Dictionary();
        String docPath = System.getProperty("user.dir") + "\\src\\test\\resources\\" + "A.txt";
        String docIDA = dictionary.addDocumentAtDictionary(docPath);
        docPath = System.getProperty("user.dir") + "\\src\\test\\resources\\" + "B.txt";
        dictionary.addDocumentAtDictionary(docPath);
        assertEquals(2, dictionary.getDocumentList().size());
//        dictionary.printDictionary();

        dictionary.removeDocumentFromDictionary(docIDA);
        assertEquals(1, dictionary.getDocumentList().size());
//        dictionary.printDictionary();

    }

    @Test
    void add_doc_list() {
        Miscellaneous.testTitleFormatting("TEST-LIST");

        Dictionary dictionary = new Dictionary();
        String listPath = System.getProperty("user.dir") + "\\src\\test\\resources\\" + "listOfDocs.txt";
        dictionary.addDocumentsFromListAtDictionary(listPath);
        assertEquals(3, dictionary.getDocumentList().size());
//        dictionary.printDictionary();
    }

}

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class addBasicDocumentAtDictionary {

    @Test
    void add_doc_A() {
        System.out.println("\n+++++++++++++++++++++++++++++   TEST-A   +++++++++++++++++++++++++++++");

        Dictionary dictionary = new Dictionary();
        String docPath = System.getProperty("user.dir") + "\\src\\test\\resources\\" + "A.txt";
        dictionary.addDocumentAtDictionary(docPath);
        assertEquals(1, dictionary.getDocumentList().size());
//        dictionary.printDictionary();

        System.out.println("+++++++++++++++++++++++++++   END TEST-A   +++++++++++++++++++++++++++");
    }

    @Test
    void add_doc_Bending() {
        System.out.println("\n++++++++++++++++++++++++++   TEST-BENDING   ++++++++++++++++++++++++++");

        Dictionary dictionary = new Dictionary();
        String docPath = System.getProperty("user.dir") + "\\src\\test\\resources\\" + "Bending Spoons.pdf";
        dictionary.addDocumentAtDictionary(docPath);
        assertEquals(1, dictionary.getDocumentList().size());
        dictionary.printDictionary();

        System.out.println("++++++++++++++++++++++++   END TEST-BENDING   ++++++++++++++++++++++++");
    }

}

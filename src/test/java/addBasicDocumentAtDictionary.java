import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class addBasicDocumentAtDictionary {

    @Test
    void add_doc_A() {
        String docPath = System.getProperty("user.dir") + "\\src\\test\\resources\\" + "A.txt";
        assertTrue(Dictionary.addDocumentAtDictionary(docPath));
        Dictionary.printDictionary();
    }

}

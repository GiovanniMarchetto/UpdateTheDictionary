import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class addBasicDocumentAtDictionary {

    @Test
    void add_doc_A() {
//        Dictionary dictionary = new Dictionary();
        String docPath = System.getProperty("user.dir") + "\\src\\test\\resources\\" + "A.txt";
        System.out.println(docPath);
        assertTrue(Dictionary.addDocumentAtDictionary(docPath));
        Dictionary.printDictionary();
    }

}

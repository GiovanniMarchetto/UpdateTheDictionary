import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class addBasicDocumentAtDictionary {

    @Test
    void add_doc_A() {
        Dictionary dictionary = new Dictionary();
        String docPath = System.getProperty("user.dir") + "\\src\\test\\resources\\" + "A.txt";
        dictionary.addDocumentAtDictionary(docPath);
        assertEquals(1,dictionary.getDocumentList().size());
    }

}

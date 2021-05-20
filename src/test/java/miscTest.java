import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class miscTest {

    @Test
    void misc_token() {
        Miscellaneous.testTitleFormatting("TEST-TOKEN");

        String token = "good";
        String normToken = Miscellaneous.getNormalizeToken(token);
        assertEquals("good",normToken);
    }

    @Test
    void removeStopWords() {
        Miscellaneous.testTitleFormatting("REMOVE STOP WORDS");

        Dictionary dictionary = new Dictionary();
        String docPath = System.getProperty("user.dir") + "\\src\\test\\resources\\" + "A.txt";
        dictionary.addDocumentAtDictionary(docPath);
        assertEquals(1, dictionary.getDocumentList().size());
//        dictionary.printDictionary();
    }

}

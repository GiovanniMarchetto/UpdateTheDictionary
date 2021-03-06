package miscellaneous;

import operations.Normalization;
import operations.StopWord;
import operations.Tokenization;
import org.junit.jupiter.api.Test;
import queries.BooleanQueryTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MiscTest {

    @Test
    void misc_token() {
        Miscellaneous.testTitleFormatting("TEST-TOKEN");

        String token = "good";
        String normToken = Normalization.getNormalizeToken(token);
        assertEquals("good", normToken);
    }

    @Test
    void removeStopWords() {
        Miscellaneous.testTitleFormatting("REMOVE STOP WORDS");
        ArrayList<String> phrase = Tokenization.getNormalizeTokenListFromPhrase("The pen is on the table");
        System.out.println(phrase);
        assertEquals(6, phrase.size());
        StopWord.removeStopWords(phrase);
        System.out.println(phrase);

//        Dictionary dictionary = new Dictionary();
//        String docPath = System.getProperty("user.dir") + "\\src\\test\\resources\\" + "A.txt";
//        dictionary.addDocumentAtDictionary(docPath);
//        assertEquals(1, dictionary.getDocumentList().size());
//        dictionary.printDictionary();
    }

    @Test
    void findPathOfResources() {
        assertNotNull(BooleanQueryTest.class.getClassLoader().getResource("A.txt"));
    }

}

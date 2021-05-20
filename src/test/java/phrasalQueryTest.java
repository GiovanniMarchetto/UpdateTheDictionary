import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class phrasalQueryTest {

    @Test
    void twoWordPhraseQuery() {
        Miscellaneous.testTitleFormatting("TEST-PHQ-TWO");

        Dictionary dictionary = booleanQueryTest.dictionaryWithTestTextAB();

        ArrayList<String> words = booleanQueryTest.baseSelectionOfWords();

        PostingList answer = PhrasalQueries.phrasalQuery(dictionary, words);

        assertEquals(1, answer.size());

//        dictionary.printDictionary();
//        System.out.println("\n\n\n");
//        answer.printPostingList();
//        System.out.println("\n\n\n");
//        dictionary.printDictionary();

    }

}

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class phrasalQueryTest {

    private ArrayList<String> getTokenListFromPhrase(String phrase){
        return new ArrayList<>(Arrays.asList(phrase.split(" ")));
    }

    @Test
    void twoWordPhraseQuery() {
        Miscellaneous.testTitleFormatting("TEST-PHQ-TWO");

        Dictionary dictionary = booleanQueryTest.dictionaryWithTestTextAB();

        ArrayList<String> words = getTokenListFromPhrase("the table");

        PostingList answer = PhrasalQueries.phrasalQuery(dictionary, words);

        assertEquals(1, answer.size());

//        dictionary.printDictionary();
//        System.out.println("\n\n\n");
//        answer.printPostingList();
//        System.out.println("\n\n\n");
//        dictionary.printDictionary();

    }

}

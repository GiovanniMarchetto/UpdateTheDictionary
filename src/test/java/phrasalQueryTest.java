import dataStructure.Dictionary;
import dataStructure.PostingList;
import miscellaneous.Miscellaneous;
import operations.PhrasalQueries;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class phrasalQueryTest {

    @Test
    void twoWordPhraseQuery() {
        Miscellaneous.testTitleFormatting("TEST-PHQ-TWO");

        Dictionary dictionary = booleanQueryTest.dictionaryWithTestTextAB();

        PostingList answer = PhrasalQueries.phrasalQuery(dictionary, "the table");

        assertEquals(1, answer.size());

//        dictionary.printDictionary();
//        System.out.println("\n\n\n");
//        answer.printPostingList();
//        System.out.println("\n\n\n");
//        dictionary.printDictionary();

    }

    @Test
    void threeWordPhraseQuery() {
        Miscellaneous.testTitleFormatting("TEST-PHQ-THREE");
        Dictionary dictionary = booleanQueryTest.dictionaryWithTestTextAB();
        PostingList answer = PhrasalQueries.phrasalQuery(dictionary, "the table boy");
        assertEquals(0, answer.size());
    }

    @Test
    void shortTimePQ() {
        Miscellaneous.testTitleFormatting("shortTimePQ");
        Dictionary dictionary = booleanQueryTest.dictionaryWithTestTextAB();

        long startTime = System.nanoTime();
        PostingList answer1 = PhrasalQueries.phrasalQuery(dictionary, "the table");
        assertEquals(1, answer1.size());
        long endTime = System.nanoTime();
        System.out.println("Total time for normal phrase query: " + (endTime - startTime));
    }

    @Test
    void shortTimePQ2() {
        Miscellaneous.testTitleFormatting("shortTimePQ2");
        Dictionary dictionary = booleanQueryTest.dictionaryWithTestTextAB();
        long startTime = System.nanoTime();
        PostingList answer2 = PhrasalQueries.alternativePhrasalQuery(dictionary, "the table");
        assertEquals(1, answer2.size());
        long endTime = System.nanoTime();
        System.out.println("Total time for quick phrase query:  " + (endTime - startTime));
    }


    @Test
    void longTimePQ() {Miscellaneous.testTitleFormatting("longTimePQ");


        Runtime runtime = Runtime.getRuntime();
        long usedMemoryBefore = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("Used Memory before: " + usedMemoryBefore);




        Dictionary dictionary = booleanQueryTest.dictionaryWithTestTextABC();
        long startTime = System.nanoTime();
        for (int i = 0; i < 10; i++) {
            PostingList answer1 = PhrasalQueries.phrasalQuery(dictionary, "To return to the previous page viewed, click the Back button on your device or app.");
            assertEquals(1, answer1.size());
        }
        long endTime = System.nanoTime();
        System.out.println("Time 10 repetitions: " + (endTime - startTime));




        long usedMemoryAfter = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("Memory increased:   " + (usedMemoryAfter-usedMemoryBefore));



    }

    @Test
    void longTimePQ2() {Miscellaneous.testTitleFormatting("longTimePQ2");

        Runtime runtime = Runtime.getRuntime();
        long usedMemoryBefore = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("Used Memory before: " + usedMemoryBefore);




        Dictionary dictionary = booleanQueryTest.dictionaryWithTestTextABC();
        long startTime = System.nanoTime();
        for (int i = 0; i < 10; i++) {
            PostingList answer2 = PhrasalQueries.alternativePhrasalQuery(dictionary, "To return to the previous page viewed, click the Back button on your device or app.");
            assertEquals(1, answer2.size());
        }
        long endTime = System.nanoTime();
        System.out.println("Time 10 repetitions:  " + (endTime - startTime));



        long usedMemoryAfter = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("Memory increased:   " + (usedMemoryAfter-usedMemoryBefore));

    }

}

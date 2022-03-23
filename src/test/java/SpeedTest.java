import dataStructure.Dictionary;
import miscellaneous.Miscellaneous;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class SpeedTest {
    final String resourcesPath = System.getProperty("user.dir") + "\\src\\test\\resources\\encyclopaedia\\";

    @Test
    void speedTestOfAddingAndRemoving() {
//        Miscellaneous.DEBUG = true;
        Miscellaneous.testTitleFormatting("TEST-SPEED");

        Dictionary dictionary = new Dictionary();

        //add part A
        addPartOfDataset(dictionary, "A");

        //add part B
        ArrayList<String> docIdOfPartB = addPartOfDataset(dictionary, "B");
        dictionary.printDocumentList();
        System.out.println();

        //add part C
        addPartOfDataset(dictionary, "C");


        //remove part B
        long startTime = System.currentTimeMillis();
        for (String docId : docIdOfPartB) {
            long partial = System.currentTimeMillis();
            dictionary.removeDocumentFromDictionary(docId);
            System.out.println("Partial: " + (System.currentTimeMillis() - partial) + " --> removing " + docId);
        }
        System.out.println("Total time to rmv of part B: " + (System.currentTimeMillis() - startTime));
        dictionary.printDocumentList();


//        Miscellaneous.DEBUG = false;
    }

    private ArrayList<String> addPartOfDataset(Dictionary dictionary, String part) {
        ArrayList<String> docIdOfPart = new ArrayList<>();
        long startTime = System.currentTimeMillis();
        for (int i = 1; i < 5; i++) {
            long partial = System.currentTimeMillis();
            String docPath = resourcesPath + "part" + part + " (" + i + ").txt";
            String docId = dictionary.addDocumentAtDictionary(docPath);
            docIdOfPart.add(docId);
            System.out.println("Partial: " + (System.currentTimeMillis() - partial) + " --> add " + docId);
        }
        System.out.println("Total time to add of part " + part + ": " + (System.currentTimeMillis() - startTime) + "\n");
        return docIdOfPart;
    }

}

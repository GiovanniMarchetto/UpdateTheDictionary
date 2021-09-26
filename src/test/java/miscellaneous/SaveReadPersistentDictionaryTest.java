package miscellaneous;

import dataStructure.Dictionary;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class SaveReadPersistentDictionaryTest {

    @Test
    void saveFile() {
    }

    @Test
    void readFile() {
    }

    @Test
    void saveAndReadFile() throws IOException, ClassNotFoundException {
        Dictionary dictionary = new Dictionary();
        String listPath = System.getProperty("user.dir") + "\\src\\test\\resources\\" + "listOfDocs.txt";
        dictionary.addDocumentsFromListAtDictionary(listPath);

        SaveReadPersistentDictionary xd = new SaveReadPersistentDictionary();
        System.out.println("### Save dictionary ###");
        xd.saveFile(dictionary);
        System.out.println("### Dictionary saved successfully ###");

        Dictionary persistentDictionary = xd.readFile();
        persistentDictionary.printDictionary();
    }
}
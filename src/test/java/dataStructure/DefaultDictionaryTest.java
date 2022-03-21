package dataStructure;

import org.junit.jupiter.api.Test;

public class DefaultDictionaryTest {
    @Test
    void getDictionary() {
        Dictionary dictionary = new Dictionary();
        String docPath = System.getProperty("user.dir") + "\\src\\test\\resources\\" + "A.txt";
        dictionary.addDocumentAtDictionary(docPath);
//        System.out.println(dictionary.getDictionary());
    }

    @Test
    void setDictionary() {
    }

    @Test
    void getDocumentList() {
    }

    @Test
    void setDocumentList() {
    }

    @Test
    void addDocumentAtDictionary() {
    }

    @Test
    void removeDocumentFromDictionary() {
    }

    @Test
    void printDictionary() {
    }

    @Test
    void printDocumentList() {
    }

    @Test
    void containsTerm() {
    }

    @Test
    void getPostingList() {
    }
}

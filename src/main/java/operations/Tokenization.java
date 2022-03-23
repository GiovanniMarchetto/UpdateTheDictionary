package operations;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Tokenization {
    public static ArrayList<String> getNormalizeTokenListFromPhrase(String phrase) {
        ArrayList<String> phraseArray = new ArrayList<>(Arrays.asList(phrase.split(" ")));
        Normalization.normalizeListOfString(phraseArray);
        return phraseArray;
    }

    public static ArrayList<String> getListOfTokenFromFile(String docPath) {
        Scanner inputStream;
        try {
            inputStream = new Scanner(new File(docPath));
        } catch (FileNotFoundException e) {
            System.out.println("Document not found " + docPath);
            return null;
        }

        ArrayList<String> tokenList = new ArrayList<>();
        while (inputStream.hasNext()) {
            String token = inputStream.next();
            tokenList.add(token);
        }
        inputStream.close();
        return tokenList;
    }
}

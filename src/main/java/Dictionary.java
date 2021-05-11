import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.UUID;

public class Dictionary {

    static ArrayList<String> documentList = new ArrayList<>();
    static HashMap<String, ArrayList<String>> dictionary = new HashMap<>();

    public static void main(String... args) {

    }

    public static void addDocumentAtDictionary(String docPath) {
        String docID = UUID.nameUUIDFromBytes(docPath.getBytes()).toString().substring(0, 8);

        // WARNING: if we add too document it can explode
        while (documentList.contains(docID)){
            docID = UUID.randomUUID().toString().substring(0, 8);
        }

        documentList.add(docID);

        ArrayList<String> tokenList = getListOfTokenFromFile(docPath);
        ArrayList<String> postingList = new ArrayList<>();

        for (String token : tokenList) {
            if (dictionary.containsKey(token)) {
                postingList = dictionary.get(token);
                if (!postingList.contains(docID)) {
                    postingList.add(docID);
                }
                dictionary.replace(token, dictionary.get(token), postingList);
            } else {
                postingList.add(docID);
                dictionary.put(token, postingList);
            }
        }
    }

    private static ArrayList<String> getListOfTokenFromFile(String docPath) {
        Scanner inputStream = null;
        try {
            inputStream = new Scanner(new File(docPath));
        } catch (FileNotFoundException e) {
            System.out.println("Document not found " + docPath);
            System.exit(0);
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

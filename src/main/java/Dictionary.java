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

    public static void addDocumentAtDictionary(String nameDoc) {
        String docID = UUID.nameUUIDFromBytes(nameDoc.getBytes()).toString().substring(0, 8);

        // TODO: is UUID really unique if we cast in to 8 characters? -> this doubt bring to this 'for' of control
//        for (int i = 0; i < documentList.size(); i++) {
//            if (docID.equals(documentList.get(i))) {
//                docID = UUID.randomUUID().toString().substring(0, 8);
//                i = -1;
//            }
//        }

        documentList.add(docID);

        Scanner inputStream = null;
        try {
            inputStream = new Scanner(new File(nameDoc));
        } catch (FileNotFoundException e) {
            System.out.println("Document not found " + nameDoc);
            System.exit(0);
        }
        while (inputStream.hasNext()) {
            String token = inputStream.next();
            ArrayList<String> postingList = new ArrayList<>();

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
        inputStream.close();
    }
}

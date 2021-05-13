import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class Dictionary {

    private HashMap<String, PostingList> dictionary;

    private ArrayList<String> documentList;

    public Dictionary() {
        dictionary = new HashMap<>();
        documentList = new ArrayList<>();
    }

    public HashMap<String, PostingList> getDictionary() {
        return dictionary;
    }

    public void setDictionary(HashMap<String, PostingList> dictionary) {
        this.dictionary = dictionary;
    }

    public ArrayList<String> getDocumentList() {
        return documentList;
    }

    public void setDocumentList(ArrayList<String> documentList) {
        this.documentList = documentList;
    }

    public void addDocumentAtDictionary(String docPath) {
        /*        String docID = UUID.nameUUIDFromBytes(docPath.getBytes()).toString().substring(0, 8);
        // WARNING: if we add too document it can explode
        //        while (this.documentList.contains(docID)) {
        //            docID = UUID.randomUUID().toString().substring(0, 8);}
        */
        String docID = UUID.randomUUID().toString().substring(0, 8);
        this.documentList.add(docID);

        ArrayList<String> tokenList = Miscellaneous.getListOfTokenFromFile(docPath);
        int positionOfToken = 1;

        for (String token : tokenList) {
            token = Miscellaneous.getNormalizeToken(token);

            PostingList postingList = new PostingList();
            if (dictionary.containsKey(token)) {
                postingList = dictionary.get(token);
            }
            postingList.addPosting(docID, positionOfToken);
            dictionary.put(token, postingList);
            positionOfToken++;
        }
    }

    public void printDictionary() {
        System.out.println("\n//////////////////////////////");
        System.out.println("//////////DICTIONARY//////////");
        System.out.println("//////////////////////////////");

        AtomicInteger counter = new AtomicInteger();
        this.dictionary.forEach((term, postingList) -> {

            if (counter.get() % 2 != 0) System.out.println("\t \t \t \t \t" + term);
            else System.out.println(term);

            postingList.printPostingListForDictionary(counter);

            counter.getAndIncrement();
        });

        System.out.println("//////////////////////////////");
        System.out.println("//////////////////////////////");
    }

    public void printDocumentList() {
        System.out.println("\n******************************");
        System.out.println("******* DOCUMENT LIST ********");

        if (this.documentList == null || this.documentList.isEmpty()) {
            System.out.println("The list is empty!");
        } else {
            for (String doc : this.documentList) {
                System.out.println(doc);
            }
        }
        System.out.println("******************************");
    }

}

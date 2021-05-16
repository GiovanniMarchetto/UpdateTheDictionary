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

        if (Miscellaneous.DEBUG) {
            System.out.println("\nAdd at the dictionary the document at path:");
            System.out.println(docPath);
            System.out.println("with the alias: " + docID);
        }

        ArrayList<String> tokenList = Miscellaneous.getListOfTokenFromFile(docPath);
        AtomicInteger positionOfToken = new AtomicInteger(1);

        for (String token : tokenList) {
            token = Miscellaneous.getNormalizeToken(token);

            PostingList postingList = new PostingList();
            if (dictionary.containsKey(token)) {
                postingList = dictionary.get(token);
            }
            postingList.addPosting(docID, positionOfToken.get());
            dictionary.put(token, postingList);
            positionOfToken.getAndIncrement();
        }

        //TODO: trim the arrays of position of the posting of the document just added
    }

    public void printDictionary() {
        System.out.println("\n//////////  DICTIONARY  //////////");

        AtomicInteger counter = new AtomicInteger();
        this.dictionary.forEach((term, postingList) -> {

            if (counter.get() % 2 != 0) {
                System.out.print("\t \t \t \t \t");
            }
            System.out.println(term);

            postingList.printPostingListForDictionary(counter);

            counter.getAndIncrement();
        });

        System.out.println("//////////////////////////////////");
        System.out.println("//////////////////////////////////");
    }

    public void printDocumentList() {
        System.out.println("\n******* DOCUMENT LIST ********");

        if (this.documentList == null || this.documentList.isEmpty()) {
            System.out.println("The list is empty!");
        } else {
            for (String doc : this.documentList) {
                System.out.println(doc);
            }
        }
        System.out.println("******************************");
    }

    public boolean containsTerm(String term) {
        return dictionary.containsKey(term);
    }

    public PostingList getPostingList(String term) {
        if (dictionary.containsKey(term)) {
            return dictionary.get(term);
        }
        return new PostingList();
    }
}

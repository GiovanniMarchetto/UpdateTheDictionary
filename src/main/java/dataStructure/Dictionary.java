package dataStructure;

import miscellaneous.Miscellaneous;
import operations.Normalization;
import operations.StopWord;
import operations.Tokenization;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static operations.StopWord.StopWordSize;
import static operations.StopWord.setStopWordList;

public class Dictionary implements Serializable {

    private StopWordSize stopWordListSize;
    private HashMap<String, PostingList> dictionary;
    private ArrayList<String> documentList;

    public Dictionary() {
        dictionary = new HashMap<>();
        documentList = new ArrayList<>();
        stopWordListSize = StopWordSize.STANDARD;
        setStopWordList(stopWordListSize);
    }

    public StopWordSize getStopWordListSize() {
        return stopWordListSize;
    }

    public void setStopWordListSize(StopWordSize newStopWordListSize) {
        stopWordListSize = newStopWordListSize;
        setStopWordList(stopWordListSize);
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

    public ArrayList<String> addDocumentsFromListAtDictionary(String listPath) {
        ArrayList<String> documentsPathList = Miscellaneous.getListOfLines(listPath);
        ArrayList<String> docIDList = new ArrayList<>();
        if (documentsPathList != null) {
            for (String docPath : documentsPathList) {
                docIDList.add(addDocumentAtDictionary(docPath));
            }
        }
        return docIDList;
    }

    public String addDocumentAtDictionary(String docPath) {
        //        String docID = UUID.nameUUIDFromBytes(docPath.getBytes()).toString().substring(0, 8);
        // WARNING: if we add too document it can explode
        boolean findFreeDocID = false;
        String docID = "";
        int MAX_TRIES = 100;
        for (int i = 0; i < MAX_TRIES; i++) {
            docID = UUID.randomUUID().toString().substring(0, 8);
            if (!this.documentList.contains(docID)) {
                this.documentList.add(docID);
                findFreeDocID = true;
                break;
            }
        }

        if (!findFreeDocID) {
            System.err.println("FAIL THE ASSIGNATION OF A DOC ID");
            return "";
        }


        if (Miscellaneous.DEBUG) {
            System.out.println("\nAdd at the dictionary the document at path:");
            System.out.println(docPath);
            System.out.println("with the alias: " + docID);
        }

        ArrayList<String> tokenList = Tokenization.getListOfTokenFromFile(docPath);
        if (tokenList == null) {
            return "";
        }
        Normalization.normalizeListOfString(tokenList);
        StopWord.removeStopWords(tokenList);

        AtomicInteger positionOfToken = new AtomicInteger(1);

        for (String token : tokenList) {
            PostingList postingList = new PostingList();
            if (dictionary.containsKey(token)) {
                postingList = dictionary.get(token);
            }
            postingList.addPosting(docID, positionOfToken.get());
            dictionary.put(token, postingList);
            positionOfToken.getAndIncrement();
        }

        return docID;
        //TODO: trim the arrays of position of the posting of the document just added
    }

    public String removeDocumentFromDictionary(String docID) {
        try {
            if (!documentList.contains(docID)) {
                throw new Exception("Document ID input not valid");
            }
            Set<String> termList = new HashSet<>(dictionary.keySet());
            for (String term : termList) {
                PostingList termPostingList = dictionary.get(term);
                termPostingList.removeDocID(docID);
                if (termPostingList.size() == 0) {
                    dictionary.remove(term);
                }
            }
            documentList.remove(docID);
            return docID;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "";
        }
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
        System.out.println("//////////////////////////////////\n");
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
        System.out.println("******************************\n");
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

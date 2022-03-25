package dataStructure;

import miscellaneous.Miscellaneous;
import operations.Normalization;
import operations.StopWord;
import operations.Tokenization;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import static operations.StopWord.StopWordSize;
import static operations.StopWord.setStopWordList;

public class Dictionary implements Serializable {

    final int MAX_DOCUMENT_AUXILIARY = 10;
    final int INITIAL_CAPACITY_INDEX = 1024;
    private final HashMap<String, PostingList> mainIndex;
    private final HashMap<String, HashSet<String>> documentList;
    private final HashMap<String, PostingList> auxiliaryIndex;
    private final ArrayList<String> auxiliaryDocumentList;
    private StopWordSize stopWordListSize;

    public Dictionary() {
        auxiliaryIndex = new HashMap<>(INITIAL_CAPACITY_INDEX);
        mainIndex = new HashMap<>(INITIAL_CAPACITY_INDEX);
        documentList = new HashMap<>();
        auxiliaryDocumentList = new ArrayList<>(MAX_DOCUMENT_AUXILIARY);
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

    public ArrayList<String> getDocumentList() {
        return new ArrayList<>(documentList.keySet());
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
        String docID = "";

        // WARNING: if we add too documents it can explode
        int MAX_TRIES = 100;
        boolean findFreeDocID = false;
        for (int i = 0; i < MAX_TRIES; i++) {
            docID = UUID.randomUUID().toString().substring(0, 8);
            if (!this.documentList.containsKey(docID)) {
                findFreeDocID = true;
                break;
            }
        }

        if (!findFreeDocID) {
            System.err.println("FAIL THE ASSIGNATION OF A DOC ID");
            return "";
        }

        Miscellaneous.printDocInfoIfDEBUG(docPath, docID);

        ArrayList<String> tokenList = Tokenization.getTokenListFromFile(docPath);
        if (tokenList == null) {
            System.err.println("The document has not : the document is not added");
            return "";
        }
        Normalization.normalizeListOfString(tokenList);
        StopWord.removeStopWords(tokenList);

        HashSet<String> termListOfDocument = new HashSet<>();

        for (int i = 0; i < tokenList.size(); i++) {
            String token = tokenList.get(i);
            termListOfDocument.add(token);

            PostingList postingList = new PostingList();
            if (auxiliaryIndex.containsKey(token)) {
                postingList = auxiliaryIndex.get(token);
            }
            postingList.addPosting(docID, i + 1);
            auxiliaryIndex.put(token, postingList);
        }

        this.documentList.put(docID, termListOfDocument);
        this.auxiliaryDocumentList.add(docID);

        if (auxiliaryDocumentList.size() > MAX_DOCUMENT_AUXILIARY
                || auxiliaryIndex.size() > INITIAL_CAPACITY_INDEX / 3) {
            mergeIndexes();
        }


//        for (String term : termListOfDocument) {
//            PostingList postingList = dictionary.get(term);
//            postingList.trim(docID);
//            dictionary.replace(term, postingList);
//        }

        return docID;
    }

    public void mergeIndexes() {
        for (String docId : auxiliaryDocumentList) {
            for (String term : documentList.get(docId)) {
                PostingList finalPostingList = auxiliaryIndex.get(term);
                finalPostingList.mergePostingList(mainIndex.get(term));
                mainIndex.put(term, finalPostingList);
            }
        }
        auxiliaryIndex.clear();
        auxiliaryDocumentList.clear();
    }

    public String removeDocumentFromDictionary(String docID) {
        try {
            if (!documentList.containsKey(docID)) {
                throw new Exception("Document ID input not valid");
            }
            HashSet<String> termList = documentList.get(docID);

            HashMap<String, PostingList> correctIndex;

            if (auxiliaryDocumentList.contains(docID)) {
                correctIndex = auxiliaryIndex;
                auxiliaryDocumentList.remove(docID);
            } else {
                correctIndex = mainIndex;
            }

            for (String term : termList) {
                PostingList termPostingList = correctIndex.get(term);
                termPostingList.removePostingByDocID(docID);
                if (termPostingList.size() == 0) {
                    correctIndex.remove(term);
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

        HashSet<String> allTerms = new HashSet<>();
        for (String docId : documentList.keySet()) {
            allTerms.addAll(documentList.get(docId));
        }

        AtomicInteger counter = new AtomicInteger();
        for (String term : allTerms) {
            if (counter.get() % 2 != 0) {
                System.out.print("\t \t \t \t \t");
            }
            System.out.println(term);

            PostingList postingListAux = auxiliaryIndex.get(term);
            if (postingListAux != null)
                postingListAux.printPostingListForDictionary(counter);

            PostingList postingListMain = mainIndex.get(term);
            if (postingListMain != null)
                postingListMain.printPostingListForDictionary(counter);

            counter.getAndIncrement();
        }

        System.out.println("//////////////////////////////////");
        System.out.println("//////////////////////////////////\n");
    }

    public void printDocumentList() {
        System.out.println("\n******* DOCUMENT LIST ********");

        if (this.documentList == null || this.documentList.isEmpty()) {
            System.out.println("The list is empty!");
        } else {
            for (String doc : this.documentList.keySet()) {
                System.out.println(doc);
            }
        }
        System.out.println("******************************\n");
    }

    public boolean containsTerm(String term) {
        return mainIndex.containsKey(term) || auxiliaryIndex.containsKey(term);
    }

    public PostingList getPostingList(String term) {
        PostingList result = new PostingList();
        if (mainIndex.containsKey(term)) {
            result.mergePostingList(mainIndex.get(term));
        }
        if (auxiliaryIndex.containsKey(term)) {
            result.mergePostingList(auxiliaryIndex.get(term));
        }

        return result;
    }
}

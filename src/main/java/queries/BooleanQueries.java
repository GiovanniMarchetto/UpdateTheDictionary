package queries;

import dataStructure.Dictionary;
import dataStructure.PostingList;
import miscellaneous.QueryMode;
import miscellaneous.SortBySize;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

public class BooleanQueries {

    public static ArrayList<String> queryTerm(Dictionary dictionary, String term) {
        PostingList postingList = dictionary.getPostingList(term);
        String[] docIDList = postingList.getDocIDListAsArray();
        return new ArrayList<>(Arrays.asList(docIDList));
    }

    public static ArrayList<String> booleanQuery(Dictionary dictionary, ArrayList<String> words, QueryMode queryMode) {
        ArrayList<String> resultDocList;
        switch (queryMode) {

            case AND -> {
                words.sort(new SortBySize()); // heuristic: bigger-->less documents should contain it
                // first word
                resultDocList = queryTerm(dictionary, words.get(0));

                for (String word : words) {
                    if (resultDocList.isEmpty()) {
                        return resultDocList;
                    }

                    PostingList postingList = dictionary.getPostingList(word);
                    Set<String> docIDList = postingList.getDocIDListAsSet();
                    resultDocList.removeIf(docID -> !docIDList.contains(docID));
                }
            }

            case OR -> {
                resultDocList = new ArrayList<>();

                for (String word : words) {
                    if (!dictionary.containsTerm(word)) {
                        continue;
                    }
                    PostingList postingList = dictionary.getPostingList(word);
                    String[] docIDList = postingList.getDocIDListAsArray();
                    for (String docId : docIDList) {
                        if (!resultDocList.contains(docId)) {
                            resultDocList.add(docId);
                        }
                    }
                }
            }

            case NOT -> {
                resultDocList = dictionary.getDocumentList();

                for (String word : words) {
                    if (dictionary.containsTerm(word)) {
                        PostingList postingList = dictionary.getPostingList(word);
                        String[] docIDList = postingList.getDocIDListAsArray();
                        for (String docID : docIDList) {
                            resultDocList.remove(docID);
                        }
                    }
                }
            }

            default -> throw new IllegalStateException("Unexpected value: " + queryMode);
        }

        return resultDocList;
    }

    public static ArrayList<String> queryBetweenTwoListOfDocID(
            ArrayList<String> list1, ArrayList<String> list2, QueryMode queryMode) {
        switch (queryMode) {
            case AND -> {
                list1.removeIf(docId -> !list2.contains(docId));
                return list1;
            }
            case OR -> {
                list2.removeIf(list1::contains);
                list1.addAll(list2);
                return list1;
            }
            case NOT -> {
                //suppose the first element is the document list of dictionary
                list1.removeIf(list2::contains);
                return list1;
            }
            default -> throw new IllegalStateException("Unexpected value: " + queryMode);
        }
    }

}

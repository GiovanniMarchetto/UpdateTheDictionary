package operations;

import dataStructure.Dictionary;
import dataStructure.PostingList;

import java.util.ArrayList;
import java.util.Set;

public class PhrasalQueries {

    public static PostingList phrasalQuery(Dictionary dictionary, ArrayList<String> phrase) {

        String firstWord = phrase.get(0);
        PostingList antePostingList = dictionary.getPostingList(firstWord);

        // Suppose that is ordered
        for (int i = 1, wordsSize = phrase.size(); i < wordsSize; i++) {
            PostingList postPostingList = dictionary.getPostingList(phrase.get(i));
            antePostingList = searchPQ(antePostingList, postPostingList);
        }

        // Build the final answer with the start of the phrase
        PostingList firstPostingList = dictionary.getPostingList(firstWord);
        PostingList answerPostingList = new PostingList();
        int totalWords = phrase.size();
        for (String docID : firstPostingList.getDocIDListAsSet()) {

            if (antePostingList.getDocIDListAsSet().contains(docID)) {

                ArrayList<Integer> anteListOfPosition = antePostingList.getListOfPositionOfDocID(docID);
                int anteLastPosition = anteListOfPosition.get(anteListOfPosition.size() - 1);

                for (int startPosition : firstPostingList.getListOfPositionOfDocID(docID)) {

                    int endPosition = startPosition + totalWords - 1;

                    if (endPosition > anteLastPosition) {
                        break;
                    }

                    if (anteListOfPosition.contains(endPosition)) {
                        answerPostingList.addPosting(docID, startPosition);
                    }
                }
            }
        }


        return answerPostingList;
    }

    private static PostingList searchPQ(PostingList ante, PostingList post) {
        PostingList finalPostingList = new PostingList();

        Set<String> docIDListAnte = ante.getDocIDListAsSet();
        Set<String> docIDListPost = post.getDocIDListAsSet();

        for (String docID : docIDListAnte) {
            if (docIDListPost.contains(docID)) {
                ArrayList<Integer> listOfPositionAnte = ante.getListOfPositionOfDocID(docID);
                ArrayList<Integer> listOfPositionPost = post.getListOfPositionOfDocID(docID);
                int lastPostPosition = listOfPositionPost.get(listOfPositionPost.size() - 1);

                for (int position : listOfPositionAnte) {
                    int nextPosition = position + 1;
                    if (nextPosition > lastPostPosition) {
                        break;
                    }
                    if (listOfPositionPost.contains(nextPosition)) {
                        finalPostingList.addPosting(docID, nextPosition);
                    }
                }
            }
        }

        return finalPostingList;
    }

}

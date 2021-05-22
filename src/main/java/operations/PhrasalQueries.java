package operations;

import dataStructure.Dictionary;
import dataStructure.PostingList;
import miscellaneous.Miscellaneous;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

public class PhrasalQueries {

    public static PostingList phrasalQuery(Dictionary dictionary, String phraseString) {

        ArrayList<String> phrase = getTokenListFromPhrase(phraseString);

        String firstWord = phrase.get(0);
        PostingList antePostingList = dictionary.getPostingList(firstWord);

        // Suppose that is ordered
        for (int i = 1, wordsSize = phrase.size(); i < wordsSize; i++) {
            PostingList postPostingList = dictionary.getPostingList(phrase.get(i));
            antePostingList = matchingPostingListOfNextWord(antePostingList, postPostingList);
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

    private static PostingList matchingPostingListOfNextWord(PostingList ante, PostingList post) {
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

    private static PostingList queryANDPostingListWithDocumentList(PostingList postingList, ArrayList<String> documentList) {
        PostingList resultPostingList= new PostingList();
        Set<String> docIDList = postingList.getDocIDListAsSet();

        for (String docID : docIDList) {
            //if the document is present in the document list AND in the posting list it remain
            if (documentList.contains(docID)) {
                resultPostingList.addPosting(docID,postingList.getListOfPositionOfDocID(docID));
            }
        }

        return resultPostingList;
    }

    public static PostingList quickPhraseQuery(Dictionary dictionary, String phraseString) {

        ArrayList<String> phrase = getTokenListFromPhrase(phraseString);

        //quick search of phrase without stop words
        Miscellaneous.doNormalizationOnArrayListOfString(phrase);

        ArrayList<String> phraseTemp = new ArrayList<>(phrase);
        Miscellaneous.removeStopWords(phraseTemp);

        ArrayList<String> baseDocumentList = BooleanQueries.queryAND(dictionary, phraseTemp);
        String firstWord = phrase.get(0);
        PostingList antePostingList = queryANDPostingListWithDocumentList(dictionary.getPostingList(firstWord), baseDocumentList);

        // Suppose that is ordered
        for (int i = 1; i < phrase.size(); i++) {
            PostingList postPostingList = dictionary.getPostingList(phrase.get(i));
            antePostingList = matchingPostingListOfNextWord(antePostingList, postPostingList);
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

    public static ArrayList<String> getTokenListFromPhrase(String phrase) {
        ArrayList<String> phraseArray = new ArrayList<>(Arrays.asList(phrase.split(" ")));
        Miscellaneous.doNormalizationOnArrayListOfString(phraseArray);
        return phraseArray;
    }
}
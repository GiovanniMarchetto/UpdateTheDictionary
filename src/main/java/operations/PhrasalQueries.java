package operations;

import dataStructure.Dictionary;
import dataStructure.PostingList;
import miscellaneous.Miscellaneous;
import miscellaneous.StopWord;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

public class PhrasalQueries {

    public static PostingList phrasalQuery(Dictionary dictionary, String phraseString) {

        ArrayList<String> phrase = getTokenListFromPhrase(phraseString);

        String firstWord = phrase.get(0);
        PostingList filteredPostingList = dictionary.getPostingList(firstWord);

        // Suppose that is ordered
        for (int i = 1, wordsSize = phrase.size(); i < wordsSize; i++) {
            PostingList postPostingList = dictionary.getPostingList(phrase.get(i));
            filteredPostingList = matchingPostingListOfTwoAdjacentWords(filteredPostingList, postPostingList);
        }

        // Build the final answer with the positions of Posting List that are hte start of the phrase
        return getAnswerPostingListWithPositionOfTheStartOfPhrase(dictionary, phrase.size(), firstWord, filteredPostingList);
    }

    private static PostingList matchingPostingListOfTwoAdjacentWords(PostingList firstPL, PostingList secondPL) {
        PostingList finalPostingList = new PostingList();

        Set<String> firstDocIDList = firstPL.getDocIDListAsSet();
        Set<String> secondDocIDList = secondPL.getDocIDListAsSet();

        for (String docID : firstDocIDList) {
            if (secondDocIDList.contains(docID)) {

                ArrayList<Integer> firstListOfPosition = firstPL.getListOfPositionOfDocID(docID);
                ArrayList<Integer> secondListOfPosition = secondPL.getListOfPositionOfDocID(docID);

                int lastPostPosition = secondListOfPosition.get(secondListOfPosition.size() - 1);

                for (int position : firstListOfPosition) {
                    int nextPosition = position + 1;
                    if (nextPosition > lastPostPosition) {
                        break;
                    }
                    if (secondListOfPosition.contains(nextPosition)) {
                        finalPostingList.addPosting(docID, nextPosition);
                    }
                }
            }
        }

        return finalPostingList;
    }

    private static PostingList getAnswerPostingListWithPositionOfTheStartOfPhrase(
            Dictionary dictionary, int totalWords, String firstWord, PostingList filteredPostingList) {

        PostingList firstPostingList = dictionary.getPostingList(firstWord);
        PostingList answerPostingList = new PostingList();
        for (String docID : filteredPostingList.getDocIDListAsSet()) {

            if (firstPostingList.getDocIDListAsSet().contains(docID)) {

                ArrayList<Integer> anteListOfPosition = filteredPostingList.getListOfPositionOfDocID(docID);
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

    /*
     *   The idea is to speed up the search for longer phrases.
     *   To do this, we tried to filter the documents first
     *       based on the longest and therefore theoretically the rarest words.
     *   In addition, to speed up this alternative search method,
     *       a weaker search was made, i.e. removing the stop words,
     *       but maintaining the positioning of the word
     *      So the possible error is to false positive like:
     *          phrase: "the cat is on the table"
     *          false positive: "the cat is at a table"
     *   In any case, the various tests have confirmed that searching
     *       with the classic method (with words in order and
     *       without previous filtering) is more efficient
     *       even on long sentences.
     * */

    public static PostingList alternativePhrasalQuery(Dictionary dictionary, String phraseString) {

        ArrayList<String> phrase = getTokenListFromPhrase(phraseString);

        //quick search of phrase without stop words
        Miscellaneous.doNormalizationOnArrayListOfString(phrase);

        ArrayList<String> phraseTemp = new ArrayList<>(phrase);
        StopWord.removeStopWords(phraseTemp);

        ArrayList<String> baseDocumentList = BooleanQueries.queryAND(dictionary, phraseTemp);
        String firstWord = phrase.get(0);
        String firstWordTemp = phraseTemp.get(0);
        PostingList filteredPostingList = queryANDPostingListWithDocumentList(dictionary.getPostingList(firstWordTemp), baseDocumentList);

        for (int i = 1; i < phraseTemp.size(); i++) {
            PostingList postPostingList = dictionary.getPostingList(phraseTemp.get(i));
            PostingList finalPostingList = new PostingList();

            Set<String> docIDListAnte = filteredPostingList.getDocIDListAsSet();
            Set<String> docIDListPost = postPostingList.getDocIDListAsSet();

            for (String docID : docIDListAnte) {
                if (docIDListPost.contains(docID)) {
                    ArrayList<Integer> listOfPositionAnte = filteredPostingList.getListOfPositionOfDocID(docID);
                    ArrayList<Integer> listOfPositionPost = postPostingList.getListOfPositionOfDocID(docID);
                    int lastPostPosition = listOfPositionPost.get(listOfPositionPost.size() - 1);

                    for (int position : listOfPositionAnte) {
                        int postPosition = position + (phrase.indexOf(phraseTemp.get(i))-phrase.indexOf(phraseTemp.get(i-1)));
                        if (postPosition > lastPostPosition) {
                            break;
                        }
                        if (listOfPositionPost.contains(postPosition)) {
                            finalPostingList.addPosting(docID, postPosition);
                        }
                    }
                }
            }

            filteredPostingList = finalPostingList;
        }

        int positionOfLastWord = phrase.indexOf(phraseTemp.get(phraseTemp.size() - 1)) + 1;

        return getAnswerPostingListWithPositionOfTheStartOfPhrase(dictionary, positionOfLastWord, firstWord, filteredPostingList);

    }

    private static PostingList queryANDPostingListWithDocumentList(PostingList postingList, ArrayList<String> documentList) {
        PostingList resultPostingList = new PostingList();
        Set<String> docIDList = postingList.getDocIDListAsSet();

        for (String docID : docIDList) {
            //if the document is present in the document list AND in the posting list it remains
            if (documentList.contains(docID)) {
                resultPostingList.addPosting(docID, postingList.getListOfPositionOfDocID(docID));
            }
        }

        return resultPostingList;
    }

    public static ArrayList<String> getTokenListFromPhrase(String phrase) {
        ArrayList<String> phraseArray = new ArrayList<>(Arrays.asList(phrase.split(" ")));
        Miscellaneous.doNormalizationOnArrayListOfString(phraseArray);
        return phraseArray;
    }
}
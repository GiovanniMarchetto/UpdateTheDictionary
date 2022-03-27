package queries;

import dataStructure.Dictionary;
import dataStructure.PostingList;
import operations.StopWord;
import operations.Tokenization;

import java.util.ArrayList;
import java.util.Set;

public class PhrasalQueries {

    public static PostingList phrasalQuery(Dictionary dictionary, String phraseString) {

        ArrayList<String> phrase = Tokenization.getNormalizeTokenListFromPhrase(phraseString);
        StopWord.removeStopWords(phrase);

        String firstWord = phrase.get(0);
        PostingList filteredPostingList = dictionary.getPostingList(firstWord);

        // Suppose that is ordered
        for (int i = 1, wordsSize = phrase.size(); i < wordsSize; i++) {
            PostingList postPostingList = dictionary.getPostingList(phrase.get(i));
            filteredPostingList = matchingPostingListOfTwoWords(filteredPostingList, postPostingList, 0);
        }

        // Build the final answer with the positions of Posting List that are hte start of the phrase
        return getAnswerPostingListWithPositionOfTheStartOfPhrase(dictionary, phrase.size(), firstWord, filteredPostingList);
    }

    private static PostingList matchingPostingListOfTwoWords(PostingList firstPL, PostingList secondPL, int numberOfWordBetween) {
        PostingList finalPostingList = new PostingList();

        Set<String> firstDocIDList = firstPL.getDocIDListAsSet();
        Set<String> secondDocIDList = secondPL.getDocIDListAsSet();

        for (String docID : firstDocIDList) {
            if (secondDocIDList.contains(docID)) {

                ArrayList<Integer> firstListOfPosition = firstPL.getListOfPositionOfDocID(docID);
                ArrayList<Integer> secondListOfPosition = secondPL.getListOfPositionOfDocID(docID);

                int lastPostPosition = secondListOfPosition.get(secondListOfPosition.size() - 1);

                for (int position : firstListOfPosition) {
                    int nextPosition = position + 1 + numberOfWordBetween;
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
     *   To do this, it's applied a deletion of stop words
     *      with the expanded stop word list
     *      but maintaining the positioning of the word
     *   So the possible error is to false positive like:
     *          phrase: "the cat is on the table"
     *          false positive: "the cat is at a table".
     *   In any case, the various tests have confirmed that searching
     *       with the classic method (with words in order and
     *       without previous filtering) is more efficient
     *       even on long sentences.
     * */

    public static PostingList phrasalQueryWithAlwaysRemovingStopWords(Dictionary dictionary, String phraseString) {

        ArrayList<String> phrase = Tokenization.getNormalizeTokenListFromPhrase(phraseString);
        StopWord.removeStopWords(phrase);

        String firstWordNonStopWord = null;
        int indexOfFirstWord = -1;
        for (int i = 0; i < phrase.size(); i++) {
            String s = phrase.get(i);
            if (!StopWord.extendedStopWordList.contains(s)) {
                firstWordNonStopWord = s;
                indexOfFirstWord = i;
                break;
            }
        }
        if (firstWordNonStopWord == null) {
            return null;
        }
        PostingList filteredPostingList = dictionary.getPostingList(firstWordNonStopWord);

        int indexOfLastWordExamined = indexOfFirstWord;

        for (int i = indexOfFirstWord + 1, wordsSize = phrase.size(); i < wordsSize; i++) {

            if (!StopWord.extendedStopWordList.contains(phrase.get(i))) {
                int numberOfWordsBetween = i - indexOfLastWordExamined - 1;
                indexOfLastWordExamined = i;

                PostingList postPostingList = dictionary.getPostingList(phrase.get(i));
                filteredPostingList = matchingPostingListOfTwoWords(filteredPostingList, postPostingList, numberOfWordsBetween);
            }
        }

        int positionOfLastWord = indexOfLastWordExamined;

        return getAnswerPostingListWithPositionOfTheStartOfPhrase(dictionary, positionOfLastWord, firstWordNonStopWord, filteredPostingList);

    }

}
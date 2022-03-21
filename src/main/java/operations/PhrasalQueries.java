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
        for (String docID : antePostingList.getDocIDListAsSet()) {

            if (firstPostingList.getDocIDListAsSet().contains(docID)) {

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
        Miscellaneous.removeStopWords(phraseTemp);

        ArrayList<String> baseDocumentList = BooleanQueries.queryAND(dictionary, phraseTemp);
        String firstWord = phrase.get(0);
        String firstWordTemp = phraseTemp.get(0);
        PostingList antePostingList = queryANDPostingListWithDocumentList(dictionary.getPostingList(firstWordTemp), baseDocumentList);

        for (int i = 1; i < phraseTemp.size(); i++) {
            PostingList postPostingList = dictionary.getPostingList(phraseTemp.get(i));
            PostingList finalPostingList = new PostingList();

            Set<String> docIDListAnte = antePostingList.getDocIDListAsSet();
            Set<String> docIDListPost = postPostingList.getDocIDListAsSet();

            for (String docID : docIDListAnte) {
                if (docIDListPost.contains(docID)) {
                    ArrayList<Integer> listOfPositionAnte = antePostingList.getListOfPositionOfDocID(docID);
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

            antePostingList = finalPostingList;
        }

        // Build the final answer with the start of the phrase
        PostingList firstPostingList = dictionary.getPostingList(firstWord);
        PostingList answerPostingList = new PostingList();
        int positionOfWordOfAntePostingList = phrase.indexOf(phraseTemp.get(phraseTemp.size()-1));
        for (String docID : antePostingList.getDocIDListAsSet()) {

            if (firstPostingList.getDocIDListAsSet().contains(docID)) {

                ArrayList<Integer> anteListOfPosition = antePostingList.getListOfPositionOfDocID(docID);
                int anteLastPosition = anteListOfPosition.get(anteListOfPosition.size() - 1);

                for (int startPosition : firstPostingList.getListOfPositionOfDocID(docID)) {

                    int endPosition = startPosition + positionOfWordOfAntePostingList;

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
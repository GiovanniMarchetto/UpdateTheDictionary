import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

public class BooleanQueries {

    public static ArrayList<String> queryAND(Dictionary dictionary, ArrayList<String> words) {

        // first word
        String firstWord = words.get(0);
        PostingList firstPostingList = dictionary.getPostingList(firstWord);
        String[] firstDocIDList = firstPostingList.getDocIDList();
        ArrayList<String> resultDocList = new ArrayList<>(Arrays.asList(firstDocIDList));

        for (String word : words) {
            if (resultDocList.isEmpty()) {
                return resultDocList;
            }

            PostingList postingList = dictionary.getPostingList(word);
            Set<String> docIDList = postingList.getDocIDListAsSet();
            resultDocList.removeIf(docID -> !docIDList.contains(docID));
        }

        return resultDocList;
    }

    public static ArrayList<String> queryOR(Dictionary dictionary, ArrayList<String> words) {
        ArrayList<String> resultDocList = new ArrayList<>();

        for (String word : words) {
            if (!dictionary.containsTerm(word)) {
                continue;
            }
            PostingList postingList = dictionary.getPostingList(word);
            String[] docIDList = postingList.getDocIDList();
            for (String docId : docIDList) {
                if (!resultDocList.contains(docId)) {
                    resultDocList.add(docId);
                }
            }
        }
        return resultDocList;
    }

    public static ArrayList<String> queryNOT(Dictionary dictionary, ArrayList<String> words) {
        ArrayList<String> resultDocList = dictionary.getDocumentList();

        for (String word : words) {
            if (dictionary.containsTerm(word)) {
                PostingList postingList = dictionary.getPostingList(word);
                String[] docIDList = postingList.getDocIDList();
                for (String docID : docIDList) {
                    resultDocList.remove(docID);
                }
            }
        }
        return resultDocList;
    }
}

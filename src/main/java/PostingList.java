import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class PostingList {

    private HashMap<String, ArrayList<Integer>> postingList;

    public PostingList() {
        postingList = new HashMap<>();
    }

    public void addPosting(String docID, int positionOfToken) {
        ArrayList<Integer> listPositions = new ArrayList<>();
        if (postingList.containsKey(docID)) {
            listPositions = postingList.get(docID);
        }
        listPositions.add(positionOfToken);
        this.postingList.put(docID, listPositions);
    }

    public int size() {
        return this.postingList.size();
    }

    public boolean contains(String docID) {
        return this.postingList.containsKey(docID);
    }

    public void printPostingListForDictionary(AtomicInteger counter) {
        this.postingList.forEach((docID, listPosition) -> {
            if (counter.get() % 2 != 0) System.out.print("\t \t \t \t \t");
            System.out.println("|---" + docID + " " + listPosition);
        });
    }
}

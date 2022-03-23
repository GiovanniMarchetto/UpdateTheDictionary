package dataStructure;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class PostingList implements Serializable {

    private HashMap<String, ArrayList<Integer>> postingList;

    public PostingList() {
        postingList = new HashMap<>();
    }

    public HashMap<String, ArrayList<Integer>> getPostingList() {
        return postingList;
    }

    public void setPostingList(HashMap<String, ArrayList<Integer>> postingList) {
        this.postingList = postingList;
    }

    public void addPosting(String docID, int positionOfToken) {
        ArrayList<Integer> listPositions = new ArrayList<>();
        if (postingList.containsKey(docID)) {
            listPositions = postingList.get(docID);
        }
        listPositions.add(positionOfToken);
        this.postingList.put(docID, listPositions);
    }

    public void addPosting(String docID, ArrayList<Integer> listPositionsToAdd) {
        ArrayList<Integer> listPositions = new ArrayList<>();
        if (postingList.containsKey(docID)) {
            listPositions = postingList.get(docID);
        }
        listPositions.addAll(listPositionsToAdd);
        listPositions.sort(Integer::compareTo);
        this.postingList.put(docID, listPositions);
    }

    public int size() {
        return this.postingList.size();
    }

    public boolean contains(String docID) {
        return this.postingList.containsKey(docID);
    }

    public String[] getDocIDListAsArray() {
        return postingList.keySet().toArray(new String[0]);
    }

    public ArrayList<String> getDocIDListAsArrayList() {
        return new ArrayList<>(postingList.keySet());
    }

    public Set<String> getDocIDListAsSet() {
        return postingList.keySet();
    }

    public ArrayList<Integer> getListOfPositionOfDocID(String docID) {
        return postingList.get(docID);
    }

    public boolean removePositionOfDocID(Integer positionToRemove, String docID) {
        ArrayList<Integer> oldList = postingList.get(docID);
        oldList.remove(positionToRemove);
        if (oldList.isEmpty()) {
            return removeDocID(docID);
        }
        postingList.replace(docID, oldList);
        return true;
    }

    public boolean removeDocID(String docID) {
        try {
            postingList.remove(docID);
        } catch (NullPointerException e) {
            return false;
        }
        return true;
    }

    public void printPostingListForDictionary(AtomicInteger counter) {
        this.postingList.forEach((docID, listPosition) -> {
            if (counter.get() % 2 != 0) {
                System.out.print("\t \t \t \t \t");
            }
            System.out.println("|---" + docID + " " + listPosition);
        });
    }

    public void printPostingList() {
        this.postingList.forEach((docID, listPosition) -> System.out.println("|---" + docID + " " + listPosition));
    }

}

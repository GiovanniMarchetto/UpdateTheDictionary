package Misc;

import java.util.Comparator;

public class SortBySize implements Comparator<String> {
    public int compare(String a, String b) {
        if (b.length() < a.length()) {
            return -1;
        }
        return 0;
    }
}

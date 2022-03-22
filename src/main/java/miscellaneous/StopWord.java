package miscellaneous;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StopWord {

    public static final List<String> extendedStopWordList = getStopWordListFromSizeString("extended");

    private static List<String> stopWordList;    // https://www.ranks.nl/stopwords

    public static List<String> getStopWordList() {
        return stopWordList;
    }

    public static void setStopWordList(StopWordSize stopWordListSize) {
        String stopWordSize;

        switch (stopWordListSize) {
            case NONE -> {
                stopWordList = new ArrayList<>();
                return;
            }
            case ESSENTIAL -> stopWordSize = "essential";
            case STANDARD -> stopWordSize = "standard";
            case EXTENDED -> stopWordSize = "extended";
            default -> throw new IllegalStateException("Unexpected value: " + stopWordListSize);
        }

        stopWordList = getStopWordListFromSizeString(stopWordSize);
    }

    private static List<String> getStopWordListFromSizeString(String stopWordSize) {
        ArrayList<String> list = new ArrayList<>();

        try {
            URL url = Miscellaneous.class.getClassLoader().getResource("stopWords_" + stopWordSize + ".txt");
            if (url == null) {
                System.err.println("No stop word list found!");
                return list;
            }
            Scanner s = new Scanner(new File(url.getPath()));
            while (s.hasNext()) {
                list.add(s.next());
            }
            s.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return list;
    }

    public static void removeStopWords(ArrayList<String> list) {
        if (stopWordList != null) {
            list.removeIf(stopWordList::contains);
        }
    }

    public enum StopWordSize {
        NONE,
        ESSENTIAL,
        STANDARD,
        EXTENDED
    }

}

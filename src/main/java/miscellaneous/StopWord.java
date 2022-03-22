package miscellaneous;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StopWord {

    private static StopWordSize stopWordListSize = StopWordSize.STANDARD;
    private static List<String> stopWordList = getStopWordList();    // https://www.ranks.nl/stopwords

    public static void setStopWordListSize(StopWordSize stopWordListSize) {
        if (stopWordListSize == null) return;
        StopWord.stopWordListSize = stopWordListSize;
        stopWordList = getStopWordList();
    }

    private static List<String> getStopWordList() {
        ArrayList<String> list = new ArrayList<>();
        String stopWordSize;

        switch (stopWordListSize) {
            case ESSENTIAL -> stopWordSize = "essential";
            case STANDARD -> stopWordSize = "standard";
            case EXTENDED -> stopWordSize = "extended";
            default -> throw new IllegalStateException("Unexpected value: " + stopWordListSize);
        }

        try {
            URL url = Miscellaneous.class.getClassLoader().getResource("stopWords_" + stopWordSize + ".txt");
            if (url == null) {
                System.err.println("No stop word list found!");
                return null;
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
        if (StopWord.stopWordList != null) {
            list.removeIf(StopWord.stopWordList::contains);
        }
    }

    public enum StopWordSize {
        ESSENTIAL,
        STANDARD,
        EXTENDED
    }

}

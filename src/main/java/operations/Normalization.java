package operations;

import java.util.ArrayList;
import java.util.Objects;

public class Normalization {


    public static void normalizeListOfString(ArrayList<String> stringArrayList) {
        String halfWord = "";
        boolean halfWordFind = false;

        for (int i = 0, stringArrayListSize = stringArrayList.size(); i < stringArrayListSize; i++) {
            String normalizeToken = getNormalizeToken(stringArrayList.get(i));

            if (normalizeToken.endsWith("-") && i != stringArrayList.size() - 1) {
                halfWord = normalizeToken.substring(0, normalizeToken.length() - 2);
                halfWordFind = true;
                continue;
            }

            if (halfWordFind) {
                normalizeToken = halfWord + normalizeToken;
                halfWord = "";
                halfWordFind = false;
            }

            stringArrayList.set(i, normalizeToken);
        }

        stringArrayList.removeIf(s -> Objects.equals(s, ""));
        stringArrayList.trimToSize();
    }

    public static String getNormalizeToken(String token) {
        token = token.toLowerCase();// no distinction of names

        //it's a dictionary, so no numbers
        token = token.replaceAll("[^a-z'-]", "");
        //TODO: plural-porter stemmer

        if (token.equals("'") || token.equals("-")) {
            token = "";
        }

        return token;
    }

}

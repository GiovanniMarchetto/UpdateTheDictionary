package miscellaneous;

import java.util.ArrayList;

public class Normalization {
    private static final String[] SPECIAL_CHARACTERS = {
            ".", ",", ";", ":", "(", ")", "!", "?", "/", "[", "]", "{", "}",
            "'", "\""
    };

    public static void normalizeListOfString(ArrayList<String> stringArrayList) {
        for (int i = 0, stringArrayListSize = stringArrayList.size(); i < stringArrayListSize; i++) {
            stringArrayList.set(i, getNormalizeToken(stringArrayList.get(i)));
        }
    }

    public static String getNormalizeToken(String token) {
        token = token.toLowerCase();// no distinction of names
        //TODO: plural?

        //TODO: special character also in the middle of the words? and 's.. ecc
        for (String suffix : SPECIAL_CHARACTERS) {
            if (token.startsWith(suffix)) {
                token = token.substring(1);
            }
            if (token.endsWith(suffix)) {
                token = token.substring(0, token.length() - 1);
            }
        }

        return token;
    }

}

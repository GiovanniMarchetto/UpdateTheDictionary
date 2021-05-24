package miscellaneous;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Miscellaneous {
    public static final boolean DEBUG = false;

    private static final String[] SPECIAL_CHARACTERS = {
            ".", ",", ";", ":", "(", ")", "!", "?", "/", "[", "]", "{", "}",
            "'", "\""
    };

    private static final String[] STOP_WORDS = {
            "the", "and", "or", "a", "an",
            "i", "you", "he", "she", "it", "we", "they",
            "is", "are",
            "to","on"
    };

    private static final List<String> STOP_WORDS_LIST = Arrays.asList(STOP_WORDS);

    public static void doNormalizationOnArrayListOfString(ArrayList<String> stringArrayList){
        for (int i = 0, stringArrayListSize = stringArrayList.size(); i < stringArrayListSize; i++) {
            stringArrayList.set(i,getNormalizeToken(stringArrayList.get(i)));
        }
    }

    public static String getNormalizeToken(String token) {
        token = token.toLowerCase();// no distinction of names
        //TODO: plural? stop word?

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

    public static void removeStopWords(ArrayList<String> list){
        list.removeIf(STOP_WORDS_LIST::contains);
    }


    public static ArrayList<String> getListOfTokenFromFile(String docPath) { // alias tokenization
        Scanner inputStream;
        try {
            inputStream = new Scanner(new File(docPath));
        } catch (FileNotFoundException e) {
            System.out.println("Document not found " + docPath);
            return null;
//            System.exit(0);
        }

        ArrayList<String> tokenList = new ArrayList<>();
        while (inputStream.hasNext()) {
            String token = inputStream.next();
            tokenList.add(token);
        }
        inputStream.close();
        return tokenList;
    }

    public static ArrayList<String> getListOfLines(String listPath) {
        Scanner inputStream;
        try {
            inputStream = new Scanner(new File(listPath));
        } catch (FileNotFoundException e) {
            System.out.println("List of document not found " + listPath);
            return null;
        }

        ArrayList<String> listDocuments = new ArrayList<>();
        while (inputStream.hasNextLine()) {
            String token = inputStream.nextLine();
            listDocuments.add(token);
        }
        inputStream.close();
        return listDocuments;
    }

    public static void testTitleFormatting(String title) {
        int maxWidth = 70;
        int titleWidth = title.length();
        int afterIndex = (maxWidth - titleWidth) / 2;
        int beforeIndex = maxWidth - titleWidth - afterIndex;
        String separator = "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++";

        System.out.printf("%s   %s   %s%n", separator.substring(0, beforeIndex), title, separator.substring(0, afterIndex));
    }
}

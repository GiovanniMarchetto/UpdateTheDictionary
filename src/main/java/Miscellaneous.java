import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Miscellaneous {
    public static final boolean DEBUG = false;

    private static final String[] SPECIAL_CHARACTERS = {".", ",", ";", ":", "(", ")", "!", "?", "/", "[", "]", "{", "}"};

    public static String getNormalizeToken(String token) {
        token = token.toLowerCase();
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


    public static ArrayList<String> getListOfTokenFromFile(String docPath) {
        Scanner inputStream = null;
        try {
            inputStream = new Scanner(new File(docPath));
        } catch (FileNotFoundException e) {
            System.out.println("Document not found " + docPath);
            System.exit(0);
        }

        ArrayList<String> tokenList = new ArrayList<>();
        while (inputStream.hasNext()) {
            String token = inputStream.next();
            tokenList.add(token);
        }
        inputStream.close();
        return tokenList;
    }
}

import java.util.concurrent.atomic.AtomicInteger;

public class Miscellaneous {
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

    public static void printDictionary() {
        System.out.println("\n//////////////////////////////");
        System.out.println("//////////DICTIONARY//////////");
        System.out.println("//////////////////////////////");

        AtomicInteger counter = new AtomicInteger();
        Dictionary.dictionary.forEach((key, postingList) -> {
            if (counter.get() % 2 != 0) System.out.println("\t \t \t \t \t" + key);
            else System.out.println(key);
            for (String s : postingList) {
                if (counter.get() % 2 != 0) System.out.println("\t \t \t \t \t" + "|---" + s);
                else System.out.println("|---" + s);
            }
            counter.getAndIncrement();
        });

        System.out.println("//////////////////////////////");
        System.out.println("//////////////////////////////");
    }

    public static void printDocumentList() {
        System.out.println("\n******************************");
        System.out.println("******* DOCUMENT LIST ********");

        if (Dictionary.documentList == null || Dictionary.documentList.isEmpty()) {
            System.out.println("The list is empty!");
        } else {
            for (String doc : Dictionary.documentList) {
                System.out.println(doc);
            }
        }
        System.out.println("******************************");
    }
}

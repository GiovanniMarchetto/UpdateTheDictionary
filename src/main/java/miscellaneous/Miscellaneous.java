package miscellaneous;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Miscellaneous {
    public static boolean DEBUG = false;

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

    public static void welcomeOutput() {
        System.out.println("\nWelcome to the updating dictionary project.");
        System.out.println(
                "\n" +
                        "    __________________   __________________\n" +
                        ".-/|                  \\ /                  |\\-.\n" +
                        "||||                   |                   ||||\n" +
                        "||||      ~~*~~        |       ~~*~~       ||||\n" +
                        "||||   UPDATING THE    |     DICTIONARY    ||||\n" +
                        "||||                   |                   ||||\n" +
                        "||||                   |                   ||||\n" +
                        "||||    --==*==--      |     --==*==--     ||||\n" +
                        "||||                   |                   ||||\n" +
                        "||||                   |                   ||||\n" +
                        "||||                   |                   ||||\n" +
                        "||||                   |                   ||||\n" +
                        "||||__________________ | __________________||||\n" +
                        "||/===================\\|/===================\\||\n" +
                        "`--------------------~___~-------------------''");
    }

}

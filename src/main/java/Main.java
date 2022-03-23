import dataStructure.Dictionary;
import dataStructure.PostingList;
import miscellaneous.Miscellaneous;
import miscellaneous.QueryMode;
import miscellaneous.SaveReadPersistentDictionary;
import operations.Tokenization;
import queries.BooleanQueries;
import queries.PhrasalQueries;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class Main {

    private static QueryMode queryMode = QueryMode.AND;

    private static Dictionary dictionary;
    private static SaveReadPersistentDictionary xd;
    private static Scanner keyboard;
    private static String input;
    private static AtomicBoolean continueOperation;

    public static void main(String[] args) {
        Miscellaneous.welcomeOutput();

        dictionary = new Dictionary();
        keyboard = new Scanner(System.in);
        continueOperation = new AtomicBoolean(true);

        xd = recoverDictionary();

        continueOperation.set(true);

        printCommandPalette();

        while (continueOperation.get()) {

            System.out.print("\nCommand: ");
            inputIsNextLine();
            switch (input) {
                case "add" -> {
                    System.out.println("Path of the document to add at the dictionary:");
                    inputIsNextLine();
                    String newDocID = dictionary.addDocumentAtDictionary(input);
                    if (!newDocID.equals("")) {
                        System.out.println("The docID " + newDocID + " has been successfully added");
                    }
                }
                case "addList" -> {
                    System.out.println("Path of the document that contain the list of documents to add at the dictionary:");
                    inputIsNextLine();
                    ArrayList<String> listDocID = dictionary.addDocumentsFromListAtDictionary(input);
                    for (String docID : listDocID) {
                        System.out.println("The docID " + docID + " has been successfully added");
                    }
                }
                case "r" -> {
                    System.out.println("Which of this document you want to remove from the dictionary?");
                    dictionary.printDocumentList();
                    inputIsNextLine();
                    String removeDocID = dictionary.removeDocumentFromDictionary(input);
                    if (!removeDocID.equals("")) {
                        System.out.println("The docID " + removeDocID + " has been successfully removed");
                    }
                }

                case "term" -> {
                    System.out.println("Which term do you want to search in the dictionary?");
                    inputIsNextLine();
                    ArrayList<String> listOfDocID = BooleanQueries.queryTerm(dictionary, input);
                    printResultDocumentList(listOfDocID);
                }

                case "mode" -> {
                    System.out.println("Which bool mode for query? --> AND, OR, NOT");
                    inputIsNextLine();

                    boolean find = false;
                    for (QueryMode mode : QueryMode.values()) {
                        if (Objects.equals(mode.toString(), input)) {
                            queryMode = QueryMode.valueOf(input);
                            find = true;
                            break;
                        }
                    }

                    if (!find) {
                        System.out.println("Wrong input!");
                    }

                    System.out.println("The current mode is " + queryMode);
                }

                case "query" -> {
                    System.out.println("Which terms do you want in bool query? (" + queryMode + " mode)");
                    inputIsNextLine();
                    ArrayList<String> listOfToken = Tokenization.getNormalizeTokenListFromPhrase(input);
                    ArrayList<String> listOfDocID;

                    switch (queryMode) {
                        case AND -> listOfDocID = BooleanQueries.queryAND(dictionary, listOfToken);
                        case OR -> listOfDocID = BooleanQueries.queryOR(dictionary, listOfToken);
                        case NOT -> listOfDocID = BooleanQueries.queryNOT(dictionary, listOfToken);
                        default -> throw new IllegalStateException("Unexpected value: " + queryMode);
                    }

                    printResultDocumentList(listOfDocID);
                }

                case "phrasal query" -> {
                    System.out.println("Which phrase do you want to search in the dictionary?");
                    inputIsNextLine();
                    PostingList listOfDocID = PhrasalQueries.phrasalQuery(dictionary, input);
                    printResultDocumentList(listOfDocID.getDocIDListAsArrayList());
                }

                case "dict" -> dictionary.printDictionary();
                case "dictList" -> dictionary.printDocumentList();


                case "help" -> printCommandPalette();
                case "quit" -> {
                    System.out.println("### Exit ###");
                    continueOperation.set(false);
                }


                default -> System.out.println("Invalid input");
            }
        }
        keyboard.close();

        System.out.println("### Save dictionary ###");
        try {
            xd.saveFile(dictionary);
            System.out.println("### Dictionary saved successfully ###");
        } catch (Exception e) {
            System.out.println("!!! ERROR DICTIONARY NOT SAVED !!!");
            System.out.println(e.getMessage());
        }
        System.out.println("### End Program ###");
    }

    private static void inputIsNextLine() {
        input = keyboard.nextLine();
    }

    private static SaveReadPersistentDictionary recoverDictionary() {

        while (continueOperation.get()) {
            System.out.println("\nDo you want to load the previous dictionary? (y/n)");
            continueOperation.set(false);
            inputIsNextLine();
            switch (input) {
                case "y" -> {
                    try {
                        dictionary = xd.readFile();
                        System.out.println("Dictionary loaded");
                    } catch (Exception e) {
                        System.out.println("Error during load of persistent dictionary... (new dictionary created)");
                        System.out.println(e.getMessage());
                    }
                }
                case "n" -> System.out.println("New dictionary created.");
                default -> {
                    System.out.println("Wrong command.");
                    continueOperation.set(true);
                }
            }
        }
        return xd;
    }

    private static void printResultDocumentList(ArrayList<String> listOfDocID) {
        if (listOfDocID != null && listOfDocID.size() > 0) {
            System.out.println("The result document list is:");
            for (String docID : listOfDocID) {
                System.out.println("\t" + docID);
            }
        } else {
            System.out.println("The result document list is empty!");
        }
    }

    public static void printCommandPalette() {
        System.out.println("\n-------------------- Command palette --------------------");
        System.out.println("add - to add a document (must insert the correct path of the txt to add)");
        System.out.println("addList - to add a list of document (path of the file that contain the list)");
        System.out.println("rmv - to remove a document");

        System.out.println();
        System.out.println("mode - change bool mode for query");
        System.out.println("term - to search a term in the dictionary");
        System.out.println("query - to do a boolean query on the dictionary");
        System.out.println("phrasal query - to search a phrase in the dictionary");

        System.out.println();
        System.out.println("dict - to print the dictionary");
        System.out.println("dictList - to print the list of document");

        System.out.println();
        System.out.println("help - to print the command palette");
        System.out.println("quit - to exit the program");
        System.out.println("\n---------------------------------------------------------");
    }

}

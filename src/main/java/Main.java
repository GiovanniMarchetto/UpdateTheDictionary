import dataStructure.Dictionary;
import dataStructure.PostingList;
import operations.BooleanQueries;
import operations.PhrasalQueries;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class Main {

    public static void main(String[] args) {
        System.out.println("\nWelcome to the updating dictionary project.");

        Dictionary dictionary = new Dictionary();
        Scanner keyboard = new Scanner(System.in);
        String input;
        AtomicBoolean continueOperation = new AtomicBoolean(true);

        printCommandPalette();

        while (continueOperation.get()) {

            System.out.print("\nCommand: ");
            input = keyboard.nextLine();
            switch (input) {
                case "add" -> {
                    System.out.println("Path of the document to add at the dictionary:");
                    input = keyboard.nextLine();
                    String newDocID = dictionary.addDocumentAtDictionary(input);
                    if (!newDocID.equals("")) {
                        System.out.println("The docID " + newDocID + " has been successfully added");
                    }
                }
                case "addList" -> {
                    System.out.println("Path of the document that contain the list of documents to add at the dictionary:");
                    input = keyboard.nextLine();
                    ArrayList<String> listDocID = dictionary.addDocumentsFromListAtDictionary(input);
                    for (String docID : listDocID) {
                        System.out.println("The docID " + docID + " has been successfully added");
                    }

                }
                case "r" -> {
                    System.out.println("Which of this document you want to remove from the dictionary?");
                    dictionary.printDocumentList();
                    input = keyboard.nextLine();
                    String removeDocID = dictionary.removeDocumentFromDictionary(input);
                    if (!removeDocID.equals("")) {
                        System.out.println("The docID " + removeDocID + " has been successfully removed");
                    }
                }


                case "term" -> {
                    System.out.println("Which term do you want to search in the dictionary?");
                    input = keyboard.nextLine();
                    ArrayList<String> listOfDocID = BooleanQueries.queryTerm(dictionary, input);
                    printResultDocumentList(listOfDocID);
                }
                case "pq" -> {
                    System.out.println("Which phrase do you want to search in the dictionary?");
                    input = keyboard.nextLine();
                    PostingList listOfDocID = PhrasalQueries.phrasalQuery(dictionary, input);
                    printResultDocumentList(listOfDocID.getDocIDListAsArrayList());
                }

                case "bool-AND" -> {
                    System.out.println("Which terms do you want in AND query (space between words)?");
                    input = keyboard.nextLine();
                    ArrayList<String> listOfToken = PhrasalQueries.getTokenListFromPhrase(input);
                    ArrayList<String> listOfDocID = BooleanQueries.queryAND(dictionary, listOfToken);
                    printResultDocumentList(listOfDocID);
                }
                case "bool-OR" -> {
                    System.out.println("Which terms do you want in OR query (space between words)?");
                    input = keyboard.nextLine();
                    ArrayList<String> listOfToken = PhrasalQueries.getTokenListFromPhrase(input);
                    ArrayList<String> listOfDocID = BooleanQueries.queryOR(dictionary, listOfToken);
                    printResultDocumentList(listOfDocID);
                }
                case "bool-NOT" -> {
                    System.out.println("Which terms do you want in NOT query (space between words)?");
                    input = keyboard.nextLine();
                    ArrayList<String> listOfToken = PhrasalQueries.getTokenListFromPhrase(input);
                    ArrayList<String> listOfDocID = BooleanQueries.queryNOT(dictionary, listOfToken);
                    printResultDocumentList(listOfDocID);
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
    }

    private static void printResultDocumentList(ArrayList<String> listOfDocID) {
        System.out.println("The result document list is:");
        for (String docID : listOfDocID) {
            System.out.println("\t" + docID);
        }
    }

    public static void printCommandPalette(){
        System.out.println("\n-------------------- Command palette --------------------");
        System.out.println("add - to add a document (must insert the correct path of the txt to add)");
        System.out.println("addList - to add a list of document (path of the file that contain the list)");
        System.out.println("rmv - to remove a document");

        System.out.println("");
        System.out.println("term - to search a term in the dictionary");
        System.out.println("pq - to search a phrase in the dictionary");

        System.out.println("");
        System.out.println("bool-AND - to do AND boolean query on the dictionary");
        System.out.println("bool-OR - to do AND boolean query on the dictionary");
        System.out.println("bool-NOT - to do AND boolean query on the dictionary");

        System.out.println("");
        System.out.println("dict - to print the dictionary");
        System.out.println("dictList - to print the list of document");

        System.out.println("");
        System.out.println("help - to print the command palette");
        System.out.println("quit - to exit the program");
        System.out.println("\n---------------------------------------------------------");
    }

}

import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class Main {

    public static void main(String[] args) {
        System.out.println("\nWelcome to the updating dictionary project.");

        Dictionary dictionary = new Dictionary();
        Scanner keyboard = new Scanner(System.in);
        String input;
        AtomicBoolean continueOperation = new AtomicBoolean(true);
        while (continueOperation.get()) {

            System.out.println("\n-------------------- Command palette --------------------");
            System.out.println("a - to add a document (must insert the correct path of the txt to add)");
            System.out.println("r - to remove a document");
            System.out.println("p - to print the dictionary");
            System.out.println("d - to print the list of document");
            System.out.println("q - to exit the program");
            System.out.println("\n---------------------------------------------------------");

            System.out.print("Command: ");
            input = keyboard.nextLine();
            switch (input) {
                case "a" -> {
                    System.out.println("Path of the document to add at the dictionary:");
                    input = keyboard.nextLine();
                    String newDocID = dictionary.addDocumentAtDictionary(input);
                    if (!newDocID.equals("")) {
                        System.out.println("The docID " + newDocID + " has been successfully added");
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
                case "p" -> dictionary.printDictionary();
                case "d" -> dictionary.printDocumentList();
                case "q" -> {
                    System.out.println("### Exit ###");
                    continueOperation.set(false);
                }
                default -> System.out.println("Invalid input");
            }
        }
        keyboard.close();
    }

}

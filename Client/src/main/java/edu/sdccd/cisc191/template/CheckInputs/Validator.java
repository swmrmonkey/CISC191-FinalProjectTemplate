package edu.sdccd.cisc191.template.CheckInputs;

import java.util.Scanner;

/**
 * Gets the list of valid inputs and
 * the message to return if not valid
 */
public class Validator {

    /**
     * Checks if the input is valid
     * Returns false and prints a message if not
     * Returns true if valid
     * @param input
     * @return
     */
    public static Boolean checkInput(String validInput, String message, String input) {
        if (!input.matches(validInput)) {
            System.out.println(message);
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * Checks if the input for time is valid
     * Returns false and prints a message if not
     * Returns true if valid
     * @param input
     * @return
     */
    public static Boolean checkTimeInput(String validInput, String secondValidInput, String message, String input) {
        if (!input.matches(validInput) && !input.matches(secondValidInput)) {
            System.out.println(message);
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * gets another input
     * @return input
     */
    public static String getInput() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter valid input: ");
        String input = scanner.nextLine();
        return input;
    }
}

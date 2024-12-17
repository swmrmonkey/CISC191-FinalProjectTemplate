package edu.sdccd.cisc191.template;

import edu.sdccd.cisc191.template.CheckInputs.InputCheck;
import edu.sdccd.cisc191.template.Utilities.SortedWithAPI;
import edu.sdccd.cisc191.template.Utilities.SortedWithBST;

import java.io.*;
import java.util.*;

/**
 * This program connects to the server, allows the user to
 * send multiple lines to be added to a file. Then,
 * user can view the most recent times or all previous
 * times, sorted by stroke, distance, and time
 */
public class Client {
    private static final File file1 = new File("Common/src/main/java/edu/sdccd/cisc191/template/AllTimes");
    private static final File file2 = new File("Common/src/main/java/edu/sdccd/cisc191/template/MostRecentTimes");
    Connections connections = new Connections();
    protected String[][] events = new String[][]{
            {"50 free", "100 free", "200 free"},
            {"50 back", "100 back", "200 back"},
            {"50 breast", "100 breast", "200 breast"},
            {"50 fly", "100 fly", "200 fly"},
            {"200 IM", "400 IM"}};

    /**
     * displays a list of events you can enter
     * @throws IOException
     */
    public void displayEvents() throws IOException {
        for (String[] event : events) {
            for (String line : event) {
                System.out.print(line + ", ");
            }
            System.out.println();
        }
    }

    /**
     * displays file contents
     * @throws IOException
     */
    public void displayFileContents(File file) throws IOException {
        //reads the file and prints it out
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            System.out.println("times:");
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
            br.close();
            System.out.println();
        }
    }

    /**
     * Allows you to enter a new time for a specific event and view previous times
     * @param args
     */
    public static void main(String[] args) {
        Client client = new Client();
        Scanner scanner = new Scanner(System.in);
        try {
            // starts connection
           client.connections.startConnection("127.0.0.1", 4444);
            // Shows events you can enter
            System.out.println("List of all events: ");
            client.displayEvents();

            // loop will keep running while the user answers "y"
            boolean cont = true;
            while (cont) {
                // asks if user wants to add an event
                System.out.print("Do you want to add an event? (y/n): ");
                String answer = scanner.nextLine();
                InputCheck.checkAnswer(answer);

                // adds event if answer is "y"
                if (answer.toLowerCase().equals("y")) {
                    // Prompts user to enter stroke, distance, and time
                    System.out.print("Enter the distance: ");
                    // checks for valid distance
                    String distance = InputCheck.checkDistance(scanner.nextLine());

                    System.out.print("Enter the stroke: ");
                    // checks for valid stroke
                    String stroke = InputCheck.checkStroke(scanner.nextLine());

                    System.out.print("Enter the time: ");
                    // checks for valid time
                    String time = InputCheck.checkTime(scanner.nextLine());

                    // puts into one line
                    String line = distance + " " + stroke + " - " + time;

                    // Updates file
                    client.connections.sendUpdateRequest(line, answer);
                }

                // while loop stops if answer is "n"
                else if (answer.toLowerCase().equals("n")) {
                    cont = false;
                }
            }

            // will display sorted times, most recent times, or breaks out of the loop
            boolean True = true;
            do {
                // asks user for which option they would like
                System.out.print("Would you like to:\n" +
                        "(1) view all times, sorted by speed\n" +
                        "(2) view most recent times\n" +
                        "(3) none\n" +
                        "Enter 1,2,or 3: ");
                String input = scanner.nextLine();

                // checks if input is valid and changes valid input to integer
                int number = Integer.parseInt(InputCheck.checkNumber(input));

                // displays sorted times using a BST, then stream API
                if (number == 1) {
                    System.out.println("----Using a BST----");
                    SortedWithBST.sort(file1);

                    System.out.println();
                    System.out.println("----Using streams----");
                    SortedWithAPI.sort(file1);
                }

                // displays most recent times
                else if (number == 2) {
                    client.displayFileContents(file2);
                }

                // breaks out of do-while loop
                else if (number == 3) {
                    True = false;
                }
            }
            while (True);

            // stops connection
            client.connections.stopConnection();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}
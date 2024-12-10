package edu.sdccd.cisc191.template;

import java.net.*;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * This program connects to the server, allows the user to
 * send multiple lines to be added to a file. Then,
 * user can view the most recent times or all previous
 * times, sorted by stroke, distance, and time
 */
public class Client {
    private static final File file1 = new File("Common/src/main/java/edu/sdccd/cisc191/template/AllTimes");
    private static final File file2 = new File("Common/src/main/java/edu/sdccd/cisc191/template/MostRecentTimes");
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private ObjectOutputStream objectOut;
    protected String[][] events = new String[][]{
            {"50 free", "100 free", "200 free"},
            {"100 back", "200 back"},
            {"100 breast", "200 breast"},
            {"100 fly", "200 fly"},
            {"200 IM", "400 IM"}};

    /**
     * starts the connection
     * @param ip
     * @param port
     * @throws IOException
     */
    public void startConnection(String ip, int port) throws IOException {
        clientSocket = new Socket(ip, port);
        objectOut = new ObjectOutputStream(clientSocket.getOutputStream());
        objectOut.flush();
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    /**
     * closes connection
     * @throws IOException
     */
    public void stopConnection() throws IOException {
        in.close();
        out.close();
        objectOut.close();
        clientSocket.close();
    }

    /**
     * sends a request to server
     * @param line
     * @throws IOException
     */
    public void sendUpdateRequest(String line, String answer) throws IOException {
        UpdateRequest updateRequest = new UpdateRequest(line, answer);
        objectOut.writeObject(updateRequest);
        objectOut.flush();
    }

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
     * Sorts times by stroke, distance, then speed using a BST
     * @param originalFile
     */
    public void sortBySpeed(File originalFile) {
        // new BST created
        BST swimmingBST = new BST();

        // reads through a file and separates each line into parts to add to the BST
        try (BufferedReader br = new BufferedReader(new FileReader(originalFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                // separates line at the "-" and puts it into a list
                String[] parts = line.split("-");

                // separates value at index[0] puts it into a list
                String[] distanceAndStroke = parts[0].split(" ");

                // puts the distance, stroke, and time in separate variables
                String distance = distanceAndStroke[0].trim();
                String stroke = distanceAndStroke[1].trim();
                String time = parts[1].trim();

                // Insert the distance, stroke, and time into BST
                swimmingBST.add(stroke, distance, time);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // prints tree
        swimmingBST.writeTree();
    }

    /**
     * Sorts times by stroke, distance, then speed with parallel streams
     * @param originalFile
     * @throws IOException
     */
    public void sortedWithAPI(File originalFile) throws IOException {
        List<StreamEvents> events = convertFileToList(originalFile);

        // starts the stream
        events.parallelStream()
                .sorted(Comparator.comparing(StreamEvents::getStroke) // Sort by stroke
                        .thenComparing(event -> Integer.parseInt(event.getDistance())) // Then sort by distance
                        .thenComparing(event -> convertTimeToSeconds(event.getTime()))) // Then sort by time
                .collect(Collectors.groupingBy(StreamEvents::getStroke)) // Group by stroke
                .forEach((stroke, eventList) -> {
                    // Print the stroke
                    System.out.println("Stroke: " + stroke);

                    // Print each event for the current stroke, sorted by distance and time
                    eventList.forEach(event -> System.out.println("  " + event.getDistance() + " - " + event.getTime()));
                    System.out.println();
                });
    }

    /**
     * Converts a file into an arraylist
     * @param file
     * @return
     */
    public List<StreamEvents> convertFileToList(File file) {
        // creates an ArrayList of StreamEvents
        List<StreamEvents> events = new ArrayList<>();

        // reads a file and puts it into the ArrayList
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("-");
                String[] distanceAndStroke = parts[0].split(" ");
                String distance = distanceAndStroke[0].trim();
                String stroke = distanceAndStroke[1].trim();
                String time = parts[1].trim();

                StreamEvents event = new StreamEvents(distance, stroke, time);

                // Add event to the list
                events.add(event);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return events;
    }

    /**
     * converts times into seconds
     * @param time
     * @return
     */
    private Double convertTimeToSeconds(String time) {
        // converts times from minutes into seconds
        if (time.contains(":")) {
            String[] timeParts = time.split(":");
            return Double.parseDouble(timeParts[0]) * 60 + Double.parseDouble(timeParts[1]);
        }
        // returns time if less than a minute
        else {
            return Double.parseDouble(time);
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
            client.startConnection("127.0.0.1", 4444);
            // Shows events you can enter
            System.out.println("List of all events: ");
            client.displayEvents();

            // loop will keep running while the user answers "y"
            boolean cont = true;
            while (cont) {
                // asks if user wants to add an event
                System.out.print("Do you want to add an event? (y/n): ");
                String answer = scanner.nextLine();

                // adds event if answer is "y"
                if (answer.toLowerCase().equals("y")) {
                    // Prompts user to enter stroke, distance, and time
                    System.out.print("Enter the distance: ");
                    String distance = scanner.nextLine();
                    System.out.print("Enter the stroke: ");
                    String stroke = scanner.nextLine();
                    System.out.print("Enter the time: ");
                    String time = scanner.nextLine();

                    // puts into one line
                    String line = distance + " " + stroke + " - " + time;

                    // Updates file
                    client.sendUpdateRequest(line, answer);
                }

                // while loop stops if answer is "n"
                else if (answer.toLowerCase().equals("n")) {
                    cont = false;
                }

                // asks for an input again
                else {
                    System.out.print("Invalid input. Try again: ");
                    answer = scanner.nextLine();
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
                int number = Integer.parseInt(scanner.nextLine());

                // displays sorted times using a BST, then stream API
                if (number == 1) {
                    System.out.println("----Using a BST----");
                    client.sortBySpeed(file1);

                    System.out.println();
                    System.out.println("----Using streams----");
                    client.sortedWithAPI(file1);
                }

                // displays most recent times
                else if (number == 2) {
                    client.displayFileContents(file2);
                }

                // breaks out of do-while loop
                else if (number == 3) {
                    True = false;
                }

                // input was invalid and asks for another input
                else {
                    System.out.print("Number not valid. Try again: ");
                    number = Integer.parseInt(scanner.nextLine());
                }
            }
            while (True);

            client.stopConnection();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}
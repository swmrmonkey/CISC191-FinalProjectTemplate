package edu.sdccd.cisc191.template;

import java.net.*;
import java.io.*;

/**
 * This program is a server that takes connection requests on
 * the specified port. It allows clients to add lines to a file
 * and then sends back the contents of the file.
 */
public class Server {
    private ServerSocket serverSocket;
    private final File file1 = new File("Common/src/main/java/edu/sdccd/cisc191/template/AllTimes");
    private final File file2 = new File("Common/src/main/java/edu/sdccd/cisc191/template/MostRecentTimes");

    /**
     * opens port
     * @param port
     * @throws Exception
     */
    public void start(int port) throws Exception {
        serverSocket = new ServerSocket(port);
        System.out.println("Server is listening on port " + port);

        while (true) {
            try {
                // accepts connection
                Socket clientSocket = serverSocket.accept(); // Accept a new client connection
                System.out.println("Client connected.");

                // handles the client request
                handleClientRequest(clientSocket);

            } catch (IOException e) {
                System.out.println("Error accepting client connection: " + e.getMessage());
            }
        }
    }

    /**
     * handles request
     * @param clientSocket
     */
    private void handleClientRequest(Socket clientSocket) {
        try (PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
             ObjectInputStream objectIn = new ObjectInputStream(clientSocket.getInputStream())) {

            // keeps updating the file while answer is "y"
            boolean keepConnection = true;
            while (keepConnection) {
                // writes content to file
                UpdateRequest updateRequest = (UpdateRequest) objectIn.readObject();
                String lineToAdd = updateRequest.getLineToAdd();
                addLineToFile(lineToAdd, file1);

                // gets most recent times and writes it into a separate file
                getsMostRecentTimes(file1, file2);

                // breaks out of loop when answer is "n"
                if (updateRequest.getAnswer().equals("n")) {
                    keepConnection = false;
                }
            }
        }
        catch (IOException | ClassNotFoundException e) {
            System.out.println("Error handling client request: " + e.getMessage());
        }
        finally {
            try {
                clientSocket.close(); // Close the client socket when done
                System.out.println("Client disconnected.");
            } catch (IOException e) {
                System.out.println("Error closing client socket: " + e.getMessage());
            }
        }
    }

    /**
     * reads through ALLTimes file and puts the most recent
     * time of each event into another file
     * @param originalFile
     * @param recentTimesFile
     */
    private void getsMostRecentTimes(File originalFile, File recentTimesFile) {
        // creates a treemap
        SwimmingEventsMap map = new SwimmingEventsMap();

        // reads through file and puts most recent time of each event in treemap
        try (BufferedReader br = new BufferedReader(new FileReader(originalFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] events = line.split("-");
                String key = events[0];
                String value = events[1];
                map.addEvent(key, value);
            }

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        // writes keys and values in the treemap into another file
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(recentTimesFile))) {
            for (String key : map.getKeySet()) {
                bw.write(key + "-" + map.getValue(key));
                bw.newLine();
                System.out.println("Added line: " + key + "-" + map.getValue(key));
            }
        }
        catch (IOException e) {
            System.out.println("Error writing to output file: " + e.getMessage());
        }
    }


    /**
     * write lines to file
     * @param line
     * @return
     */
    private PrintWriter addLineToFile(String line, File file) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(line);
            writer.newLine();
            System.out.println("Added line: " + line);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * starts server
     * @param args
     */
    public static void main(String[] args) {
        Server server = new Server();
        try {
            server.start(4444);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

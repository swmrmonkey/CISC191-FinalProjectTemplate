package edu.sdccd.cisc191.template;

import javafx.application.Application;

import java.net.*;
import java.io.*;

/**
 * This program is a server that takes connection requests on
 * the specified port. It allows clients to add lines to a file
 * and then sends back the contents of the file.
 */
public class Server {
    private ServerSocket serverSocket;
    private final File timesFile = new File("Common/src/main/java/edu/sdccd/cisc191/template/Times");

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

            // writes content to file
            UpdateRequest updateRequest = (UpdateRequest) objectIn.readObject();
            String lineToAdd = updateRequest.getLineToAdd();

            //adds the line the client typed in to the times file
            UpdateFile.addLineToFile(lineToAdd);

            //launches the times display
            Application.launch(DisplayTimes.class, new String[]{});
        } catch (IOException | ClassNotFoundException e) {
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

    public static void main(String[] args) {
        Server server = new Server();
        try {
            server.start(4444);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

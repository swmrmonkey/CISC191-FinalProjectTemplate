package edu.sdccd.cisc191.template;

import javafx.application.Application;

import java.net.*;
import java.io.*;
import java.util.Scanner;

/**
 * This program connects to the server, allows the user to
 * send a line to be added to a file, and then displays
 * the contents of the file.
 */
public class Client {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private ObjectOutputStream objectOut;

    /**
     * starts the connection
     * @param ip
     * @param port
     * @throws IOException
     */
    public void startConnection(String ip, int port) throws IOException {
        clientSocket = new Socket(ip, port);
        objectOut = new ObjectOutputStream(clientSocket.getOutputStream());
        objectOut.flush(); // Ensure the stream is ready for writing
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
    public void sendUpdateRequest(String line) throws IOException {
        UpdateRequest updateRequest = new UpdateRequest(line);
        objectOut.writeObject(updateRequest);
        objectOut.flush();
    }

    public static void main(String[] args) {
        Client client = new Client();
        Scanner scanner = new Scanner(System.in);
        try {
            client.startConnection("127.0.0.1", 4444);

            //launches the events display
            Application.launch(DisplayEvents.class, new String[]{});

            //sends the user input to the server
            client.sendUpdateRequest(DisplayEvents.getNewLine());

            client.stopConnection();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}
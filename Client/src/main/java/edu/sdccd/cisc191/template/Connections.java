package edu.sdccd.cisc191.template;

import java.io.*;
import java.net.Socket;

/**
 * Class for starting a connection, closing the connection,
 * and sending an update request
 */
public class Connections {
    private Socket clientSocket;
    private ObjectOutputStream objectOut;
    private BufferedReader in;
    private PrintWriter out;

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
}

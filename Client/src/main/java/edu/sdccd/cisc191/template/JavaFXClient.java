package edu.sdccd.cisc191.template;


import javafx.application.Application;
import java.io.IOException;

/**
 * This is a JavaFX app that allows you to add an event to
 * the AllTImes file
 */
public class JavaFXClient {
    public static void main(String[] args) {
        Client client = new Client();

        try {
            // start connection
            client.connections.startConnection("127.0.0.1", 4444);

            // launch application
            Application.launch(AddEvent.class, args);

            // sends update request
            client.connections.sendUpdateRequest(AddEvent.getNewLine(), "y");

            // close connection
            client.connections.stopConnection();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

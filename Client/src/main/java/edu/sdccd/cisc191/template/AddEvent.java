package edu.sdccd.cisc191.template;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * This class sets up the stage to add an event
 */
public class AddEvent extends Application {
    static String newLine;

    public static String getNewLine() {
        return newLine;
    }

    @Override
    public void start(Stage stage) {
        // Setting up layout
        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(20));

        // Distance input
        Label distanceLabel = new Label("Enter Distance: ");
        TextField distanceField = new TextField();
        grid.add(distanceLabel, 0, 0);
        grid.add(distanceField, 1, 0);

        // Stroke input
        Label strokeLabel = new Label("Enter Stroke: ");
        TextField strokeField = new TextField();
        grid.add(strokeLabel, 0, 1);
        grid.add(strokeField, 1, 1);

        // Time input
        Label timeLabel = new Label("Enter Time: ");
        TextField timeField = new TextField();
        grid.add(timeLabel, 0, 2);
        grid.add(timeField, 1, 2);

        // Add button
        Button addButton = new Button("Add");
        grid.add(addButton, 0, 3);

        // Set up the actions
        addButton.setOnAction(e -> {
            // Get the input values
            String distance = distanceField.getText();
            String stroke = strokeField.getText();
            String time = timeField.getText();

            // Construct the line to be sent to the server
            String line = distance + " " + stroke + " - " + time;

            System.out.println("Added line to AllTimes file: " + line);

            // Send the update request
            newLine = line;
            stage.close();
        });

        // Set up the scene and stage
        Scene scene = new Scene(grid, 400, 300);
        stage.setTitle("JavaFX Client");
        stage.setScene(scene);
        stage.show();
    }
}

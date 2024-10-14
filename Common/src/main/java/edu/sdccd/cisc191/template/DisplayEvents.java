package edu.sdccd.cisc191.template;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.control.TextArea;

import java.io.*;


public class DisplayEvents extends Application{

    //declares the variables
    private final File eventsFile = new File("Common/src/main/java/edu/sdccd/cisc191/template/Events");
    static String newLine;

    public static String getNewLine(){
        return newLine;
    }

    /*
     * displays an application that mimics a flashcard system
     * @param a new Stage
     */
    @Override
    public void start(Stage stage) throws Exception {
        //creates variables
        Label title = new Label();
        Label directions = new Label();
        Label eventsList = new Label();
        TextArea textA = new TextArea();
        //sets size of text area
        textA.setPrefHeight(20);
        textA.setPrefWidth(80);
        Button addTimeBtn = new Button("Add Time");

        //sets title
        title.setText("Events:");
        //displays events list
        eventsList.setText(UpdateFile.getFileContents(eventsFile));

        //adds nodes to vbox1
        VBox vbox1 = new VBox(8);
        vbox1.getChildren().addAll(title, eventsList);

        //display directions for input
        directions.setText("Enter the event and time in this format (event: time): ");

        //event handler for submit button, closes the application once clicked
        addTimeBtn.setOnAction(actionEvent ->  {
            newLine = textA.getText();
            textA.setText("Clicked!");
            stage.close();
        });

        //adds nodes to vbox2
        VBox vbox2 = new VBox(8);
        vbox2.getChildren().addAll(directions, textA, addTimeBtn);

        //adds vboxes to hbox
        HBox hbox = new HBox(10);
        hbox.getChildren().addAll(vbox1, vbox2);

        Scene scene = new Scene(hbox, 650, 300, Color.WHITE);
        stage.setTitle("Events");
        stage.setScene(scene);
        stage.show();
    }
}
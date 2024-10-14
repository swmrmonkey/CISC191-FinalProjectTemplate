package edu.sdccd.cisc191.template;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;


public class DisplayTimes extends Application{

    //declares the variables
    private final File timesFile = new File("Common/src/main/java/edu/sdccd/cisc191/template/Times");

    /*
     * displays an application that mimics a flashcard system
     * @param a new Stage
     */
    @Override
    public void start(Stage stage) throws Exception {
        //creates the label variables
        Label title = new Label();
        Label eventsList = new Label();
        Label timesList = new Label();

        //sets the title
        title.setText("Times:");
        //displays the list of times
        timesList.setText(UpdateFile.getFileContents(timesFile));

        //adds the nodes in vbox
        VBox vbox = new VBox(8);
        vbox.getChildren().addAll(title, timesList);

        //main
        Scene scene = new Scene(vbox, 650, 300, Color.WHITE);
        stage.setTitle("Events");
        stage.setScene(scene);
        stage.show();
    }
}
package edu.sdccd.cisc191.template;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import java.io.*;
import java.net.Socket;
public class TicTacToe extends Application{
    private BufferedReader in;
    private PrintWriter out;
    private boolean gameOver = false;
    private boolean isXTurn = true;
    private Button[][] buttons = new Button[3][3];
    private GameBoardLabel playerTurn = new GameBoardLabel();
    private GameBoardLabel score = new GameBoardLabel();
    private boolean tie=false;
    private Button restartButton = new Button("Restart");
    private Button saveButton = new Button("Save Game");
    private Button loadButton = new Button("Load Game");
    private Button scoresButton = new Button("Scores");
    private Socket socket;
    private String response;
    private boolean isClicked =false;
    private PlayerSwitch playerSwitch; // creates a private PlayerSwitch variable
    /**
     * Launches the application
     * @param args array of arguments to be passed
     */
    public static void main(String[] args) {
        launch(args);
    }
    /**
     * Sets up the game by making a client socket, creating all the JavaFX
     elements, and creating the buttons so they can be clicked.
     * @param primaryStage the primary stage for this application, on which the
    scene can be set.
     */
    public void start(Stage primaryStage) {
        try{
            socket = new Socket("localhost", 2222);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new
                    InputStreamReader(socket.getInputStream()));
        }
        catch(IOException e){
            e.printStackTrace();
        }
// Creates the game logic object
        playerSwitch = new PlayerSwitch(isXTurn);
        restartButton.setOnAction(event -> {
            restart();
            gameOver = false;
            tie = false;
            isXTurn = true;
            updateHeader();
        });
        saveButton.setOnAction(event -> saveGame());
        loadButton.setOnAction(event -> loadGame());
        scoresButton.setOnAction(event -> {
            requestScore();
            isClicked=true;
        });
        GridPane grid = new GridPane();
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(grid);
        HBox hbox = new HBox(playerTurn, scoresButton, restartButton, saveButton,
                loadButton, score);
        borderPane.setTop(hbox);
        updateHeader();
        for(int row = 0; row < 3; row++){
            for(int column = 0; column < 3; column++){
                GameBoardButton button = new GameBoardButton(row, column, this);
                button.setMinSize(160,150); // size of each button
                button.setStyle("-fx-font-size: 50;");
                button.setOnAction(event -> {
                    button.handleButtonClick();
                });
                grid.add(button,column,row);
                buttons[row][column]=button;
            }
        }
        Scene scene = new Scene(borderPane,500, 550);
        primaryStage.setTitle("Tic - Tac - Toe");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    /**
     * Updates the header by displaying who wins, the current turn, and the current
     score of the game.
     * The score is only updated if the Score button is clicked
     */
    public void updateHeader(){
        if(tie==true){
            playerTurn.setText("Tie!");
        }
        else if(gameOver == true){
            playerTurn.setText(playerSwitch.previousTurn()+" wins!");
        }
        else{
            playerTurn.setText(playerSwitch.currentTurn()+"'s turn");
        }
        if(isClicked==false){
            score.setText("SCORES X:" + 0 + " O:" + 0 + " TIES:" + 0);
        }
        else{
            score.setText(response);
        }
    }
    /**
     * Iterates through the 2D array of buttons and makes them all un-clickable
     */
    public void disableBoard() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col].setDisable(true);
            }
        }
    }
    /**
     * Performs all the logic for determining if there is a winner.
     * Rows are done by iterating through each row and comparing the first element
     to the other two.
     * Columns are done by iterating through each column and comparing the first
     element to the other two.
     * Diagonals are done by just comparing the three elements.
     * If there is a win, then it disables the board, determines which player won,
     and sends the appropriate String to the server.
     * If there is no win, then it checks for a tie by making sure all the elements
     are not empty.
     */
    public void check(){
        int count=0;
//rows
        for(int row = 0; row < 3; row++){
            if(buttons[row][0].getText().equals("")){
                continue;
            }
            if(buttons[row][0].getText().equals(buttons[row][1].getText()) &&
                    buttons[row][0].getText().equals(buttons[row][2].getText())){
                disableBoard();
                gameOver=true;
                if(playerSwitch.currentTurn().equals("X")){
                    out.println("RESULT X_WIN");
                }
                else{
                    out.println("RESULT O_WIN");
                }
                return;
            }
        }
//columns
        for(int col = 0; col < 3; col++){
            if(buttons[0][col].getText().equals("")){
                continue;
            }
            if(buttons[0][col].getText().equals(buttons[1][col].getText()) &&
                    buttons[0][col].getText().equals(buttons[2][col].getText())){
                disableBoard();
                gameOver=true;
                if(playerSwitch.currentTurn().equals("X")){
                    out.println("RESULT X_WIN");
                }
                else{
                    out.println("RESULT O_WIN");
                }
                return;
            }
        }
//diagonal 1
        if(buttons[0][0].getText().equals(buttons[1][1].getText()) && buttons[0]
                [0].getText().equals(buttons[2][2].getText()) && !(buttons[0]
                [0].getText().equals(""))){
            disableBoard();
            gameOver=true;
            if(playerSwitch.currentTurn().equals("X")){
                out.println("RESULT X_WIN");
            }
            else{
                out.println("RESULT O_WIN");
            }
            return;
        }
//diagonal 2
        if(buttons[0][2].getText().equals(buttons[1][1].getText()) && buttons[0]
                [2].getText().equals(buttons[2][0].getText()) && !(buttons[0]
                [2].getText().equals(""))){
            disableBoard();
            gameOver=true;
            if(playerSwitch.currentTurn().equals("X")){
                out.println("RESULT X_WIN");
            }
            else{
                out.println("RESULT O_WIN");
            }
            return;
        }
//tie
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if(!(buttons[i][j].getText().equals(""))){
                    count++;
                }
            }
        }
        if(count==9){
            disableBoard();
            gameOver=true;
            tie=true;
            out.println("RESULT TIE");
        }
    }
    /**
     * Iterates through the 2D array and sets all the buttons to blank text and
     makes them clickable.
     */
    public void restart(){
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col].setDisable(false);
                buttons[row][col].setText("");
            }
        }
    }
    /**
     * Saves the state of the board by writing it to a file using Object I/O
     streams.
     */
    private void saveGame() {
        try (ObjectOutputStream out = new ObjectOutputStream(new
                FileOutputStream("tic_tac_toe_save.dat"))) {
// Save the board state
            String[][] boardState = new String[3][3];
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    boardState[i][j] = buttons[i][j].getText();
                }
            }
            out.writeObject(boardState);
            out.writeBoolean(isXTurn); // Save whose turn it is
            out.writeBoolean(gameOver);
            out.writeBoolean(tie);
            System.out.println("Game saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving the game: " + e.getMessage());
        }
    }
    /**
     * Loads the state of the board by reading it from the file using Object I/O
     streams.
     */
    private void loadGame() {
        try (ObjectInputStream in = new ObjectInputStream(new
                FileInputStream("tic_tac_toe_save.dat"))) {
// Load the board state
            String[][] boardState = (String[][]) in.readObject();
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    buttons[i][j].setText(boardState[i][j]);
                }
            }
            isXTurn = in.readBoolean(); // Load whose turn it is
            gameOver = in.readBoolean();
            tie = in.readBoolean();
            updateHeader();
            if(gameOver){
                disableBoard();
            }
            System.out.println("Game loaded successfully.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading the game: " + e.getMessage());
        }
    }
    /**
     * Sends a String to the server to trigger a response.
     * Reads that response and updates the header with it.
     */
    public void requestScore() {
        out.println("GET_SCORE");
        try {
            response = in.readLine();
            updateHeader();
        }
        catch(IOException e){
            System.out.println("Error receiving score: " + e.getMessage());
        }
    }
    /**
     * Gets the current turn
     * @return current turn
     */
    public String getCurrentTurn() {
        return playerSwitch.currentTurn();
    }
    /**
     * Switches turn
     */
    public void getSwitchTurn() {
        playerSwitch.switchTurn();
    }
}
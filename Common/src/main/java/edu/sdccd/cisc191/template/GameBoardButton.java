package edu.sdccd.cisc191.template;
import javafx.scene.control.Button;


public class GameBoardButton extends Button {
    private int row;
    private int column;
    private TicTacToe gameBoard;

    /**
     * Creates a button that can be clicked and modified for the game.
     * @param row integer for the row the button is in.
     * @param column integer for the column the button is in.
     * @param gameBoard an instance of TicTacToe so that each button has access to the game logic.
     */
    public GameBoardButton(int row, int column, TicTacToe gameBoard) {
        this.row = row;
        this.column = column;
        this.gameBoard=gameBoard;
    }

    /**
     * Executes code when a gameBoardButton is clicked.
     * Creates either an X or O on the button.
     * Makes it un-clickable.
     * Checks to see if there is a win.
     * Switches the player's turn.
     * Updates header accordingly.
     */
    public void handleButtonClick(){
        setText(gameBoard.getCurrentTurn());
        setDisable(true);
        gameBoard.check();
        gameBoard.getSwitchTurn();
        gameBoard.updateHeader();
    }
}

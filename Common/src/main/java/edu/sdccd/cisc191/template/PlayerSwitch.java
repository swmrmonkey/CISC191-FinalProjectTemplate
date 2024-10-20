package edu.sdccd.cisc191.template;
public class PlayerSwitch {
    private boolean isXTurn;
    public PlayerSwitch(boolean isXTurn) {
        this.isXTurn = isXTurn;
    }
    /**
     * Gets the symbol for the current player depending on whose turn it is
     * @return a string representing either X or O
     */
    public String currentTurn(){
        String turn;
        if(isXTurn){
            turn="X";
        }
        else{
            turn="0";
        }
        return turn;
    }
    /**
     * Gets the symbol for the player that just went
     * @return a string representing either X or O
     */
    public String previousTurn(){
        String turn;
        if(isXTurn){
            turn="O";
        }
        else{
            turn="X";
        }
        return turn;
    }
    /**
     * Swaps the boolean so that it is O's turn
     */
    public void switchTurn(){
        isXTurn =!isXTurn;
    }
}
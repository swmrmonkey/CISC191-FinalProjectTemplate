package edu.sdccd.cisc191.template;

import java.io.Serializable;

/**
 * This class sends a request to the server
 * to update the file
 */
public class UpdateRequest implements Serializable {
    private String line;
    private String answer;

    public UpdateRequest(String line, String answer) {
        this.line = line;
        this.answer = answer;
    }

    /**
     * gets the lines to add to file
     * @return lines
     */
    public String getLineToAdd() {
        return line;
    }

    /**
     * gets answer from client
     * @return answer
     */
    public String getAnswer() {
        return answer;
    }

    @Override
    public String toString() {
        return "UpdateRequest{" + "lineToAdd='" + line +
                "answer='" + answer + '\'' + '}';
    }

}

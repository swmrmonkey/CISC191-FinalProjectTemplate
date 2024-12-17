package edu.sdccd.cisc191.template.CheckInputs;


/**
 * Checks if any inputs are invalid
 */
public class InputCheck {

    /**
     * checks if number is valid
     * @param number
     * @return valid number
     */
    public static String checkNumber(String number) {
        String validInputs = "1|2|3";
        String message = "Invalid number. Please enter 1,2 or 3";

        return getValidInput(validInputs, message, number);

    }

    /**
     * Checks if answer is valid
     * @param answer
     */
    public static void checkAnswer(String answer) {
        Validator.checkInput("y|n", "Invalid answer. Please answer \"y\" or \"n\"", answer);
    }

    /**
     * Checks if distance is valid
     * @param distance
     * @return valid distance
     */
    public static String checkDistance(String distance) {
        String validInputs = "50|100|200|500";
        String message = "Invalid distance. Please enter 50, 100, 200, or 500";

        return getValidInput(validInputs, message, distance);
    }

    /**
     * checks if stroke is valid
     * @param stroke
     * @return valid stroke
     */
    public static String checkStroke(String stroke) {
        String validInputs = "free|back|breast|fly|IM";
        String message = "Invalid stroke. Please enter free, back, breast, fly, or IM";

        return getValidInput(validInputs, message, stroke);
    }

    /**
     * checks if time is valid
     * @param time
     * @return valid time
     */
    public static String checkTime(String time) {
        String validInputs = "\\d{0,1}:\\d{2}\\.\\d{2}";
        String secondValidInputs = "\\d{2}\\.\\d{2}";
        String message = "Invalid time. Please use a minutes:seconds:milliseconds format";

        return getValidTimeInput(validInputs, secondValidInputs, message, time);
    }

    /**
     * gets a valid input for distance and stroke
     * @param validInputs
     * @param message
     * @param answer
     * @return answer
     */
    private static String getValidInput(String validInputs, String message, String answer) {
        //checks if input is valid
        Boolean notValid = Validator.checkInput(validInputs, message, answer);

        //keeps asking for an input until it is valid
        while (notValid == false) {
            answer = Validator.getInput();
            notValid = Validator.checkInput(validInputs, message, answer);
        }
        return answer;
    }

    /**
     * gets a valid input for time
     * @param validInput
     * @param secondValidInput
     * @param message
     * @param answer
     * @return answer
     */
    private static String getValidTimeInput(String validInput, String secondValidInput, String message, String answer) {
        //checks if input is valid
        Boolean notValid = Validator.checkTimeInput(validInput, secondValidInput, message, answer);

        //keeps asking for an input until it is valid
        while (notValid == false) {
            answer = Validator.getInput();
            notValid = Validator.checkTimeInput(validInput, secondValidInput, message, answer);
        }
        return answer;
    }
}

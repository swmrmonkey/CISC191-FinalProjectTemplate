package edu.sdccd.cisc191.template.Utilities;

/**
 * This class converts a time, that is given as a string,
 * into seconds and returns it as a double
 */
public class ConvertTime {

    /**
     * converts times into seconds
     * @return time
     */
    public static double convert(String time) {
        // converts times over a minute into seconds
        if (time.contains(":")) {
            String[] timeParts = time.split(":");
            return Double.parseDouble(timeParts[0]) * 60 + Double.parseDouble(timeParts[1]);
        }
        // returns time if less than a minute
        else {
            return Double.parseDouble(time);
        }
    }
}

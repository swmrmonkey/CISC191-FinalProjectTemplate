package edu.sdccd.cisc191.template.Utilities;

import edu.sdccd.cisc191.template.BST.BST;
import edu.sdccd.cisc191.template.Events.StreamEvents;

public class GetStringParts {
    /**
     * turns a line into a list and returns it
     * @param line
     */
    private static String[] splitIntoList(String line) {
        // separates line at the "-" and puts it into a list
        String[] parts = line.split("-");

        // separates value at index[0] puts it into a list
        String[] distanceAndStroke = parts[0].split(" ");

        // puts distance, stroke, and time into a list
        String[] event = {distanceAndStroke[0].trim(), distanceAndStroke[1].trim(), parts[1].trim()};
        return event;
    }

    public static void insertIntoBST(String line, BST bst) {
        String[] list = splitIntoList(line);
        bst.add(list[0], list[1], list[2]);
    }

    public static void newStreamEvents(String line) {
        String[] list = splitIntoList(line);
        StreamEvents event = new StreamEvents(list[0], list[1], list[2]);
    }
}

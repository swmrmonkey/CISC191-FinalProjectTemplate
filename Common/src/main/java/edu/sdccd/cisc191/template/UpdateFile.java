package edu.sdccd.cisc191.template;

import javafx.scene.control.Label;

import java.io.*;

public class UpdateFile {

    private static StringBuilder stringBuilder;
    private static final File file = new File("Common/src/main/java/edu/sdccd/cisc191/template/Times");

    /**
     * write lines to file
     * @param line
     */
    public static void addLineToFile(String line) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(line);
            writer.newLine();
            System.out.println("Added line: " + line);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * returns the text in the file
     * @param file
     * @return String that contains the file's contents
     */
    public static String getFileContents(File file) throws IOException {
        try (BufferedReader fileReader = new BufferedReader(new FileReader(file))) {
            String line;
            stringBuilder = new StringBuilder();
            while ((line = fileReader.readLine()) != null) {
                //adds all the lines in a string builder with new lines
                stringBuilder.append(line);
                stringBuilder.append("\n");
            }
        }
        return stringBuilder.toString();
    }
}

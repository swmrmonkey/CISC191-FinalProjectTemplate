package edu.sdccd.cisc191.template.Utilities;

import edu.sdccd.cisc191.template.BST.BST;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class SortedWithBST {
    /**
     * Sorts times by stroke, distance, then speed using a BST
     * @param originalFile
     */
    public static void sort(File originalFile) {
        // new BST created
        BST swimmingBST = new BST();

        // reads through a file and separates each line into parts to add to the BST
        try (BufferedReader br = new BufferedReader(new FileReader(originalFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                GetStringParts.insertIntoBST(line, swimmingBST);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // prints tree
        swimmingBST.writeTree();
    }

}

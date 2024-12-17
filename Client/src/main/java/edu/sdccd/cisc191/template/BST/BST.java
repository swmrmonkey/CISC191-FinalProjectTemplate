package edu.sdccd.cisc191.template.BST;

import edu.sdccd.cisc191.template.Events.Event;

/**
 * This class implements a Binary Search Tree that stores swimming events
 * The tree is organized based on stroke, distance, and time
 */
public class BST {
    private BSTNode root;

    // Constructor
    public BST() {
        this.root = null;
    }

    /**
     * adds an event into the BST
     * if stroke is already in the tree, it adds the event to the linked list
     * else a new node is created for the stroke
     * @param stroke
     * @param distance
     * @param time
     */
    public void add(String distance, String stroke, String time) {
        root = addRecursively(root, stroke, distance, time);
    }

    /**
     * uses recursion to add event into BST
     * if node is null, a new node is created
     * otherwise, it traverses through tree from left to right based on comparison
     * @param node
     * @param stroke
     * @param distance
     * @param time
     * @return the node
     */
    private BSTNode addRecursively(BSTNode node, String stroke, String distance, String time) {
        // base case
        if (node == null) {
            node = new BSTNode(stroke);
            node.addEvent(distance, time);
            return node;
        }


        // compare stroke with current node's key
        int compare = stroke.compareTo(node.getKey());

        // traverses left
        if (compare < 0) {
            node.setLeft(addRecursively(node.getLeft(), stroke, distance, time));
        }

        // traverses right
        else if (compare > 0) {
            node.setRight(addRecursively(node.getRight(), stroke, distance, time));
        }

        // stroke already exists, so event is added to linked list in sorted order
        else {
            node.addEvent(distance, time);
        }
        return node;
    }

    /**
     * prints BST from left to right
     */
    public void writeTree() {
        printTreeRecursively(root);
    }

    /**
     * uses recursion to print BST in-order
     * @param node
     */
    private void printTreeRecursively(BSTNode node) {
        if (node != null) {
            // recursively prints left subtree
            printTreeRecursively(node.getLeft());

            // prints current node
            System.out.println("Stroke: " + node.getKey());

            // prints everything in the linked list
            for (Event event : node.getEvents()) {
                System.out.println("  " + event);
            }
            System.out.println();

            // recursively prints right subtree
            printTreeRecursively(node.getRight());
        }
    }
}

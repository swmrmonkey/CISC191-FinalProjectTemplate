package edu.sdccd.cisc191.template.BST;

import edu.sdccd.cisc191.template.Events.Event;

import java.util.LinkedList;

/**
 * This class creates a node for the Binary Search Tree
 */
public class BSTNode {
    private String key; // Stroke (e.g., "free")
    private LinkedList<Event> events; // Linked list of events sorted by distance and time
    private BSTNode left;
    private BSTNode right;

    // constructor
    public BSTNode(String key) {
        this.key = key;
        this.events = new LinkedList<>();
    }

    /**
     * gets key
     * @return key
     */
    public String getKey() {
        return key;
    }

    /**
     * gets a linked list
     * @return getEvents, which is a linked list
     */
    public LinkedList<Event> getEvents() {
        return events;
    }

    /**
     * gets left node
     * @return left node
     */
    public BSTNode getLeft() {
        return left;
    }

    /**
     * sets the left node
     * @param left
     */
    public void setLeft(BSTNode left) {
        this.left = left;
    }

    /**
     * gets right node
     * @return right node
     */
    public BSTNode getRight() {
        return right;
    }

    /**
     * sets the right node
     * @param right
     */
    public void setRight(BSTNode right) {
        this.right = right;
    }

    /**
     * adds an event
     * @param distance
     * @param time
     */
    public void addEvent(String distance, String time) {
        Event newEvent = new Event(distance, time);

        // Insert the new event in a sorted order by distance and time
        int index = 0;
        for (Event event : events) {
            if (newEvent.compareTo(event) < 0) {
                break;
            }
            index++;
        }
        events.add(index, newEvent);
    }
}


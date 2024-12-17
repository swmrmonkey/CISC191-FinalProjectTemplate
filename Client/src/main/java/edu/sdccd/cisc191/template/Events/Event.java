package edu.sdccd.cisc191.template.Events;

import edu.sdccd.cisc191.template.Utilities.ConvertTime;

/**
 * This class creates a swimming event. The distance and time,
 * as a string and double, can be accessed
 */
public class Event implements Comparable<Event> {
    private String distance;
    private String time;


    // constructor
    public Event(String distance, String time) {
        this.distance = distance;
        this.time = time;
    }

    /**
     * gets distance
     * @return distance
     */
    public String getDistance() {
        return distance;
    }

    /**
     * gets time as a string
     * @return time
     */
    public String getTime() {
        return time;
    }

    @Override
    /**
     * returns Event as string
     */
    public String toString() {
        return distance + " - " + time;
    }

    @Override
    /**
     * Compares the events based on their distance,
     * then time
     */
    public int compareTo(Event next) {
        // First compare distances
        int distanceComparison = Integer.compare(Integer.parseInt(this.distance), Integer.parseInt(next.distance));
        if (distanceComparison != 0) {
            return distanceComparison;
        }

        // If distances are equal, compare times
        return Double.compare(ConvertTime.convert(this.time), ConvertTime.convert(next.time));
    }
}

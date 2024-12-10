package edu.sdccd.cisc191.template;

/**
 * This class creates a swimming event. The distance and time,
 * as a string and double, can be accessed
 */
public class Event implements Comparable<Event> {
    private String distance;
    private String time;
    private Double timeInSeconds;

    // constructor
    public Event(String distance, String time, Double timeInSeconds) {
        this.distance = distance;
        this.time = time;
        this.timeInSeconds = timeInSeconds;
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

    /**
     * gets time in seconds, as a double
     * @return time in seconds
     */
    public Double getTimeInSeconds() {
        return timeInSeconds;
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
        return this.time.compareTo(next.time);
    }
}

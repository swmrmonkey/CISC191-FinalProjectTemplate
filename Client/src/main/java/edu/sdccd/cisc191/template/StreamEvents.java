package edu.sdccd.cisc191.template;

/**
 * This class creates swimming events, which is used
 * in sortedWithAPI method in Client. Unlike the Event class,
 * the distance, stroke, and time are accessed
 */
public class StreamEvents{
    private String distance;
    private String stroke;
    private String time;

    // constructor
    public StreamEvents(String distance, String stroke, String time) {
        this.distance = distance;
        this.stroke = stroke;
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
     * gets stroke
     * @return stroke
     */
    public String getStroke() {
        return stroke;
    }

    /**
     * gets time
     * @return time
     */
    public String getTime() {
        return time;
    }

    @Override
    /**
     * returns StreamEvents as string
     */
    public String toString() {
        return distance + " " + stroke + " - " + time;
    }
}

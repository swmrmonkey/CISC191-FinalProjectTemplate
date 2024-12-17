package edu.sdccd.cisc191.template.Events;

/**
 * This class creates swimming events, which is used
 * in sortedWithAPI method in Client. Unlike the Event class,
 * the distance, stroke, and time are accessed
 */
public class StreamEvents extends Event {
    private String stroke;

    // constructor
    public StreamEvents(String distance, String stroke, String time) {
        super(distance, time);
        this.stroke = stroke;
    }

    /**
     * gets stroke
     * @return stroke
     */
    public String getStroke() {
        return stroke;
    }
}

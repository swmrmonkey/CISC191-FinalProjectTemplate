package edu.sdccd.cisc191.template;

import java.util.Set;
import java.util.TreeMap;

/**
 * This class implements a Treemap in order to get the most recent times
 * from the AllTimes file
 */
public class SwimmingEventsMap {
    private TreeMap<String, String> eventTreeMap;

    // constructor
    public SwimmingEventsMap() {
        eventTreeMap = new TreeMap<>();
    }

    /**
     * add event to the treemap
     * @param eventName
     * @param eventTime
     */
    public void addEvent(String eventName, String eventTime) {
        eventTreeMap.put(eventName, eventTime);
    }

    /**
     * get event from treemap
     * @return event
     */
    public String getEvent() {
        return eventTreeMap.keySet().iterator().next();
    }

    /**
     * gets the treemap
     * @return treemap
     */
    public TreeMap<String, String> getEventTreeMap() {
        return eventTreeMap;
    }

    /**
     * creates a set out of key elements
     * @return set of keys
     */
    public Set<String> getKeySet() {
        return eventTreeMap.keySet();
    }

    /**
     * gets the value of the key
     * @param key
     * @return value of key
     */
    public String getValue(String key) {
        return eventTreeMap.get(key);
    }
}

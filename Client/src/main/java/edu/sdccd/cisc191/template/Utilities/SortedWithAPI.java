package edu.sdccd.cisc191.template.Utilities;

import edu.sdccd.cisc191.template.Events.StreamEvents;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SortedWithAPI {
    /**
     * Sorts times by stroke, distance, then speed with parallel streams
     * @param originalFile
     * @throws IOException
     */
    public static void sort(File originalFile) throws IOException {
        List<StreamEvents> events = convertFileToList(originalFile);

        // starts the stream
        Map<String, List<StreamEvents>> sortedEvents = events.parallelStream()
                .sorted(Comparator.comparing(StreamEvents::getStroke) // Sort by stroke
                        .thenComparing(event -> Integer.parseInt(event.getDistance())) // Then sort by distance
                        .thenComparing(event -> ConvertTime.convert(event.getTime()))) // Then sort by time
                .collect(Collectors.groupingBy(StreamEvents::getStroke)); // Group by stroke

        // prints the map
        sortedEvents.forEach((stroke, eventList) -> {
            // Print the stroke
            System.out.println("Stroke: " + stroke);

            // Print each event for the current stroke, sorted by distance and time
            eventList.forEach(event -> System.out.println("  " + event.getDistance() + " - " + event.getTime()));
            System.out.println();
        });
    }

    /**
     * Converts a file into an arraylist
     * @param file
     * @return
     */
    private static List<StreamEvents> convertFileToList(File file) {
        // creates an ArrayList of StreamEvents
        List<StreamEvents> events = new ArrayList<>();

        // reads a file and puts it into the ArrayList
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                GetStringParts.newStreamEvents(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return events;
    }
}

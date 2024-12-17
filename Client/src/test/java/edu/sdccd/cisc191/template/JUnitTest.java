package edu.sdccd.cisc191.template;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import edu.sdccd.cisc191.template.Client;

import static org.junit.jupiter.api.Assertions.*;

class EventsArrayTest {

    private Client client;

    @BeforeEach
    void setUp() {
        client = new Client();
    }

    @Test
    void testEventsArrayContents() {
        String[][] expectedEvents = {
                {"50 free", "100 free", "200 free"},
                {"50 back", "100 back", "200 back"},
                {"50 breast", "100 breast", "200 breast"},
                {"50 fly", "100 fly", "200 fly"},
                {"200 IM", "400 IM"}
        };

        // Check if the length of the events array matches the expected length
        assertEquals(expectedEvents.length, client.events.length, "The number of event categories does not match.");

        // Loop through each category and check the events
        for (int i = 0; i < expectedEvents.length; i++) {
            assertArrayEquals(expectedEvents[i], client.events[i], "Events in category " + (i + 1) + " do not match.");
        }
    }

    @Test
    void testSpecificEventInArray() {
        // Check if a specific event exists in the array
        String event = client.events[0][1]; // "100 free"
        assertEquals("100 free", event, "The event at client.events[0][1] should be '100 free'.");
    }

    @Test
    void testArrayBounds() {
        // Ensure no IndexOutOfBoundsException is thrown
        assertDoesNotThrow(() -> {
            for (String[] category : client.events) {
                for (String event : category) {
                    assertNotNull(event); // Ensure no null events
                }
            }
        }, "Accessing events array should not throw an IndexOutOfBoundsException.");
    }
}
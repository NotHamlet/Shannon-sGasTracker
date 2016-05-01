package edu.auburn.eng.csse.comp3710.idklol.shannonsgastracker.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.auburn.eng.csse.comp3710.idklol.shannonsgastracker.GasEntry;
import edu.auburn.eng.csse.comp3710.idklol.shannonsgastracker.LogEntry;
import edu.auburn.eng.csse.comp3710.idklol.shannonsgastracker.VehicleLog;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p/>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<LogEntry> ITEMS = new ArrayList<>();
    public static final VehicleLog VEHICLE_LOG;

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, LogEntry> ITEM_MAP = new HashMap<String, LogEntry>();

    private static final int COUNT = 25;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(GasEntry.randomEntry());
        }

        VEHICLE_LOG = VehicleLog.createRandomLog();
    }

    private static void addItem(LogEntry item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.getID(), item);
    }

    private static DummyItem createDummyItem(int position) {
        return new DummyItem(String.valueOf(position), "Item " + position, makeDetails(position));
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class DummyItem {
        public final String id;
        public final String content;
        public final String details;

        public DummyItem(String id, String content, String details) {
            this.id = id;
            this.content = content;
            this.details = details;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}

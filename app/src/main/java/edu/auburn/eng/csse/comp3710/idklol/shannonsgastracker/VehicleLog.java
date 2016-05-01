package edu.auburn.eng.csse.comp3710.idklol.shannonsgastracker;

import java.util.ArrayList;
import java.util.Random;

/**
 *  Stores and manages a list of entries for a single Vehicle
 */

public class VehicleLog {
    private ArrayList<LogEntry> mEntries;


    public VehicleLog() {
        mEntries = new ArrayList<>();
    }

    public int size() {
        return mEntries.size();
    }

    public LogEntry get(int index) {
        return mEntries.get(index);
    }

    public void addEntry(LogEntry entry) {
        if (entry ==  null) {
            throw new IllegalArgumentException();
        }

        mEntries.add(entry);
    }

    // TODO: Remove this before release
    public static VehicleLog createRandomLog() {
        VehicleLog result = new VehicleLog();

        Random r = new Random();

        for (int i = 0; i < 25; i++) {
            int n = r.nextInt(100);
            if (n < 75) {
                result.mEntries.add(GasEntry.randomEntry());
            }
            else if (n < 90) {
                result.mEntries.add(ServiceEntry.randomEntry());
            }
            else {
                result.mEntries.add(LogEntry.randomEntry());
            }
        }

        return result;
    }


}

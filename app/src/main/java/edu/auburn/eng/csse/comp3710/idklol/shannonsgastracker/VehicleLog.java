package edu.auburn.eng.csse.comp3710.idklol.shannonsgastracker;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

/**
 *  Stores and manages a list of entries for a single Vehicle
 */

public class VehicleLog {
    private static final double TOLERANCE = .0001;


    private ArrayList<LogEntry> mEntries;
    private Comparator<LogEntry> mComp = new OdometerComparator();

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

        for (int i = 0; i < mEntries.size(); i++) {
            if (entry.getID().equals(mEntries.get(i).getID())) {
                mEntries.remove(i);
                i--;
            }
        }

        int index = 0;
        while (index < mEntries.size() && mComp.compare(entry, mEntries.get(index)) > 0) {
            index++;
        }
        mEntries.add(index,entry);

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

    public LogEntry getEntryWithID(String entryID) {
        for (LogEntry entry : mEntries) {
            if (entry.getID().equals(entryID)) {
                return entry;
            }
        }

        return null;
    }

    public void deleteEntryWithID(String entryID) {
        for (int i = 0; i < mEntries.size(); i++) {
            if (mEntries.get(i).getID().equals(entryID)) {
                mEntries.remove(i);
                i--;
            }
        }
    }

    private class OdometerComparator implements Comparator<LogEntry>{

        @Override
        public int compare(LogEntry lhs, LogEntry rhs) {
            double result = (rhs.getOdometer()-lhs.getOdometer());
            if (Math.abs(result) < TOLERANCE) {
                return 0;
            }
            if (result > 0) {
                return 1;
            }
            return -1;
        }

    }
}

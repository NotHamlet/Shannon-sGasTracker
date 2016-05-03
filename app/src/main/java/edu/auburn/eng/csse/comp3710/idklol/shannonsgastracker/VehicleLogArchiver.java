package edu.auburn.eng.csse.comp3710.idklol.shannonsgastracker;

import java.io.IOException;

public interface VehicleLogArchiver {
    void loadEntries(VehicleLog destination);

    void saveEntries(VehicleLog source) throws IOException;
}

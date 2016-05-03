package edu.auburn.eng.csse.comp3710.idklol.shannonsgastracker;

import android.content.Context;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface VehicleLogArchiver {
    VehicleLog loadEntries(Context context) throws IOException;

    void saveEntries(VehicleLog source, Context context) throws IOException;
}

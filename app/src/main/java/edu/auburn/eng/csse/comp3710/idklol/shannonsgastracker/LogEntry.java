package edu.auburn.eng.csse.comp3710.idklol.shannonsgastracker;

/**
 * Created by stephen on 4/27/16.
 */

import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class LogEntry {
    private String ID = UUID.randomUUID().toString();
    private Date mDate = new Date();
    private double mOdometer;
    private String mNote;

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public double getOdometer() {
        return mOdometer;
    }

    public void setOdometer(double odometer) {
        mOdometer = odometer;
    }

    // TODO: Remove this before release
    public static LogEntry randomEntry() {
        LogEntry result = new LogEntry();
        Random r = new Random();
        result.setOdometer((10000+r.nextInt(990000))/10.0);
        return result;
    }

    public void setNote(String note) {
        mNote = note;
    }

    public String getNote() {
        return mNote;
    }
}

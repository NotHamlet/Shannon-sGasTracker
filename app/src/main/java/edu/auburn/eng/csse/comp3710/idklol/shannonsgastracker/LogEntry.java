package edu.auburn.eng.csse.comp3710.idklol.shannonsgastracker;

/**
 * Created by stephen on 4/27/16.
 */

import java.util.Date;
import java.util.UUID;

public class LogEntry {
    private String ID = UUID.randomUUID().toString();
    private Date mDate = new Date();
    private double mOdometer;

    public Date getDate() {
        return mDate;
    }


    public void setDate(Date date) {
        mDate = date;
    }

    public String getID() {
        return ID;
    }

    public double getOdometer() {
        return mOdometer;
    }

    public void setOdometer(double odometer) {
        mOdometer = odometer;
    }


}

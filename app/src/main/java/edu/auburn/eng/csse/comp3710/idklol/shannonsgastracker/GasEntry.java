package edu.auburn.eng.csse.comp3710.idklol.shannonsgastracker;

import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * Created by stephen on 4/27/16.
 */
public class GasEntry implements LogEntry {

    private Date mDate = new Date();
    private double mGallons;
    private double mPrice;
    String id = UUID.randomUUID().toString();

        public GasEntry() {
        mGallons = 0;
        mPrice = 0;
    }

    public static GasEntry randomEntry() {
        GasEntry newEntry = new GasEntry();
        Random rand = new Random();
        newEntry.mPrice = ((double)(50+rand.nextInt(200)))/100;
        newEntry.mGallons = (1+rand.nextInt(149))/10;
        return newEntry;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public double getPrice() {
        return mPrice;
    }

    public void setPrice(double price) {
        mPrice = price;
    }

    public double getGallons() {
        return mGallons;
    }

    public void setGallons(double gallons) {
        mGallons = gallons;
    }

    @Override
    public String getID() {
        return id;
    }
}

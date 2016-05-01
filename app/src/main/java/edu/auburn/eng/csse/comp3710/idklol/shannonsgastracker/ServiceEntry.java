package edu.auburn.eng.csse.comp3710.idklol.shannonsgastracker;

import java.util.Random;

/**
 * Created by stephen on 5/1/16.
 */
public class ServiceEntry extends LogEntry{
    private String mServiceType;

    public String getServiceType() {
        return mServiceType;
    }

    public void setServiceType(String serviceType) {
        mServiceType = serviceType;
    }

    //TODO: consider adding something like "next service mileage" to allow for reminders

    //TODO: Remove this method before release
    public static LogEntry randomEntry() {
        ServiceEntry result = new ServiceEntry();
        Random r = new Random();
        result.setOdometer((10000+r.nextInt(990000))/10.0);
        result.setServiceType("Oil Change");
        return result;
    }

}

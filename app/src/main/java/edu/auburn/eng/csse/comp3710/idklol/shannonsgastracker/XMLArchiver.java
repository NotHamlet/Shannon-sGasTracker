package edu.auburn.eng.csse.comp3710.idklol.shannonsgastracker;

import android.content.Context;
import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;

/**
 * Implements the VehicleLogArchiver class to serialize and deserialize XML representations
 *      of VehicleLog instances
 */
public class XMLArchiver implements VehicleLogArchiver {

    private static final String TAG_VEHICLE_LOG = "VehicleLog";
    private static final String TAG_GAS_ENTRY = "GasEntry";
    private static final String TAG_SERVICE_ENTRY = "ServiceEntry";
    private static final String TAG_ENTRY = "Entry";

    private static final String ATTR_ODOMETER = "odometer";
    private static final String ATTR_ID = "id";
    private static final String ATTR_GALLONS = "gallons";
    private static final String ATTR_PRICE = "price";
    private static final String ATTR_SERVICE_TYPE = "service_type";
    private static final String ATTR_NOTE = "note";


    @Override
    public VehicleLog loadEntries(Context context) throws IOException {

        // TODO: Clean this up to make better use of XML (Currently a bit of a hack)

        VehicleLog newLog = new VehicleLog();

        XmlPullParser xpp = Xml.newPullParser();
        String fileName = String.format("%s.vlog", "default");
        try {
            xpp.setInput(context.openFileInput(fileName), "UTF-8");
        } catch (Exception e) {
            return newLog;
        }


        try {
            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if (xpp.getName().equals(TAG_VEHICLE_LOG)){
                            buildVehicleLog(xpp, newLog);
                        }
                        break;
                }
                eventType = xpp.next();
            }
        } catch (XmlPullParserException e) {
            Log.e("XMLTest", "Some sort of XML Issue has transpired!");
            e.printStackTrace();
            return newLog;
        }

        System.out.println("End document");

        return newLog;
    }

    private void buildVehicleLog(XmlPullParser xpp, VehicleLog newlog)
            throws IOException, XmlPullParserException {

        int eventType = xpp.nextTag();
        while (eventType != XmlPullParser.END_TAG) {
            String tag = xpp.getName();
            Log.i("XMLTest", tag);
            LogEntry newEntry;
            if (tag.equals(TAG_GAS_ENTRY)) {
                GasEntry gasEntry= new GasEntry();
                String gallonsAttr = xpp.getAttributeValue("",ATTR_GALLONS);
                if (gallonsAttr != null) {
                    gasEntry.setGallons(Double.valueOf(gallonsAttr));
                }
                String priceAttr = xpp.getAttributeValue("",ATTR_PRICE);
                if (priceAttr != null) {
                    gasEntry.setPrice(Double.valueOf(priceAttr));
                }
                newEntry = gasEntry;
            }
            else if (tag.equals(TAG_SERVICE_ENTRY)) {
                ServiceEntry serviceEntry = new ServiceEntry();
                String serviceTypeAttr = xpp.getAttributeValue("",ATTR_SERVICE_TYPE);
                if (serviceTypeAttr != null) {
                    serviceEntry.setServiceType(serviceTypeAttr);
                }
                newEntry = serviceEntry;
            }
            else {
                newEntry = new LogEntry();
            }


            String odometerAttr = xpp.getAttributeValue("", ATTR_ODOMETER);
            if (odometerAttr != null) {
                newEntry.setOdometer(Double.valueOf(odometerAttr));
            }
            String idAttr = xpp.getAttributeValue("", ATTR_ID);
            if (idAttr != null) {
                newEntry.setID(idAttr);
            }
            String noteAttr = xpp.getAttributeValue("", ATTR_NOTE);
            if (noteAttr != null) {
                newEntry.setNote(noteAttr);
            }

            newlog.addEntry(newEntry);

            xpp.nextTag();
            eventType = xpp.nextTag();

        }
    }

    @Override
    public void saveEntries(VehicleLog source, Context context) throws IOException{
        XmlSerializer ser = Xml.newSerializer();
        String fileName = String.format("%s.vlog", "default");
        ser.setOutput(context.openFileOutput(fileName, Context.MODE_PRIVATE), "UTF-8");

        ser.startDocument("UTF-8", true);
        ser.startTag("", TAG_VEHICLE_LOG);
        for (int i = 0; i < source.size(); i++) {
            serializeEntry(source.get(i),ser);
        }
        ser.endTag("", TAG_VEHICLE_LOG);
        ser.endDocument();

//        Log.i("XMLTest", writer.toString());

    }

    private void serializeEntry(LogEntry entry, XmlSerializer ser) throws IOException{
        if (entry instanceof GasEntry) {
            GasEntry gasE = (GasEntry)entry;
            ser.startTag("", TAG_GAS_ENTRY)
                    .attribute("", ATTR_ID, gasE.getID())
                    .attribute("", ATTR_ODOMETER, Double.toString(gasE.getOdometer()))
                    .attribute("", ATTR_GALLONS, Double.toString(gasE.getGallons()))
                    .attribute("", ATTR_PRICE, Double.toString(gasE.getPrice()))
                    .attribute("", ATTR_NOTE, entry.getNote())
                    .endTag("", TAG_GAS_ENTRY);
        }
        else if (entry instanceof ServiceEntry) {
            ServiceEntry serviceE = (ServiceEntry)entry;
            ser.startTag("", TAG_SERVICE_ENTRY)
                    .attribute("", ATTR_ID, serviceE.getID())
                    .attribute("", ATTR_ODOMETER, Double.toString(serviceE.getOdometer()))
                    .attribute("", ATTR_SERVICE_TYPE, serviceE.getServiceType())
                    .attribute("", ATTR_NOTE, entry.getNote())
                    .endTag("", TAG_SERVICE_ENTRY);
        }
        else {
            ser.startTag("", TAG_ENTRY)
                    .attribute("", ATTR_ID, entry.getID())
                    .attribute("", ATTR_ODOMETER, Double.toString(entry.getOdometer()))
                    .attribute("", ATTR_NOTE, entry.getNote())
                    .endTag("", TAG_ENTRY);
        }
    }
}

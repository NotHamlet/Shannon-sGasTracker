package edu.auburn.eng.csse.comp3710.idklol.shannonsgastracker;

import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;
import java.io.StringWriter;

/**
 * Implements the VehicleLogArchiver class to seriealize and deserialize XML representations of VehicleLog
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


    @Override
    public void loadEntries(VehicleLog destination) {

    }

    @Override
    public void saveEntries(VehicleLog source) throws IOException{
        XmlSerializer ser = Xml.newSerializer();
        StringWriter writer = new StringWriter();
        ser.setOutput(writer);

        ser.startDocument("UTF-8", true);
        ser.text("\n");
        ser.startTag("", TAG_VEHICLE_LOG);
        ser.text("\n");
        for (int i = 0; i < source.size(); i++) {
            serializeEntry(source.get(i),ser);
        }
        ser.endTag("", TAG_VEHICLE_LOG);
        ser.endDocument();

        Log.i("XMLtest", writer.toString());

    }

    private void serializeEntry(LogEntry entry, XmlSerializer ser) throws IOException{
        if (entry instanceof GasEntry) {
            GasEntry gasE = (GasEntry)entry;
            ser.startTag("", TAG_GAS_ENTRY)
                    .attribute("", ATTR_ID, gasE.getID())
                    .attribute("", ATTR_ODOMETER, Double.toString(gasE.getOdometer()))
                    .attribute("", ATTR_GALLONS, Double.toString(gasE.getGallons()))
                    .attribute("", ATTR_PRICE, Double.toString(gasE.getGallons()))
                    .endTag("", TAG_GAS_ENTRY)
                    .text("\n");
        }
        else if (entry instanceof ServiceEntry) {
            ServiceEntry serviceE = (ServiceEntry)entry;
            ser.startTag("", TAG_SERVICE_ENTRY)
                    .attribute("", ATTR_ID, serviceE.getID())
                    .attribute("", ATTR_ODOMETER, Double.toString(serviceE.getOdometer()))
                    .attribute("", ATTR_SERVICE_TYPE, serviceE.getServiceType())
                    .endTag("", TAG_SERVICE_ENTRY)
                    .text("\n");
        }
        else {
            ser.startTag("", TAG_ENTRY)
                    .attribute("", ATTR_ID, entry.getID())
                    .attribute("", ATTR_ODOMETER, Double.toString(entry.getOdometer()))
                    .endTag("", TAG_ENTRY)
                    .text("\n");
        }
    }
}

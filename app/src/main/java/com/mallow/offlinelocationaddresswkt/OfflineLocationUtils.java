package com.mallow.offlinelocationaddresswkt;

import android.content.Context;
import android.util.Log;

import com.opencsv.CSVReader;

import org.locationtech.spatial4j.context.jts.JtsSpatialContext;
import org.locationtech.spatial4j.io.WKTReader;
import org.locationtech.spatial4j.shape.Point;
import org.locationtech.spatial4j.shape.SpatialRelation;
import org.locationtech.spatial4j.shape.jts.JtsGeometry;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;

/**
 * Created by manikandan on 24/02/18.
 */

public class OfflineLocationUtils {
    public static final String TAG = "OfflineLocationUtils";

    static String getLocationAddress(Context context) {
        String currentAddress = "Can't find location";
        try {
            JtsSpatialContext ctx = JtsSpatialContext.GEO;
            CSVReader reader = new CSVReader(new InputStreamReader(context.getAssets().open("province_polygon_data.csv")));//Specify asset file
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                String wktString = nextLine[2];
                if (wktString != null && wktString.length() > 0) {
                    JtsGeometry dynamicPolyI = (JtsGeometry) ((WKTReader) ctx.getFormats().getWktReader()).parseIfSupported(wktString);
                    //a boundary point of base
                    Point pointB = ctx.makePoint(-123.21234, 48.746778);
                    SpatialRelation relate = pointB.relate(dynamicPolyI);
                    if (relate.equals(SpatialRelation.WITHIN)) {
                        currentAddress = nextLine[0] + "," + nextLine[1];
                        Log.d(TAG, "WITHIN " + nextLine[0] + ", " + nextLine[1]);
                        break;
                    }
                } else {
                    Log.d(TAG, "onCreate: values is null or length is 0");
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return currentAddress;
    }
}

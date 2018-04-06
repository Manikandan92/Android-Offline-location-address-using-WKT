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
import java.util.ArrayList;

/**
 * Created by manikandan on 24/02/18.
 */

public class OfflineLocationUtils {
    public static final String TAG = "OfflineLocationUtils";
    public static ArrayList<LocationModel> locationList = new ArrayList<>();

    static String getLocationAddressByProvince(Context context) {
        locationList = getLocationList();
        String currentAddress = "Can't find location";
        try {
            JtsSpatialContext ctx = JtsSpatialContext.GEO;
            CSVReader reader = new CSVReader(new InputStreamReader(context.getAssets().open("province_polygon_data.csv")));//Specify asset file
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                String wktString = nextLine[2];
                if (wktString != null && wktString.length() > 0) {
                    JtsGeometry dynamicPolyI = (JtsGeometry) ((WKTReader) ctx.getFormats().getWktReader()).parseIfSupported(wktString);
                    for (LocationModel locationModel : locationList) {
                        //a boundary point of base
                        Point pointB = ctx.makePoint(locationModel.getLongitude(), locationModel.getLatitude());
                        SpatialRelation relate = pointB.relate(dynamicPolyI);
                        if (relate.equals(SpatialRelation.WITHIN)) {
                            currentAddress = nextLine[0] + "," + nextLine[1];
                            Log.d(TAG, "WITHIN ==> Current province:  " + nextLine[0] + ", " + nextLine[1]);
                            break;
                        }
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

    static String getLocationAddressByCountry(Context context) {
        locationList = getLocationList();
        String currentAddress = "Can't find location";
        try {
            JtsSpatialContext ctx = JtsSpatialContext.GEO;
            CSVReader reader = new CSVReader(new InputStreamReader(context.getAssets().open("country_polygon_data.csv")));//Specify asset file
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                String wktString = nextLine[1];
                if (wktString != null && wktString.length() > 0) {
                    JtsGeometry dynamicPolyI = (JtsGeometry) ((WKTReader) ctx.getFormats().getWktReader()).parseIfSupported(wktString);
                    for (LocationModel locationModel : locationList) {
                        //a boundary point of base
                        Point pointB = ctx.makePoint(locationModel.getLongitude(), locationModel.getLatitude());
                        SpatialRelation relate = pointB.relate(dynamicPolyI);
                        if (relate.equals(SpatialRelation.WITHIN)) {
                            currentAddress = nextLine[0];
                            Log.d(TAG, "WITHIN ==> Current Country: " + nextLine[0]);
                        }
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

    private static ArrayList<LocationModel> getLocationList() {
        ArrayList<LocationModel> locationList = new ArrayList<>();
        //        Vancouver Convention Centre West Building
//        1055 Canada Pl, Vancouver, BC V6C 0C3, Canada, 1055 Canada Pl, Vancouver, BC V6C, Canada
//        49.288870, -123.115855
//
//        Inuvik, Unorganized
//        Northwest Territories, Canada
//        68.795043, -127.428602
//
//        Yukon
//        Yukon Territory, Canada
//        63.717804, -140.241127
//
//        British Columbia
//        Canada
//        50.241523, -123.726077
//                ==================================================
//        Northwest Arctic
//        Alaska, USA
//        66.908693, -157.696565
//
//        Montana
//        United States
//        48.183147, -108.019215
//
//
//        Dillingham
//                Alaska, USA
//        60.479019, -157.151555
//
//        Yukon-Koyukuk
//        Alaska, USA
//        65.976517, -149.503706

        // us
        locationList.add(new LocationModel(66.908693, -157.696565));
        // ca
        locationList.add(new LocationModel(49.288870, -123.115855));
        //us
        locationList.add(new LocationModel(48.183147, -108.019215));
        // ca
        locationList.add(new LocationModel(68.795043, -127.428602));
        //us
        locationList.add(new LocationModel(60.479019, -157.151555));
        // ca
        locationList.add(new LocationModel(63.717804, -140.241127));
        //us
        locationList.add(new LocationModel(65.976517, -149.503706));
        // ca
        locationList.add(new LocationModel(50.241523, -123.726077));
        return locationList;
    }
}

package com.example.tinyterm1.helloworld.Services;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.tinyterm1.helloworld.ApiCall.GeoLocationApiCall;
import com.example.tinyterm1.helloworld.ApiCall.GeoLocationErrorInterface;
import com.example.tinyterm1.helloworld.ApiCall.GeoLocationSuccessInterface;
import com.example.tinyterm1.helloworld.Models.GeoLocation;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by TinyTerm1 on 08/01/2017.
 */

public class FichadoPost {
    public void Post(final Context context, GeoLocationSuccessInterface successInterface, GeoLocationErrorInterface errorInterface) {
        GeoLocationApiCall geoLocationApiCall = new GeoLocationApiCall();
        GeoLocation actualLocation = new GeoLocation();

        GPSTracker gps = new GPSTracker(context);

        if(gps.canGetLocation()) {
            actualLocation.setCoordenadaX(gps.getLatitude());
            actualLocation.setCoordenadaY(gps.getLongitude());
            actualLocation.setTimeStamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

            geoLocationApiCall.postCurrentLocation(actualLocation, context, successInterface, errorInterface);
            gps.stopUsingGPS();
        }
    }
}

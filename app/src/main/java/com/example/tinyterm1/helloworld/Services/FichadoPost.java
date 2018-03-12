package com.example.tinyterm1.helloworld.Services;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.tinyterm1.helloworld.Activities.PrincipalActivity;
import com.example.tinyterm1.helloworld.ApiCall.GeoLocationApiCall;
import com.example.tinyterm1.helloworld.ApiCall.GeoLocationErrorInterface;
import com.example.tinyterm1.helloworld.ApiCall.GeoLocationSuccessInterface;
import com.example.tinyterm1.helloworld.Models.GeoLocation;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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
            if(gps.getLatitude() == 0 && gps.getLongitude() == 0)
                errorInterface.MostrarMensaje("No se pudo obtener la ubicación actual.", Calendar.getInstance().toString());
            else {
                actualLocation.setCoordenadaX(gps.getLatitude());
                actualLocation.setCoordenadaY(gps.getLongitude());
                actualLocation.setTimeStamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

                geoLocationApiCall.postCurrentLocation(actualLocation, context, successInterface, errorInterface);
                gps.stopUsingGPS();
            }
        }else{
            errorInterface.MostrarMensaje("Habilite el GPS para el correcto funcionamiento de la aplicación.", Calendar.getInstance().toString());
        }

    }
}

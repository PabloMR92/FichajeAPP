package com.example.tinyterm1.helloworld.ApiCall;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.tinyterm1.helloworld.Models.GeoLocationErrorRequest;
import com.example.tinyterm1.helloworld.Models.LoginErrorRequest;
import com.example.tinyterm1.helloworld.Models.FichadoResponse;
import com.example.tinyterm1.helloworld.Models.GeoLocation;
import com.example.tinyterm1.helloworld.SharedPreferences.UUIDKeyValueDB;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.io.IOException;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by TinyTerm1 on 04/01/2017.
 */

public class GeoLocationApiCall {

    public void postCurrentLocation(final GeoLocation currentLocation, final Context ctx, final GeoLocationSuccessInterface geoLocationSuccessInterface,
                                    final GeoLocationErrorInterface geoLocationErrorInterface){


        currentLocation.setUUID(UUIDKeyValueDB.getUUID(ctx));
        Call<FichadoResponse> call = RestClient.getApiService(ctx).fichar(currentLocation, UUIDKeyValueDB.getUUID(ctx));
        call.enqueue(new Callback<FichadoResponse>() {
                         @Override
                         public void onResponse(Response<FichadoResponse> response, Retrofit retrofit) {

                             try {
                                 int responseCoe = response.code();
                                 if (response.code() == 200) {
                                     geoLocationSuccessInterface.MostrarMensaje(response.body().getMensaje(), currentLocation.getTimeStamp().toString());
                                 }
                                 else if(response.code() == 400) {
                                     Gson gson = new Gson();
                                     GeoLocationErrorRequest responseLoginError = new GeoLocationErrorRequest();
                                     TypeAdapter<GeoLocationErrorRequest> adapter = gson.getAdapter(GeoLocationErrorRequest.class);
                                     try {
                                         if (response.errorBody() != null) {
                                             responseLoginError =
                                                     adapter.fromJson(
                                                             response.errorBody().string());
                                             geoLocationErrorInterface.MostrarMensaje(responseLoginError.getMessage(), currentLocation.getTimeStamp().toString());
                                         }
                                     } catch (IOException e) {
                                         e.printStackTrace();
                                     }
                                 }
                                 else if (response.code() == 401){
                                     geoLocationErrorInterface.MostrarMensaje("Acceso no autorizado. Póngase en contacto con un administrador.", currentLocation.getTimeStamp().toString());
                                 }
                                 else if (response.code() == 500){
                                     geoLocationErrorInterface.MostrarMensaje("Ocurrió un error inesperado en el servidor. Inténtelo denuevo más tarde.", currentLocation.getTimeStamp().toString());
                                 }
                                 else {
                                     geoLocationErrorInterface.MostrarMensaje("Ocurrió un error inesperado en la aplicación. Inténtelo denuevo más tarde.", currentLocation.getTimeStamp().toString());
                                 }
                             } catch (Exception e)
                             {
                                 Log.d("onResponse", "There is an error");
                                 e.printStackTrace();
                             }
                         }
                         @Override
                         public void onFailure(Throwable t) {
                             Log.d("onFailure", t.toString());
                         }
                     }

        );
    }
}

package com.example.tinyterm1.helloworld.ApiCall;

import android.content.Context;
import android.util.Log;

import com.example.tinyterm1.helloworld.Models.Establecimiento;
import com.example.tinyterm1.helloworld.Models.ListadoRequest;
import com.example.tinyterm1.helloworld.SharedPreferences.UUIDKeyValueDB;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class GetEstablecimientosApiCall {

    public void getEstablecimientos(Context ctx, final EstablecimientosSuccessInterface establecimientosSuccessInterface){
        Map<String, String> params = new HashMap<>();
        params.put("UUID", UUIDKeyValueDB.getUUID(ctx));

        Call<ArrayList<Establecimiento>> call = RestClient.getApiService(ctx).ObtenerEstablecimientos(params);
        call.enqueue(new Callback<ArrayList<Establecimiento>>() {
                         @Override
                         public void onResponse(Response<ArrayList<Establecimiento>> response, Retrofit retrofit) {

                             try {
                                 if (response.code() == 200) {
                                     establecimientosSuccessInterface.GetEstablecimientos(response.body());
                                 }
                                 else if(response.code() == 400) {
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

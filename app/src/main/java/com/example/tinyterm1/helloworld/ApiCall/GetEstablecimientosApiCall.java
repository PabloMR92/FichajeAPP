package com.example.tinyterm1.helloworld.ApiCall;

import android.content.Context;
import android.util.Log;

import com.example.tinyterm1.helloworld.Models.Establecimiento;
import com.example.tinyterm1.helloworld.Models.ListadoRequest;
import com.example.tinyterm1.helloworld.SharedPreferences.UUIDKeyValueDB;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class GetEstablecimientosApiCall {

    public void getEstablecimientos(Context ctx, final EstablecimientosSuccessInterface establecimientosSuccessInterface){
        Map<String, String> params = new HashMap<>();
        params.put("UUID", UUIDKeyValueDB.getUUID(ctx));

        Call<ArrayList<Establecimiento>> call = RestClient.getApiService(ctx).ObtenerEstablecimientos(params);
        call.enqueue(new Callback<ArrayList<Establecimiento>>() {
            @Override
            public void onResponse(Call<ArrayList<Establecimiento>> call, Response<ArrayList<Establecimiento>> response) {
                try {
                    if (response.code() == 200) {
                        establecimientosSuccessInterface.GetEstablecimientos(response.body());
                    } else if (response.code() == 400) {
                    }
                } catch (Exception e) {
                    Log.d("onResponse", "There is an error");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Establecimiento>> call, Throwable t) {
                Log.d("onFailure", t.toString());
            }
                     }

        );
    }
}

package com.example.tinyterm1.helloworld.ApiCall;

import android.content.Context;
import android.util.Log;

import com.example.tinyterm1.helloworld.Models.Establecimiento;
import com.example.tinyterm1.helloworld.Models.TipoDeHorario;
import com.example.tinyterm1.helloworld.SharedPreferences.UUIDKeyValueDB;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by TinyTerm1 on 15/01/2017.
 */


public class GetTiposHorarioApiCall {

    public void getTiposHorario(Context ctx, final TipoHorarioSuccessInterface tipoHorarioSuccessInterface){
        Map<String, String> params = new HashMap<>();
        params.put("UUID", UUIDKeyValueDB.getUUID(ctx));

        Call<ArrayList<TipoDeHorario>> call = RestClient.getApiService(ctx).ObtenerTiposHorario(params);
        call.enqueue(new Callback<ArrayList<TipoDeHorario>>() {
            @Override
            public void onResponse(Call<ArrayList<TipoDeHorario>> call, Response<ArrayList<TipoDeHorario>> response) {
                try {
                    if (response.code() == 200) {
                        tipoHorarioSuccessInterface.GetTiposHorario(response.body());
                    } else if (response.code() == 400) {
                    }
                } catch (Exception e) {
                    Log.d("onResponse", "There is an error");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<TipoDeHorario>> call, Throwable t) {
                Log.d("onFailure", t.toString());
            }
                     }

        );
    }
}


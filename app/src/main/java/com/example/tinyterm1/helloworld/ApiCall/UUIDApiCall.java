package com.example.tinyterm1.helloworld.ApiCall;

import android.content.Context;
import android.util.Log;

import com.example.tinyterm1.helloworld.Models.ListadoRequest;
import com.example.tinyterm1.helloworld.SharedPreferences.UUIDKeyValueDB;

import java.util.ArrayList;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Pablo on 03/02/2018.
 */

public class UUIDApiCall {
    public void getUUID(Map<String, String> params, final Context ctx, final UUIDSuccessInterface uuidSuccessInterface){
        Call<Boolean> call = RestClient.getApiService(ctx).uuid(params);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                try {
                    if (response.code() == 200) {
                        uuidSuccessInterface.Existe(response.body());
                    } else if (response.code() == 400) {
                    }
                } catch (Exception e) {
                    Log.d("onResponse", "There is an error");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Log.d("onFailure", t.toString());
            }
                     }

        );
    }
}

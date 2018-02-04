package com.example.tinyterm1.helloworld.ApiCall;

import android.content.Context;
import android.util.Log;

import com.example.tinyterm1.helloworld.Models.ListadoRequest;
import com.example.tinyterm1.helloworld.SharedPreferences.UUIDKeyValueDB;

import java.util.ArrayList;
import java.util.Map;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by Pablo on 03/02/2018.
 */

public class UUIDApiCall {
    public void getUUID(Map<String, String> params, final Context ctx, final UUIDSuccessInterface uuidSuccessInterface){
        Call<Boolean> call = RestClient.getApiService(ctx).uuid(params);
        call.enqueue(new Callback<Boolean>() {
                         @Override
                         public void onResponse(Response<Boolean> response, Retrofit retrofit) {

                             try {
                                 if (response.code() == 200) {
                                     uuidSuccessInterface.Existe(response.body());
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

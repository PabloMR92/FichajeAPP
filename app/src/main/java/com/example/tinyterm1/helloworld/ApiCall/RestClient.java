package com.example.tinyterm1.helloworld.ApiCall;

import android.content.Context;

import com.example.tinyterm1.helloworld.SharedPreferences.DireccionSV;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Retrofit;

/**
 * Created by TinyTerm1 on 02/01/2017.
 */

public class RestClient {

    private static ApiCallREST apiService;

    public static ApiCallREST getApiService(Context ctx) {
        final String BASE_URL = DireccionSV.getDireccion(ctx);

        if (apiService == null) {
            apiService = null;
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(5, TimeUnit.MINUTES)
                    .readTimeout(5,TimeUnit.MINUTES).build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            apiService = retrofit.create(ApiCallREST.class);
        }
        return apiService;
    }
}
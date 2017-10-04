package com.example.tinyterm1.helloworld.ApiCall;

import android.content.Context;

import com.example.tinyterm1.helloworld.SharedPreferences.DireccionSV;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by TinyTerm1 on 02/01/2017.
 */

public class RestClient {

    private static ApiCallREST apiService;

    public static ApiCallREST getApiService(Context ctx) {
        final String BASE_URL = DireccionSV.getDireccion(ctx);

        if (apiService == null) {
            apiService = null;
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            apiService = retrofit.create(ApiCallREST.class);
        }
        return apiService;
    }
}
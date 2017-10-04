package com.example.tinyterm1.helloworld.ApiCall;

import android.content.Context;
import android.util.Log;

import com.example.tinyterm1.helloworld.Models.Domain;
import com.example.tinyterm1.helloworld.Models.TipoDeHorario;
import com.example.tinyterm1.helloworld.SharedPreferences.UUIDKeyValueDB;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class GetDomainsApiCall {

    public void getDomains(Context ctx, final DomainSuccessInterface domainSuccessInterface){
        Call<ArrayList<Domain>> call = RestClient.getApiService(ctx).ObtenerDomain();
        call.enqueue(new Callback<ArrayList<Domain>>() {
                         @Override
                         public void onResponse(Response<ArrayList<Domain>> response, Retrofit retrofit) {

                             try {
                                 if (response.code() == 200) {
                                     domainSuccessInterface.GetDomains(response.body());
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

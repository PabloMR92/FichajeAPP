package com.example.tinyterm1.helloworld.ApiCall;

import android.content.Context;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tinyterm1.helloworld.Adapter.ReporteListAdapter;
import com.example.tinyterm1.helloworld.Models.ListadoRequest;
import com.example.tinyterm1.helloworld.Models.Reporte;
import com.example.tinyterm1.helloworld.R;
import com.example.tinyterm1.helloworld.SharedPreferences.UUIDKeyValueDB;
import com.example.tinyterm1.helloworld.UUID;

import java.util.ArrayList;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by TinyTerm1 on 02/01/2017.
 */

public class ReporteApiCall {

    public void getReporte(Map<String, String> params, final Context ctx, final ReporteSuccessInterface reporteSuccessInterface){
        params.put("UUID", UUIDKeyValueDB.getUUID(ctx));

        Call<ArrayList<ListadoRequest>> call = RestClient.getApiService(ctx).reporte(params);
        call.enqueue(new Callback<ArrayList<ListadoRequest>>() {
            @Override
            public void onResponse(Call<ArrayList<ListadoRequest>> call, Response<ArrayList<ListadoRequest>> response) {
                try {
                    if (response.code() == 200) {
                        reporteSuccessInterface.MostrarReporte(response.body().get(0));
                    } else if (response.code() == 400) {
                    }
                } catch (Exception e) {
                    Log.d("onResponse", "There is an error");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ListadoRequest>> call, Throwable t) {
                Log.d("onFailure", t.toString());
            }
                     }

        );
    }
}



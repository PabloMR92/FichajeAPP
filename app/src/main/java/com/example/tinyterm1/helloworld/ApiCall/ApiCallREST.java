package com.example.tinyterm1.helloworld.ApiCall; /**
 * Created by TinyTerm1 on 25/12/2016.
 */

import com.example.tinyterm1.helloworld.Models.Domain;
import com.example.tinyterm1.helloworld.Models.Establecimiento;
import com.example.tinyterm1.helloworld.Models.FichadoResponse;
import com.example.tinyterm1.helloworld.Models.GeoLocation;
import com.example.tinyterm1.helloworld.Models.ListadoRequest;
import com.example.tinyterm1.helloworld.Models.TipoDeHorario;
import com.example.tinyterm1.helloworld.Models.UserLogin;
import com.example.tinyterm1.helloworld.UUID;

import java.util.ArrayList;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ApiCallREST {

    @POST("api/Login")
    Call<UUID> logIn(@Body UserLogin usuario);

    @GET("api/ListadoTimeSheet")
    Call<ArrayList<ListadoRequest>> reporte(@QueryMap Map<String, String> params);

    @GET("api/UUID")
    Call<Boolean> uuid(@QueryMap Map<String, String> params);

    @POST("api/GeoLocation")
    Call<FichadoResponse> fichar(@Body GeoLocation actualLocation, @Query("UUID") String UUID);

    @GET("api/TipoDeHorario")
    Call<ArrayList<TipoDeHorario>> ObtenerTiposHorario(@QueryMap Map<String, String> params);

    @GET("api/Establecimiento")
    Call<ArrayList<Establecimiento>> ObtenerEstablecimientos(@QueryMap Map<String, String> params);

    @GET("api/Domain")
    Call<ArrayList<Domain>> ObtenerDomain();
}

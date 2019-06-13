package com.clarity.clarity.network;

import com.clarity.clarity.model.Configuration;
import com.clarity.clarity.model.Geolocation;
import com.clarity.clarity.model.JWT;
import com.clarity.clarity.model.TokenInfo;
import com.clarity.clarity.model.UserLogin;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ClarityAPI {
    @POST("api/Login")
    Observable<JWT> logIn(@Body UserLogin credentials);

    @GET("api/TokenInfo")
    Observable<TokenInfo> validateToken(@Header("Authorization") String authorization);

    @GET("api/Configuration")
    Observable<Configuration> getConfiguration(@Header("Authorization") String authorization);

    @POST("api/ClockIn")
    Observable<Response<Void>> clockIn(@Header("Authorization") String authorization, @Body Geolocation currentLocation);
}
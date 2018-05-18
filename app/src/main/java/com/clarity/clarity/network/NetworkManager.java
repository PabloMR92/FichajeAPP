package com.clarity.clarity.network;

import android.text.TextUtils;

import com.clarity.clarity.model.Geolocation;
import com.clarity.clarity.model.JWT;
import com.clarity.clarity.model.TokenInfo;
import com.clarity.clarity.model.UserLogin;
import com.clarity.clarity.utils.StorageKeys;
import com.google.gson.GsonBuilder;
import com.orhanobut.hawk.Hawk;

import org.json.JSONObject;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkManager {
    private static final String AUTH_SCHEME = "Bearer";
    private ClarityAPI mService;

    public NetworkManager() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.17:3000/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .build();

        this.mService = retrofit.create(ClarityAPI.class);
    }

    public Observable<JWT> logIn(UserLogin credentials) {
        return this.mService.logIn(credentials);
    }

    public Observable<TokenInfo> validateToken() {
        return this.mService.validateToken(getAuthorizationHeader());
    }

    public Observable<Response<Void>> clockIn(Geolocation currentLocation) {
        return this.mService.clockIn(getAuthorizationHeader(), currentLocation);
    }

    public String getErrorMessage(ResponseBody responseBody) {
        try {
            JSONObject jsonObject = new JSONObject(responseBody.string());
            return jsonObject.getString("errorMsg");
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    private String getAuthorizationHeader(){
        return TextUtils.join(" ", new String[]{AUTH_SCHEME, Hawk.get(StorageKeys.Token)});
    }
}
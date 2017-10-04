package com.example.tinyterm1.helloworld.ApiCall;

import android.content.Context;
import android.util.Log;

import com.example.tinyterm1.helloworld.Models.LoginErrorRequest;
import com.example.tinyterm1.helloworld.Models.UserLogin;
import com.example.tinyterm1.helloworld.SharedPreferences.UUIDKeyValueDB;
import com.example.tinyterm1.helloworld.UUID;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.io.IOException;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;


public class LoginApiCall {
    public void LogIn(UserLogin user, final Context ctx, final LoginErrorInterface errorInterface, final LoginSuccessInterface successInterface){
        Call<UUID> call = RestClient.getApiService(ctx).logIn(user);

        call.enqueue(new Callback<UUID>() {
                         @Override
                         public void onResponse(Response<UUID> response, Retrofit retrofit) {

                             try {
                                 int responseCode = response.code();
                                 if (response.code() == 400) {
                                     Gson gson = new Gson();
                                     LoginErrorRequest responseLoginError = new LoginErrorRequest();
                                     TypeAdapter<LoginErrorRequest> adapter = gson.getAdapter(LoginErrorRequest.class);
                                     try {
                                         if (response.errorBody() != null) {
                                             responseLoginError =
                                                     adapter.fromJson(
                                                             response.errorBody().string());
                                             errorInterface.MostrarError(responseLoginError);
                                         }
                                     } catch (IOException e) {
                                         e.printStackTrace();
                                     }
                                 } else if (response.code() == 200) {
                                     UUIDKeyValueDB.setUUID(ctx, response.body().get_UUID());
                                     successInterface.IrAPrincipal();
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


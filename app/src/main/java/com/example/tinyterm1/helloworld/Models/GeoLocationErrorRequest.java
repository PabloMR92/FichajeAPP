package com.example.tinyterm1.helloworld.Models;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

/**
 * Created by TinyTerm1 on 04/01/2017.
 */

public class GeoLocationErrorRequest {
        @SerializedName("Message")
        private String message;

        @SerializedName("ModelState")
        private GeoLocationModelState modelState;

        public GeoLocationErrorRequest(String message, GeoLocationModelState modelState)
        {
            this.message    = message;
            this.modelState = modelState;
        }

        public GeoLocationErrorRequest(){}

        public String getMessage()
        {
            return TextUtils.join("",modelState.getLocation());
        }

        public void setMessage(String message)
        {
            this.message = message;
        }

        public GeoLocationModelState getModelState()
        {
            return modelState;
        }

        public void setModelState(GeoLocationModelState modelState)
        {
            this.modelState = modelState;
        }

}

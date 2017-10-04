package com.example.tinyterm1.helloworld.Models;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

public class LoginErrorRequest
{
    @SerializedName("Message")
    private String message;

    @SerializedName("ModelState")
    private LoginModelState modelState;

    public LoginErrorRequest(String message, LoginModelState modelState)
    {
        this.message    = message;
        this.modelState = modelState;
    }

    public LoginErrorRequest(){}

    public String getMessage()
    {
        return TextUtils.join(", " , modelState.getLogin()) + "\n" + TextUtils.join(", " , modelState.getPassword());
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public LoginModelState getModelState()
    {
        return modelState;
    }

    public void setModelState(LoginModelState modelState)
    {
        this.modelState = modelState;
    }
}

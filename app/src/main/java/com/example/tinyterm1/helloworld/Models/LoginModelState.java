package com.example.tinyterm1.helloworld.Models;

/**
 * Created by TinyTerm1 on 26/12/2016.
 */

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class LoginModelState
{
    @SerializedName("usuario.login")
    private List<String> login;

    @SerializedName("usuario.password")
    private List<String> password;

    public LoginModelState(List<String> login, List<String> password)
    {
        this.setLogin(login);
        this.setPassword(password);
    }

    public List<String> getLogin() {
        return login == null ? new ArrayList<String>() : login;
    }

    public void setLogin(List<String> login) {
        this.login = login;
    }

    public List<String> getPassword() {
        return password == null ? new ArrayList<String>() : password;
    }

    public void setPassword(List<String> password) {
        this.password = password;
    }
}
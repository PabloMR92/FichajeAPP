package com.example.tinyterm1.helloworld.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TinyTerm1 on 04/01/2017.
 */

public class GeoLocationModelState {

    @SerializedName("actualLocation.location")
    private List<String> location;

    public GeoLocationModelState(List<String> login)
    {
        this.setLocation(login);
    }

    public List<String> getLocation() {
        return location == null ? new ArrayList<String>() : location;
    }

    public void setLocation(List<String> login) {
        this.location = login;
    }

}

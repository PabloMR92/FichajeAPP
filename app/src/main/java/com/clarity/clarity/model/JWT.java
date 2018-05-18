package com.clarity.clarity.model;

import org.parceler.Parcel;

@Parcel
public class JWT {
    private String token;

    public JWT() {}

    public JWT(String token) {
        this.token = token;
    }

    public String getToken() { return token; }
}

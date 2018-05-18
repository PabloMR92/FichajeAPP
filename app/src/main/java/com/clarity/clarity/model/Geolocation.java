package com.clarity.clarity.model;

import org.parceler.Parcel;

@Parcel
public class Geolocation {
    private double coordenadaX;
    private double coordenadaY;
    private String timestamp;

    public Geolocation() {}

    public Geolocation(double coordenadaX, double coordenadaY, String timestamp) {
        this.coordenadaX = coordenadaX;
        this.coordenadaY = coordenadaY;
        this.timestamp = timestamp;
    }
}

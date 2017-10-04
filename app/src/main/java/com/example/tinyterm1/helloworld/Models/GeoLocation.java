package com.example.tinyterm1.helloworld.Models;

import java.text.SimpleDateFormat;

/**
 * Created by TinyTerm1 on 04/01/2017.
 */

public class GeoLocation {
    private double CoordenadaX;
    private double CoordenadaY;
    private String Timestamp;
    private String UUID;

    public double getCoordenadaX() {
        return CoordenadaX;
    }

    public void setCoordenadaX(double coordenadaX) {
        CoordenadaX = coordenadaX;
    }

    public double getCoordenadaY() {
        return CoordenadaY;
    }

    public void setCoordenadaY(double coordenadaY) {
        CoordenadaY = coordenadaY;
    }

    public String getTimeStamp() {
        return Timestamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.Timestamp = timeStamp;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }
}

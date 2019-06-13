package com.clarity.clarity.model;

import org.parceler.Parcel;

@Parcel
public class Configuration {

    private int clockInInterval;

    public Configuration() {}

    public Configuration(int ClockInInterval) {
        this.clockInInterval = ClockInInterval;
    }

    public int getClockInInterval() { return clockInInterval; }
}

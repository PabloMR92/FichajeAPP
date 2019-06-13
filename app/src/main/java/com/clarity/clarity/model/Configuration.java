package com.clarity.clarity.model;

import org.parceler.Parcel;

@Parcel
public class Configuration {

    private int ClockInInterval;

    public Configuration() {}

    public Configuration(int ClockInInterval) {
        this.ClockInInterval = ClockInInterval;
    }

    public int getClockInInterval() { return ClockInInterval; }
}

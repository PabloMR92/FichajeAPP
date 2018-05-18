package com.clarity.clarity.rx;

import org.parceler.Parcel;

@Parcel
public class RetryObj {
    private Throwable error;
    private int retries;

    public RetryObj() {}

    public RetryObj(Throwable error, int retries) {
        this.error = error;
        this.retries = retries;
    }

    public Throwable getError() { return error; }

    public int getRetries() { return retries; }

    public void setRetries(int newRetries) { this.retries = newRetries; }
}

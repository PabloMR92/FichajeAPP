package com.clarity.clarity.model;

import org.parceler.Parcel;

@Parcel
public class TokenInfo {
    private boolean isValid;

    public TokenInfo() {}

    public TokenInfo(boolean isValid) {
        this.isValid = isValid;
    }

    public boolean getIsValid() { return isValid; }
}

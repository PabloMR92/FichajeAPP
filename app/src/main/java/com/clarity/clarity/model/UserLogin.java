package com.clarity.clarity.model;

import org.parceler.Parcel;

@Parcel
public class UserLogin {
    private String username;
    private String password;
    private String dni;

    public UserLogin() {}

    public UserLogin(String username, String password, String dni) {
        this.username = username;
        this.password = password;
        this.dni = dni;
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getDni() { return dni; }
}

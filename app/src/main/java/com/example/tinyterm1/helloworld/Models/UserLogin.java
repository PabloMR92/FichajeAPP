package com.example.tinyterm1.helloworld.Models;

/**
 * Created by TinyTerm1 on 25/12/2016.
 */


public class UserLogin {
    private String login;
    private String password;
    private String dni;

    public UserLogin(String login, String password, String dni)
    {
        this.login = login;
        this.password = password;
        this.dni = dni;
    }

    public UserLogin(){}

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String userName) {
        this.login = login;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }
}

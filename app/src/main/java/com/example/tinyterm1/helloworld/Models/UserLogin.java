package com.example.tinyterm1.helloworld.Models;

/**
 * Created by TinyTerm1 on 25/12/2016.
 */


public class UserLogin {
    private String login;
    private String password;

    public UserLogin(String login, String password)
    {
        this.login = login;
        this.password = password;
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
}

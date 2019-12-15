package com.example.bean;

import java.io.Serializable;

public class UsersBean implements Serializable {
    String userName;
    String pass;

    public UsersBean() {
    }

    public UsersBean(String userName, String pass) {
        this.userName = userName;
        this.pass = pass;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}

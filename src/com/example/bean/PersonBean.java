package com.example.bean;

import java.io.Serializable;

public class PersonBean implements Serializable {
    String userName;
    String name;
    Integer age; // -1代表没有数据
    String teleno;

    public PersonBean() {
    }

    public PersonBean(String userName, String name, Integer age, String teleno) {
        this.userName = userName;
        this.name = name;
        this.age = age;
        this.teleno = teleno;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getTeleno() {
        if(teleno==null){
            return null;
        }
        return teleno;
    }

    public void setTeleno(String teleno) {
        this.teleno = teleno;
    }
}

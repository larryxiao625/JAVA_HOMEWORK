package com.example.service;

import com.example.bean.UsersBean;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;

public interface IUser {
    public Object getUsers();
    public Object getPersons();
    public Object writeUser(HttpServletRequest request);
    public Object writePerson(HttpServletRequest request);
    public Object deleteUser(HttpServletRequest request);
    public Object loginWithPwd(UsersBean requestBody);
    public Object register(HttpServletRequest request);
}

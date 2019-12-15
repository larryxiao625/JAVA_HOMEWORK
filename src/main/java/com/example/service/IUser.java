package main.java.com.example.service;

import javax.servlet.http.HttpServletRequest;

public interface IUser {
    public Object getUsers();
    public Object getPersons();
    public Object writeUser(HttpServletRequest request);
    public Object writePerson(HttpServletRequest request);
    public Object deleteUser(HttpServletRequest request);
}

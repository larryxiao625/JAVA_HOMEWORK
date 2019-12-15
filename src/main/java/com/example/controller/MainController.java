package com.example.controller;

import com.example.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin("*")
@RestController
@EnableAutoConfiguration
public class MainController {


    @Autowired
    UserServiceImpl userService;

    @RequestMapping("/")
    public String sayHello() {
        return "hello";
    }

    @RequestMapping(value = "/getUsers", method = RequestMethod.GET)
    public Object getUsers() {
        return userService.getUsers();
    }

    @RequestMapping(value = "/getPersons", method = RequestMethod.GET)
    public Object getPersons() {
        return userService.getPersons();
    }

    @RequestMapping(value = "/writeUser", method = RequestMethod.GET)
    public Object writeUser(HttpServletRequest request) {
        return userService.writeUser(request);
    }

    @RequestMapping(value = "/writePerson", method = RequestMethod.GET)
    public Object writePerson(HttpServletRequest request) {
        return userService.writePerson(request);
    }

    @RequestMapping(value = "/deleteUsers", method = RequestMethod.GET)
    public Object deleteUser(HttpServletRequest request) {
        return userService.deleteUser(request);
    }

}

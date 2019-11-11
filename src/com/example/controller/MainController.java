package com.example.controller;

import com.example.bean.PersonBean;
import com.example.bean.UsersBean;
import com.example.service.impl.UserServiceImpl;
import com.example.util.SqlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@CrossOrigin("*")
@RestController
@EnableAutoConfiguration
public class MainController {
    private static String showUsersTable = "select * from users";
    private static String showPersonTable = "select * from person";

    @Autowired
    UserServiceImpl userService;

    @RequestMapping("/")
    public String sayHello() {
        return "hello";
    }

    @RequestMapping(value = "/getUsers", method = RequestMethod.GET)
    public Object getUsers() {

        try {
            return SqlUtil.showTable(Objects.requireNonNull(SqlUtil.progressSql(showUsersTable, 1)));
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping(value = "/getPersons", method = RequestMethod.GET)
    public Object getPersons() {
        try {
            return SqlUtil.showTable(Objects.requireNonNull(SqlUtil.progressSql(showPersonTable, 1)));
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping(value = "/writeUser", method = RequestMethod.GET)
    public Object writeUser(HttpServletRequest request) {
        List<Object> usersList = new ArrayList<>();
        usersList.add(new UsersBean(request.getParameter("username"), request.getParameter("password")));
        SqlUtil.insertData(usersList);
        return "写入成功";
    }

    @RequestMapping(value = "/writePerson", method = RequestMethod.GET)
    public Object writePerson(HttpServletRequest request) {
        List<Object> personList = new ArrayList<>();
        personList.add(new PersonBean(request.getParameter("username"), request.getParameter("name"), Integer.valueOf(request.getParameter("age")), request.getParameter("")));
        SqlUtil.insertData(personList);
        return "写入成功"+request.getParameter("username");
    }

    @RequestMapping(value = "/deleteUsers", method = RequestMethod.GET)
    public Object deleteUser(HttpServletRequest request) {
        SqlUtil.deleteUser(request.getParameter("username"), 2);
        return "删除成功"+request.getParameter("username");
    }

}

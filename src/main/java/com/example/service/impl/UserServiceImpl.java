package com.example.service.impl;

import com.example.bean.PersonBean;
import com.example.bean.UsersBean;
import com.example.util.SqlUtil;
import main.java.com.example.service.IUser;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements IUser {
    private static String showUsersTable = "select * from users";
    private static String showPersonTable = "select * from person";
    @Override
    public Object getUsers() {
        try {
            return SqlUtil.showTable(Objects.requireNonNull(SqlUtil.progressSql(showUsersTable, 1)));
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Object getPersons() {
        try {
            return SqlUtil.showTable(Objects.requireNonNull(SqlUtil.progressSql(showPersonTable, 1)));
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Object writeUser(HttpServletRequest request) {
        List<Object> usersList = new ArrayList<>();
        usersList.add(new UsersBean(request.getParameter("username"), request.getParameter("password")));
        SqlUtil.insertData(usersList);
        return "写入成功";
    }

    @Override
    public Object writePerson(HttpServletRequest request) {
        List<Object> personList = new ArrayList<>();
        personList.add(new PersonBean(request.getParameter("username"), request.getParameter("name"), Integer.valueOf(request.getParameter("age")), request.getParameter("")));
        SqlUtil.insertData(personList);
        return "写入成功"+request.getParameter("username");
    }

    @Override
    public Object deleteUser(HttpServletRequest request) {
        SqlUtil.deleteUser(request.getParameter("username"), 2);
        return "删除成功"+request.getParameter("username");
    }
}

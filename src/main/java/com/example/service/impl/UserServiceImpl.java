package com.example.service.impl;

import com.example.bean.PersonBean;
import com.example.bean.UsersBean;
import com.example.config.ResponseData;
import com.example.service.IUser;
import com.example.util.MD5Utils;
import com.example.util.SqlUtil;
import org.apache.commons.codec.digest.Md5Crypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.UnsatisfiedServletRequestParameterException;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements IUser {
    private static String showUsersTable = "select * from users";
    private static String showPersonTable = "select * from person";

    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

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
        usersList.add(new UsersBean(request.getParameter("username"), MD5Utils.generate(request.getParameter("password"))));
        SqlUtil.insertData(usersList);
        return "写入成功";
    }

    @Override
    public Object writePerson(HttpServletRequest request) {
        List<Object> personList = new ArrayList<>();
        personList.add(new PersonBean(request.getParameter("username"), request.getParameter("name"), Integer.valueOf(request.getParameter("age")), request.getParameter("")));
        SqlUtil.insertData(personList);
        return "写入成功" + request.getParameter("username");
    }

    @Override
    public Object deleteUser(HttpServletRequest request) {
        SqlUtil.deleteUser(request.getParameter("username"), 2);
        return "删除成功" + request.getParameter("username");
    }

    @Override
    public Object loginWithPwd(UsersBean usersBean) {
        System.out.println(usersBean.getUserName());
        if (usersBean == null || usersBean.getUserName().isEmpty()) {
            return new ResponseData().error(201, null, "用户名不能为空");
        } else {
            ResultSet pwdResult = SqlUtil.getLoginPwd(usersBean.getUserName());
            try {
                if (pwdResult.next()) {
                    System.out.println(MD5Utils.verify(usersBean.getPass(), pwdResult.getString("pass")));
                    if (MD5Utils.verify(usersBean.getPass(), pwdResult.getString("pass"))) {
                        ResultSet userSet = SqlUtil.getUserResult(usersBean.getUserName());
                        return SqlUtil.showTable(userSet);
                    } else {
                        return new ResponseData().error(202, null, "密码错误");
                    }
                } else {
                    return new ResponseData().error(203, null, "用户不存在");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public Object register(HttpServletRequest request) {
        List<Object> usersList = new ArrayList<>();
        usersList.add(new UsersBean(request.getParameter("username"), MD5Utils.generate(request.getParameter("password"))));
        usersList.add(new PersonBean(request.getParameter("username"), request.getParameter("name"), Integer.valueOf(request.getParameter("age")), request.getParameter("teleno")));
        SqlUtil.insertData(usersList);
        return "写入成功";
    }
}

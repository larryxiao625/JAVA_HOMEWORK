package com.example.service.impl;

import com.example.bean.PersonBean;
import com.example.bean.UsersBean;
import com.example.service.IUser;
import com.example.util.SqlUtil;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements IUser {
    private static Connection connection;
    private static String showUsersTable = "select * from users";
    private static String showPersonTable = "select * from person";

    public static void main(String[] args) {
        connection = SqlUtil.getConnection();
        createDatabase();
        createTable();
        insertData();
        insertDataStep3();
        deleteData();
    }

    /**
     * 第0步：新建数据库操作
     */
    private static void createDatabase() {
        String createDatabase = "CREATE DATABASE test;";
        try {
            SqlUtil.progressSql(createDatabase, 1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 第一步建表操作
     */
    private static void createTable() {
        String createUsersSql = "CREATE TABLE users(" +
                "username VARCHAR(10) PRIMARY KEY," +
                "pass VARCHAR(8) NOT NULL" +
                ");";
        String createPersonSql = "CREATE TABLE person(" +
                "username VARCHAR(10) NOT NULL," +
                "name VARCHAR(20) PRIMARY KEY," +
                "age INT," +
                "teleno CHAR(11)" +
                ");";

        try {
            SqlUtil.progressSql(createUsersSql, 2);
            SqlUtil.progressSql(createPersonSql, 2);
            SqlUtil.showTable(Objects.requireNonNull(SqlUtil.progressSql(showUsersTable, 1)));
            SqlUtil.showTable(Objects.requireNonNull(SqlUtil.progressSql(showPersonTable, 1)));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * 第二部插入数据操作
     */
    private static void insertData() {
        List<Object> usersList = new ArrayList<>();
        List<Object> personList = new ArrayList<>();
        usersList.add(new UsersBean("ly", "123456"));
        usersList.add(new UsersBean("liming", "345678"));
        usersList.add(new UsersBean("test", "111111"));
        usersList.add(new UsersBean("test1", "123456"));
        personList.add(new PersonBean("ly", "雷力", -1, null));
        personList.add(new PersonBean("liming", "李明", 25, null));
        personList.add(new PersonBean("test", "测试用户", 20, "13388449933"));
        SqlUtil.insertData(usersList);
        SqlUtil.insertData(personList);
        try {
            SqlUtil.showTable(Objects.requireNonNull(SqlUtil.progressSql(showUsersTable, 1)));
            SqlUtil.showTable(Objects.requireNonNull(SqlUtil.progressSql(showPersonTable, 1)));
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    /**
     * 第三步插入数据
     */
    private static void insertDataStep3() {
        List<Object> personList = new ArrayList<>();
        personList.add(new PersonBean("ly", "王五", -1, null));
        personList.add(new PersonBean("test2", "测试用户2", 2, null));
        personList.add(new PersonBean("test1", "测试用户1", 33, null));
        personList.add(new PersonBean("test", "张三", 23, "18877009966"));
        personList.add(new PersonBean("admin", "admin", -1, null));
        SqlUtil.insertData(personList);
        try {
            SqlUtil.showTable(Objects.requireNonNull(SqlUtil.progressSql(showUsersTable, 1)));
            SqlUtil.showTable(Objects.requireNonNull(SqlUtil.progressSql(showPersonTable, 1)));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 第四步删除数据
     */
    private static void deleteData() {
        SqlUtil.deleteUser("test", 2);
        try {
            SqlUtil.showTable(Objects.requireNonNull(SqlUtil.progressSql(showUsersTable, 1)));
            SqlUtil.showTable(Objects.requireNonNull(SqlUtil.progressSql(showPersonTable, 1)));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

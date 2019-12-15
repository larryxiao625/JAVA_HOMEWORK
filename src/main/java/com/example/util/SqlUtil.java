package com.example.util;

import com.example.bean.PersonBean;
import com.example.bean.UsersBean;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

public class SqlUtil {
    private static Connection connection = null;
    private static Statement statement = null;
    private static String USERS_TABLE = "users";
    private static String PERSON_TABLE = "person";

    /**
     * 数据库连接操作
     * 单例模式
     *
     * @return 数据库Connection
     */
    public static Connection getConnection() {
        if (connection == null) {
            try {
                InputStream inputStream = SqlUtil.class.getClassLoader().getResourceAsStream("jdbc.properties");
                Properties properties = new Properties();
                properties.load(inputStream);
                Class.forName(properties.getProperty("connectionName")); // 注册jdbc驱动
                connection = DriverManager.getConnection(properties.getProperty("jdbcurl"), properties.getProperty("user"), properties.getProperty("password")); // 建立数据库链接
            } catch (ClassNotFoundException | SQLException | IOException e) {
                e.printStackTrace();
            }

        }
        return connection;
    }

    /**
     * 预留获取Statement方法
     *
     * @return Statement对象
     * @throws SQLException sql语句错误
     */
    public synchronized static Statement getStatement() throws SQLException {
        if (connection == null) {
            getConnection();
        }
        if (statement == null) {
            statement = connection.createStatement();
        }
        return statement;
    }

    /**
     * 操作数据库
     *
     * @param sql  sql语句
     * @param type 操作类型(1为有返回集,2为无返回集)
     */
    public synchronized static ResultSet progressSql(String sql, int type) throws SQLException {
        if (connection == null) {
            getConnection();
        }
        if (statement == null) {
            statement = connection.createStatement();
        }
        if (type == 1) {
            return statement.executeQuery(sql);
        } else if (type == 2) {
            statement.executeUpdate(sql);
            System.out.println("操作成功");
            return null;
        }
        return null;
    }

    /**
     * 展示表数据
     */
    public synchronized static JsonArray showTable(ResultSet rs) throws SQLException {
        JsonArray jsonElements = new JsonArray();
        ResultSetMetaData metaData = rs.getMetaData();
//        for (int i = 0; i < metaData.getColumnCount(); i++) {
//            String columnName = metaData.getColumnName(i + 1);
//            System.out.print(columnName + "\t");
//        }
        try {
            while (rs.next()) {
                JsonObject jsonObject = new JsonObject();
                for (int i = 0; i < metaData.getColumnCount(); i++) {
                    String columnName = metaData.getColumnLabel(i + 1);
                    String value = rs.getString(columnName);
                    jsonObject.addProperty(columnName, value);
                }
                jsonElements.add(jsonObject);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        return jsonElements;
    }

    /**
     * 检查是否数据存在表中
     *
     * @param dataBean
     * @return
     */
    public synchronized static boolean isFieldExist(Object dataBean) {
        String type = null;

        try {

            if (dataBean instanceof PersonBean) {
                PersonBean personBean = (PersonBean) dataBean;
                type = PERSON_TABLE;
                String selectSql = "select * from person WHERE username = ?;"; // 防止SQL注入
                PreparedStatement ps = getConnection().prepareStatement(selectSql);
                ps.setString(1, personBean.getUserName());
                if (ps.executeQuery().next()) {
                    return true;
                } else {
                    return false;
                }

            } else if (dataBean instanceof UsersBean) {
                UsersBean usersBean = (UsersBean) dataBean;
                type = PERSON_TABLE;
                String selectSql = "select * from users WHERE username = ?;"; // 防止SQL注入
                PreparedStatement ps = getConnection().prepareStatement(selectSql);
                ps.setString(1, usersBean.getUserName());
                System.out.println(ps.toString());
                if (ps.executeQuery().next()) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 插入数据操作
     */
    public synchronized static void insertData(List<Object> dataBeanList) {
        String type = null;
        String insertSql = null;
        for (Object dataBean : dataBeanList) {
            try {

                if (dataBean instanceof PersonBean) {
                    PersonBean personBean = (PersonBean) dataBean;
                    type = PERSON_TABLE;
                    if (isFieldExist(personBean)) {
                        insertSql = "UPDATE person " +
                                "SET name = ?,age = ?,teleno = ? " +
                                "WHERE username = ?;"; // 防止SQL注入
                        PreparedStatement ps = getConnection().prepareStatement(insertSql);
                        ps.setString(1, personBean.getName());
                        if (personBean.getAge() == -1) {
                            ps.setNull(2, Types.INTEGER);
                        } else {
                            ps.setInt(2, personBean.getAge());
                        }
                        ps.setString(3, String.valueOf(personBean.getTeleno()));
                        ps.setString(4, personBean.getUserName());
//                        System.out.println(ps.toString());
                        ps.executeUpdate();
                    } else {
                        List<Object> usersBeans = new ArrayList<>();
                        usersBeans.add(new UsersBean(personBean.getUserName(), "888888"));
                        insertData(usersBeans);
                        insertSql = "INSERT INTO person (username,name,age,teleno)" +
                                "VALUES" +
                                "(?,?,?,?);"; // 防止SQL注入
                        PreparedStatement ps = getConnection().prepareStatement(insertSql);
                        ps.setString(1, personBean.getUserName());
                        ps.setString(2, personBean.getName());
                        if (personBean.getAge() == -1) {
                            ps.setNull(3, Types.INTEGER);
                        } else {
                            ps.setInt(3, personBean.getAge());
                        }
                        ps.setString(4, personBean.getTeleno());
                        ps.executeUpdate();
                    }

                } else if (dataBean instanceof UsersBean) {
                    UsersBean usersBean = (UsersBean) dataBean;
                    type = PERSON_TABLE;
                    if (isFieldExist(usersBean)) {
                        return;
                    } else {
                        insertSql = "INSERT INTO users (username,pass)" +
                                "VALUES" +
                                "(?,?);"; // 防止SQL注入
                        PreparedStatement ps = getConnection().prepareStatement(insertSql);
                        ps.setString(1, usersBean.getUserName());
                        ps.setString(2, usersBean.getPass());
                        ps.executeUpdate();
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 删除数据操作
     *
     * @param userName 键名
     * @param type     删除类型(1:全匹配,2:半匹配)
     */
    public synchronized static void deleteUser(String userName, int type) {
        String deleteSql = null;
        PreparedStatement ps = null;
        try {
            if (type == 1) {
                deleteSql = "delete users,person from users LEFT JOIN person ON users.username=person.username where users.username = ?";
                ps = getConnection().prepareStatement(deleteSql);
                ps.setString(1, userName);
            } else if (type == 2) {
                deleteSql = "delete users,person from users LEFT JOIN person ON users.username=person.username where users.username like ?";
                ps = getConnection().prepareStatement(deleteSql);
                ps.setString(1, userName + "%");
            }
            Objects.requireNonNull(ps).executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

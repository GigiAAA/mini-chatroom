package com.bittech.Riri.chatroom.utils;

import java.sql.*;
import java.util.Properties;

/**
 * 封装JDBC操作的公共方法
 */
public class JDBCUtils {
    private static String driverName;
    private static String url;
    private static String userName;
    private static String password;

    //这四个属性只需加再一次即可，使用静态代码块实现
    static {
        Properties properties=CommUtils.loadProperties("db.properties");
        driverName=properties.getProperty("driverName");
        url=properties.getProperty("url");
        userName=properties.getProperty("userName");
        password=properties.getProperty("password");
        //1.加载驱动
        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            System.err.println("加载数据库驱动出错");
            e.printStackTrace();
        }
    }

    //获取数据库连接操作
    public static Connection getConnection(){
        try {
            return DriverManager.getConnection(url,userName,password);
        } catch (SQLException e) {
            System.err.println("获取数据库连接出错");
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 关闭数据库资源操作
     * @param connection
     * @param statement
     * @param resultSet
     */
    public static void closeResources(Connection connection, Statement statement, ResultSet resultSet){
        if(connection!=null){
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public static void closeResources(Connection connection,Statement statement){
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

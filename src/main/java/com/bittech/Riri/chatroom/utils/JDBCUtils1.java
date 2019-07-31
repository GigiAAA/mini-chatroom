package com.bittech.Riri.chatroom.utils;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.pool.DruidPooledConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * 基于DruidDataSource
 */
public class JDBCUtils1 {
    private static DruidDataSource dataSource;
    static {
        Properties properties=CommUtils.loadProperties("datasource.properties");
        try {
            //注册驱动
            dataSource= (DruidDataSource) DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            System.err.println("获取数据源失败");
            e.printStackTrace();
        }
    }
    public static DruidPooledConnection getConnection(){
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            System.err.println("获取连接失败");
            e.printStackTrace();
        }
        return null;
    }
    public static void closeResources(Connection connection, Statement statement){
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
    public static void closeResources(Connection connection, Statement statement, ResultSet resultSet){
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
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

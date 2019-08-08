package com.bittech.Riri.chatroom.dao;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.bittech.Riri.chatroom.utils.CommUtils;
import com.bittech.Riri.chatroom.utils.JDBCUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Properties;

/**
 * 封装基础操作,数据源、获取连接、关闭资源
 */
public class BaseDao {
    private static DataSource dataSource;
    static {
        //获取数据源
        Properties properties=CommUtils.loadProperties("datasource.properties");
        try {
            dataSource=DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            System.err.println("数据源加载失败");
        }
    }
    //获取数据库连接
    protected Connection getConnection(){
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            System.err.println("获取连接失败");
        }
        return null;
    }
    //关闭资源Statement ResultSet Connection
    protected void closeResources(Statement statement, Connection connection){
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    protected void closeResources(Statement statement, Connection connection,ResultSet resultSet){
        closeResources(statement,connection);
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

package com.bittech.Riri.chatroom.dao;

import com.bittech.Riri.chatroom.entity.User;
import org.apache.commons.codec.digest.DigestUtils;

import java.sql.*;

/**
 * 关于用户模块的dao层
 */
public class AccountDao extends BaseDao {
    //用户登陆 select
    public User userLogin(String userName,String password){
        Connection connection=null;
        PreparedStatement statement=null;
        ResultSet resultSet=null;
        User user=null;
        try {
            connection=getConnection();
            String sql=" select * from user where username= ? and password=? ";
            statement=connection.prepareStatement(sql);
            statement.setString(1,userName);
            statement.setString(2,DigestUtils.md5Hex(password));
            resultSet=statement.executeQuery();
            if(resultSet.next()){
                user=getUserInfo(resultSet);
            }
        }catch (Exception e){
            System.err.println("查询用户信息出错");
            e.printStackTrace();
        }finally {
            closeResources(statement,connection,resultSet);
        }
        return user;
    }
    //用户注册 insert
    public boolean userRegister(User user){
        String userName=user.getUserName();
        String password=user.getPassword();
        String picture=user.getPicture();
        Connection connection=null;
        PreparedStatement statement=null;
        boolean isSuccess=false;
        try{
            connection=getConnection();
            String  sql=" INSERT INTO chatroom_websocket.user(username, password,picture) VALUES (?,?,?) ";
            //返回主键受影响的行数
            statement=connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            statement.setString(1,userName);
            statement.setString(2,DigestUtils.md5Hex(password));
            statement.setString(3,picture);
            isSuccess=(statement.executeUpdate()==1);
        }catch (Exception e){
            System.err.println("用户注册失败");
            e.printStackTrace();
        }finally {
            closeResources(statement,connection);
        }
        return isSuccess;
    }
    ///将数据表信息封装到User类中
    public User getUserInfo(ResultSet resultSet) throws SQLException {
        User user=new User();
        user.setId(resultSet.getInt("id"));
        user.setUserName(resultSet.getString("username"));
        user.setPassword(resultSet.getString("password"));
        user.setPicture(resultSet.getString("picture"));
        return user;
    }
}

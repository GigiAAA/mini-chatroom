package com.bittech.Riri.chatroom.dao;

import com.bittech.Riri.chatroom.entity.User;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class AccountDaoTest {
    private AccountDao accountDao=new AccountDao();
    @Test
    public void userLogin() {
        User user=accountDao.userLogin("xiaozhuzhu","123");
        System.out.println(user);
        Assert.assertNotNull(user);
    }

    @Test
    public void userRegister() {
        User user=new User();
        user.setUserName("xiaozhuzhu");
        user.setPassword("123");
        user.setPicture("E:"+File.separator+"桌面背景"+File.separator+"3.png");
        boolean isSuccess=accountDao.userRegister(user);
        Assert.assertEquals(true,isSuccess);
    }
}
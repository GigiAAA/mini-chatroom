package com.bittech.Riri.chatroom.utils;

import org.junit.Assert;
import org.junit.Test;

import java.util.Properties;

import static org.junit.Assert.*;

public class CommUtilsTest {
    @Test
    public void loadProperties() {
        String fileName="db.properties";
        Properties properties=CommUtils.loadProperties(fileName);
        String url=properties.getProperty("url");
        Assert.assertNotNull(url);
    }
}
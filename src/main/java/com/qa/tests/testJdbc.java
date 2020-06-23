package com.qa.tests;


import com.qa.util.MysqlUtil;
import org.testng.annotations.Test;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

public class testJdbc {
    public static Connection conn=null;
    public static PreparedStatement statement=null;
    public static ResultSet resultSet=null;
    public static String driver=null;
    public static String url=null;
    public static String user=null;
    public static  String pwd=null;
    @Test
    public void testSelect2() throws IOException, ClassNotFoundException {

        InputStream is = new BufferedInputStream(new FileInputStream(System.getProperty("user.dir") + "/src/main/java/com/qa/config/config.properties"));
        // 3.创建一个properties对象
        Properties properties = new Properties();
        // 4.加载输入流
        properties.load(is);
        // 5.获取相关参数的值
        driver=properties.getProperty("driver");
        url=properties.getProperty("url");
        user=properties.getProperty("username");
        pwd=properties.getProperty("password");
        //加载驱动
        Class.forName(driver);
    }
}


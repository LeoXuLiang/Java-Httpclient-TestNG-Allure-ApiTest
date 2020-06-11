package com.qa.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.io.InputStream;
import java.util.Properties;

public class JDBCUtil {

    private static String driver;
    private static String url;
    private static String username;
    private static String password;

    /**
     * 静态代码块加载配置文件信息
     */
    static {
        try {
            // 1.通过当前类获取类加载器
//            ClassLoader classLoader = JDBCUtil.class.getClassLoader();
            // 2.通过类加载器的方法获得一个输入流
            InputStream is = new BufferedInputStream(new FileInputStream(System.getProperty("user.dir") + "/src/main/java/com/qa/config/config.properties"));
            // 3.创建一个properties对象
            Properties props = new Properties();
            // 4.加载输入流
            props.load(is);
            // 5.获取相关参数的值
            driver = props.getProperty("driver");
            url = props.getProperty("url");
            username = props.getProperty("username");
            password = props.getProperty("password");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取连接方法
     *
     * @return
     */
    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            System.out.println("驱动类库不能发现");
        } catch (SQLException e) {
            System.out.println("SQL异常");
        }
        return conn;
    }
    /**
     * 释放资源方法
     *
     * @param conn
     * @param pstmt
     * @param rs
     */
    public static void release(Connection conn, Statement pstmt, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
}

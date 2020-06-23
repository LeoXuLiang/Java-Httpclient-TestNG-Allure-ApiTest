package com.qa.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;


public class JdbcPz {

    public static Connection conn=null;
    public static PreparedStatement statement=null;
    public static ResultSet resultSet=null;
    public static String driver=null;
    public static String url=null;
    public static String user=null;
    public static  String pwd=null;
    /***
     * 加载配置文件  初始化
     */
    static {
        try {
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
            System.out.println("》》》 配置加载完成!");
            //加载驱动
            Class.forName(driver);
            System.out.println("》》》 驱动加载完成!\n");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    /***
     * 获得连接
     * @return
     */
    public static Connection getConnection(){
        Connection connection=null;
        System.out.println("✔  数据库已连接！\n");
        try {
            connection= DriverManager.getConnection(url, user, pwd);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    /***
     * 关闭连接  释放资源
     */
    public static void closeAll(){
        try {
            if (resultSet!=null){
                resultSet.close();
            }
            if(statement!=null){
                statement.close();
            }
            if (conn!=null){
                conn.close();
            }
            System.out.println("* 所有连接已关闭  ！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

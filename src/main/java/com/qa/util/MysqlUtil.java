package com.qa.util;


import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

    /**
     * Created by otote Cotter on 2018/9/11.
     */
    public class MysqlUtil {
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
                //加载驱动
                Class.forName(driver);
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
            try {
                connection=DriverManager.getConnection(url, user, pwd);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return connection;
        }


        /***
         * 获取操作数据库的对象
         * @param sql sql语句
         * @param ob   参数    可变
         * @return PreparedStatement
         */
        public static PreparedStatement getStatement(String sql,Object...ob){
            //加载驱动
            try {
                //创建连接对象
                conn= getConnection();
                //创建执行对象
                statement=conn.prepareStatement(sql);
                //如果有参数  则添加参数
                if (ob.length>0){
                    for(int i=0;i<ob.length;i++){
                        statement.setObject(i+1, ob[i]);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return statement;
        }

        /***
         * 查询
         * 返回查询的结果集合
         * @param sql sql语句
         * @param ob   可变参数
         * @return ResultSet结果集合
         */
        public static ResultSet mySelect(String sql,Object...ob){
            PreparedStatement statement=getStatement(sql, ob);
            try {
                resultSet=statement.executeQuery();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            System.out.println("OK");
            return resultSet;
        }

        /****
         * 对数据库的增、删、改
         * @param sql sql语句
         * @param ob   可变参数
         * @return 操作完成的sql语句数量
         */
        public static int myUpdate(String sql,Object...ob){
            PreparedStatement statement=getStatement(sql, ob);
            //执行成功的条数
            int count=0;
            try {
                count=statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return count;
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
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


package com.qa.tests;

import com.qa.util.JDBCUtil;
import java.sql.*;

public class testJdbc2 {

    public static void main(String[] args) throws SQLException {
        Connection conn = JDBCUtil.getConnection();
        Statement stmt  =  null;
        ResultSet rs = null;
        String sqlResult = null;
        stmt = conn.createStatement() ;    // 实例化Statement对象
        rs = stmt.executeQuery("SELECT code FROM `mobile_validate` WHERE mobile = '18900000001';");    // 执行数据库更新操作
        //展开结果集数据库，获得数据行数 count
        int count = 0;
        while(rs.next()){
            count = count + 1;
        }
        System.out.println("count = " + count);

        //展开结果集数据库，获得表中列数 line
        int line = rs.getMetaData().getColumnCount();
        System.out.println("line = " + line);

        while(rs.next()) {//遍历行，next()方法返回值为Boolean，当存在数据行返回true,反之false
            for(int i = 0; i <= line; i++) { //遍历列
                sqlResult = rs.getString(i);
            }
            System.out.println();
        }
        JDBCUtil.release(conn,stmt,rs);// 关闭操作

        System.out.println("sqlResult : " +sqlResult);
//        System.out.println(getDataList("SELECT code FROM `mobile_validate` WHERE mobile = '18900000001';"));
    }
}
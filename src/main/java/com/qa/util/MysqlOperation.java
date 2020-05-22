package com.qa.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MysqlOperation {
    private static final String[] mysqlmessage = Mysqlread.message;

    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName(mysqlmessage[0]);
            conn = DriverManager.getConnection(mysqlmessage[1], mysqlmessage[2], mysqlmessage[3]);
        } catch (ClassNotFoundException e) {
            System.out.println("驱动类库不能发现");
        } catch (SQLException e) {
            System.out.println("SQL异常");
        }
        return conn;
    }

    public static void close(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println("SQL异常");
            }
        }
    }

    public static void close(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            statement = null;
        }
    }

    public static void close(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            rs = null;
        }
    }
}


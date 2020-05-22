package com.qa.tests;

import com.qa.util.MysqlOperation;
import org.testng.annotations.Test;
import java.sql.Connection;

public class TestPropertisread {
    @Test
    public void test() {
        Connection conn = MysqlOperation.getConnection();
        System.out.println(conn);
        MysqlOperation.close(conn);
    }
}
package com.qa.tests;


import com.qa.util.Xt_Operate;

import java.util.List;
import java.util.Map;

public class testJdbc2 extends Xt_Operate {

    public static void main(String[] args) {

       /* MysqlUtil mysqlUtil = new MysqlUtil();
        System.out.println(mysqlUtil.mySelect("SELECT `name` FROM `user` WHERE login_name LIKE '1990000000%';"));
        mysqlUtil.closeAll();
    }*/
        LoadJdbc();
        //查询数据并返回查询结果
        afferentSQL("SELECT NAME FROM `user` WHERE login_name = '19900000002';");
        //SelectXT（）使用list接收返回结果
        List<Object> usersa = SelectXT();

        //数据读取示例
        //获取第一条数据
        Map<String, Object> rowData = (Map<String, Object>)usersa.get(0);
        //获取第一条数据的name字段
        rowData.get("name");

        System.out.println("第1条数据的name字段："+rowData.get("name"));



    }
}


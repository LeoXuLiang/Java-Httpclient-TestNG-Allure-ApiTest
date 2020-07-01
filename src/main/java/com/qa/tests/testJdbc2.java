package com.qa.tests;


import com.qa.util.Xt_Operate;
import java.util.List;
import java.util.Map;

public class testJdbc2 extends Xt_Operate {

    public static void main(String[] args) {

        LoadJdbc();
        //查询数据并返回查询结果
        afferentSQL("SELECT NAME FROM `user` WHERE login_name like '1990000000%';");
        //SelectXT（）使用list接收返回结果
        List<Object> usersa = SelectXT();

        //数据读取示例
        //获取第一条数据
        Map<String, Object> rowData = (Map<String, Object>)usersa.get(1);
        //获取第一条数据的name字段
        rowData.get("name");

        System.out.println("第1条数据的name字段："+rowData.get("name"));



    }
}

